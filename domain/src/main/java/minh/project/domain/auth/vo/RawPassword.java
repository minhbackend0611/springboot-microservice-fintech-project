package minh.project.domain.auth.vo;

import minh.project.domain.shared.error.ErrorCode;
import minh.project.domain.shared.exception.DomainException;

public record RawPassword(String value) {

    private static final String STRONG_PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";

    public RawPassword {
        if (value == null || value.trim().isEmpty() || !value.matches(STRONG_PASSWORD_PATTERN)) {
            throw new DomainException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
