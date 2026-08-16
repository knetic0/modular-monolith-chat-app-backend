package com.mehmetsolak.mini_chat_app.common.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternationalizationService {

    private final MessageSource messageSource;

    public String getMessage(String key, Object... args){
        return messageSource
                .getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
