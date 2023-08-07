package kea.alog.project.domain.projectMember.service;

import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.projectMember.dto.request.ProjectMemberRequestDto;

public interface ProjectMemberService {

    PageDto<Long> findAll(Long projectPk, String keyword, int page, int size);

    void join(Long projectPk, ProjectMemberRequestDto ProjectMemberRequestDto);

    void remove(Long projectPk, ProjectMemberRequestDto projectMemberRequestDto);
}
