package com.project.hero.bot.model.lostark;

import java.util.List;

public record ArmoryProfile(
        String CharacterImage,
        Integer ExpeditionLevel,    // 원대렙
        List<Stats> Stats,
        String ServerName,
        String CharacterName,
        String CharacterClassName,
        String ItemMaxLevel

) {
    public record Stats(
            String Type,    // 치특신제인숙
            String Value
    ){}
}
