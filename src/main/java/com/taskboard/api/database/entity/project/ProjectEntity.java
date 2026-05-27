package com.taskboard.api.database.entity.project;

import com.taskboard.api.database.BaseEntity;
import com.taskboard.api.database.entity.task.TaskEntity;
import jakarta.persistence.*;

import lombok.*;

import java.util.*;


@Entity
@Table(name = "projects")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    // orphanRemoval = true -> remove tasks when project is removed
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEntity> tasks = new ArrayList<>();
}
