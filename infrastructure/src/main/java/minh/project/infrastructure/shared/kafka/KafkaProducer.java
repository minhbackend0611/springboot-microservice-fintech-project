package minh.project.infrastructure.shared.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@RequiredArgsConstructor
public abstract class KafkaProducer<T> {

    protected final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(T event) {

        Message<T> message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic())
                .build();

        kafkaTemplate.send(message);

    }

    protected abstract String topic();

}
