package minh.project.controller.auth.request;

import jakarta.validation.constraints.NotBlank;

public record VerifyOtpRequest(
        @NotBlank(message = "Email must not be blank")
        String email,
        @NotBlank(message = "OTP must not be blank")
        String otp
) {
}
