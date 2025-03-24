package com.schedulo.schedulo.controller;

import com.schedulo.schedulo.exception.UserErrorException;
import com.schedulo.schedulo.model.UserSignupDto;
import com.schedulo.schedulo.model.UserSignupResponseDto;
import com.schedulo.schedulo.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {

    @GetMapping("/test")
    public ResponseEntity<String> userLogin(){
        return new ResponseEntity<String>(String.valueOf(200), HttpStatusCode.valueOf(200));
    }
}
