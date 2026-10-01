package minh.project.infrastructure.config.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AuthTopicConfig {

    private final static String prefixTopic = "auth";

    private static NewTopic templateTopic(String postfix) {
        return TopicBuilder.name(prefixTopic + postfix)
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic sendOtpCreateAccountOutboxTopic() {
        return templateTopic(".send-otp-create-account");
    }

    @Bean
    public NewTopic sendOtpForgotPasswordOutboxTopic() {
        return templateTopic(".send-otp-forgot-password");
    }

}
