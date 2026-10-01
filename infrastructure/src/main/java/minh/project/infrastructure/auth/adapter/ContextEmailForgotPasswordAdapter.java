package minh.project.infrastructure.auth.adapter;

import minh.project.application.auth.port.EmailContextManager;
import minh.project.application.auth.variant.ForgotPassword;
import minh.project.domain.shared.error.ErrorCode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContextEmailForgotPasswordAdapter extends EmailContextManager<ForgotPassword> {

    private static final String PREFIX = "AUTH:EMAIL:FORGOT-PASSWORD:";

    private static final ErrorCode errorCode = ErrorCode.LOST_CONTEXT_EMAIL;

    public ContextEmailForgotPasswordAdapter(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
