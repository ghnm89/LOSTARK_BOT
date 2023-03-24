package com.project.hero.bot.enums;

import lombok.Getter;

@Getter
public enum LostArkClass {
    // 전사
    WARLORD("워로드", "[상시 방어력 감소 12%]\n [백/헤드 어택 피해 증가 12%]"),
    DESTROYER("디스트로이어", "[상시 방어력 감소 12%]"),
    BERSERKER("버서커", "[상시 받는 피해 증가 6%]"),
    SLAYER("슬레이어", "[상시 받는 피해 증가 6%]"),
    HOLYKNIGHT("홀리나이트", "[주는 피해 증가 10%]"),

    // 무도가
    SOUL_MASTER("기공사", "[공격력 증가 6%]"),
    BATTLE_MASTER("배틀마스터", "[순간 치명타 확률 증가 18%]"),
    INFIGHTER("인파이터", "[상시 받는 피해 증가 6%]"),
    STRIKER("스트라이커", "[순간 치명타 확률 증가 18%]"),
    LANCE_MASTER("창술사", "[순간 치명타 확률 증가 18%]"),

    // 헌터
    DEVIL_HUNTER("데빌헌터", "[상시 치명타 확률 증가 10%]"),
    GUNSLINGER("건슬링어", "[상시 치명타 확률 증가 10%]"),
    BLASTER("블래스터", "[상시 방어력 감소 12%]"),
    HAWK_EYE("호크아이", "[상시 받는 피해 증가 6%]"),
    SCOUTER("스카우터", "[공격력 증가 6%]"),

    // 마법사
    ARCANA("아르카나", "[상시 치명타 확률 증가 10%]"),
    SUMMONER("서머너", "[상시 방어력 감소 12%]"),
    SORCERESS("소서리스", "[상시 받는 피해 증가 6%]"),
    BARD("바드", "[주는 피해 증가 10%]"),

    // 스페셜리스트
    ARTIST("도화가", "[주는 피해 증가 10%]"),
    AEROMANCER("기상술사", "[상시 치명타 확률 증가 10%]"),

    // 암살자
    DEMONIC("데모닉", "[상시 받는 피해 증가 6%]"),
    BLADE("블레이드", "[백/헤드 어택 피해 증가 12%]"),
    REAPER("리퍼", "[상시 방어력 감소 12%]"),
    SOUL_EATER("소울이터", "");

    private final String name;
    private final String synergy;

    LostArkClass(String name, String synergy) {
        this.name = name;
        this.synergy = synergy;
    }

    public static String getSynergyByName(String name) {
        for (LostArkClass lc : LostArkClass.values()) {
            if (name.equals(lc.name)) {
                return lc.synergy;
            }
        }

        return null;
    }
}
