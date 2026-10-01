package minh.project.infrastructure.noti.jpa;

import minh.project.infrastructure.noti.model.EmailHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmailHistoryJpaRepository extends JpaRepository<EmailHistoryJpaEntity, UUID> {

    @Query(value = """
            SELECT * FROM noti.email_histories
            WHERE (type = :type)
            AND
            (status IN ('PENDING', 'FAILED')) 
            ORDER BY created_at
            LIMIT 50
            FOR UPDATE
            SKIP LOCKED
            """, nativeQuery = true)
    List<EmailHistoryJpaEntity> findByTypeToSendForUpdate(@Param("type") String type);

    EmailHistoryJpaEntity save(EmailHistoryJpaEntity emailHistoryJpaEntity);

    Optional<EmailHistoryJpaEntity> findByEventId(UUID eventId);

}
