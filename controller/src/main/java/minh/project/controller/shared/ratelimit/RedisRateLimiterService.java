package minh.project.controller.shared.ratelimit;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RedisRateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private DefaultRedisScript<Long> script;

    @PostConstruct
    public void init() {
        script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("scripts/rate_limiter.lua"));
        script.setResultType(Long.class);
    }

    public boolean isAllowed(String apiKey, int capacity, int refillRate) {
        String redisKey = "ratelimit:api:" + apiKey;
        String currentTimestamp = String.valueOf(Instant.now().getEpochSecond());

        Long result = redisTemplate.execute(
                script,
                Collections.singletonList(redisKey), // KEYS[1]
                String.valueOf(capacity),           // ARGV[1]
                String.valueOf(refillRate),         // ARGV[2]
                currentTimestamp                    // ARGV[3]
        );

        return result != null && result == 1;
    }
}
