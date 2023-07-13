package kea.alog.project.domain.project.service;

import kea.alog.project.domain.project.dto.request.CreateProjectRequestDto;
import kea.alog.project.domain.project.dto.response.CreateProjectResponseDto;
import kea.alog.project.domain.project.entity.Project;

public interface ProjectService {

    Project findByPk(Long projectPk);

    CreateProjectResponseDto create(CreateProjectRequestDto createProjectRequestDto);
}
