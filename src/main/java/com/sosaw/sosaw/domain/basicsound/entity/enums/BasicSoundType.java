package com.sosaw.sosaw.domain.basicsound.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BasicSoundType {
    DOG_BARK("Dog Bark"),        // 강아지 짓는 소리
    CAT_MEOW("Cat Meow"),        // 고양이 소리
    BABY_CRY("Baby Cry"),        // 아기 우는 소리
    PHONE_RING("Phone Ring"),      // 전화벨 소리
    DOORBELL("Doorbell"),        // 초인종 소리
    DOOR_OPEN_CLOSE("Door In-Use"), // 문 여닫는 소리
    FIRE_ALARM("Fire/Smoke Alarm"),      // 화재 경보기 소리
    CAR_HORN("Car Honk"),       // 경적 소리
    SIREN("Siren"),          // 비상 경보음 소리
    UNKNOWN("Unknown");

//    HUMAN_LAUGH("Laughing"),     // 사람 웃는 소리
//    KNOCK("Knocking"),           // 노크 소리
//    MICROWAVE("Microwave"),       // 전자레인지 소리

    private final String label;

    public static BasicSoundType fromLabel(String find) {
        return Arrays.stream(values())
                .filter(e -> e.label.equalsIgnoreCase(find))
                .findFirst()
                .orElse(UNKNOWN);  // 매칭 없으면 null
    }
}
