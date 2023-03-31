package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import org.springframework.cglib.core.Converter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(@RequestParam Integer data)
    {
        System.out.println("data = " + data);
        return "Ok";
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data)
    {
        System.out.println("data = " + data);
        return "OK";
    }

    @GetMapping("/ip-port")
    public String helloV2(@RequestParam IpPort ipPort)
    {
        System.out.println("Ip = " + ipPort.getIp());
        System.out.println("Port = " + ipPort.getPort());
        return "OK";
    }
}
