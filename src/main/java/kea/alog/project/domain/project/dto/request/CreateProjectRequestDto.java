package kea.alog.project.domain.project.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateProjectRequestDto {

    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Long teamPk;
    @NotNull
    private Long pmPk;
}
