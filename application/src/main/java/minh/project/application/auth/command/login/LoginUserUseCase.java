package minh.project.application.auth.command.login;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import minh.project.application.auth.dto.UserInfoDTO;
import minh.project.application.auth.mapper.AuthMapper;
import minh.project.application.auth.port.IJwtService;
import minh.project.application.auth.port.IPasswordHasher;
import minh.project.application.auth.support.UserFinder;
import minh.project.domain.auth.model.User;
import minh.project.domain.auth.repository.UserRepository;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.shared.result.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final IPasswordHasher passwordHasher;
    private final IJwtService jwtService;
    private final AuthMapper authMapper;
    private final UserFinder userFinder;

    @Value("${app.security.login.lock_duration_times}")
    private Duration LOCK_DURATION_TIMES;

    @Value("${app.security.login.max_attempts}")
    private int MAX_ATTEMPTS;

    @Value("${app.security.login.max_cycle_attempts}")
    private int MAX_CYCLE_ATTEMPTS;

    @Transactional
    public Result<LoginUserResponse> execute(LoginUserCommand command) {

        Email email = new Email(command.email());

        User user = userFinder.loadByEmailOrThrow(email);

        Result<Void> result = user.authenticate(command.password(), passwordHasher::matches,
                LOCK_DURATION_TIMES, MAX_ATTEMPTS, MAX_CYCLE_ATTEMPTS);

        userRepository.save(user);

        if (result.isFailure()) return result.map(null);

        UserInfoDTO userInfoDTO = authMapper.toDTO(user);

        String accessToken = jwtService.generateAccessToken(user.getEmail().value());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail().value());

        return result.map(LoginUserResponse.builder()
                        .user(userInfoDTO)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build());
    }
}
