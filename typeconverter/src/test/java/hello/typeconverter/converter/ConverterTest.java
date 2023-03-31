package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

public class ConverterTest {

    @Test
    void stringToInteger()
    {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");
        Assertions.assertThat(result).isEqualTo(10);
    }
    
    @Test
    void stringToIpPort()
    {
        StringToIpPortConverter IpPortConverter = new StringToIpPortConverter();
        IpPortToStringConverter StrPortConverter = new IpPortToStringConverter();
        String source = "127.0.0.1:8080";

        IpPort result = IpPortConverter.convert(source);
        String result2 = StrPortConverter.convert(result);
        System.out.println("result = " + result);
        System.out.println("result2 = " + result2);


    }
}
