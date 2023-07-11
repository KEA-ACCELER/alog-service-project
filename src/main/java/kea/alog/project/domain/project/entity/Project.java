package kea.alog.project.domain.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import kea.alog.project.common.entity.BaseEntity;
import kea.alog.project.domain.projectMember.entity.ProjectMember;
import kea.alog.project.domain.topic.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Project extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Long pk;

    @Column(name = "name", length = 60)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "team_name", length = 10)
    private String teamName;

    @Column(name = "team_pk")
    private Long teamPk;

    @Column(name = "pm_nn", length = 10)
    private String pmNn;

    @Column(name = "pm_pk")
    private Long pmPk;

    @OneToMany(mappedBy = "project")
    private List<Topic> topics = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @Builder
    public Project(String name, String description, String teamName,
        Long teamPk, String pmNn, Long pmPk) {
        this.name = name;
        this.description = description;
        this.teamName = teamName;
        this.teamPk = teamPk;
        this.pmNn = pmNn;
        this.pmPk = pmPk;
    }


}
