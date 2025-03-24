package com.schedulo.schedulo.service.impl;

import com.schedulo.schedulo.entity.User;
import com.schedulo.schedulo.exception.UserErrorException;
import com.schedulo.schedulo.model.UserSignupDto;
import com.schedulo.schedulo.model.UserSignupResponseDto;
import com.schedulo.schedulo.repository.UserRepository;
import com.schedulo.schedulo.service.UserAuthenticationService;
import com.schedulo.schedulo.utils.authentication.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTHelper jwtHelper;

    @Override
    public UserSignupResponseDto userSignUp(UserSignupDto request) throws Exception{

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            throw new UserErrorException("User with email " + request.getEmail() + " already exists!");
        }

        UUID uuid = UUID.randomUUID();
        long leastSignificantBits = uuid.getLeastSignificantBits();

        long randomIntId = (long) (leastSignificantBits & 0xFFFFFFFFL);

        User newUser = new User();
        newUser.setUserId(randomIntId);
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setCreatedOn(LocalDateTime.now());
        newUser.setUpdatedOn(LocalDateTime.now());

        userRepository.save(newUser);

        UserSignupResponseDto userSignupResponseDto = new UserSignupResponseDto();
        userSignupResponseDto.setToken(jwtHelper.generateToken(request.getEmail()));

        return userSignupResponseDto;
    }

    @Override
    public UserSignupResponseDto userLogin(UserSignupDto request) throws Exception{
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(!user.isPresent()){
            throw new UserErrorException("User with email " + request.getEmail() + " does not exist!");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserSignupResponseDto userSignupResponseDto = new UserSignupResponseDto();
        userSignupResponseDto.setToken(jwtHelper.generateToken(user.get().getEmail()));

        return userSignupResponseDto;
    }
}
