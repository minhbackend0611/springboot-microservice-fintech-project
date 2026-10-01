package minh.project.application.auth.command.reset_password;

import minh.project.application.auth.port.EmailContextManager;
import minh.project.application.auth.port.IPasswordHasher;
import minh.project.application.auth.support.UserFinder;
import minh.project.application.auth.variant.ForgotPassword;
import minh.project.domain.auth.model.User;
import minh.project.domain.auth.repository.UserRepository;
import minh.project.domain.auth.vo.Email;
import minh.project.domain.auth.vo.RawPassword;
import minh.project.domain.shared.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResetPasswordUseCase {

    private final UserFinder userFinder;

    private final EmailContextManager<ForgotPassword> emailContextManager;

    private final IPasswordHasher passwordHasher;

    private final UserRepository userRepository;

    public ResetPasswordUseCase(
            UserFinder userFinder,
            EmailContextManager<ForgotPassword> emailContextManager, IPasswordHasher passwordHasher, UserRepository userRepository) {
        this.userFinder = userFinder;
        this.emailContextManager = emailContextManager;
        this.passwordHasher = passwordHasher;
        this.userRepository = userRepository;
    }

    @Transactional
    public Result<Void> execute(ResetPasswordCommand resetPasswordCommand) {

        Email email = new Email(resetPasswordCommand.email());

        emailContextManager.verifyContext(email.value());

        RawPassword rawPassword = new RawPassword(resetPasswordCommand.newPassword());

        User user = userFinder.loadByEmailOrThrow(email);

        String passwordHash = passwordHasher.hash(rawPassword.value());

        user.setPasswordHash(passwordHash);

        userRepository.save(user);

        emailContextManager.deleteEmail(email.value());

        return Result.success();

    }

}
