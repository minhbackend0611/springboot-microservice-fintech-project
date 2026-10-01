package minh.project.infrastructure.shared.event;

import java.util.Map;
import java.util.UUID;

public record SendOtpMessage(UUID eventId, Map<String, Object> payload) {}
