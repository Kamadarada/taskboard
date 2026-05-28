package com.taskboard.api.exception;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(String message , UUID id) {
        super(message + " " +  id);
    }
}
