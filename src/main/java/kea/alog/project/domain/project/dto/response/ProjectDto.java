package kea.alog.project.domain.project.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectDto {

    private String name;
    private String description;
    private Long teamPk;
    private Long pmPk;
    private List<TopicDto> topics = new ArrayList<>();
    private List<Long> projectMembers = new ArrayList<>();
    private LocalDateTime createdAt;
}
