package minh.project.infrastructure.auth.processor.impl;

import minh.project.infrastructure.auth.jpa.AuthOutboxEventJpaRepository;
import minh.project.infrastructure.auth.producer.impl.SendOtpCreateAccountProducer;
import org.springframework.stereotype.Component;

@Component
public class OutboxOtpCreateAccountEventProcessor
        extends OutboxOtpEventProcessor<SendOtpCreateAccountProducer> {

    public OutboxOtpCreateAccountEventProcessor(
            SendOtpCreateAccountProducer producer,
            AuthOutboxEventJpaRepository authOutboxEventJpaRepository) {
        super(producer, authOutboxEventJpaRepository);
    }

}
