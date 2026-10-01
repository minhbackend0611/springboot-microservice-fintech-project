package minh.project.infrastructure.auth.producer.impl;

import minh.project.infrastructure.auth.producer.SendOtpProducer;
import minh.project.infrastructure.shared.event.SendOtpMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendOtpCreateAccountProducer extends SendOtpProducer {

    public SendOtpCreateAccountProducer(KafkaTemplate<String, SendOtpMessage> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public String topic() {
        return "auth.send-otp-create-account";
    }
}
