package com.erick.minibankingmicroservice.exception;

import com.erick.minibankingmicroservice.response.ApiResponse;
import com.erick.minibankingmicroservice.service.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleApiException(ApiException ex) {

        logger.error("error in {} ", ex.getMessage(), ex);
        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleGeneralException(Exception ex) {
        logger.error("error in {} ", ex.getMessage(), ex);

        return new ApiResponse<>(
                false,
                "Internal server error",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<?> handleOptimisticLockingException() {

        return new ApiResponse<>(
                false,
                "Lagi ada yg proses broski",
                null,
                LocalDateTime.now()
        );
    }
}
