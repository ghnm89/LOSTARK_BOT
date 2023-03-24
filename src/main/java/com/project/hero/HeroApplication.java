package com.project.hero;

import com.project.hero.bot.config.BotToken;
import com.project.hero.bot.listener.HeroDiscordListener;
import com.project.hero.bot.service.BotService;
import com.project.hero.bot.service.LostArkService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HeroApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(HeroApplication.class, args);
        BotToken botToken = context.getBean(BotToken.class);
        String token = botToken.getBotToken();
//        String test = botToken.getTestToken();

        JDA jda = JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new HeroDiscordListener(new BotService(new LostArkService())))
                .build();

    }

}
