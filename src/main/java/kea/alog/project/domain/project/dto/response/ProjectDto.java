package kea.alog.project.domain.project.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
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
    // TODO: projectMember 구현 후 dto로 변경 필요
    private List<ProjectMember> projectMembers = new ArrayList<>();
    private LocalDateTime createdAt;
}
