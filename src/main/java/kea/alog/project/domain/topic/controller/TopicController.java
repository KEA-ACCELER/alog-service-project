package kea.alog.project.domain.topic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kea.alog.project.common.dto.PageDto;
import kea.alog.project.common.dto.ResponseDto;
import kea.alog.project.common.dto.TokenPayloadDto;
import kea.alog.project.domain.topic.constant.TopicSortType;
import kea.alog.project.domain.topic.dto.request.CreateTopicRequestDto;
import kea.alog.project.domain.topic.dto.request.UpdateTopicRequestDto;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.dto.response.TopicPkResponseDto;
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

@Tag(name = "topics", description = "토픽 API")
@RequestMapping("/api/projects/{projectPk}/topics")
@RestController
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @Operation(summary = "토픽 전체 조회")
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

    @Operation(summary = "토픽 상세 조회")
    @GetMapping("/{topicPk}")
    public ResponseDto<TopicDto> findOne(
        @PathVariable("projectPk") Long projectPk,
        @PathVariable("topicPk") Long topicPk) {
        return ResponseDto.success(200, topicService.findOne(projectPk, topicPk));
    }

    @Operation(summary = "토픽 생성")
    @PostMapping
    public ResponseDto<TopicPkResponseDto> create(
        HttpServletRequest request, @PathVariable("projectPk") Long projectPk,
        @Valid @RequestBody CreateTopicRequestDto createTopicRequestDto) {
        TokenPayloadDto userInfo = (TokenPayloadDto) request.getAttribute("user");
        return ResponseDto.success(201,
            topicService.create(projectPk, userInfo.getUserPk(), createTopicRequestDto));
    }

    @Operation(summary = "토픽 수정")
    @PatchMapping("/{topicPk}")
    public ResponseDto update(@PathVariable("projectPk") Long projectPk,
        @PathVariable("topicPk") Long topicPk,
        @RequestBody UpdateTopicRequestDto updateTopicRequestDto) {
        topicService.update(projectPk, topicPk, updateTopicRequestDto);
        return ResponseDto.success(200);
    }

    @Operation(summary = "토픽 삭제")
    @DeleteMapping("/{topicPk}")
    public ResponseDto delete(@PathVariable("projectPk") Long projectPk,
        @PathVariable("topicPk") Long topicPk) {
        topicService.delete(projectPk, topicPk);
        return ResponseDto.success(200);
    }

}
