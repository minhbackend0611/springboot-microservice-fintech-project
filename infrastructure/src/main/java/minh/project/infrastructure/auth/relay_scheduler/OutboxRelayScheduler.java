package minh.project.infrastructure.auth.relay_scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minh.project.infrastructure.auth.model.outbox.AuthOutboxEvent;
import minh.project.infrastructure.auth.processor.AuthOutboxEventProcessor;
import minh.project.infrastructure.auth.repository.AuthOutboxFetcher;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class OutboxRelayScheduler<T extends AuthOutboxEventProcessor> {

    protected final T processor;

    protected final AuthOutboxFetcher authOutboxFetcher;

    protected void relay() {

        List<AuthOutboxEvent> authOutboxEventList = fetchPending();

        if (authOutboxEventList.isEmpty()) return;

        for (var event : authOutboxEventList) {
            try {
                this.processEvent(event);
            } catch (Exception e) {
                log.debug("DEBUG IN PROCESS EVENT LOOP: {}", e.getMessage());
            }
        }

    }

    protected abstract List<AuthOutboxEvent> fetchPending();
    protected abstract void processEvent(AuthOutboxEvent authOutboxEvent);
}

