package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@Validated @ModelAttribute ItemSaveForm form,
                          BindingResult bindingResult)
    {
        log.info("API 컨트롤러 호출");
        if(bindingResult.hasErrors())
        {
            log.info("검증 오류 발생 errors={}", bindingResult);
            log.info("{}",form);
            return bindingResult.getAllErrors();
        }

        return form;
    }
}
