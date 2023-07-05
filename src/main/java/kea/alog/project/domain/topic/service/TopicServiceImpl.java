package kea.alog.project.domain.topic.service;

import java.util.stream.Collectors;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.entity.Topic;
import kea.alog.project.domain.topic.mapper.TopicMapper;
import kea.alog.project.domain.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Override
    public PageDto<TopicDto> findAll(Long projectPk, String keyword, TopicSortType sortType,
        int page, int size) {
        Sort sort = getSort(sortType);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Topic> topicPage =
            keyword == null ? topicRepository.findAllByProjectPk(projectPk, pageable)
                : topicRepository.findByNameContainingOrDescriptionContainingAndProjectPk(keyword,
                    keyword, projectPk,
                    pageable);

        return PageDto.<TopicDto>builder()
                      .content(topicPage.getContent().stream().map(topicMapper::topicToDto)
                                        .collect(Collectors.toList()))
                      .totalPages(topicPage.getTotalPages())
                      .totalElements(topicPage.getTotalElements())
                      .pageNumber(topicPage.getNumber())
                      .pageSize(topicPage.getSize())
                      .build();

    }

    private Sort getSort(TopicSortType sortType) {
        switch (sortType) {
            case START_DATE_ASC:
                return Sort.by(Sort.Direction.ASC, "startDate");
            case START_DATE_DESC:
                return Sort.by(Sort.Direction.DESC, "startDate");
            case DUE_DATE_ASC:
                return Sort.by(Sort.Direction.ASC, "dueDate");
            case DUE_DATE_DESC:
                return Sort.by(Sort.Direction.DESC, "dueDate");
            case ASC:
                return Sort.by(Sort.Direction.ASC, "createdAt");
            default:
                return Sort.by(Sort.Direction.DESC, "createdAt");
        }
    }
}
