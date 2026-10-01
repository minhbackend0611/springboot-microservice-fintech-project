package minh.project.application.auth.command.send_otp_create_account;

import lombok.extern.slf4j.Slf4j;
import minh.project.application.auth.port.IOutboxEventPublisher;
import minh.project.application.auth.port.OtpManager;
import minh.project.application.auth.port.OtpRateLimiterService;
import minh.project.application.auth.variant.CreateAccount;
import minh.project.application.shared.usecase.sendotp.SendOtpCommand;
import minh.project.application.shared.usecase.sendotp.SendOtpUseCase;
import minh.project.domain.auth.port.IOtpGenerator;
import minh.project.domain.auth.service.UserDomainService;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.shared.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SendOtpCreateAccountUseCase extends SendOtpUseCase<CreateAccount> {

    private final static String AGGREGATE_TYPE = "USER";

    private final static String EVENT_TYPE = "OTP_REQUESTED_CREATE_ACCOUNT";

    private final UserDomainService userDomainService;

    public SendOtpCreateAccountUseCase(IOtpGenerator otpGenerator, OtpManager<CreateAccount> otpManager, IOutboxEventPublisher outboxEventPublisher, OtpRateLimiterService otpRateLimiterService, UserDomainService userDomainService) {
        super(otpGenerator, otpManager, outboxEventPublisher, otpRateLimiterService);
        this.userDomainService = userDomainService;
    }


    @Transactional
    public Result<Void> execute(SendOtpCommand sendOtpCommand) {
        return super.execute(sendOtpCommand);
    }

    @Override
    public void validateEmail(Email email) {
        userDomainService.enforceEmailUniqueness(email);
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
