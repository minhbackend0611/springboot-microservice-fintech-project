package minh.project.infrastructure.noti.repository;

import lombok.RequiredArgsConstructor;
import minh.project.domain.noti.model.EmailHistory;
import minh.project.domain.noti.repository.EmailHistoryRepository;
import minh.project.infrastructure.noti.jpa.EmailHistoryJpaRepository;
import minh.project.infrastructure.noti.mapper.EmailPersistenceMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailHistoryRepositoryImpl implements EmailHistoryRepository {

    private final EmailHistoryJpaRepository emailHistoryJpaRepository;

    private final EmailPersistenceMapper emailPersistenceMapper;

    @Override
    public EmailHistory save(EmailHistory emailHistory) {
        return emailPersistenceMapper.toDomain(
                emailHistoryJpaRepository.save(
                        emailPersistenceMapper.toInfrastructure(emailHistory)
                )
        );
    }

    @Override
    public Optional<EmailHistory> findByEventId(UUID eventId) {
        return emailHistoryJpaRepository
                .findByEventId(eventId)
                .map(emailPersistenceMapper::toDomain);
    }
}
