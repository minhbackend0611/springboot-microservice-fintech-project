package minh.project.controller.shared.exception;

import minh.project.application.shared.exception.AppException;
import minh.project.controller.shared.response.ApiResponse;
import minh.project.domain.shared.exception.DomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Void>> handlingCustomException(AppException appException) {

        ApiResponse<Void> body = ApiResponse
                .error(appException.getErrorCode());

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ApiResponse<Void>> handlingCustomException(DomainException domainException) {

        ApiResponse<Void> body = ApiResponse
                .error(domainException.getErrorCode());


        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handlingAppException(MethodArgumentNotValidException methodArgumentNotValidException) {

        String errorMessage = methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ApiResponse<Void> body = ApiResponse.<Void>builder()
                .code(14444)
                .message(errorMessage)
                .build();
        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handlingRuntimeException(RuntimeException runtimeException) {
        ApiResponse<Void> body = ApiResponse.<Void>builder()
                .code(55000)
                .message(runtimeException.getMessage())
                .build();
        return ResponseEntity
                .internalServerError()
                .body(body);
    }
}
