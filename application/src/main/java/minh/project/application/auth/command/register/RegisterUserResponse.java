package minh.project.application.auth.command.register;

import lombok.Builder;
import minh.project.application.auth.dto.UserInfoDTO;

@Builder
public record RegisterUserResponse(UserInfoDTO user, String accessToken, String refreshToken) {
}
