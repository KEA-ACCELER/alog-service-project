package kea.alog.project.domain.project.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.dto.ResponseDto;
import kea.alog.project.common.dto.TokenPayloadDto;
import kea.alog.project.domain.project.constant.ProjectSortType;
import kea.alog.project.domain.project.dto.request.CreateProjectRequestDto;
import kea.alog.project.domain.project.dto.request.UpdateProjectRequestDto;
import kea.alog.project.domain.project.dto.response.MyProjectDto;
import kea.alog.project.domain.project.dto.response.ProjectDto;
import kea.alog.project.domain.project.dto.response.ProjectPkResponseDto;
import kea.alog.project.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "projects", description = "프로젝트 API")
@RequestMapping("/api/projects")
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "프로젝트 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공"),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 request", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping()
    public ResponseDto<ProjectPkResponseDto> create(
        HttpServletRequest request,
        @Valid @RequestBody CreateProjectRequestDto createProjectRequestDto) {
        TokenPayloadDto userInfo = (TokenPayloadDto) request.getAttribute("user");
        return ResponseDto.success(201,
            projectService.create(userInfo.getUserPk(), createProjectRequestDto));
    }

    @Operation(summary = "프로젝트 전체 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 request", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping()
    public ResponseDto<PageDto<ProjectDto>> findAll(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam("sortType") ProjectSortType sortType, @RequestParam("page") int page,
        @RequestParam("size") int size) {
        return ResponseDto.success(200, projectService.findAll(keyword, sortType, page, size));
    }

    @Operation(summary = "프로젝트 상세 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "유효하지 않은 request", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 pk", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/{projectPk}")
    public ResponseDto<ProjectDto> findOne(@PathVariable("projectPk") Long projectPk) {
        return ResponseDto.success(200, projectService.findOne(projectPk));
    }

    @Operation(summary = "프로젝트 수정")
    @PatchMapping("/{projectPk}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 pk", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    public ResponseDto update(
        @PathVariable("projectPk") Long projectPk,
        @Valid @RequestBody UpdateProjectRequestDto updateProjectRequestDto
    ) {
        projectService.update(projectPk, updateProjectRequestDto);
        return ResponseDto.success(200);
    }

    @Operation(summary = "프로젝트 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "성공"),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트 pk", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/{projectPk}")
    public ResponseDto delete(@PathVariable("projectPk") Long projectPk) {
        projectService.delete(projectPk);
        return ResponseDto.success(204);
    }


    @Operation(summary = "내가 속한 프로젝트 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("mine")
    public ResponseDto<PageDto<MyProjectDto>> findMine(HttpServletRequest request,
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam("sortType") ProjectSortType sortType, @RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam(value = "teamPk", required = false) Long teamPk) {
        TokenPayloadDto userInfo = (TokenPayloadDto) request.getAttribute("user");
        return ResponseDto.success(200,
            projectService.findMine(userInfo.getUserPk(), keyword, sortType, page, size, teamPk));
    }
}
