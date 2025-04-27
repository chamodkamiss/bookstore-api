package com.bookstore.exception;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {
    @Override
    public Response toResponse(OutOfStockException exception) {
        Map<String,String> error = new HashMap<>();
        error.put("error", "Out of Stock");
        error.put("message", exception.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                       .entity(error)
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }
    
}
