package minh.project.infrastructure.auth.jpa;

import minh.project.infrastructure.auth.model.outbox.AuthOutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface AuthOutboxEventJpaRepository extends JpaRepository<AuthOutboxEvent, UUID> {

    @Query(value = """
            SELECT * FROM auth.outbox_events        
            WHERE status = :status AND event_type = :eventType
            ORDER BY created_at
            LIMIT 50
            FOR UPDATE 
            SKIP LOCKED
            """, nativeQuery = true)
    List<AuthOutboxEvent> findByStatusAndEventTypeForUpdate(
            @Param("status") String status,
            @Param("eventType") String eventType
    );


}
