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

    Page<Topic> findAllByProjectPkAndStatus(Long projectPk, Status status, Pageable pageable);

    Page<Topic> findByNameContainingOrDescriptionContainingAndProjectPkAndStatus(String name,
        String description,
        Long projectPk,
        Status status,
        Pageable pageable);

    Optional<Topic> findByPkAndProjectPkAndStatus(Long pk, Long projectPk, Status status);
}
