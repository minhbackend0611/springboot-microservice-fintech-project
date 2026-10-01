package minh.project.application.noti.command;

import java.util.UUID;

public record SendEmailCommand(UUID eventId, String email, String subject, String contentHtml) {
}
