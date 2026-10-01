package minh.project.controller.shared.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RedisRateLimiterService redisRateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        KeyRateLimit rateLimit = handlerMethod.getMethodAnnotation(KeyRateLimit.class);

        if (rateLimit == null) {
            return true;
        }

        String apiKey = rateLimit.key();
        int capacity = rateLimit.capacity();
        int refillRate = rateLimit.refillRate();

        boolean allowed = redisRateLimiterService.isAllowed(apiKey, capacity, refillRate);

        if (!allowed) {
            response.setStatus(429);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\":\"too many requests\",\"code\":429,\"isSuccess\":false}");
            return false;
        }

        return true;
    }
}
