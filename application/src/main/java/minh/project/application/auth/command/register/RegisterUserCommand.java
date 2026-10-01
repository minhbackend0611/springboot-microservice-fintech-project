package minh.project.application.auth.command.register;

import lombok.Builder;

@Builder
public record RegisterUserCommand(String email, String password) {}
