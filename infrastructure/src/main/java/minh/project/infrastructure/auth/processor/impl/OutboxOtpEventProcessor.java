package minh.project.infrastructure.auth.processor.impl;

import lombok.RequiredArgsConstructor;
import minh.project.infrastructure.auth.jpa.AuthOutboxEventJpaRepository;
import minh.project.infrastructure.auth.model.outbox.AuthOutboxEvent;
import minh.project.infrastructure.auth.processor.AuthOutboxEventProcessor;
import minh.project.infrastructure.auth.producer.SendOtpProducer;
import minh.project.infrastructure.shared.event.SendOtpMessage;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class OutboxOtpEventProcessor
        <T extends SendOtpProducer>
        implements AuthOutboxEventProcessor {

    protected final T producer;

    protected final AuthOutboxEventJpaRepository authOutboxEventJpaRepository;

    @Transactional
    public void process(AuthOutboxEvent authOutboxEvent) {

        SendOtpMessage sendOtpMessage = new SendOtpMessage(
                authOutboxEvent.getId(),
                authOutboxEvent.getPayload()
        );

        authOutboxEvent.markAsPublished();

        authOutboxEventJpaRepository.save(authOutboxEvent);

        producer.sendMessage(sendOtpMessage);

    }

}
