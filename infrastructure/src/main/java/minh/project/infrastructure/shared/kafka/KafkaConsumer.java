package minh.project.infrastructure.shared.kafka;

public interface KafkaConsumer<T> {

    void consume(T data);
}
