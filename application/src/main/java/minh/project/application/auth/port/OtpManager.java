package minh.project.application.auth.port;


import lombok.RequiredArgsConstructor;
import minh.project.domain.shared.error.ErrorCode;
import minh.project.domain.shared.exception.DomainException;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@RequiredArgsConstructor
public abstract class OtpManager<V> {

    private final StringRedisTemplate redisTemplate;

    public void saveOtp(String email, String otpCode, Duration ttl) {
        String key = getOtpPrefix() + email;
        redisTemplate.opsForValue().set(key, otpCode, ttl);
    }

    public String getOtp(String email) {
        String key = getOtpPrefix() + email;
        return redisTemplate.opsForValue().get(key);
    }

    public void verifyOtp(String email, String otp) {
        String otpCache = getOtp(email);

        if (otpCache == null) {
            throw new DomainException(ErrorCode.OTP_NOT_FOUND);
        }
        else if (!otpCache.equals(otp)) {
            throw new DomainException(ErrorCode.OTP_MISMATCH);
        }

        deleteOtp(email);

    }

    public void deleteOtp(String email) {
        String key = getOtpPrefix() + email;
        redisTemplate.delete(key);
    }

    public abstract String getOtpPrefix();
}