package kea.alog.project.domain.projectMember.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import kea.alog.project.common.entity.BaseEntity;
import kea.alog.project.domain.project.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projectMember")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ProjectMember extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Long pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_pk")
    private Project project;

    @Column(name = "user_nn", length = 10)
    private String userNn;

    @Column(name = "user_pk")
    private Long userPk;

    @Builder
    public ProjectMember(Project project, String userNn, Long userPk) {
        this.project = project;
        this.userNn = userNn;
        this.userPk = userPk;
    }


}
