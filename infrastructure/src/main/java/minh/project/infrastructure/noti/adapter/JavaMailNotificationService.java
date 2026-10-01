package minh.project.infrastructure.noti.adapter;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minh.project.application.noti.port.EmailNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JavaMailNotificationService implements EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmail(String toEmail, String subject, String contentHtml) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);

            helper.setText(contentHtml, true);

            mailSender.send(mimeMessage);
            log.info("The OTP email has been successfully sent to: {}", toEmail);

        } catch (Exception e) {
            log.error("Serious error when sending HTML email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Email sending failed.", e);
        }
    }
}