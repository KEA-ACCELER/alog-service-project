package kea.alog.project.domain.topic.service;

import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.request.CreateTopicRequestDto;
import kea.alog.project.domain.topic.dto.request.UpdateTopicRequestDto;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.dto.response.TopicPkResponseDto;
import kea.alog.project.domain.topic.entity.Topic;

public interface TopicService {

    Topic findByProjectPkAndTopicPk(Long projectPk, Long topicPk);

    PageDto<TopicDto> findAll(Long projectPk, String keyword, TopicSortType sortType, int page,
        int size);

    TopicDto findOne(Long projectPk, Long topicPk);

    TopicPkResponseDto create(Long projectPk,
        CreateTopicRequestDto createTopicRequestDto);

    void update(Long projectPk, Long topicPk,
        UpdateTopicRequestDto updateTopicRequestDto);

    void delete(Long projectPk, Long topicPk);
}
