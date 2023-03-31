package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.FieldError;
import org.springframework.validation.MessageCodesResolver;

public class MessgaeCodesResolverTest {
    
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    
    @Test
    void messageCodesResolverObject()
    {
        String[] messageCodes = codesResolver.resolveMessageCodes("배고파","item");
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
    }

    @Test
    void messageCodesResolverField()
    {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        Assertions.assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );

        //  Same
        //  bindingResult.rejectValue("itemName","required",null,null);
        //  bindingResult.addError(new FieldError("item","itemName",null,false,messageCodes,null,null));

    }
}
