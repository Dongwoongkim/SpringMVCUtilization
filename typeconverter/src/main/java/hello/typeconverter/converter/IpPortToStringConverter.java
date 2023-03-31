package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.NumberFormat;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort,String> {
    @Override
    public String convert(IpPort source) {
        log.info("convert source = {}", source);
        return source.getIp() + ":" + String.valueOf(source.getPort());
    }
}
