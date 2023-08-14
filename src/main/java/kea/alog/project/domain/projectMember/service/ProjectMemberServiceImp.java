package kea.alog.project.domain.projectMember.service;

import java.util.Optional;
import java.util.stream.Collectors;
import kea.alog.project.common.constant.Status;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.openFeign.NoticeFeign;
import kea.alog.project.common.openFeign.dto.request.CreateNoticeRequestDto;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.project.service.ProjectService;
import kea.alog.project.domain.projectMember.dto.request.ProjectMemberRequestDto;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
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
    private final NoticeFeign noticeFeign;

    @Override
    public PageDto<Long> findAll(Long projectPk, String keyword, int page,
        int size) {

        projectService.findByPk(projectPk);
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectMember> projectMemberPage = projectMemberRepository.findAllByProjectPkAndStatus(
            projectPk, Status.NORMAL, pageable);

        return PageDto.<Long>builder()
            .content(projectMemberPage.getContent().stream()
                .map(ProjectMember::getUserPk)
                .collect(Collectors.toList()))
            .totalPages(projectMemberPage.getTotalPages())
            .totalElements(projectMemberPage.getTotalElements())
            .pageNumber(projectMemberPage.getNumber())
            .pageSize(projectMemberPage.getSize())
            .build();
    }

    @Override
    @Transactional
    public void join(Long projectPk, ProjectMemberRequestDto projectMemberRequestDto) {
        Project project = projectService.findByPk(projectPk);
        
        for (Long userPk : projectMemberRequestDto.getUserPks()) {
            if (projectMemberRepository.findByProjectPkAndUserPkAndStatus(projectPk, userPk,
                Status.NORMAL).isEmpty()) {
                ProjectMember projectMember = ProjectMember.builder().project(project)
                    .userPk(userPk)
                    .build();
                projectMemberRepository.save(projectMember);

                String message = String.format("'$s'에 초대되었습니다.", project.getName());
                noticeFeign.create(
                    CreateNoticeRequestDto.builder().userPk(userPk).MsgContent(message).build());
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
