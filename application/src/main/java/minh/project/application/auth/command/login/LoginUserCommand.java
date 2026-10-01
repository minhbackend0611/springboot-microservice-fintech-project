package minh.project.application.auth.command.login;

import lombok.Builder;

@Builder
public record LoginUserCommand(String email, String password){}
