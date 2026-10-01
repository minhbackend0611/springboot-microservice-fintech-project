package minh.project.application.auth.command.send_otp_forgot_password;

import minh.project.application.auth.port.IOutboxEventPublisher;
import minh.project.application.auth.port.OtpManager;
import minh.project.application.auth.port.OtpRateLimiterService;
import minh.project.application.auth.support.UserFinder;
import minh.project.application.auth.variant.ForgotPassword;
import minh.project.application.shared.usecase.sendotp.SendOtpUseCase;
import minh.project.domain.auth.port.IOtpGenerator;
import minh.project.domain.auth.vo.Email;
import org.springframework.stereotype.Service;

@Service
public class SendOtpForgotPasswordUseCase extends SendOtpUseCase<ForgotPassword> {

    private final static String AGGREGATE_TYPE = "USER";

    private final static String EVENT_TYPE = "OTP_REQUESTED_FORGOT_PASSWORD";

    private final UserFinder userFinder;

    public SendOtpForgotPasswordUseCase(IOtpGenerator otpGenerator, OtpManager<ForgotPassword> otpManager, IOutboxEventPublisher outboxEventPublisher, OtpRateLimiterService otpRateLimiterService, UserFinder userFinder) {
        super(otpGenerator, otpManager, outboxEventPublisher, otpRateLimiterService);
        this.userFinder = userFinder;
    }


    @Override
    public void validateEmail(Email email) {
        userFinder.loadByEmailOrThrow(email);
    }

    @Override
    public String getAggregateType() {
        return AGGREGATE_TYPE;
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }
}
