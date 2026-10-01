package minh.project.domain.auth.service;

import lombok.RequiredArgsConstructor;
import minh.project.domain.auth.repository.UserRepository;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.shared.error.ErrorCode;
import minh.project.domain.shared.exception.DomainException;

@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;

    public void enforceEmailUniqueness(Email email) {
        if (userRepository.existsByEmail(email.value())) {
            throw new DomainException(ErrorCode.EMAIL_IS_ALREADY_IN_USE);
        }
    }

}
