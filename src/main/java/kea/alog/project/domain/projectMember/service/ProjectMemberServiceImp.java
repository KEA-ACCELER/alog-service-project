package kea.alog.project.domain.projectMember.service;

import java.util.Optional;
import java.util.stream.Collectors;
import kea.alog.project.common.constant.Status;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.project.service.ProjectService;
import kea.alog.project.domain.projectMember.dto.request.ProjectMemberRequestDto;
import kea.alog.project.domain.projectMember.dto.response.ProjectMemberResponseDto;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
import kea.alog.project.domain.projectMember.mapper.ProjectMemberMapper;
import kea.alog.project.domain.projectMember.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImp implements ProjectMemberService {

    private final ProjectService projectService;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberMapper projectMemberMapper;

    @Override
    public PageDto<ProjectMemberResponseDto> findAll(Long projectPk, String keyword, int page,
        int size) {

        projectService.findByPk(projectPk);
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectMember> projectMemberPage = projectMemberRepository.findAllByProjectPkAndStatus(
            projectPk, Status.NORMAL, pageable);

        return PageDto.<ProjectMemberResponseDto>builder()
                      .content(projectMemberPage.getContent().stream()
                                                .map(projectMemberMapper::projectMemberToDto)
                                                .collect(
                                                    Collectors.toList()))
                      .totalPages(projectMemberPage.getTotalPages())
                      .totalElements(projectMemberPage.getTotalElements())
                      .pageNumber(projectMemberPage.getNumber())
                      .pageSize(projectMemberPage.getSize())
                      .build();
    }

    @Override
    @Transactional
    public void join(Long projectPk, ProjectMemberRequestDto projectMemberRequestDto) {
        projectService.findByPk(projectPk);

        Project project = projectService.findByPk(projectPk);
        for (Long userPk : projectMemberRequestDto.getUserPks()) {
            if (projectMemberRepository.findByProjectPkAndUserPkAndStatus(projectPk, userPk,
                Status.NORMAL).isEmpty()) {
                ProjectMember projectMember = ProjectMember.builder().project(project)
                                                           .userPk(userPk)
                                                           .build();
                projectMemberRepository.save(projectMember);
            }
        }
    }

    @Override
    @Transactional
    public void remove(Long projectPk, ProjectMemberRequestDto projectMemberRequestDto) {
        projectService.findByPk(projectPk);

        for (Long userPk : projectMemberRequestDto.getUserPks()) {
            Optional<ProjectMember> projectMember = projectMemberRepository.findByProjectPkAndUserPkAndStatus(
                projectPk, userPk, Status.NORMAL);
            if (projectMember.isPresent()) {
                projectMember.get().setStatus(Status.DELETED);
            }
        }
    }
}
