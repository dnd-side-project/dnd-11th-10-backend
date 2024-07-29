package com.dnd.spaced.global.security.filter;

import com.dnd.spaced.global.security.dto.response.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class OAuth2RegistrationValidateFilter extends OncePerRequestFilter {

    private static final String AUTHORIZE_URI = "/login";
    private static final List<String> REGISTRATION_ID = List.of("google");

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.contains(AUTHORIZE_URI)) {
            String[] splitRequestUri = requestURI.split("/");
            String registrationId = splitRequestUri[splitRequestUri.length - 1];

            if (!REGISTRATION_ID.contains(registrationId)) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());

                PrintWriter writer = response.getWriter();

                ExceptionResponse exceptionResponse = new ExceptionResponse(
                        "INVALID_REGISTRATION_ID",
                        "지원하지 않는 소셜 로그인 방식입니다."
                );
                writer.println(objectMapper.writeValueAsString(exceptionResponse));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}