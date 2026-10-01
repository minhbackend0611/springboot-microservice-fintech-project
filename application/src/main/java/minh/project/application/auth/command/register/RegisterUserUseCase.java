package minh.project.application.auth.command.register;


import minh.project.application.auth.dto.UserInfoDTO;
import minh.project.application.auth.mapper.AuthMapper;
import minh.project.application.auth.port.EmailContextManager;
import minh.project.application.auth.port.IJwtService;
import minh.project.application.auth.port.IPasswordHasher;
import minh.project.application.shared.port.IdGeneratorService;
import minh.project.domain.auth.model.User;
import minh.project.domain.auth.repository.UserRepository;
import minh.project.domain.auth.service.UserDomainService;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.auth.vo.RawPassword;
import minh.project.domain.shared.result.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserUseCase {

    public RegisterUserUseCase(
            UserRepository userRepository,
            IPasswordHasher passwordHasher,
            AuthMapper authMapper,
            IJwtService jwtService,
            UserDomainService userDomainService,
            @Qualifier("contextEmailCreateAccountAdapter")
            EmailContextManager emailContextManager,
            IdGeneratorService idGeneratorService) {

        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.authMapper = authMapper;
        this.jwtService = jwtService;
        this.userDomainService = userDomainService;
        this.emailContextManager = emailContextManager;
        this.idGeneratorService = idGeneratorService;
    }

    private final UserRepository userRepository;

    private final IPasswordHasher passwordHasher;

    private final IJwtService jwtService;

    private final AuthMapper authMapper;

    private final UserDomainService userDomainService;

    private final EmailContextManager emailContextManager;

    private final IdGeneratorService idGeneratorService;

    @Transactional
    public Result<RegisterUserResponse> execute(RegisterUserCommand registerUserCommand) {

        Email email = new Email(registerUserCommand.email());

        emailContextManager.verifyContext(email.value());

        RawPassword rawPassword = new RawPassword(registerUserCommand.password());

        userDomainService.enforceEmailUniqueness(email);

        String passwordHash = passwordHasher.hash(rawPassword.value());

        User domainUser = new User(idGeneratorService.nextId(), email, passwordHash);

        userRepository.save(domainUser);

        UserInfoDTO userInfoDTO = authMapper.toDTO(domainUser);

        String accessToken = jwtService.generateAccessToken(email.value());
        String refreshToken = jwtService.generateRefreshToken(email.value());

        emailContextManager.deleteEmail(email.value());


        return Result.success(RegisterUserResponse.builder()
                .user(userInfoDTO)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

    }

}
