package kea.alog.project.domain.topic.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TopicPkResponseDto {

    private Long projectPk;
    private Long topicPk;
}
