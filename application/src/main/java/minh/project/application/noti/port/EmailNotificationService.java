package minh.project.application.noti.port;

public interface EmailNotificationService {
    void sendEmail(String email, String subject, String contentHtml);
}
