package kea.alog.project.domain.projectMember;
import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import kea.alog.project.domain.project.Project;
import kea.alog.project.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(name = "projectMember")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ProjectMember extends BaseTimeEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_pk")
    private Long projectMemberPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project")
    private Project project;

    @Column(name = "user_nn", length=10)
    private String userNn;

    @Column(name = "user_pk")
    private Long userPk;

    @Builder
    public ProjectMember(Project project, String userNn, Long userPk){
        this.project = project;
        this.userNn = userNn;
        this.userPk = userPk;
    }



}
