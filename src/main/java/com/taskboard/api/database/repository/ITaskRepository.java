package com.taskboard.api.database.repository;

import com.taskboard.api.database.entity.task.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<TaskEntity, UUID> {

    Page<TaskEntity> findByProjectId(UUID projectId, Pageable pageable);
}
