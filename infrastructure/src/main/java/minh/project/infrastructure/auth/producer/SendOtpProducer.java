package minh.project.infrastructure.auth.producer;

import lombok.extern.slf4j.Slf4j;
import minh.project.infrastructure.shared.event.SendOtpMessage;
import minh.project.infrastructure.shared.kafka.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;


@Slf4j
public abstract class SendOtpProducer extends KafkaProducer<SendOtpMessage> {

    public SendOtpProducer(KafkaTemplate<String, SendOtpMessage> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public void sendMessage(SendOtpMessage event) {
        super.sendMessage(event);
    }

    @Override
    public abstract String topic();

}
