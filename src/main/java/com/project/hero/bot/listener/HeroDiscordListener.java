package com.project.hero.bot.listener;

import com.project.hero.bot.model.lostark.ArmoryProfile;
import com.project.hero.bot.service.LostArkService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
public class HeroDiscordListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        log.info("get message ====> {}", message.getContentDisplay());

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            log.info("Message is empty");
        }

        if (message.getContentDisplay().startsWith("$")) {
            String[] args = message.getContentDisplay().substring(1).split(" ");

            switch (args[0]) {
                case "info": {
                    ArmoryProfile profile = LostArkService.getUserBasicStats(args[1]);
                    textChannel.sendMessage(profile.CharacterImage()).queue();
                    textChannel.sendMessage(profile.ExpeditionLevel().toString()).queue();
                    textChannel.sendMessage(profile.Stats().get(1).Type() + " : " + profile.Stats().get(1).Value()).queue();
                    break;
                }
                default: textChannel.sendMessage("잘못된 명령어입니다. 다시 시도해 주세요!").queue();
            }
        }

    }
}
