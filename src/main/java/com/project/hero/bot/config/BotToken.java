package com.project.hero.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotToken {
    @Value("${discord-bot-token}")
    private String botToken;

    public String getBotToken() {
        return botToken;
    }
}
