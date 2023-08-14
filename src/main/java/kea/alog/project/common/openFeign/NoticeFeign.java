package kea.alog.project.common.openFeign;

import kea.alog.project.common.openFeign.dto.request.CreateNoticeRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "notice-service",
    url = "${notice.service.url}"
)
public interface NoticeFeign {

    @PostMapping("api/noti")
    String create(@RequestBody CreateNoticeRequestDto createNoticeRequestDto);
}
