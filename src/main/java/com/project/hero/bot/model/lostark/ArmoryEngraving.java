package com.project.hero.bot.model.lostark;

import java.util.List;

public record ArmoryEngraving(
        List<Effects> Effects
) {
    public record Effects(
            String Name,
            String Description
    ){}
}
