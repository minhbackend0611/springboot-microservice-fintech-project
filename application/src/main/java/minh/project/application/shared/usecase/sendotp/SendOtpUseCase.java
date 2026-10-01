package minh.project.application.shared.usecase.sendotp;

import lombok.RequiredArgsConstructor;
import minh.project.application.auth.port.IOutboxEventPublisher;
import minh.project.application.auth.port.OtpManager;
import minh.project.application.auth.port.OtpRateLimiterService;
import minh.project.domain.auth.port.IOtpGenerator;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.shared.error.ErrorCode;
import minh.project.domain.shared.result.Result;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public abstract class SendOtpUseCase<V> {

    protected final IOtpGenerator otpGenerator;

    protected final OtpManager<V> otpManager;

    protected final IOutboxEventPublisher outboxEventPublisher;

    protected final OtpRateLimiterService otpRateLimiterService;

    @Value("${app.security.register.otp.length}")
    private int OTP_LENGTH;

    @Value("${app.security.register.otp.ttl}")
    private Duration OTP_TTL;

    public Result<Void> execute(SendOtpCommand sendOtpCommand) {

        Email email = new Email(sendOtpCommand.email());

        validateEmail(email);

        if (!otpRateLimiterService.isAllowed(email.value())) {

            return Result.failure(ErrorCode.OTP_RATE_LIMIT);

        }

        String otp = otpGenerator.generateOtp(OTP_LENGTH);

        Map<String, Object> payload = Map.of(
                "email", email.value(),
                "otp", otp
        );

        outboxEventPublisher.publish(getAggregateType(), email.value(), getEventType(), payload);

        otpManager.saveOtp(email.value(), otp, OTP_TTL);

        return Result.success();

    }

    public abstract void validateEmail(Email email);

    public abstract String getAggregateType();

    public abstract String getEventType();

}
