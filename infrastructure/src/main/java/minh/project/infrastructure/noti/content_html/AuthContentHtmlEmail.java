package minh.project.infrastructure.noti.content_html;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Duration;

@Component
public class AuthContentHtmlEmail extends ContentHtmlEmailCreator {

    @Value("${app.security.register.otp.ttl}")
    private Duration OTP_TTL;

    public AuthContentHtmlEmail(TemplateEngine templateEngine) {
        super(templateEngine);
    }

    public String templateContentHtmlOtpVerification(String otp, String src) {
        Context context = new Context();

        context.setVariable("otp", otp);
        context.setVariable("duration", OTP_TTL.toMinutes() + " minutes");
        context.setVariable("appName", appName);

        return templateEngine.process(src, context);
    }

    public String templateSubjectOtpVerification(String subject) {
        return String.format(
                "%s %s",
                this.appName,
                subject
        );
    }

    public String getContentHtmlOtpVerificationCreateAccount(String otp) {
        return templateContentHtmlOtpVerification(otp, "email/otp-verification-create-account");
    }

    public String getContentHtmlOtpVerificationForgotPassword(String otp) {
        return templateContentHtmlOtpVerification(otp, "email/otp-verification-forgot-password");
    }

    public String getSubjectOtpVerificationCreateAccount() {
        return templateSubjectOtpVerification("Verify Your Account");
    }
    public String getSubjectOtpVerificationForgotPassword() {
        return templateSubjectOtpVerification("Password Reset Verification Code");
    }
}
