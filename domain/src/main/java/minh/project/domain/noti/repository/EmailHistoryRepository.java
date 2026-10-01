package minh.project.domain.noti.repository;

import minh.project.domain.noti.model.EmailHistory;

import java.util.Optional;
import java.util.UUID;

public interface EmailHistoryRepository {
    EmailHistory save(EmailHistory emailHistory);
    Optional<EmailHistory> findByEventId(UUID eventId);
}
