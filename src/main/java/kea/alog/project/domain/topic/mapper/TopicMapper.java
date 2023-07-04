package kea.alog.project.domain.topic.mapper;

import kea.alog.project.domain.topic.dto.response.TopicDto;
import kea.alog.project.domain.topic.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TopicMapper {

    @Mapping(target = "projectPk", source = "topic.project.pk")
    TopicDto topicToDto(Topic topic);
}
