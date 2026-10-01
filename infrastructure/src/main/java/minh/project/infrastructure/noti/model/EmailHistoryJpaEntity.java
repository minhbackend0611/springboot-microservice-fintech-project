package minh.project.infrastructure.noti.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import minh.project.domain.noti.vo.EmailStatus;
import minh.project.domain.noti.vo.EmailType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "email_histories", schema = "noti")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert
public class EmailHistoryJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column(name = "eventId", updatable = false, nullable = false)
    UUID eventId;

    @Column(name = "email", length = 256, nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "VARCHAR(20)", nullable = false)
    EmailType type;

    @Column(name = "subject", length = 256, nullable = false)
    String subject;

    @Column(name = "content_html", nullable = false, columnDefinition = "TEXT")
    String contentHtml;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    EmailStatus status;

    @Column(name = "retry_count")
    Integer retryCount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "provider_response", columnDefinition = "jsonb")
    Map<String, Object> providerResponse;

    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    public void markAsSuccess() {
        this.status = EmailStatus.SUCCESS;
    }

    public void markAsFailed() {
        this.status = EmailStatus.FAILED;
    }

    public boolean isRetryable() {
        if (this.retryCount >= 3) {
            this.status = EmailStatus.RETRIES_EXCEEDED;
            return false;
        }
        this.retryCount++;
        return true;
    }

}
