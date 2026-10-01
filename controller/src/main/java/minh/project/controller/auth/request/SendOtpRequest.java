package minh.project.controller.auth.request;

import jakarta.validation.constraints.NotBlank;

public record SendOtpRequest(
        @NotBlank
        String email) {

}
