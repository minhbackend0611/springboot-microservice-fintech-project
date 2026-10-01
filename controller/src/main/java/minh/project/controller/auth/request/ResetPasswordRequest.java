package minh.project.controller.auth.request;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @NotBlank
        String email,
        @NotBlank
        String newPassword) {
}
