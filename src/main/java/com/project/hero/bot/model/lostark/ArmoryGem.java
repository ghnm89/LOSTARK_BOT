package com.project.hero.bot.model.lostark;

import java.util.List;

public record ArmoryGem(
        List<Gem> Gems
) {
    public record Gem(
            String Name,
            Integer Level
    ){}
}
