package com.project.hero.bot.model.lostark;

import java.util.List;

public record ArmoryCard(
        List<Effects> effects
) {
    public record Effects (
            List<Items> Items
    ) {
        public record Items (
                String name,
                String Description
        ) {}
    }
}
