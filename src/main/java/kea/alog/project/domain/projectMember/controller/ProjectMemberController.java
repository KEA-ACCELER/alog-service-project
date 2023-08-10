package kea.alog.project.domain.projectMember.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.dto.ResponseDto;
import kea.alog.project.domain.projectMember.dto.request.ProjectMemberRequestDto;
import kea.alog.project.domain.projectMember.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "project-member", description = "프로젝트 멤버 API")
@RestController
@RequestMapping("/api/projects/{projectPk}/members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @Operation(summary = "프로젝트 멤버 전체 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 request", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 pk", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("")
    public ResponseDto<PageDto<Long>> findAll(
        @PathVariable("projectPk") Long projectPk,
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam("page") int page,
        @RequestParam("size") int size
    ) {
        return ResponseDto.success(200,
            projectMemberService.findAll(projectPk, keyword, page, size));
    }

    @Operation(summary = "프로젝트 멤버 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공"),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 pk", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping("")
    public ResponseDto join(@PathVariable("projectPk") Long projectPk,
        @RequestBody() ProjectMemberRequestDto projectMemberRequestDto) {
        projectMemberService.join(projectPk, projectMemberRequestDto);
        return ResponseDto.success(201);
    }

    @Operation(summary = "프로젝트 멤버 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공"),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 pk", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("")
    public ResponseDto delete(@PathVariable("projectPk") Long projectPk,
        @RequestBody() ProjectMemberRequestDto projectMemberRequestDto) {
        projectMemberService.remove(projectPk, projectMemberRequestDto);
        return ResponseDto.success(204);
    }
}
