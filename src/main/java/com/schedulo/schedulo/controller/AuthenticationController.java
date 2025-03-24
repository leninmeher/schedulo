package com.schedulo.schedulo.controller;

import com.schedulo.schedulo.exception.UserErrorException;
import com.schedulo.schedulo.model.UserSignupDto;
import com.schedulo.schedulo.model.UserSignupResponseDto;
import com.schedulo.schedulo.service.UserAuthenticationService;
import com.schedulo.schedulo.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("/user/sign-up")
    public ResponseEntity<CustomResponse> userSignUp(@Valid @RequestBody UserSignupDto request){
        String responseMessage;
        UserSignupResponseDto responseDto;
        try{
            responseDto = userAuthenticationService.userSignUp(request);
        }catch (UserErrorException ufe){
            return new ResponseEntity<>(new CustomResponse(403, ufe.getMessage()), HttpStatusCode.valueOf(403));
        }catch(Exception e){
            responseMessage = e.getMessage();
            return new ResponseEntity<>(new CustomResponse(500, responseMessage), HttpStatusCode.valueOf(500));
        }

        return new ResponseEntity<>(new CustomResponse(200, responseDto), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/user/login")
    public ResponseEntity<CustomResponse> userLogin(@Valid @RequestBody UserSignupDto request){
        String responseMessage;
        UserSignupResponseDto responseDto;
        try{
            responseDto = userAuthenticationService.userLogin(request);
        }catch (UserErrorException ufe){
            return new ResponseEntity<>(new CustomResponse(403, ufe.getMessage()), HttpStatusCode.valueOf(403));
        }catch(Exception e){
            responseMessage = e.getMessage();
            return new ResponseEntity<>(new CustomResponse(500, responseMessage), HttpStatusCode.valueOf(500));
        }

        return new ResponseEntity<>(new CustomResponse(200, responseDto), HttpStatusCode.valueOf(200));
    }
}
