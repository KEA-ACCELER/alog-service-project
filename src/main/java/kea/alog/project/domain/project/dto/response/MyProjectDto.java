package kea.alog.project.domain.project.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyProjectDto {

    private Long pk;
    private String name;
    private String description;
    private Long teamPk;
    private Long pmPk;
    private LocalDateTime createdAt;
}
