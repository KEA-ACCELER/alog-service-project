package kea.alog.project.domain.projectMember.service;

import java.util.stream.Collectors;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.projectMember.dto.response.ProjectMemberDto;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
import kea.alog.project.domain.projectMember.mapper.ProjectMemberMapper;
import kea.alog.project.domain.projectMember.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImp implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberMapper projectMemberMapper;

    @Override
    public PageDto<ProjectMemberDto> findAll(Long projectPk, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectMember> projectMemberPage = projectMemberRepository.findAllByProjectPk(
            projectPk, pageable);

        return PageDto.<ProjectMemberDto>builder()
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
}
