package minh.project.application.auth.port;


public interface IJwtService {
    String generateAccessToken(String email);
    String generateRefreshToken(String email);
}
