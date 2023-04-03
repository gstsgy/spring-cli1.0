package com.gstsgy.base.utils;

import com.gstsgy.base.model.LangCode;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/2/28 下午2:20
 */
@Component
public class LangUtils implements MessageSourceAware {
    private static MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        LangUtils.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        return getMessage(key, new Object[]{});
    }

    public static String getMessage(String key, Object... args) {
        Locale locale = WebUtils.getLocale();
        return messageSource.getMessage(key, args, locale);
    }

    public static String getMessage(LangCode langCode) {
        return getMessage(langCode.getLangCode(), new Object[]{});
    }

    public static String getMessageByLangCode(LangCode langCode, LangCode... args) {
        Locale locale = WebUtils.getLocale();
        if(locale == null) {
            locale = Locale.getDefault();
        }
        String[] arg1 = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            arg1[i] = args[i].getLangCode();
        }
        return messageSource.getMessage(langCode.getLangCode(), arg1, locale);
    }
}
