package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {


    // parse : String to Object with formatting
    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text : {} , Locale : {}",text, locale);

        // "1,000" -> 1000
        return NumberFormat.getInstance(locale).parse(text);
    }

    // print : Object To String with formatting
    @Override
    public String print(Number object, Locale locale) {
        log.info("object = {} , locale = {}", object, locale);

        // 1000 -> "1,000"
        return NumberFormat.getInstance().format(object);
    }
}
