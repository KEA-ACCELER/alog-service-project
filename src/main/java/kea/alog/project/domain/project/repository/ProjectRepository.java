package kea.alog.project.domain.project.repository;

import java.util.Optional;
import kea.alog.project.common.constant.Status;
import kea.alog.project.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByPkAndStatus(Long projectPk, Status status);
}
