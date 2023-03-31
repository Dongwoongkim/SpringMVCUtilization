package hello.thymeleafbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class ThymeleafBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafBasicApplication.class, args);
	}

}
