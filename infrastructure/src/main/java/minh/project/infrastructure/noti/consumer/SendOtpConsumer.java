package minh.project.infrastructure.noti.consumer;

import minh.project.application.noti.command.SendEmailCommand;
import minh.project.application.noti.command.SendEmailUseCase;
import minh.project.infrastructure.noti.content_html.AuthContentHtmlEmail;
import minh.project.infrastructure.shared.event.SendOtpMessage;
import minh.project.infrastructure.shared.kafka.KafkaConsumer;

public abstract class SendOtpConsumer implements KafkaConsumer<SendOtpMessage> {

    protected final String groupId = "notification";

    protected final SendEmailUseCase sendEmailUseCase;

    protected final AuthContentHtmlEmail authContentHtmlEmail;

    protected SendOtpConsumer(SendEmailUseCase sendEmailUseCase, AuthContentHtmlEmail authContentHtmlEmail) {
        this.sendEmailUseCase = sendEmailUseCase;
        this.authContentHtmlEmail = authContentHtmlEmail;
    }

    public void consume(SendOtpMessage event) {

        String email = (String) event.payload().get("email");
        String otp = (String) event.payload().get("otp");

        String contentHtml = getContentHtml(otp);

        String subject = getSubject();

        SendEmailCommand sendOtpEmailCommand = new SendEmailCommand(event.eventId(), email, subject, contentHtml);

        sendEmailUseCase.execute(sendOtpEmailCommand);

    }

    public abstract String getContentHtml(String otp);

    public abstract String getSubject();

}

