package minh.project.application.auth.port;

public interface OtpRateLimiterService {

    boolean isAllowed(String email);

}
