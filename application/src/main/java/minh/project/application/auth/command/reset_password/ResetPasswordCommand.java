package minh.project.application.auth.command.reset_password;

import lombok.Builder;

@Builder
public record ResetPasswordCommand(String email, String newPassword) {
}
