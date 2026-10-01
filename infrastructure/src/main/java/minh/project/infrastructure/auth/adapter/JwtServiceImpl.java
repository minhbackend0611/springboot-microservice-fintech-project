package minh.project.infrastructure.auth.adapter;

import lombok.RequiredArgsConstructor;
import minh.project.application.auth.port.IJwtService;
import minh.project.infrastructure.shared.security.JwtUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements IJwtService {

    private final JwtUtils jwtUtils;


    @Override
    public String generateAccessToken(String email) {
        return jwtUtils.generateAccessToken(email);
    }

    @Override
    public String generateRefreshToken(String email) {
        return jwtUtils.generateRefreshToken(email);
    }
}
