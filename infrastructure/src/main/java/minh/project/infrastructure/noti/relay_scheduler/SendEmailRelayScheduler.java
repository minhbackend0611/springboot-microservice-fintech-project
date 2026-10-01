package minh.project.infrastructure.noti.relay_scheduler;

import lombok.RequiredArgsConstructor;
import minh.project.infrastructure.noti.model.EmailHistoryJpaEntity;
import minh.project.infrastructure.noti.repository.EmailHistoryFetcher;
import minh.project.infrastructure.noti.service.SendEmailService;

import java.util.List;

@RequiredArgsConstructor
public abstract class SendEmailRelayScheduler {

    protected final EmailHistoryFetcher emailHistoryFetcher;

    protected final SendEmailService sendEmailService;

    public void relay() {

        List<EmailHistoryJpaEntity> emailHistoryJpaEntityList = fetch();

        if (emailHistoryJpaEntityList.isEmpty()) return;

        emailHistoryJpaEntityList.forEach(this::processEmails);

    }
    public abstract List<EmailHistoryJpaEntity> fetch();

    public void processEmails(EmailHistoryJpaEntity emailHistoryJpaEntity) {
        sendEmailService.process(emailHistoryJpaEntity);
    }
}
