package minh.project.application.shared.usecase.verifyotp;

public record VerifyOtpCommand(String email, String otp) {
}
