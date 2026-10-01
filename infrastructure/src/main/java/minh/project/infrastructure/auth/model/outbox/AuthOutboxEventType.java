package minh.project.infrastructure.auth.model.outbox;

import minh.project.infrastructure.shared.outbox.OutboxEventType;

public enum AuthOutboxEventType implements OutboxEventType {

    OTP_REQUESTED_CREATE_ACCOUNT, OTP_REQUESTED_FORGOT_PASSWORD;

    @Override
    public String getValue() {
        return this.name();
    }
}
