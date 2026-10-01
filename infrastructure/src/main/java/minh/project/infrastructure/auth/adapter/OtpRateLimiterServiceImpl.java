package minh.project.infrastructure.auth.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minh.project.application.auth.port.OtpRateLimiterService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpRateLimiterServiceImpl implements OtpRateLimiterService {

    private final StringRedisTemplate redisTemplate;

    private static final String OTP_LOCK_KEY = "ratelimit:otp:lock:%s";
    private static final String OTP_COUNT_KEY = "ratelimit:otp:count:%s";

    public boolean isAllowed(String email) {
        String lockKey = String.format(OTP_LOCK_KEY, email);
        String countKey = String.format(OTP_COUNT_KEY, email);

        Boolean isLocked = redisTemplate.hasKey(lockKey);
        if (Boolean.TRUE.equals(isLocked)) {
            log.warn("[Spam Alert] Email {} is sending OTPs too quickly (< 60 seconds)", email);
            return false;
        }

        String currentCountStr = redisTemplate.opsForValue().get(countKey);
        if (currentCountStr != null) {
            int currentCount = Integer.parseInt(currentCountStr);
            if (currentCount >= 3) {
                log.warn("[Spam Alert] Email {} has exceeded the limit 3 times/5 minutes", email);
                return false;
            }
        }

        redisTemplate.opsForValue().set(lockKey, "LOCKED", 60, TimeUnit.SECONDS);

        if (currentCountStr == null) {
            redisTemplate.opsForValue().set(countKey, "1", 5, TimeUnit.MINUTES);
        } else {
            redisTemplate.opsForValue().increment(countKey);
        }

        return true;
    }
}
