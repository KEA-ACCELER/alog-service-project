package kea.alog.project.domain.project.service;

import kea.alog.project.common.constant.Status;
import kea.alog.project.common.exception.EntityNotFoundException;
import kea.alog.project.domain.project.dto.request.CreateProjectRequestDto;
import kea.alog.project.domain.project.dto.response.CreateProjectResponseDto;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project findByPk(Long projectPk) {
        return projectRepository.findByPkAndStatus(projectPk, Status.NORMAL)
                                .orElseThrow(() -> new EntityNotFoundException("ENTITY_NOT_FOUND"));
    }

    @Override
    public CreateProjectResponseDto create(CreateProjectRequestDto createProjectRequestDto) {
        Project project = Project.builder().name(createProjectRequestDto.getName())
                                 .description(createProjectRequestDto.getDescription())
                                 .teamPk(createProjectRequestDto.getTeamPk())
                                 .pmPk(createProjectRequestDto.getPmPk()).build();

        Long projectPk = projectRepository.save(project).getPk();

        return CreateProjectResponseDto.builder().projectPk(projectPk).build();
    }
}
