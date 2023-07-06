package kea.alog.project.domain.topic.repository;

import kea.alog.project.domain.topic.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAllByProjectPk(Long projectPk, Pageable pageable);

    Page<Topic> findByNameContainingOrDescriptionContainingAndProjectPk(String name,
        String description,
        Long projectPk,
        Pageable pageable);

    Topic findByPk(Long pk);
}
