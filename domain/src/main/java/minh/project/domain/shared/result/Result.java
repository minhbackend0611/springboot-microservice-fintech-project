package minh.project.domain.shared.result;

import minh.project.domain.shared.error.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Result<T> {

    private final boolean success;
    private final T value;             // null <=> failure
    private final ErrorCode errorCode; // null <=> success

    public static <T> Result<T> success(T value) {
        return new Result<>(true, value, null);
    }

    public static Result<Void> success() {
        return new Result<>(true, null, null);
    }

    public static <T> Result<T> failure(ErrorCode errorCode) {
        return new Result<>(false, null, errorCode);
    }


    public boolean isFailure() {
        return !success;
    }

    public <U> Result<U> map(Function<T, U> mapper) {
        if (isFailure()) return Result.failure(this.errorCode);
        return Result.success(mapper.apply(this.value));
    }

    public <U> Result<U> map(U instance) {
        if (isFailure()) return Result.failure(this.errorCode);
        return Result.success(instance);
    }

    public Result<T> onSuccess(Consumer<T> action) {
        if (success) action.accept(this.value);
        return this;
    }

    public Result<T> onFailure(Consumer<ErrorCode> action) {
        if (isFailure()) action.accept(this.errorCode);
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", value=" + value +
                ", errorCode=" + errorCode +
                '}';
    }
}
