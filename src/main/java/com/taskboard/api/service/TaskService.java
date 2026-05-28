package com.taskboard.api.service;


import com.taskboard.api.database.entity.project.ProjectEntity;
import com.taskboard.api.database.entity.task.TaskEntity;
import com.taskboard.api.database.repository.IProjectRepository;
import com.taskboard.api.database.repository.ITaskRepository;
import com.taskboard.api.dto.request.TaskRequestDto;
import com.taskboard.api.dto.response.TaskResponseDto;
import com.taskboard.api.exception.TaskNotFoundException;
import com.taskboard.api.utils.mappers.TaskMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ITaskRepository taskRepository;
    private final IProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskResponseDto create(TaskRequestDto taskDto) {
        ProjectEntity projectEntity = projectRepository.findById(taskDto.projectId())
                .orElseThrow(() -> new TaskNotFoundException("Project not found", taskDto.projectId()));

        TaskEntity taskEntity = taskMapper.toEntity(taskDto, projectEntity);
        TaskEntity savedTask = taskRepository.save(taskEntity);

        return taskMapper.toResponse(savedTask);
    }

    public Page<TaskResponseDto> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable).map(taskMapper::toResponse);
    }

    public Page<TaskResponseDto> findByProjectId(UUID projectId, Pageable pageable) {
        return taskRepository.findByProjectId(projectId, pageable).map(taskMapper::toResponse);
    }

    public void delete(UUID id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Project not found", id));

        taskRepository.deleteById(taskEntity.getId());
    }

    public TaskResponseDto update(@Valid TaskRequestDto taskDto, UUID id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Project not found", id));

        taskEntity.setName(taskDto.name());
        taskEntity.setDescription(taskDto.description());
        taskEntity.setPriority(taskDto.priority());
        taskEntity.setStatus(taskDto.status());
        TaskEntity updatedTask = taskRepository.save(taskEntity);

        return taskMapper.toResponse(updatedTask);
    }
}
