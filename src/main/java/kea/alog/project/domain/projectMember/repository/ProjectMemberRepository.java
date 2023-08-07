package kea.alog.project.domain.projectMember.repository;

import java.util.Optional;
import kea.alog.project.common.constant.Status;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    Page<ProjectMember> findAllByProjectPkAndStatus(Long projectPk, Status status,
        Pageable pageable);

    Optional<ProjectMember> findByProjectPkAndUserPkAndStatus(Long projectPk, Long userPk,
        Status status);
}
