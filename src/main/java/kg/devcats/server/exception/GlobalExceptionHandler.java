package kg.devcats.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MailSendException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse mailSendingExceptionHandling(RuntimeException re, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        errorResponse.setMessage(re.getMessage());
        errorResponse.setDescription(request.getDescription(false));
        return errorResponse;
    }

    @ExceptionHandler({UserNotFoundException.class, ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundExceptionHandling(RuntimeException re, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(re.getMessage());
        errorResponse.setDescription(request.getDescription(false));
        return errorResponse;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class,
            ClientAccountNotFoundException.class, InsufficientFundsException.class,
            DuplicateEmailException.class, RequestNotFoundException.class,
            ImageIsEmptyException.class, ImageNotAppropriateException.class,
            CurrencyNotFoundException.class, ProductNotFoundException.class,
            InsufficientProductQuantityException.class, CatalogNotFoundException.class,
            PurchaseQuantityException.class, DivisionByZeroException.class,
            UserIsAlreadyRegisteredException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestHandling(RuntimeException re, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(re.getMessage());
        errorResponse.setDescription(request.getDescription(false));
        return errorResponse;
    }

    @ExceptionHandler(TokenIsExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthorizedExceptionsHandling(RuntimeException re, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(re.getMessage());
        errorResponse.setDescription(request.getDescription(false));
        return errorResponse;
    }


}
