package com.project.translate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.translate.dto.response.ExceptionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = extractToken(request);

        try{
            if(jwtToken != null){
                if(jwtService.validateToken(jwtToken)){
                    String userId = jwtService.getIdFromToken(jwtToken);
                    String role = jwtService.getRoleFromToken(jwtToken);

                    var authentication = new UsernamePasswordAuthenticationToken(
                                    userId,
                            null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                            );

                    authentication.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);

        }catch(Exception e){
            sendExceptionResponse(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    "AUTHENTICATION_ERROR",
                    "Authentication failed"
            );
        }

    }


    public boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getServletPath();

        return path.equals("/login")
                || path.equals("/register")
                || path.equals("/public/");
    }


    public void sendExceptionResponse(HttpServletResponse response,
                                  HttpStatus status,
                                  String code,
                                  String message) throws IOException {

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ExceptionResponse errorResponse = new ExceptionResponse(status.value(), code, message, LocalDateTime.now());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }





    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
