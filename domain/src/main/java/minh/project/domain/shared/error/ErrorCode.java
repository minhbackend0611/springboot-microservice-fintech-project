package minh.project.domain.shared.error;
public enum ErrorCode {

    // DomainError -> Prefix 2xxxx
    INVALID_PASSWORD(24000, "Your length password is required from 8 to 20 characters. Your password must contain at least one A-Z, a-z, 0-9 and a special character"),
    INVALID_EMAIL(24001, "Invalid gmail"),
    EMAIL_IS_ALREADY_IN_USE(24002, "Your email is already in use"),
    INVALID_CREDENTIALS(24003, "Your email/password is invalid"),
    LOGIN_TEMPORARILY_LOCKED(
    24004,
            "Your account has been temporarily locked due to multiple failed login attempts"
    ),

    LOGIN_PERMANENTLY_LOCKED(
    24005,
            "Your account has been locked for security reasons. Please contact support"
    ),

    OTP_NOT_FOUND(24006, "No valid OTP was found. Please request a new one."),
    OTP_MISMATCH(24007, "OTP is incorrect. Please try again."),
    OTP_RATE_LIMIT(24008, "Too many OTP requests, please wait 5 minutes"),
    // AppError -> Prefix 1xxxx

    USER_NOT_FOUND(14001, "User is not found"),
    EMAIL_NOT_VERIFIED(14002, "Email is not verified. Please verify your email before continuing."),
    DUPLICATE_OTP_EMAIL(14003, "OTP is duplicated."),
    OUTBOX_EVENT_NOT_FOUND(14004, "Outbox event not found"),
    LOST_CONTEXT_EMAIL(14005, "Email is not verified. Please verify your email before continuing."),
    TOO_MANY_REQUESTS(14006, "Too many request");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}