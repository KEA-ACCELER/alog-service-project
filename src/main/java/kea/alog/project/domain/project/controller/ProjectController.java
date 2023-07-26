package kea.alog.project.domain.project.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.dto.ResponseDto;
import kea.alog.project.domain.project.constant.ProjectSortType;
import kea.alog.project.domain.project.dto.request.CreateProjectRequestDto;
import kea.alog.project.domain.project.dto.request.UpdateProjectRequestDto;
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
    @PostMapping()
    public ResponseDto<ProjectPkResponseDto> create(
        @Valid @RequestBody CreateProjectRequestDto createProjectRequestDto) {
        return ResponseDto.success(201, projectService.create(createProjectRequestDto));
    }

    @Operation(summary = "프로젝트 전체 조회")
    @GetMapping()
    public ResponseDto<PageDto<ProjectDto>> findAll(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam("sortType") ProjectSortType sortType, @RequestParam("page") int page,
        @RequestParam("size") int size) {
        return ResponseDto.success(200, projectService.findAll(keyword, sortType, page, size));
    }

    @Operation(summary = "프로젝트 상세 조회")
    @GetMapping("/{projectPk}")
    public ResponseDto<ProjectDto> findOne(@PathVariable("projectPk") Long projectPk) {
        return ResponseDto.success(200, projectService.findOne(projectPk));
    }

    @Operation(summary = "프로젝트 수정")
    @PatchMapping("/{projectPk}")
    public ResponseDto<ProjectPkResponseDto> update(
        @PathVariable("projectPk") Long projectPk,
        @Valid @RequestBody UpdateProjectRequestDto updateProjectRequestDto
    ) {
        return ResponseDto.success(200, projectService.update(projectPk, updateProjectRequestDto));
    }

    @Operation(summary = "프로젝트 삭제")
    @DeleteMapping("/{projectPk}")
    public ResponseDto<ProjectPkResponseDto> delete(@PathVariable("projectPk") Long projectPk) {
        return ResponseDto.success(200, projectService.delete(projectPk));
    }
}
