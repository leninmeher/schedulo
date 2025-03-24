package com.schedulo.schedulo.utils.authentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schedulo.schedulo.utils.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        CustomResponse response1 = new CustomResponse();
        response1.setMessage("The requested resource does not exist.");
        response1.setStatusCode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response1, status);
    }
}

