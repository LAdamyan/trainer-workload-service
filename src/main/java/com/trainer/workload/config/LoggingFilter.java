package com.trainer.workload.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
@WebFilter("/*")
public class LoggingFilter implements jakarta.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, jakarta.servlet.ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String transactionId = MDC.get("transactionId");
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();

        log.info("[{}] Request: {} {}", transactionId, method, uri);

        chain.doFilter(request, response);

        int status = httpResponse.getStatus();
        log.info("[{}] Response status: {}", transactionId, status);
    }
}
