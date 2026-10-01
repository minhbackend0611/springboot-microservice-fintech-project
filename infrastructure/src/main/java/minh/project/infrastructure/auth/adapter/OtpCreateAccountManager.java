package minh.project.infrastructure.auth.adapter;

import minh.project.application.auth.port.OtpManager;
import minh.project.application.auth.variant.CreateAccount;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class OtpCreateAccountManager extends OtpManager<CreateAccount> {

    private final static String OTP_PREFIX = "AUTH:OTP:CREATE-ACCOUNT:";

    public OtpCreateAccountManager(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getOtpPrefix() {
        return OTP_PREFIX;
    }
}
