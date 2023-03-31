package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class) // @ResponseBody 자동 적용
    public ErrorResult illegalExHandler(IllegalArgumentException e)
    {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD",e.getMessage()); // json
    }

    @ExceptionHandler // UserException.class 생략 가능
    public ResponseEntity<ErrorResult> userExHandler(UserException e)
    {
        log.error("[exceptionHandler] ex", e);
        ErrorResult result = new ErrorResult("USER-EX",e.getMessage());
        return new ResponseEntity(result,HttpStatus.BAD_REQUEST);
    }

    // 처리하지 못하고 남은 예외들 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e)
    {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX","내부 오류");
    }
}
