package com.taskboard.api.database.repository;

import com.taskboard.api.database.entity.project.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProjectRepository extends JpaRepository<ProjectEntity, UUID> {
}
