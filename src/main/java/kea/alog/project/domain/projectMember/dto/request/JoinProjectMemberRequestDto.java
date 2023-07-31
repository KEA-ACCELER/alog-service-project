package kea.alog.project.domain.projectMember.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import lombok.Getter;

@Getter
public class JoinProjectMemberRequestDto {

    @NotNull
    ArrayList<Long> userPks;
}

