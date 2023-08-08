package kea.alog.project.domain.topic.service;

import java.util.stream.Collectors;
import kea.alog.project.common.constant.Status;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.exception.EntityNotFoundException;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.project.service.ProjectService;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.request.CreateTopicRequestDto;
import kea.alog.project.domain.topic.dto.request.UpdateTopicRequestDto;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.dto.response.TopicPkResponseDto;
import kea.alog.project.domain.topic.entity.Topic;
import kea.alog.project.domain.topic.mapper.TopicMapper;
import kea.alog.project.domain.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final ProjectService projectService;

    @Override
    public Topic findByProjectPkAndTopicPk(Long projectPk, Long topicPk) {
        return topicRepository.findByPkAndProjectPkAndStatusAndProjectStatus(topicPk, projectPk,
            Status.NORMAL, Status.NORMAL).orElseThrow(
            () -> new EntityNotFoundException(404, "ENTITY_NOT_FOUND"));
    }

    @Override
    public PageDto<TopicDto> findAll(Long projectPk, String keyword, TopicSortType sortType,
        int page, int size) {
        Sort sort = getSort(sortType);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Topic> topicPage =
            keyword == null ? topicRepository.findAllByProjectPkAndStatusAndProjectStatus(projectPk,
                Status.NORMAL,
                Status.NORMAL,
                pageable)
                : topicRepository.findByNameContainingOrDescriptionContainingAndProjectPkAndStatusAndProjectStatus(
                    keyword,
                    keyword, projectPk, Status.NORMAL, Status.NORMAL,
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
        return switch (sortType) {
            case START_DATE_ASC -> Sort.by(Sort.Direction.ASC, "startDate");
            case START_DATE_DESC -> Sort.by(Sort.Direction.DESC, "startDate");
            case DUE_DATE_ASC -> Sort.by(Sort.Direction.ASC, "dueDate");
            case DUE_DATE_DESC -> Sort.by(Sort.Direction.DESC, "dueDate");
            case ASC -> Sort.by(Sort.Direction.ASC, "createdAt");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }

    @Override
    public TopicDto findOne(Long projectPk, Long topicPk) {
        Topic topic = findByProjectPkAndTopicPk(projectPk, topicPk);
        return topicMapper.topicToDto(topic);
    }

    @Transactional
    @Override
    public TopicPkResponseDto create(Long projectPk,
        CreateTopicRequestDto createTopicRequestDto) {
        Project project = projectService.findByPk(projectPk);

        Topic topic = topicRepository.save(Topic.builder()
                                                .project(project)
                                                .name(createTopicRequestDto.getName())
                                                .description(createTopicRequestDto.getDescription())
                                                .startDate(createTopicRequestDto.getStartDate())
                                                .dueDate(createTopicRequestDto.getDueDate())
                                                .build());

        return TopicPkResponseDto.builder().topicPk(topic.getPk()).projectPk(projectPk).build();
    }

    @Transactional
    @Override
    public void update(Long projectPk, Long topicPk,
        UpdateTopicRequestDto updateTopicRequestDto) {
        Topic topic = findByProjectPkAndTopicPk(projectPk, topicPk);

        topicMapper.updateTopicFromDto(updateTopicRequestDto, topic);
    }

    @Transactional
    @Override
    public void delete(Long projectPk, Long topicPk) {
        Topic topic = findByProjectPkAndTopicPk(projectPk, topicPk);

        topic.setStatus(Status.DELETED);
        topicRepository.save(topic);
    }
}
