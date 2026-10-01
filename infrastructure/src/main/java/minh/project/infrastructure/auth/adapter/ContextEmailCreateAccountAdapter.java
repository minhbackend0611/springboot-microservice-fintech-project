package minh.project.infrastructure.auth.adapter;

import minh.project.application.auth.port.EmailContextManager;
import minh.project.application.auth.variant.CreateAccount;
import minh.project.domain.shared.error.ErrorCode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ContextEmailCreateAccountAdapter extends EmailContextManager<CreateAccount> {

    private static final String PREFIX = "AUTH:EMAIL:CREATE-ACCOUNT:";

    private static final ErrorCode errorCode = ErrorCode.EMAIL_NOT_VERIFIED;

    public ContextEmailCreateAccountAdapter(StringRedisTemplate redisTemplate) {
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
