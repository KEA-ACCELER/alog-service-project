package kea.alog.project.domain.project.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectDto {

    private Long pk;
    private String name;
    private String description;
    private Long teamPk;
    private Long pmPk;
    private List<TopicDto> topics;
    private List<Long> projectMembers;
    private LocalDateTime createdAt;
}
