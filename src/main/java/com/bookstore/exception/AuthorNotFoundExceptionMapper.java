package com.bookstore.exception;

import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException> {
    @Override
    public Response toResponse (AuthorNotFoundException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Author Not Found");
        error.put("message", exception.getMessage());
        
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
