package kea.alog.project.domain.topic.controller;

import jakarta.validation.Valid;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.dto.ResponseDto;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.request.CreateTopicRequestDto;
import kea.alog.project.domain.topic.dto.request.UpdateTopicRequestDto;
import kea.alog.project.domain.topic.dto.response.CreateTopicResponseDto;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.dto.response.UpdateTopicResponseDto;
import kea.alog.project.domain.topic.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/projects/{projectPk}/topics")
@RestController
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping()
    public ResponseDto<PageDto<TopicDto>> findAll(
        // TODO: dto로 param 한 번에 받는 방법
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam("sortType") TopicSortType sortType,
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @PathVariable("projectPk") Long projectPk
    ) {
        return ResponseDto.success(200,
            topicService.findAll(projectPk, keyword, sortType, page, size));
    }

    @GetMapping("/{topicPk}")
    public ResponseDto<TopicDto> findOne(
        @PathVariable("projectPk") Long projectPk,
        @PathVariable("topicPk") Long topicPk) {
        return ResponseDto.success(200, topicService.findOne(projectPk, topicPk));
    }

    @PostMapping
    public ResponseDto<CreateTopicResponseDto> create(@PathVariable("projectPk") Long projectPk,
        @Valid @RequestBody CreateTopicRequestDto createTopicRequestDto) {
        return ResponseDto.success(201, topicService.create(projectPk, createTopicRequestDto));
    }

    @PatchMapping("/{topicPk}")
    public ResponseDto<UpdateTopicResponseDto> update(@PathVariable("projectPk") Long projectPk,
        @PathVariable("topicPk") Long topicPk,
        @RequestBody UpdateTopicRequestDto updateTopicRequestDto) {
        return ResponseDto.success(200,
            topicService.update(projectPk, topicPk, updateTopicRequestDto));
    }

    @DeleteMapping("/{topicPk}")
    public ResponseDto delete(@PathVariable("projectPk") Long projectPk,
        @PathVariable("topicPk") Long topicPk) {
        return ResponseDto.success(200, topicService.delete(projectPk, topicPk));
    }

}
