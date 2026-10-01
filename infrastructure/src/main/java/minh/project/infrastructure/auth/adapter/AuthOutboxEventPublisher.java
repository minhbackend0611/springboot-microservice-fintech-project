package minh.project.infrastructure.auth.adapter;

import lombok.RequiredArgsConstructor;
import minh.project.application.auth.port.IOutboxEventPublisher;
import minh.project.infrastructure.auth.jpa.AuthOutboxEventJpaRepository;
import minh.project.infrastructure.auth.model.outbox.AuthOutboxEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthOutboxEventPublisher implements IOutboxEventPublisher {

    private final AuthOutboxEventJpaRepository authOutboxEventJpaRepository;

    @Override
    public void publish(String aggregateType, String aggregateId, String eventType, Map<String, Object> payload) {

        AuthOutboxEvent authOutboxEvent = AuthOutboxEvent.create(aggregateType, aggregateId, eventType, payload);

        authOutboxEventJpaRepository.save(authOutboxEvent);

    }
}
