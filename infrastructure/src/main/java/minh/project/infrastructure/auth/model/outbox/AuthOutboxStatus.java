package minh.project.infrastructure.auth.model.outbox;

import minh.project.infrastructure.shared.outbox.OutboxStatus;

public enum AuthOutboxStatus implements OutboxStatus {
    PENDING, PUBLISHED, FAILED;

    @Override
    public String getValue() { return this.name(); }
}
