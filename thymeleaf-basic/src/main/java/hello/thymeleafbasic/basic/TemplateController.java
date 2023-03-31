package hello.thymeleafbasic.basic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {


    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
    @GetMapping("/fragment")
    public String template()
    {
        return "template/fragment/fragmentMain";
    }

    @GetMapping("/layout")
    public String layout()
    {
        return "template/layout/layoutMain";
    }

    @GetMapping("/layoutExtend")
    public String layoutExtends()
    {
        return "template/layoutExtend/layoutExtendMain";
    }
}
