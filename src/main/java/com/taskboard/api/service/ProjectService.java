package com.taskboard.api.service;


import com.taskboard.api.database.entity.project.ProjectEntity;
import com.taskboard.api.database.entity.user.UserEntity;
import com.taskboard.api.database.repository.IProjectRepository;
import com.taskboard.api.database.repository.IUserRepository;
import com.taskboard.api.dto.request.ProjectRequestDto;
import com.taskboard.api.dto.response.ProjectResponseDto;
import com.taskboard.api.exception.ProjectNotFoundException;
import com.taskboard.api.utils.mappers.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final IProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final IUserRepository userRepository;

    public ProjectResponseDto create(ProjectRequestDto projectDto) {
        UserEntity projectOwner = userRepository.findById(UUID.fromString(projectDto.projectOwnerId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectEntity projectEntity = projectRepository.save(ProjectEntity.builder().name(projectDto.name()).description(projectDto.description()).projectOwner(projectOwner)
                .build());
        return projectMapper.toResponse(projectEntity);
    }

    public Page<ProjectResponseDto> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(projectMapper::toResponse);
    }

    public ProjectResponseDto findById(UUID id) {
        return projectRepository.findById(id)
                .map(projectMapper::toResponse)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found", id));
    }

    public void delete(UUID id) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found", id));
        projectRepository.deleteById(projectEntity.getId());
    }

    public ProjectResponseDto update(UUID id, ProjectRequestDto projectDto) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found", id));

        projectEntity.setName(projectDto.name());
        projectEntity.setDescription(projectDto.description());
        ProjectEntity updatedProject = projectRepository.save(projectEntity);

        return projectMapper.toResponse(updatedProject);
    }
}
