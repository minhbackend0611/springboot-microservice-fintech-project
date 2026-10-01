package minh.project.domain.auth.model;


import lombok.Getter;
import lombok.Setter;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.auth.vo.Role;
import minh.project.domain.auth.vo.Status;
import minh.project.domain.shared.error.ErrorCode;
import minh.project.domain.shared.result.Result;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;

@Getter
@Setter
public class User {

    private UUID userId;

    private Email email;

    private Integer failedAttemptCount;

    private Integer failedCycleCount;

    private String passwordHash;

    private String pinHash;

    private Role role;

    private Boolean isVerified;

    private String customerId;

    private Status status;

    private Instant lockedAt;

    public User(UUID userId, Email email, String passwordHash) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.failedAttemptCount = 0;
        this.role = Role.USER;
        this.isVerified = false;
    }

    public Result<Void> authenticate(String rawPassword, BiFunction<String, String, Boolean> passwordMatcher,
                                     final Duration LOCK_DURATION_TIME, final int MAX_ATTEMPTS, final int MAX_CYCLE_ATTEMPTS) {

        if (this.status == Status.PERMANENTLY_LOCKED) {
            return Result.failure(ErrorCode.LOGIN_PERMANENTLY_LOCKED);
        }

        if (this.status == Status.TEMPORARILY_LOCKED) {
            if (lockedAt.plus(LOCK_DURATION_TIME).isBefore(Instant.now())) {
                this.failedAttemptCount -= 1;
            }
            else {
                return Result.failure(ErrorCode.LOGIN_TEMPORARILY_LOCKED);
            }
        }

        boolean isMatch = passwordMatcher.apply(rawPassword, this.passwordHash);

        if (!isMatch) {
            this.failedAttemptCount++;
            if (this.failedAttemptCount >= 5) {
                this.failedCycleCount++;
                if (failedCycleCount >= 3) {
                    this.status = Status.PERMANENTLY_LOCKED;
                    return Result.failure(ErrorCode.LOGIN_PERMANENTLY_LOCKED);
                }
                else {
                    this.status = Status.TEMPORARILY_LOCKED;
                    this.lockedAt = Instant.now();
                    return Result.failure(ErrorCode.LOGIN_TEMPORARILY_LOCKED);
                }
            }
            return Result.failure(ErrorCode.INVALID_CREDENTIALS);
        }
        this.failedAttemptCount = 0;
        this.failedCycleCount = 0;
        this.status = Status.ACTIVE;
        this.lockedAt = null;

        return Result.success();
    }
}

