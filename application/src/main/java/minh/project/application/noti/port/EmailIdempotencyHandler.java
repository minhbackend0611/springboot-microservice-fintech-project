package minh.project.application.noti.port;

import java.util.UUID;

public interface EmailIdempotencyHandler {

    boolean handlerIdempotencyByEventId(UUID eventId);

}
