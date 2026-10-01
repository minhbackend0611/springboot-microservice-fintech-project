package minh.project.application.auth.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserInfoDTO(UUID userId, String email, String role,
                          Boolean isActive, Boolean isVerified, String customerId) {

}
