package com.schedulo.schedulo.utils.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schedulo.schedulo.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTForbiddenEntryPoint implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        CustomResponse response1 = new CustomResponse();
        response1.setMessage("You do not have permission to access this resource.");
        response1.setStatusCode(HttpStatus.FORBIDDEN.value());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), response1);
    }
}
