package kea.alog.project.domain.topic.dto.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateTopicRequestDto {

    private String name;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

}
