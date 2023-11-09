package com.example.eshop.filter;

import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.service.rate_limiter.RateLimiterService;
import io.github.bucket4j.Bucket;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RequestFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getRequestURI().contains("api/v1/order_crud/make-order")) {
            String userId = request.getParameter("userId");

            if(StringUtils.isNotBlank(userId)) {
                Bucket bucket;
                try {
                    bucket = rateLimiter.resolveBucket(userId);
                } catch (GlobalEShopException e) {
                    sendErrorResponse(response, HttpStatus.BAD_REQUEST.value(), e.getMessage());
                    return;
                }
                if(bucket.tryConsume(1)) {
                    filterChain.doFilter(request, response);
                } else {
                    sendErrorResponse(response, HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.name());
                }
            }else
                sendErrorResponse(response, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @SneakyThrows
    private void sendErrorResponse(HttpServletResponse response, int status, String message) {
        response.setStatus(status);
        response.getWriter().write(message);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
