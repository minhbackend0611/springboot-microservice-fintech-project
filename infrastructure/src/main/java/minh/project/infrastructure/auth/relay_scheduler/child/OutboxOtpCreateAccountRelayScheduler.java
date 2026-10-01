package minh.project.infrastructure.auth.relay_scheduler.child;

import minh.project.infrastructure.auth.model.outbox.AuthOutboxEvent;
import minh.project.infrastructure.auth.processor.impl.OutboxOtpCreateAccountEventProcessor;
import minh.project.infrastructure.auth.relay_scheduler.OutboxRelayScheduler;
import minh.project.infrastructure.auth.repository.AuthOutboxFetcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OutboxOtpCreateAccountRelayScheduler extends OutboxRelayScheduler<OutboxOtpCreateAccountEventProcessor> {
    public OutboxOtpCreateAccountRelayScheduler(OutboxOtpCreateAccountEventProcessor processor, AuthOutboxFetcher authOutboxFetcher) {
        super(processor, authOutboxFetcher);
    }

    @Override
    @Scheduled(fixedDelay = 5_000)
    public void relay() {
        super.relay();
    }
    @Override
    @Transactional
    public List<AuthOutboxEvent> fetchPending() {
        return authOutboxFetcher.fetchCreateAccountPendingForUpdate();
    }

    @Override
    protected void processEvent(AuthOutboxEvent authOutboxEvent) {
        processor.process(authOutboxEvent);
    }
}
