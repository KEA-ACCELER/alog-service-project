package kea.alog.project.domain.project.service;

import java.util.stream.Collectors;
import kea.alog.project.common.constant.Status;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.exception.EntityNotFoundException;
import kea.alog.project.domain.project.constant.ProjectSortType;
import kea.alog.project.domain.project.dto.request.CreateProjectRequestDto;
import kea.alog.project.domain.project.dto.request.UpdateProjectRequestDto;
import kea.alog.project.domain.project.dto.response.ProjectDto;
import kea.alog.project.domain.project.dto.response.ProjectPkResponseDto;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.project.mapper.ProjectMapper;
import kea.alog.project.domain.project.repository.ProjectRepository;
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
    public ProjectPkResponseDto create(CreateProjectRequestDto createProjectRequestDto) {
        Project project = Project.builder().name(createProjectRequestDto.getName())
                                 .description(createProjectRequestDto.getDescription())
                                 .teamPk(createProjectRequestDto.getTeamPk())
                                 .pmPk(createProjectRequestDto.getPmPk()).build();

        Long projectPk = projectRepository.save(project).getPk();

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

    private Sort getSort(ProjectSortType sortType) {
        return switch (sortType) {
            case ASC -> Sort.by(Direction.ASC, "createdAt");
            case DESC -> Sort.by(Direction.DESC, "createdAt");
        };
    }
}
