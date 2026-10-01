package minh.project.infrastructure.shared.outbox;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class AbstractOutboxEvent<S extends OutboxStatus, T extends OutboxEventType> {

    @Id
    private UUID id;
    private String aggregateType;
    private String aggregateId;
    private String eventType;

    @JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "jsonb")
    private Map<String, Object> payload;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    private Instant createdAt;

    @Version
    private Integer version;

    public abstract S getConcreteStatus();

    public abstract T getConcreteEventType();

    protected void setConcreteStatus(S newStatus) {
        this.status = newStatus.getValue();
    }

    protected void initializeBase(String aggregateType, String aggregateId, String eventType, Map<String, Object> payload, S initialStatus) {
        this.id = Generators.timeBasedEpochGenerator().generate();
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = initialStatus.getValue();
        this.createdAt = Instant.now();
    }
}