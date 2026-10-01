package minh.project.infrastructure.noti.relay_scheduler;

import minh.project.infrastructure.noti.model.EmailHistoryJpaEntity;
import minh.project.infrastructure.noti.repository.EmailHistoryFetcher;
import minh.project.infrastructure.noti.service.SendEmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendEmailOtpRelayScheduler extends SendEmailRelayScheduler {


    public SendEmailOtpRelayScheduler(EmailHistoryFetcher emailHistoryFetcher, SendEmailService sendEmailService) {
        super(emailHistoryFetcher, sendEmailService);
    }

    @Scheduled(fixedDelay = 3_000)
    public void relay() {
        super.relay();
    }

    @Override
    public List<EmailHistoryJpaEntity> fetch() {
        return emailHistoryFetcher.fetchOtpTypePendingOrFailedForUpdate();
    }
}
