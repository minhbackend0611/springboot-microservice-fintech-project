package minh.project.application.noti.command;

import lombok.RequiredArgsConstructor;
import minh.project.application.noti.port.EmailIdempotencyHandler;
import minh.project.application.shared.port.IdGeneratorService;
import minh.project.domain.noti.model.EmailHistory;
import minh.project.domain.noti.repository.EmailHistoryRepository;
import minh.project.domain.noti.vo.EmailType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SendEmailUseCase {

    private final EmailHistoryRepository emailHistoryRepository;

    private final EmailIdempotencyHandler emailIdempotencyHandler;

    private final IdGeneratorService idGeneratorService;

    @Transactional
    public void execute(SendEmailCommand sendOtpEmailCommand) {

        if (emailIdempotencyHandler.handlerIdempotencyByEventId(sendOtpEmailCommand.eventId())) return;

        EmailHistory emailHistory = new EmailHistory(
                idGeneratorService.nextId(),
                sendOtpEmailCommand.eventId(),
                sendOtpEmailCommand.email(),
                sendOtpEmailCommand.subject(),
                sendOtpEmailCommand.contentHtml(),
                EmailType.OTP_EMAIL
        );

        emailHistoryRepository.save(emailHistory);
    }


}
