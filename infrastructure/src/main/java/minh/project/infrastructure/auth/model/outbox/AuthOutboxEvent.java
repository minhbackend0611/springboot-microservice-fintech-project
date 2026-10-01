package minh.project.infrastructure.auth.model.outbox;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import minh.project.infrastructure.shared.outbox.AbstractOutboxEvent;

import java.util.Map;


@Entity
@Table(name = "outbox_events", schema = "auth")
public class AuthOutboxEvent extends AbstractOutboxEvent<AuthOutboxStatus, AuthOutboxEventType> {

    @Override
    public AuthOutboxStatus getConcreteStatus() {
        return AuthOutboxStatus.valueOf(this.getStatus());
    }

    @Override
    public AuthOutboxEventType getConcreteEventType() {
        return AuthOutboxEventType.valueOf(this.getEventType());
    }

    public static AuthOutboxEvent create(String aggregateType, String aggregateId, String eventType, Map<String, Object> payload) {
        AuthOutboxEvent event = new AuthOutboxEvent();
        event.initializeBase(aggregateType, aggregateId, eventType, payload, AuthOutboxStatus.PENDING);
        return event;
    }

    public void markAsPublished() {
        this.setConcreteStatus(AuthOutboxStatus.PUBLISHED);
    }

    public void markAsFailed() {
        this.setConcreteStatus(AuthOutboxStatus.FAILED);
    }
}