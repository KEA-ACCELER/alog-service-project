package kea.alog.project.domain.topic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import kea.alog.project.common.entity.BaseEntity;
import kea.alog.project.domain.project.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "topic")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class Topic extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Long pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_pk")
    @NotNull
    private Project project;

    @Column(name = "name", length = 60)
    @NotNull
    private String name;

    @Column(name = "description", length = 100)
    @NotNull
    private String description;

    @Column(name = "start_date")
    @NotNull
    private LocalDateTime startDate;

    @Column(name = "due_date")
    @NotNull
    private LocalDateTime dueDate;

    @Builder
    public Topic(Project project, String name, String description,
        LocalDateTime startDate, LocalDateTime dueDate) {
        this.project = project;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
}
