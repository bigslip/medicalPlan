package ir.parsdeveloper.commons.utils;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author hadi tayebi
 */
public final class MessageBundleUtil {
    private static MessageSource messageSource;

    public MessageBundleUtil() {
    }

    public MessageBundleUtil(MessageSource messageSource) {
        MessageBundleUtil.messageSource = messageSource;
    }

    public static String getMessage(String key, Object... args) {
        return getMessage(key, Locale.getDefault(), args);

    }

    public static String getMessage(String key, Locale locale, Object... args) {
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (Exception e) {
            return key;
        }
    }


    public static MessageSource getMessageSource() {
        return MessageBundleUtil.messageSource;
    }

    public static void setMessageSource(MessageSource messageSource) {
        MessageBundleUtil.messageSource = messageSource;
    }
}
