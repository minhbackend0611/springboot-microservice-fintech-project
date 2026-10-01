package minh.project.infrastructure.noti.consumer;

import minh.project.application.noti.command.SendEmailUseCase;
import minh.project.infrastructure.noti.content_html.AuthContentHtmlEmail;
import minh.project.infrastructure.shared.event.SendOtpMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SendOtpForgotPasswordConsumer extends SendOtpConsumer {

    private final String topic = "auth.send-otp-forgot-password";

    protected SendOtpForgotPasswordConsumer(
            SendEmailUseCase sendEmailUseCase,
            AuthContentHtmlEmail authContentHtmlEmail) {
        super(sendEmailUseCase, authContentHtmlEmail);
    }

    @Override
    @KafkaListener(topics = topic, groupId = groupId)
    public void consume(SendOtpMessage event) {
        super.consume(event);
    }



    @Override
    public String getContentHtml(String otp) {
        return authContentHtmlEmail.getContentHtmlOtpVerificationForgotPassword(otp);
    }

    @Override
    public String getSubject() {
        return authContentHtmlEmail.getSubjectOtpVerificationForgotPassword();
    }
}
