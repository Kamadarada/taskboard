package com.taskboard.api.controller;


import com.taskboard.api.dto.request.ProjectRequestDto;
import com.taskboard.api.dto.response.ProjectResponseDto;
import com.taskboard.api.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDto create(@Valid @RequestBody ProjectRequestDto projectDto){
        return projectService.create(projectDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjectResponseDto> findAll(@PageableDefault(size = 10) Pageable pageable){
        return projectService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDto findById(@PathVariable UUID id){
        return projectService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        projectService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDto update(@Valid @PathVariable UUID id, @Valid @RequestBody ProjectRequestDto projectDto){
        return projectService.update(id, projectDto);
    }
}
