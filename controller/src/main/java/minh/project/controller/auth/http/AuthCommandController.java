package minh.project.controller.auth.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minh.project.application.auth.command.reset_password.ResetPasswordCommand;
import minh.project.application.auth.command.reset_password.ResetPasswordUseCase;
import minh.project.application.auth.command.verify_otp_forgot_password.VerifyOtpForgotPasswordUseCase;
import minh.project.application.auth.command.login.LoginUserCommand;
import minh.project.application.auth.command.login.LoginUserUseCase;
import minh.project.application.auth.command.register.RegisterUserCommand;
import minh.project.application.auth.command.register.RegisterUserUseCase;
import minh.project.application.auth.command.send_otp_create_account.SendOtpCreateAccountUseCase;
import minh.project.application.auth.command.send_otp_forgot_password.SendOtpForgotPasswordUseCase;
import minh.project.application.shared.usecase.sendotp.SendOtpCommand;
import minh.project.application.auth.command.verify_otp_create_account.VerifyOtpCreateAccountUseCase;
import minh.project.application.shared.usecase.verifyotp.VerifyOtpCommand;
import minh.project.controller.auth.AuthEndpoint;
import minh.project.controller.auth.request.*;
import minh.project.controller.shared.response.ApiResponse;
import minh.project.controller.shared.response_entity_factory.ResponseEntityFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AuthEndpoint
@RequiredArgsConstructor
public class AuthCommandController {

    private final RegisterUserUseCase registerUserUseCase;

    private final LoginUserUseCase loginUserUseCase;

    private final SendOtpCreateAccountUseCase sendOtpCreateAccountUseCase;

    private final VerifyOtpCreateAccountUseCase verifyOtpCreateAccountUseCase;

    private final SendOtpForgotPasswordUseCase sendOtpForgotPasswordUseCase;

    private final VerifyOtpForgotPasswordUseCase verifyOtpForgotPasswordUseCase;

    private final ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/register/finalize")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody @Valid RegisterRequest registerRequest) {

        RegisterUserCommand registerUserCommand = RegisterUserCommand
                .builder()
                .email(registerRequest.email())
                .password(registerRequest.password())
                .build();

        return ResponseEntityFactory.response(registerUserUseCase.execute(registerUserCommand), 201);

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest loginRequest) {

        LoginUserCommand loginUserCommand = LoginUserCommand.builder()
                .email(loginRequest.email())
                .password(loginRequest.password())
                .build();

        return ResponseEntityFactory.response(loginUserUseCase.execute(loginUserCommand), 200);

    }

    @PostMapping("/register/verify-otp")
    public ResponseEntity<ApiResponse<?>> verifyOtpRegister(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        VerifyOtpCommand verifyOtpCommand = new VerifyOtpCommand(verifyOtpRequest.email(), verifyOtpRequest.otp());
        return ResponseEntityFactory.response(verifyOtpCreateAccountUseCase.execute(verifyOtpCommand), 200);
    }

    @PostMapping("/register/send-otp")
    public ResponseEntity<ApiResponse<?>> sendOtpRegister(@Valid @RequestBody SendOtpRequest sendOtpRequest) {
        SendOtpCommand sendOtpCommand = new SendOtpCommand(sendOtpRequest.email());
        return ResponseEntityFactory.response(sendOtpCreateAccountUseCase.execute(sendOtpCommand), 200);
    }

    @PostMapping("/forgot-password/send-otp")
    public ResponseEntity<ApiResponse<?>> sendOtpForgotPassword(@Valid @RequestBody SendOtpRequest sendOtpRequest) {
        SendOtpCommand sendOtpCommand = new SendOtpCommand(sendOtpRequest.email());
        return ResponseEntityFactory.response(sendOtpForgotPasswordUseCase.execute(sendOtpCommand), 200);
    }

    @PostMapping("/forgot-password/verify-otp")
    public ResponseEntity<ApiResponse<?>> verifyOtpForgotPassword(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        VerifyOtpCommand verifyOtpCommand = new VerifyOtpCommand(verifyOtpRequest.email(), verifyOtpRequest.otp());
        return ResponseEntityFactory.response(verifyOtpForgotPasswordUseCase.execute(verifyOtpCommand), 200);
    }

    @PostMapping("/forgot-password/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {

        ResetPasswordCommand resetPasswordCommand = ResetPasswordCommand
                .builder()
                .email(resetPasswordRequest.email())
                .newPassword(resetPasswordRequest.newPassword())
                .build();

        return ResponseEntityFactory.response(resetPasswordUseCase.execute(resetPasswordCommand), 200);

    }

}
