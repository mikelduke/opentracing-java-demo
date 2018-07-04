package com.mikelduke.vhs.members.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
    
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String reason) {
        super(reason);
    }
}
