package minh.project.application.auth.port;

import lombok.RequiredArgsConstructor;
import minh.project.application.shared.exception.AppException;
import minh.project.domain.shared.error.ErrorCode;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@RequiredArgsConstructor
public abstract class EmailContextManager<V> {

    private final StringRedisTemplate redisTemplate;

    public void saveEmail(String email, Duration ttl) {
        String key = getPrefix() + email;
        redisTemplate.opsForValue().set(key, email, ttl);
    }

    public String getEmail(String email) {

        String key = getPrefix() + email;

        return redisTemplate.opsForValue().get(key);
    }

    public void verifyContext(String email) {

        String emailCache = getEmail(email);

        if (emailCache == null) {
            throw new AppException(getErrorCode());
        }

    }

    public void deleteEmail(String email) {
        String key = getPrefix() + email;
        redisTemplate.delete(email);
    }

    public abstract String getPrefix();

    public abstract ErrorCode getErrorCode();

}
