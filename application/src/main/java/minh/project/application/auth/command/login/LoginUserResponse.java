package minh.project.application.auth.command.login;

import lombok.Builder;
import minh.project.application.auth.dto.UserInfoDTO;

@Builder
public record LoginUserResponse(
        String accessToken,
        String refreshToken,
        UserInfoDTO user
) {}
