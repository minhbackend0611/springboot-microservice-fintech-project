package minh.project.application.auth.port;

public interface IPasswordHasher {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);
}
