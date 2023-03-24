package com.project.hero.bot.listener;

import com.project.hero.bot.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeroDiscordListener extends ListenerAdapter {
    private final BotService botService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            log.info("Message is empty");
        } else {
            log.info("received message ====> {} : {}", message.getAuthor().getName(), message.getContentDisplay());
        }

        if (message.getContentDisplay().startsWith("$")) {
            String[] args = message.getContentDisplay().substring(1).split(" ");

            switch (args[0]) {
                case "help" -> textChannel.sendMessageEmbeds(botService.help()).queue();
                case "정보" -> textChannel.sendMessageEmbeds(botService.characterInfo(args[1])).queue();
                case "검증" -> {
                    Map<String, Object> valid = botService.validCharacterCanJoin(args[1]);
                    String confirm = String.valueOf(valid.get("confirm"));
                    String failReason = String.valueOf(valid.get("reason"));

                    textChannel.sendMessage(confirm + (confirm.equals("불합격") ? "\n" + failReason : "")).queue();
                }
                default -> textChannel.sendMessage("잘못된 명령어입니다. 다시 시도해 주세요! (명령어 확인 : $help)").queue();
            }
        }
    }
}
