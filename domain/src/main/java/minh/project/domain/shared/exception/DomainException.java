package minh.project.domain.shared.exception;

import minh.project.domain.shared.error.ErrorCode;

public class DomainException extends BaseException {
    public DomainException(ErrorCode errorCode) {
        super(errorCode);
    }
}
