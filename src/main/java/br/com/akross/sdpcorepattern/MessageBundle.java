package br.com.akross.sdpcorepattern;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageBundle {

    private static MessageSource messageSource;

    private MessageBundle(MessageSource messageSource) {
        MessageBundle.messageSource = messageSource;
    }

    public static String getMessage(String key, Object... arguments) {
        try {
            return messageSource.getMessage(key, arguments, Locale.getDefault());
        } catch (Exception e) {
            return "Key not recognized: " + key;
        }
    }
}