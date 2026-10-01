package minh.project.infrastructure.auth.processor.impl;

import minh.project.infrastructure.auth.jpa.AuthOutboxEventJpaRepository;
import minh.project.infrastructure.auth.producer.impl.SendOtpForgotPasswordProducer;
import org.springframework.stereotype.Component;

@Component
public class OutboxOtpForgotPasswordEventProcessor extends OutboxOtpEventProcessor<SendOtpForgotPasswordProducer> {

    public OutboxOtpForgotPasswordEventProcessor(
            SendOtpForgotPasswordProducer producer,
            AuthOutboxEventJpaRepository authOutboxEventJpaRepository) {
        super(producer, authOutboxEventJpaRepository);
    }

}
