package kea.alog.project.domain.topic.service;

import kea.alog.project.common.dto.PageDto;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.response.TopicDto;

public interface TopicService {

    PageDto<TopicDto> findAll(String keyword, TopicSortType sortType, int page, int size);
}
