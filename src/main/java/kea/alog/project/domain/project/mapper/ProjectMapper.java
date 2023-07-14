package kea.alog.project.domain.project.mapper;

import kea.alog.project.domain.project.dto.response.ProjectDto;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.topic.mapper.TopicMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = TopicMapper.class)
public interface ProjectMapper {

    @Mapping(target = "topics", source = "topics")
    ProjectDto projectToDto(Project project);
}
