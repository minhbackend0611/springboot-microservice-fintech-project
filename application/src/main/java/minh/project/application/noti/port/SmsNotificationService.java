package minh.project.application.noti.port;

public interface SmsNotificationService {

    void sendSms(String email, String content);

}
