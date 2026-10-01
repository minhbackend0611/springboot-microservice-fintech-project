package minh.project.controller.auth.http;

import lombok.RequiredArgsConstructor;
import minh.project.application.auth.command.login.LoginUserUseCase;
import minh.project.controller.auth.AuthEndpoint;
import minh.project.controller.shared.ratelimit.KeyRateLimit;
import minh.project.controller.shared.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@AuthEndpoint
@RequiredArgsConstructor
public class AuthQueryController {

    private final LoginUserUseCase loginUserUseCase;

    @GetMapping("/welcome")
    @KeyRateLimit(key = "/welcome", capacity = 3, refillRate = 1)
    public ResponseEntity<ApiResponse<?>> welcome() {
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "Welcome")));
    }

    @GetMapping("/welcome-user")
    public ResponseEntity<ApiResponse<?>> welcomeUser() {
        return ResponseEntity.ok(ApiResponse.success(Map.of("message", "Welcome user")));
    }

}
