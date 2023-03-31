package hello.exception.api;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @Data
    @AllArgsConstructor
    static class MemberDto
    {
        private String memberId;
        private String name;
    }

    // 처리하지 못하고 남은 예외들 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e)
    {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX","내부 오류");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
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

    @GetMapping("/api2/members/{id}")
    public ApiExceptionController.MemberDto getMember(@PathVariable("id") String id)
    {
        if(id.equals("user-ex"))
        {
            throw new UserException("사용자 오류");
        }
        return new ApiExceptionController.MemberDto(id,"hello "+id);
    }
}
