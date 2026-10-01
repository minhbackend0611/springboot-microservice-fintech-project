package minh.project.application.auth.port;

import java.util.Map;

public interface IOutboxEventPublisher {

    void publish(
            String aggregateType,
            String aggregateId,
            String eventType,
            Map<String, Object> payload
    );

}
