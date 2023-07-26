package kea.alog.project.domain.projectMember.service;

import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.projectMember.dto.response.ProjectMemberResponseDto;

public interface ProjectMemberService {

    PageDto<ProjectMemberResponseDto> findAll(Long projectPk, String keyword, int page, int size);
}
