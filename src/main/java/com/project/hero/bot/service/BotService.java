package com.project.hero.bot.service;

import com.project.hero.bot.model.lostark.ArmoryCard;
import com.project.hero.bot.model.lostark.ArmoryEngraving;
import com.project.hero.bot.model.lostark.ArmoryGem;
import com.project.hero.bot.model.lostark.ArmoryProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotService {
    private final LostArkService lostArkService;

    public MessageEmbed help() {
        return new EmbedBuilder()
                .setTitle("명령어 도움말")
                .setColor(Color.BLUE)
                .addField("캐릭터 정보 검색", "$정보 [캐릭터명]", true)
                .addField("레이드 자격 검증", "$검증 [캐릭터명]", false)
                .setDescription("모든 명령어는 ' $ ' 로 시작합니다.")
                .setFooter("자격 조건은 [원정대 200], [보석 7랩] 입니다.")
                .build();
    }
    
    public MessageEmbed characterInfo(String name) {
        ArmoryProfile profile = lostArkService.getUserBasicStats(name);
        ArmoryCard card = lostArkService.getUserCards(name);
        ArmoryEngraving engraving = lostArkService.getUserEngravings(name);
        StringBuilder sb = new StringBuilder();
        engraving.Effects().forEach(effect -> sb.append(effect.Name()).append("\n"));

        return new EmbedBuilder()
                .setTitle("검색 결과")
                .setColor(Color.GREEN)
                .addField("이름", profile.CharacterName(), true)
                .addField("서버", profile.ServerName(), true)
                .addField("클래스", profile.CharacterClassName(), true)
                .addField("아이템 레벨", profile.ItemMaxLevel(), true)
                .addField("원정대 레벨", String.valueOf(profile.ExpeditionLevel()), true)
                .addField("스탯", profile.Stats().get(0).Type() + " : " + profile.Stats().get(0).Value() + "\n" +
                        profile.Stats().get(1).Type() + " : " + profile.Stats().get(1).Value() + "\n" +
                        profile.Stats().get(3).Type() + " : " + profile.Stats().get(3).Value() + "\n" +
                        profile.Stats().get(7).Type() + " : " + profile.Stats().get(7).Value(), true)
                .addField("각인", sb.toString(), true)
                .addField("카드 효과", card == null ? "장착한 카드가 없습니다." : card.Effects().get(0).Items().get(card.Effects().get(0).Items().size()-1).Name(), false)
                .setThumbnail(profile.CharacterImage())
                .build();
    }

    public Map<String, Object> validCharacterCanJoin(String name) {
        ArmoryProfile profile = lostArkService.getUserBasicStats(name);
        ArmoryGem gem = lostArkService.getUserGems(name);
//                    ArmoryCard card = LostArkService.getUserCards(args[1]);

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
