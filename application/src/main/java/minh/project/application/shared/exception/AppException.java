package minh.project.application.shared.exception;

import minh.project.domain.shared.exception.BaseException;
import minh.project.domain.shared.error.ErrorCode;

public class AppException extends BaseException {
    public AppException(ErrorCode errorCode) {
        super(errorCode);
    }
}
