package kea.alog.project.domain.topic.service;

import java.util.List;
import kea.alog.project.domain.topic.dto.response.TopicDto;

public interface TopicService {

    List<TopicDto> findAll(String keyword);
}
