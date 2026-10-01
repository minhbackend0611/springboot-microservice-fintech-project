package minh.project.infrastructure.auth.processor;

import minh.project.infrastructure.auth.model.outbox.AuthOutboxEvent;

public interface AuthOutboxEventProcessor {

    void process(AuthOutboxEvent event);

}
