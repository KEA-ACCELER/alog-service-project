package kea.alog.project.domain.topic.service;

import java.util.List;
import java.util.stream.Collectors;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.entity.Topic;
import kea.alog.project.domain.topic.mapper.TopicMapper;
import kea.alog.project.domain.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Override
    public List<TopicDto> findAll(String keyword, TopicSortType sortType) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        switch (sortType) {
            case START_DATE_ASC:
                sort = Sort.by(Sort.Direction.ASC, "startDate");
                break;
            case START_DATE_DESC:
                sort = Sort.by(Sort.Direction.DESC, "startDate");
                break;
            case DUE_DATE_ASC:
                sort = Sort.by(Sort.Direction.ASC, "dueDate");
                break;
            case DUE_DATE_DESC:
                sort = Sort.by(Sort.Direction.DESC, "dueDate");
                break;
            case ASC:
                sort = Sort.by(Sort.Direction.ASC, "createdAt");
                break;
            case DESC:
                sort = Sort.by(Sort.Direction.DESC, "createdAt");
        }

        List<Topic> topics =
            keyword == null ? topicRepository.findAll(sort)
                : topicRepository.findByNameContainingOrDescriptionContaining(keyword, keyword,
                    sort);

        return topics.stream().map(topicMapper::topicToDto).collect(Collectors.toList());
    }
}
