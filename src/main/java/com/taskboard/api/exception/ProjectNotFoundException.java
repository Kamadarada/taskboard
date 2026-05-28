package com.taskboard.api.exception;

import java.util.UUID;

public class ProjectNotFoundException extends RuntimeException{

    public ProjectNotFoundException(String message, UUID id) {
        super(message + " " + id);
    }
}
