package kea.alog.project.domain.project.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateProjectRequestDto {

    private String name;
    private String description;
    private Long teamPk;
    private Long pmPk;
}
