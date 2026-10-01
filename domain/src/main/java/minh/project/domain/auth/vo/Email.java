package minh.project.domain.auth.vo;
import minh.project.domain.shared.error.ErrorCode;
import minh.project.domain.shared.exception.DomainException;

public record Email(String value) {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public Email {
        if (value == null || value.trim().isEmpty() || !value.matches(EMAIL_PATTERN)) {
            throw new DomainException(ErrorCode.INVALID_EMAIL);
        }
    }
}
