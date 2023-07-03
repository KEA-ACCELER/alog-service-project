package kea.alog.project.domain.project;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import kea.alog.project.domain.projectMember.ProjectMember;
import kea.alog.project.domain.topic.Topic;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import kea.alog.project.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(name = "project")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Project extends BaseTimeEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_pk")
    private Long projectPk;

    @Column(name = "project_name", length=60)
    private String projectName;

    @Column(name = "project_description", length=200)
    private String projectDescription;

    @Column(name="project_team_name", length=10)
    private String projectTeamName;

    @Column(name="project_team_pk")
    private Long projectTeamPk;

    @Column(name="pm_nn", length=10)
    private String pmNn;

    @Column(name="pm_pk")
    private Long pmPk;

    @OneToMany(mappedBy = "project")
    private List<Topic> topics = new ArrayList<>();

    @OneToMany(mappedBy = "projectMember")
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @Builder
    public Project(String projectName, String projectDescription, String projectTeamName, Long projectTeamPk, String pmNn, Long pmPk){
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectTeamName = projectTeamName;
        this.projectTeamPk = projectTeamPk;
        this.pmNn = pmNn;
        this.pmPk = pmPk;
    }


}
