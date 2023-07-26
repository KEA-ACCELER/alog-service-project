package kea.alog.project.domain.projectMember.controller;

import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.dto.ResponseDto;
import kea.alog.project.domain.projectMember.dto.response.ProjectMemberDto;
import kea.alog.project.domain.projectMember.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects/{projectPk}/members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping("")
    public ResponseDto<PageDto<ProjectMemberDto>> findAll(
        @PathVariable("projectPk") Long projectPk,
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam("page") int page,
        @RequestParam("size") int size
    ) {
        return ResponseDto.success(200,
            projectMemberService.findAll(projectPk, keyword, page, size));
    }
}
