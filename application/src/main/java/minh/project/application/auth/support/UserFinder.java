package minh.project.application.auth.support;

import lombok.RequiredArgsConstructor;
import minh.project.application.shared.exception.AppException;
import minh.project.domain.auth.model.User;
import minh.project.domain.auth.repository.UserRepository;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.shared.exception.DomainException;
import minh.project.domain.shared.error.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository userRepository;

    public User loadByEmailOrThrow(Email email) {
        return userRepository.findByEmail(email.value())
                .orElseThrow(() -> new DomainException(ErrorCode.INVALID_CREDENTIALS));
    }

    public User loadByIdOrThrow(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}