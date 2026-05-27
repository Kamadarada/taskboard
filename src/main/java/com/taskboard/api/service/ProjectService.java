package com.taskboard.api.service;


import com.taskboard.api.database.entity.project.ProjectEntity;
import com.taskboard.api.database.repository.IProjectRepository;
import com.taskboard.api.dto.request.ProjectRequestDto;
import com.taskboard.api.dto.response.ProjectResponseDto;
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

    public ProjectResponseDto create(ProjectRequestDto projectDto) {
        ProjectEntity projectEntity = projectRepository.save(ProjectEntity.builder().name(projectDto.name()).description(projectDto.description())
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
                .orElseThrow();
    }

    public void delete(UUID id) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.deleteById(projectEntity.getId());
    }

    public ProjectResponseDto update(UUID id, ProjectRequestDto projectDto) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        projectEntity.setName(projectDto.name());
        projectEntity.setDescription(projectDto.description());
        ProjectEntity updatedProject = projectRepository.save(projectEntity);

        return projectMapper.toResponse(updatedProject);
    }
}
