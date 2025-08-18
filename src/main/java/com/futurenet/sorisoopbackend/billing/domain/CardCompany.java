package com.futurenet.sorisoopbackend.billing.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum CardCompany {
    IBK_BC("3K", "기업 BC", "IBK_BC"),
    GWANGJUBANK("46", "광주은행", "GWANGJUBANK"),
    LOTTE("71", "롯데카드", "LOTTE"),
    KDBBANK("30", "한국산업은행", "KDBBANK"),
    BC("31", "BC카드", "BC"),
    SAMSUNG("51", "삼성카드", "SAMSUNG"),
    SAEMAUL("38", "새마을금고", "SAEMAUL"),
    SHINHAN("41", "신한카드", "SHINHAN"),
    SHINHYEOP("62", "신협", "SHINHYEOP"),
    CITI("36", "씨티카드", "CITI"),
    WOORI_BC("33", "우리BC카드(BC 매입)", "WOORI"),
    WOORI("W1", "우리카드(우리 매입)", "WOORI"),
    POST("37", "우체국예금보험", "POST"),
    SAVINGBANK("39", "저축은행중앙회", "SAVINGBANK"),
    JEONBUKBANK("35", "전북은행", "JEONBUKBANK"),
    JEJUBANK("42", "제주은행", "JEJUBANK"),
    KAKAOBANK("15", "카카오뱅크", "KAKAOBANK"),
    KBANK("3A", "케이뱅크", "KBANK"),
    TOSSBANK("24", "토스뱅크", "TOSSBANK"),
    HANA("21", "하나카드", "HANA"),
    HYUNDAI("61", "현대카드", "HYUNDAI"),
    KOOKMIN("11", "KB국민카드", "KOOKMIN"),
    NONGHYEOP("91", "NH농협카드", "NONGHYEOP"),
    SUHYEOP("34", "Sh수협은행", "SUHYEOP"),
    DINERS("6D", "다이너스 클럽", "DINERS"),
    MASTER("4M", "마스터카드", "MASTER"),
    UNIONPAY("3C", "유니온페이", "UNIONPAY"),
    AMEX("7A", "아메리칸 익스프레스", "AMEX"),
    JCB("4J", "JCB", "JCB"),
    VISA("4V", "VISA", "VISA"),
    UNKNOWN("00", "알 수 없음", "UNKNOWN");

    private final String code;
    private final String koreanName;
    private final String englishName;

    private static final Map<String, CardCompany> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(CardCompany::getCode, e -> e));

    public static CardCompany fromCode(String code) {
        return CODE_MAP.getOrDefault(code, UNKNOWN);
    }
}
