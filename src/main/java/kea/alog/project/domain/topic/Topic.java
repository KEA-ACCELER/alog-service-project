package kea.alog.project.domain.topic;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "topic")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Topic extends BaseTimeEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_pk")
    private Long topicPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project")
    private Project project;

    @Column(name = "topic_name", length=60)
    private String topicName;

    @Column(name = "topic_description", length=100)
    private String topicDescription;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="due_date")
    private LocalDateTime dueDate;

    @Builder
    public Topic(Project project, String topicName, String topicDescription, LocalDateTime startDate, LocalDateTime dueDate){
        this.project = project;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
}
