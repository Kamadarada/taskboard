package com.taskboard.api.controller;

import com.taskboard.api.dto.request.TaskRequestDto;
import com.taskboard.api.dto.response.TaskResponseDto;
import com.taskboard.api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDto create(@Valid @RequestBody TaskRequestDto taskDto){
        return taskService.create(taskDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskResponseDto> findAll(@PageableDefault(size = 10) Pageable pageable){
        return taskService.findAll(pageable);
    }

    @GetMapping("/project/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskResponseDto> findByProjectId(@PathVariable UUID projectId, @PageableDefault(size = 10) Pageable pageable){
        return taskService.findByProjectId(projectId, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        taskService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDto update(@Valid @RequestBody TaskRequestDto taskDto, @PathVariable UUID id){
        return taskService.update(taskDto, id);
    }
}
