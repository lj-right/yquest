package com.suifeng.yquest.config;

import com.suifeng.yquest.sensitive.WordContext;
import com.suifeng.yquest.sensitive.WordFilter;
import com.suifeng.yquest.service.SensitiveWordsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensitiveConfig {

    @Bean
    public WordContext wordContext(SensitiveWordsService service) {
        return new WordContext(true, service);
    }

    @Bean
    public WordFilter wordFilter(WordContext wordContext) {
        return new WordFilter(wordContext);
    }

}
