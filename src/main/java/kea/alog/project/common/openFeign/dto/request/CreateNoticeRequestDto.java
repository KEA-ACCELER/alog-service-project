package kea.alog.project.common.openFeign.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateNoticeRequestDto {

    private Long userPk;
    private String MsgContent;
}
