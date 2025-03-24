package com.schedulo.schedulo.service;

import com.schedulo.schedulo.model.UserSignupDto;
import com.schedulo.schedulo.model.UserSignupResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthenticationService {
    public UserSignupResponseDto userSignUp(UserSignupDto request) throws Exception;
    public UserSignupResponseDto userLogin(UserSignupDto request) throws Exception;
}
