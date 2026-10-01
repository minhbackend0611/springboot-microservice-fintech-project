package minh.project.infrastructure.noti.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minh.project.application.noti.port.EmailNotificationService;
import minh.project.infrastructure.noti.jpa.EmailHistoryJpaRepository;
import minh.project.infrastructure.noti.model.EmailHistoryJpaEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Component
public class SendEmailService {

    private final EmailHistoryJpaRepository emailHistoryJpaRepository;

    private final EmailNotificationService emailNotificationService;

    @Transactional
    public void process(EmailHistoryJpaEntity emailHistoryJpaEntity) {

        if (emailHistoryJpaEntity.isRetryable()) {

            try {

                emailNotificationService.sendEmail(
                        emailHistoryJpaEntity.getEmail(),
                        emailHistoryJpaEntity.getSubject(),
                        emailHistoryJpaEntity.getContentHtml()
                );

                log.info("DEBUG SEND EMAIL HERE!!!");

                emailHistoryJpaEntity.markAsSuccess();

            } catch (Exception e) {

                emailHistoryJpaEntity.markAsFailed();

                log.debug("DEBUG FAILED SEND EMAIL: {}", e.getMessage());

            }

        }

        emailHistoryJpaRepository.save(emailHistoryJpaEntity);

    }

}
