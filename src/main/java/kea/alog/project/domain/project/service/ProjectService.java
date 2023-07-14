package kea.alog.project.domain.project.service;

import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.project.constant.ProjectSortType;
import kea.alog.project.domain.project.dto.request.CreateProjectRequestDto;
import kea.alog.project.domain.project.dto.request.UpdateProjectRequestDto;
import kea.alog.project.domain.project.dto.response.ProjectDto;
import kea.alog.project.domain.project.dto.response.ProjectPkResponseDto;
import kea.alog.project.domain.project.entity.Project;

public interface ProjectService {

    Project findByPk(Long projectPk);

    ProjectDto findOne(Long projectPk);

    ProjectPkResponseDto create(CreateProjectRequestDto createProjectRequestDto);

    PageDto<ProjectDto> findAll(String keyword, ProjectSortType sortType, int page,
        int size);

    ProjectPkResponseDto update(Long projectPk, UpdateProjectRequestDto updateProjectRequestDto);
}
