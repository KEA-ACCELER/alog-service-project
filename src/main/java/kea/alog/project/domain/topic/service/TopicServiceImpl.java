package kea.alog.project.domain.topic.service;

import java.util.List;
import java.util.stream.Collectors;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.mapper.TopicMapper;
import kea.alog.project.domain.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Override
    public List<TopicDto> findAll() {
        return topicRepository.findAll().stream().map(topicMapper::topicToDto)
                              .collect(Collectors.toList());
    }
}
