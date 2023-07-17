package kea.alog.project.domain.topic.repository;

import java.util.Optional;
import kea.alog.project.common.constant.Status;
import kea.alog.project.domain.topic.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAllByProjectPkAndStatusAndProjectStatus(Long projectPk, Status status,
        Status projectStatus, Pageable pageable);

    Page<Topic> findByNameContainingOrDescriptionContainingAndProjectPkAndStatusAndProjectStatus(
        String name,
        String description,
        Long projectPk,
        Status status,
        Status projectStatus,
        Pageable pageable);

    Optional<Topic> findByPkAndProjectPkAndStatusAndProjectStatus(Long pk, Long projectPk,
        Status status, Status projectStatus);
}
