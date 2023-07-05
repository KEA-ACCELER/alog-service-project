package kea.alog.project.domain.topic.repository;

import java.util.List;
import kea.alog.project.domain.topic.entity.Topic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAll(Sort sort);

    List<Topic> findByNameContainingOrDescriptionContaining(String name, String description,
        Sort sort);

}
