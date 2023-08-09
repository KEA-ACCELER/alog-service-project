package kea.alog.project.domain.project.mapper;

import java.util.List;
import java.util.stream.Collectors;
import kea.alog.project.domain.project.dto.request.UpdateProjectRequestDto;
import kea.alog.project.domain.project.dto.response.MyProjectDto;
import kea.alog.project.domain.project.dto.response.ProjectDto;
import kea.alog.project.domain.project.entity.Project;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
import kea.alog.project.domain.topic.mapper.TopicMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = TopicMapper.class)
public abstract class ProjectMapper {

    @Mapping(target = "topics", source = "project.topics")
    @Mapping(target = "projectMembers", source = "project.projectMembers")
    public abstract ProjectDto projectToDto(Project project);

    public abstract MyProjectDto projectToMyProjectDto(Project project);

    public abstract void updateProjectFromDto(UpdateProjectRequestDto updateProjectRequestDto,
        @MappingTarget Project project);

    protected List<Long> mapProjectMembers(List<ProjectMember> projectMembers) {
        return projectMembers.stream()
                             .map(ProjectMember::getUserPk)
                             .collect(Collectors.toList());
    }
}

