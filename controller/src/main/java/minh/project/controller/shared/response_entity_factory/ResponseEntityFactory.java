package minh.project.controller.shared.response_entity_factory;

import lombok.extern.slf4j.Slf4j;
import minh.project.controller.shared.response.ApiResponse;
import minh.project.domain.shared.result.Result;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseEntityFactory {

    public static ResponseEntity<ApiResponse<?>> response(Result result, int statusCode) {

        log.debug("Value of result: {}", result);

        if (result.isSuccess()) {
            return ResponseEntity.status(statusCode).body(ApiResponse.success(result.getValue()));
        }
        else return ResponseEntity.badRequest().body(ApiResponse.error(result.getErrorCode()));
    }
}
