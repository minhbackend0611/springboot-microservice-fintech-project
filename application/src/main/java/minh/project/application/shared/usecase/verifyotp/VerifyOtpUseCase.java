package minh.project.application.shared.usecase.verifyotp;

import lombok.RequiredArgsConstructor;
import minh.project.application.auth.port.EmailContextManager;
import minh.project.application.auth.port.OtpManager;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.shared.result.Result;

import java.time.Duration;

@RequiredArgsConstructor
public abstract class VerifyOtpUseCase<V> {

    protected final OtpManager<V> otpManager;

    protected final EmailContextManager<V> emailContextManager;

    public Result<Void> execute(VerifyOtpCommand verifyOtpCommand) {

        Email email = new Email(verifyOtpCommand.email());

        otpManager.verifyOtp(email.value(), verifyOtpCommand.otp());

        emailContextManager.saveEmail(verifyOtpCommand.email(), Duration.ofMinutes(10));

        return Result.success();

    }

    public abstract Duration getContextDuration();

}
