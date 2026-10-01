package minh.project.infrastructure.auth.adapter;

import minh.project.application.auth.port.OtpManager;
import minh.project.application.auth.variant.ForgotPassword;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class OtpForgotPasswordManager extends OtpManager<ForgotPassword> {

    private final static String OTP_PREFIX = "AUTH:OTP:FORGOT-PASSWORD:";

    public OtpForgotPasswordManager(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getOtpPrefix() {
        return OTP_PREFIX;
    }
}
