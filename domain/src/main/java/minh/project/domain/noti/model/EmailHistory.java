package minh.project.domain.noti.model;

import lombok.Getter;
import lombok.Setter;
import minh.project.domain.noti.vo.EmailStatus;
import minh.project.domain.noti.vo.EmailType;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class EmailHistory {
    private UUID id;
    private UUID eventId;
    private String email;
    private String subject;
    private String contentHtml;
    private EmailType type;
    private EmailStatus status;
    private Integer retryCount;
    Map<String, Object> providerResponse;
    private final Instant createdAt;

    public EmailHistory(UUID id, UUID eventId, String email, String subject, String contentHtml, EmailType type) {
        this.id = id;
        this.eventId = eventId;
        this.email = email;
        this.subject = subject;
        this.contentHtml = contentHtml;
        this.type = type;
        this.status = EmailStatus.PENDING;
        this.retryCount = 0;
        this.createdAt = Instant.now();
    }

    public void markAsSent() {
        this.status = EmailStatus.SUCCESS;
    }

    public void setResponse(Map<String, Object> response) {
        this.providerResponse = response;
    }

    public boolean canRetry(int maxRetryAttempts) {
        return this.status == EmailStatus.FAILED && this.retryCount < maxRetryAttempts;
    }

}
