package kea.alog.project.domain.project.controller;


import jakarta.validation.Valid;
import kea.alog.project.common.dto.ResponseDto;
import kea.alog.project.domain.project.dto.request.CreateProjectRequestDto;
import kea.alog.project.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/projects")
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping()
    public ResponseDto create(@Valid @RequestBody CreateProjectRequestDto createProjectRequestDto) {
        return ResponseDto.success(201, projectService.create(createProjectRequestDto));
    }
}
