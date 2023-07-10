package kea.alog.project.domain.topic.mapper;

import kea.alog.project.domain.topic.dto.request.UpdateTopicRequestDto;
import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TopicMapper {

    @Mapping(target = "projectPk", source = "topic.project.pk")
    TopicDto topicToDto(Topic topic);

    void updateTopicFromDto(UpdateTopicRequestDto updateTopicRequestDto,
        @MappingTarget Topic topic);
}
