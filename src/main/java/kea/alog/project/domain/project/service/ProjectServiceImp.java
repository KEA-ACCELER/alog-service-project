package kea.alog.project.domain.project.service;

import kea.alog.project.common.constant.Status;
import kea.alog.project.common.exception.EntityNotFoundException;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.project.repository.ProjectRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ProjectServiceImp implements ProjectService {

    private ProjectRepository projectRepository;

    @Override
    public Project findByPk(Long projectPk) {
        return projectRepository.findByPkAndStatus(projectPk, Status.NORMAL)
                                .orElseThrow(() -> new EntityNotFoundException("ENTITY_NOT_FOUND"));
    }
}
