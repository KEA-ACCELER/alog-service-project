package kea.alog.project.domain.topic.controller;

import java.util.List;
import kea.alog.project.common.dto.ResponseDto;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/projects/topics")
@RestController
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping()
    public ResponseDto<List<TopicDto>> findAll(
        // TODO: dto로 param 한 번에 받는 방법
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam("sortType") TopicSortType sortType
    ) {
        return ResponseDto.success(200,
            topicService.findAll(keyword, sortType));
    }
}
