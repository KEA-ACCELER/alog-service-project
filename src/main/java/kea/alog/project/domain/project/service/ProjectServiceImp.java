package kea.alog.project.domain.project.service;

import java.util.List;
import java.util.stream.Collectors;
import kea.alog.project.common.constant.Status;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.exception.EntityNotFoundException;
import kea.alog.project.domain.project.constant.ProjectSortType;
import kea.alog.project.domain.project.dto.request.CreateProjectRequestDto;
import kea.alog.project.domain.project.dto.request.UpdateProjectRequestDto;
import kea.alog.project.domain.project.dto.response.MyProjectDto;
import kea.alog.project.domain.project.dto.response.ProjectDto;
import kea.alog.project.domain.project.dto.response.ProjectPkResponseDto;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.project.mapper.ProjectMapper;
import kea.alog.project.domain.project.repository.ProjectRepository;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
import kea.alog.project.domain.projectMember.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Project findByPk(Long projectPk) {
        return projectRepository.findByPkAndStatus(projectPk, Status.NORMAL)
            .orElseThrow(
                () -> new EntityNotFoundException(404, "ENTITY_NOT_FOUND"));
    }

    @Override
    public ProjectDto findOne(Long projectPk) {
        return projectMapper.projectToDto(findByPk(projectPk));
    }

    @Transactional
    @Override
    public ProjectPkResponseDto create(Long userPk,
        CreateProjectRequestDto createProjectRequestDto) {
        Project project = Project.builder().name(createProjectRequestDto.getName())
            .description(createProjectRequestDto.getDescription())
            .teamPk(createProjectRequestDto.getTeamPk())
            .pmPk(userPk).build();

        Long projectPk = projectRepository.save(project).getPk();

        ProjectMember projectMember = ProjectMember.builder().userPk(userPk).project(project)
            .build();
        projectMemberRepository.save(projectMember);
        return ProjectPkResponseDto.builder().projectPk(projectPk).build();
    }

    @Override
    public PageDto<ProjectDto> findAll(String keyword, ProjectSortType sortType, int page,
        int size) {
        Sort sort = getSort(sortType);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Project> projectPage =
            keyword == null ? projectRepository.findAllByStatus(Status.NORMAL, pageable) :
                projectRepository.findAllByNameContainingOrDescriptionContainingAndStatus(
                    keyword,
                    keyword,
                    Status.NORMAL, pageable);

        return PageDto.<ProjectDto>builder()
            .content(projectPage.getContent().stream().map(projectMapper::projectToDto)
                .collect(
                    Collectors.toList()))
            .totalPages(projectPage.getTotalPages())
            .totalElements(projectPage.getTotalElements())
            .pageNumber(projectPage.getNumber())
            .pageSize(projectPage.getSize())
            .build();
    }

    @Transactional
    @Override
    public void update(Long projectPk,
        UpdateProjectRequestDto updateProjectRequestDto) {
        Project project = findByPk(projectPk);

        projectMapper.updateProjectFromDto(updateProjectRequestDto, project);
    }

    @Transactional
    @Override
    public void delete(Long projectPk) {
        Project project = findByPk(projectPk);
        project.setStatus(Status.DELETED);
        projectRepository.save(project);
    }

    @Override
    public PageDto<MyProjectDto> findMine(Long userPk, String keyword, ProjectSortType sortType,
        int page, int size, Long teamPk) {
        Sort sort = getSort(sortType);
        Pageable pageable = PageRequest.of(page, size, sort);

        return teamPk == null ? findMineAll(userPk, keyword, pageable)
            : findMineByTeamPk(teamPk, pageable, keyword);
    }

    private Sort getSort(ProjectSortType sortType) {
        return switch (sortType) {
            case ASC -> Sort.by(Direction.ASC, "createdAt");
            case DESC -> Sort.by(Direction.DESC, "createdAt");
        };
    }

    private PageDto<MyProjectDto> findMineAll(Long userPk, String keyword, Pageable pageable) {
        return keyword == null ? findMineAllWithoutKeyword(userPk, pageable)
            : findMineAllWithKeyword(userPk, keyword, pageable);
    }

    private PageDto<MyProjectDto> findMineAllWithoutKeyword(Long userPk, Pageable pageable) {
        Page<ProjectMember> projectMemberPage = projectMemberRepository.findByUserPkAndStatusAndProjectStatus(
            userPk, Status.NORMAL, Status.NORMAL, pageable);

        List<Project> projects = projectMemberPage.getContent().stream()
            .map(projectMember -> projectMember.getProject())
            .toList();

        return PageDto.<MyProjectDto>builder()
            .content(
                projects.stream().map(projectMapper::projectToMyProjectDto)
                    .collect(Collectors.toList()))
            .totalPages(projectMemberPage.getTotalPages())
            .totalElements(projectMemberPage.getTotalElements())
            .pageNumber(projectMemberPage.getNumber())
            .pageSize(projectMemberPage.getSize())
            .build();
    }

    private PageDto<MyProjectDto> findMineAllWithKeyword(Long userPk, String keyword,
        Pageable pageable) {
        Page<Project> projectPage = projectRepository.findByProjectMembersUserPkAndProjectMembersStatusAndNameContaining(
            userPk,
            Status.NORMAL, keyword, pageable);

        return PageDto.<MyProjectDto>builder()
            .content(
                projectPage.getContent().stream()
                    .map(projectMapper::projectToMyProjectDto)
                    .collect(Collectors.toList()))
            .totalPages(projectPage.getTotalPages())
            .totalElements(projectPage.getTotalElements())
            .pageNumber(projectPage.getNumber())
            .pageSize(projectPage.getSize())
            .build();
    }

    private PageDto<MyProjectDto> findMineByTeamPk(Long teamPk, Pageable pageable,
        String keyword) {
        Page<Project> projectPage =
            keyword == null ? projectRepository.findByTeamPkAndStatus(teamPk, Status.NORMAL,
                pageable)
                : projectRepository.findByTeamPkAndStatusAndNameContaining(teamPk, Status.NORMAL,
                    keyword,
                    pageable);

        return PageDto.<MyProjectDto>builder()
            .content(projectPage.getContent().stream().map(projectMapper::projectToMyProjectDto)
                .collect(Collectors.toList()))
            .totalPages(projectPage.getTotalPages())
            .totalElements(projectPage.getTotalElements())
            .pageNumber(projectPage.getNumber())
            .pageSize(projectPage.getSize())
            .build();
    }
}
