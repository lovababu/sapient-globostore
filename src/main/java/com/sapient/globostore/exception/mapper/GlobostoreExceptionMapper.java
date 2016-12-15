package com.sapient.globostore.exception.mapper;

import com.sapient.globostore.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by dpadal on 12/15/2016.
 */
@Provider
@Slf4j
public class GlobostoreExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        log.error("Exception: ", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                ApiResponse.builder()
                        .withStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .withMessage(exception.getMessage())
                        .build()
        ).build();
    }
}
