package minh.project.infrastructure.auth.adapter;

import lombok.RequiredArgsConstructor;
import minh.project.domain.auth.port.IOtpGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class SecureOtpGenerator implements IOtpGenerator {

    private final SecureRandom secureRandom = new SecureRandom();

    private static final String DIGITS = "0123456789";

    @Override
    public String generateOtp(int length) {

        StringBuilder otp = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            otp.append(DIGITS.charAt(secureRandom.nextInt(DIGITS.length())));
        }

        return otp.toString();
    }
}
