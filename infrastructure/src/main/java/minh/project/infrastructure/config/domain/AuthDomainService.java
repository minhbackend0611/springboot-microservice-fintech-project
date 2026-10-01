package minh.project.infrastructure.config.domain;

import minh.project.domain.auth.repository.UserRepository;
import minh.project.domain.auth.service.UserDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthDomainService {

    @Bean
    public UserDomainService userDomainService(UserRepository userRepository) {
        return new UserDomainService(userRepository);
    }

}
