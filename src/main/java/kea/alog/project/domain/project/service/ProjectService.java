package kea.alog.project.domain.project.service;

import kea.alog.project.domain.project.entity.Project;

public interface ProjectService {

    Project findByPk(Long projectPk);
}
