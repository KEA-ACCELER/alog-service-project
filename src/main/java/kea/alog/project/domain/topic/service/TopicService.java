package kea.alog.project.domain.topic.service;

import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.request.CreateTopicRequestDto;
import kea.alog.project.domain.topic.dto.request.UpdateTopicRequestDto;
import kea.alog.project.domain.topic.dto.response.CreateTopicResponseDto;
import kea.alog.project.domain.topic.dto.response.DeleteTopicResponseDto;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.dto.response.UpdateTopicResponseDto;

public interface TopicService {

    PageDto<TopicDto> findAll(Long projectPk, String keyword, TopicSortType sortType, int page,
        int size);

    TopicDto findOne(Long projectPk, Long topicPk);

    CreateTopicResponseDto create(Long projectPk,
        CreateTopicRequestDto createTopicRequestDto);

    UpdateTopicResponseDto update(Long projectPk, Long topicPk,
        UpdateTopicRequestDto updateTopicRequestDto);

    DeleteTopicResponseDto delete(Long projectPk, Long topicPk);
}
