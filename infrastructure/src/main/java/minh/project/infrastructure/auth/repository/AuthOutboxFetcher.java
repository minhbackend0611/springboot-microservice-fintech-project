package minh.project.infrastructure.auth.repository;

import lombok.RequiredArgsConstructor;
import minh.project.infrastructure.auth.jpa.AuthOutboxEventJpaRepository;
import minh.project.infrastructure.auth.model.outbox.AuthOutboxEvent;
import minh.project.infrastructure.auth.model.outbox.AuthOutboxEventType;
import minh.project.infrastructure.auth.model.outbox.AuthOutboxStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthOutboxFetcher {

    private final AuthOutboxEventJpaRepository authOutboxEventJpaRepository;

    @Transactional
    public List<AuthOutboxEvent> fetchCreateAccountPendingForUpdate() {
        return authOutboxEventJpaRepository.findByStatusAndEventTypeForUpdate(
                AuthOutboxStatus.PENDING.name(), AuthOutboxEventType.OTP_REQUESTED_CREATE_ACCOUNT.name()
        );
    }

    @Transactional
    public List<AuthOutboxEvent> fetchForgotPasswordPendingForUpdate() {
        return authOutboxEventJpaRepository.findByStatusAndEventTypeForUpdate(
                AuthOutboxStatus.PENDING.name(), AuthOutboxEventType.OTP_REQUESTED_FORGOT_PASSWORD.name()
        );
    }
}
