package kea.alog.project.domain.topic.repository;

import kea.alog.project.domain.topic.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAll(Pageable pageable);

    Page<Topic> findByNameContainingOrDescriptionContaining(String name, String description,
        Pageable pageable);

}
