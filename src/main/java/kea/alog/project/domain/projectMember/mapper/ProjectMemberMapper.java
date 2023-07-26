package kea.alog.project.domain.projectMember.mapper;

import kea.alog.project.domain.projectMember.dto.response.ProjectMemberDto;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMemberMapper {

    ProjectMemberDto projectMemberToDto(ProjectMember projectMember);
}
