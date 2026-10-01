package minh.project.infrastructure.noti.repository;

import lombok.RequiredArgsConstructor;
import minh.project.infrastructure.noti.jpa.EmailHistoryJpaRepository;
import minh.project.infrastructure.noti.model.EmailHistoryJpaEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailHistoryFetcher {

    private final EmailHistoryJpaRepository emailHistoryJpaRepository;

    @Transactional
    public List<EmailHistoryJpaEntity> fetchOtpTypePendingOrFailedForUpdate() {
        return emailHistoryJpaRepository.findByTypeToSendForUpdate("OTP_EMAIL");
    }

}
