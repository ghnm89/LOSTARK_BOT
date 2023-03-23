package com.project.hero.bot.listener;

import com.project.hero.bot.model.lostark.ArmoryCard;
import com.project.hero.bot.model.lostark.ArmoryGem;
import com.project.hero.bot.model.lostark.ArmoryProfile;
import com.project.hero.bot.service.LostArkService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HeroDiscordListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        log.info("received message ====> {} : {}", message.getAuthor().getName(), message.getContentDisplay());

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().equals("")) {
            log.info("Message is empty");
        }

        if (message.getContentDisplay().startsWith("$")) {
            String[] args = message.getContentDisplay().substring(1).split(" ");

            switch (args[0]) {
                case "help" -> {
                    MessageEmbed embedMsg = new EmbedBuilder()
                            .setTitle("명령어 도움말")
                            .setColor(Color.BLUE)
                            .addField("캐릭터 정보 검색", "$정보 [캐릭터명]", true)
                            .addField("레이드 자격 검증", "$검증 [캐릭터명]", false)
                            .setDescription("모든 명령어는 ' $ ' 로 시작합니다.")
                            .setFooter("자격 조건은 [원정대 200], [보석 7랩] 입니다.")
                            .build();

                    textChannel.sendMessageEmbeds(embedMsg).queue();
                }
                case "정보" -> {
                    ArmoryProfile profile = LostArkService.getUserBasicStats(args[1]);
                    ArmoryCard card = LostArkService.getUserCards(args[1]);
                    MessageEmbed embedMsg = new EmbedBuilder()
                            .setTitle("검색 결과")
                            .setColor(Color.GREEN)
                            .addField("이름", profile.CharacterName(), true)
                            .addField("클래스", profile.CharacterClassName(), true)
                            .addField("아이템 레벨", profile.ItemMaxLevel(), true)
                            .addField("원정대 레벨", String.valueOf(profile.ExpeditionLevel()), true)
                            .addField("스탯", profile.Stats().get(0).Type() + " : " + profile.Stats().get(0).Value() + "\n" +
                                    profile.Stats().get(1).Type() + " : " + profile.Stats().get(1).Value() + "\n" +
                                    profile.Stats().get(3).Type() + " : " + profile.Stats().get(3).Value() + "\n" +
                                    profile.Stats().get(7).Type() + " : " + profile.Stats().get(7).Value(), true)
                            .addField("카드 효과", card.Effects().get(0).Items().get(card.Effects().get(0).Items().size()-1).Name(), false)
                            .setThumbnail(profile.CharacterImage())
                            .build();

                    textChannel.sendMessageEmbeds(embedMsg).queue();

                }
                case "검증" -> {
                    ArmoryProfile profile = LostArkService.getUserBasicStats(args[1]);
                    ArmoryGem gem = LostArkService.getUserGems(args[1]);
                    ArmoryCard card = LostArkService.getUserCards(args[1]);

                    Map<String, Object> valid = validCanJoin(profile, gem, card);
                    String confirm = String.valueOf(valid.get("confirm"));
                    String failReason = String.valueOf(valid.get("reason"));

                    textChannel.sendMessage(confirm + (confirm.equals("불합격") ? "\n" + failReason : "")).queue();

                }
                default -> textChannel.sendMessage("잘못된 명령어입니다. 다시 시도해 주세요! (명령어 확인 : $help)").queue();
            }
        }

    }

    private Map<String, Object> validCanJoin(ArmoryProfile profile, ArmoryGem gem, ArmoryCard card) {
        Map<String, Object> valid = new HashMap<>();
        boolean confirm = true;
        StringBuilder failReason = new StringBuilder();
        failReason.append("<실패사유>\n");

        // 원정대 체크
        if (profile.ExpeditionLevel() < 200) {
            confirm = false;
            failReason.append("1. 원정대 레벨 200 미만\n");
        }

        // 보석 체크
        int i = 0;
        for (ArmoryGem.Gem g : gem.Gems()) {
            if (g.Level() < 7) {
                i++;
            }
        }
        if (i > 0) {
            confirm = false;
            failReason.append("2. 7랩 미만 보석 ").append(i).append(" 개 존재\n");
        }

        valid.put("confirm", confirm ? "합격" : "불합격");
        valid.put("reason", failReason.toString());

        return valid;
    }
}
