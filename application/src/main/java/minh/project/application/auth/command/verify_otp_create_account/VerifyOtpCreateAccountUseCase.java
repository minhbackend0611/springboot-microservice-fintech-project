package minh.project.application.auth.command.verify_otp_create_account;

import minh.project.application.auth.port.EmailContextManager;
import minh.project.application.auth.port.OtpManager;
import minh.project.application.auth.variant.CreateAccount;
import minh.project.application.shared.usecase.verifyotp.VerifyOtpCommand;
import minh.project.application.shared.usecase.verifyotp.VerifyOtpUseCase;
import minh.project.domain.shared.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
public class VerifyOtpCreateAccountUseCase extends VerifyOtpUseCase<CreateAccount> {

    @Value("${app.security.register.otp.context.create-account}")
    private Duration contextCreateAccount;

    public VerifyOtpCreateAccountUseCase(
            OtpManager<CreateAccount> otpManager,
            EmailContextManager<CreateAccount> emailContextManager) {
        super(otpManager, emailContextManager);
    }

    @Override
    @Transactional
    public Result<Void> execute(VerifyOtpCommand verifyOtpCommand) {
        return super.execute(verifyOtpCommand);
    }

    @Override
    public Duration getContextDuration() {
        return contextCreateAccount;
    }
}
