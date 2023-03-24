package com.project.hero.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotToken {
    @Value("${discord-bot-token}")
    private String botToken;

    @Value("${test-bot-token}")
    private String testToken;

    public String getBotToken() {
        return botToken;
    }

    public String getTestToken() {
        return testToken;
    }
}
