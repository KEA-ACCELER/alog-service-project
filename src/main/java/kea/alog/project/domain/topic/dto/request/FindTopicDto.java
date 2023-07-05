package kea.alog.project.domain.topic.dto.request;

import jakarta.validation.constraints.NotNull;
import kea.alog.project.domain.topic.constant.TopicSortType;
import lombok.Getter;

@Getter
public class FindTopicDto {

    private String keyword;

    @NotNull
    private TopicSortType sortType;
}
