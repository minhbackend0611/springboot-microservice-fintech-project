package minh.project.domain.auth.port;

public interface IOtpGenerator {

    String generateOtp(int length);

}
