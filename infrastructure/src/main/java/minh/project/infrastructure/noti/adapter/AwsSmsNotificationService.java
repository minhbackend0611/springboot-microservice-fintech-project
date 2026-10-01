package minh.project.infrastructure.noti.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minh.project.application.noti.port.SmsNotificationService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsSmsNotificationService implements SmsNotificationService {

    private final SnsClient snsClient;

    @Override
    public void sendSms(String phoneNumber, String content) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .phoneNumber(phoneNumber)
                    .message("Hello test message")
                    .messageAttributes(Map.of(
                            "AWS.SNS.SMS.SMSType", MessageAttributeValue.builder()
                                    .dataType("String")
                                    .stringValue("Transactional")
                                    .build(),

                            "AWS.SNS.SMS.SenderID", MessageAttributeValue.builder()
                                    .dataType("String")
                                    .stringValue("PayPay")
                                    .build()
                    ))
                    .build();

            PublishResponse response = snsClient.publish(request);

            log.info("[AWS SNS] SMS sent to {} | MessageId: {}",
                    phoneNumber, response.messageId());

        } catch (SnsException e) {
            log.error("[AWS SNS] Failed to send SMS to {} | code: {} | message: {}",
                    phoneNumber, e.awsErrorDetails().errorCode(), e.getMessage());
            throw e;
        }
    }
}

