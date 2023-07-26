package kea.alog.project.domain.projectMember.dto.response;

import kea.alog.project.common.constant.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberDto {

    private Long userPk;

    // TODO: user service에서 받아와서 관련 정보 추가

    private Status status;
}
