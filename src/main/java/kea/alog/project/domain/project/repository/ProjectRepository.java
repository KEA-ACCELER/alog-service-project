package kea.alog.project.domain.project.repository;

import java.util.Optional;
import kea.alog.project.common.constant.Status;
import kea.alog.project.domain.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByPkAndStatus(Long projectPk, Status status);

    Page<Project> findAllByStatus(Status status, Pageable pageable);

    Page<Project> findAllByNameContainingOrDescriptionContainingAndStatus(String name,
        String description,
        Status status, Pageable pageable);

    Page<Project> findByProjectMembersUserPkAndProjectMembersStatusAndNameContaining(Long userPk,
        Status status,
        String keyword,
        Pageable pageable);
}
