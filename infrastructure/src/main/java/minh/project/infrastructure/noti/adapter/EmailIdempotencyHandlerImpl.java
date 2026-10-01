package minh.project.infrastructure.noti.adapter;

import lombok.RequiredArgsConstructor;
import minh.project.application.noti.port.EmailIdempotencyHandler;
import minh.project.infrastructure.noti.jpa.EmailHistoryJpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmailIdempotencyHandlerImpl implements EmailIdempotencyHandler {

    private final EmailHistoryJpaRepository emailHistoryJpaRepository;


    @Override
    public boolean handlerIdempotencyByEventId(UUID eventId) {
       return emailHistoryJpaRepository.findByEventId(eventId).isPresent();
    }
}
