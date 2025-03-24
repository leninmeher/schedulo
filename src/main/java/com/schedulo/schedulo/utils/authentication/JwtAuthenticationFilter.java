package com.schedulo.schedulo.utils.authentication;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.schedulo.schedulo.entity.User;
import com.schedulo.schedulo.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null) {
            try {
                String email = jwtHelper.verifyToken(token).getSubject();

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Load user details from the database
                    User user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println(authentication);
                }
            } catch (JWTVerificationException e) {
                // Token is invalid or expired
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getEmailFromToken(String token) throws Exception{
        String extractedToken = "";
        if (token != null && token.startsWith("Bearer ")) {
            extractedToken = token.substring(7);

            String email = jwtHelper.verifyToken(extractedToken).getSubject();
            if(email==null || email==""){
                throw  new UsernameNotFoundException("User Not Found !");
            }

            return email;
        }
        return "";
    }

}