package com.futurenet.sorisoopbackend.customfairytale.infrastructure.util;

public class OpenAIPromptUtil {

    private OpenAIPromptUtil() {}

//    public static String makeCharacterInfoPrompt() {
//        return """
//        다음 이미지를 보고, 그림에 등장하는 모든 캐릭터(주인공 및 주변 인물·동물·곤충 포함)의 외형적 특징만 자세히 분석하여 추출해 줘.
//        반드시 아래 항목을 모두 포함해서 영어로 한 문단으로 작성해줘.
//        단, 그림 스타일, 색감, 분위기 등은 절대 언급하지 말고, 오직 캐릭터의 신체적 특징, 표정, 머리/눈/얼굴/체형, 복장, 소품, 들고 있는 물건 등만 상세히 설명해줘.
//
//        <분석 항목>
//        - 성별/나이대: (예: 6-7세 정도의 어린 소녀)
//        - 얼굴 특징: (예: 크고 동그란 눈, 오똑한 코, 작고 얇은 입술, 주근깨가 있는 뺨, 해맑은 미소)
//        - 머리 스타일: (예: 밝은 갈색의 짧은 단발 머리, 왼쪽으로 넘긴 앞머리)
//        - 복장 및 소품: (예: 파란색과 흰색 줄무늬가 있는 셔츠, 멜빵바지, 붉은색 운동화, 한 손에 든 작은 곰인형)
//        - 기타 특징: (예: 셔츠에 그려진 노란색 별 무늬, 들고 있는 채집망 등)
//
//        각 캐릭터(동물·곤충 등)별로 한 문단씩 작성해주고,
//        그림에서 정보가 부족하면 어린이 동화책에 어울릴 만한 상상으로 적절히 채워줘.
//    """;
//    }

    public static String makeCharacterInfoPrompt() {
        return """
    이미지를 보고, 등장하는 모든 캐릭터(주인공 및 주변 인물, 동물, 곤충 포함)의 외형적 특징을 영어로 한 문단씩 자연스럽게 설명해줘.

    <분석 항목>
    - 성별/나이대 (예: 6-7세 정도의 어린 소녀)
    - 얼굴 특징 (예: 크고 동그란 눈, 오똑한 코, 작고 얇은 입술, 주근깨가 있는 뺨, 해맑은 미소)
    - 머리 스타일 (예: 밝은 갈색의 짧은 단발 머리, 왼쪽으로 넘긴 앞머리)
    - 복장 및 소품 (예: 파란색과 흰색 줄무늬 셔츠, 멜빵바지, 붉은 운동화, 작은 곰인형 등)
    - 기타 특징 (예: 셔츠의 노란 별 무늬, 들고 있는 채집망 등)

    그림 스타일, 색감, 분위기 등은 포함하지 않아도 되고,
    그림에서 보이지 않는 정보는 어린이 동화책에 어울릴 만한 상상으로 보완해도 좋아.
    각 캐릭터(동물·곤충 등 포함)는 한 문단씩 작성해주고, 영어로만 답변해줘.
    """;
    }

    public static String makeCustomFairyTaleScriptPrompt(int age) {
        return String.format("""
        이 이미지를 보고 %d세 아동이 재미있게 읽을 수 있는 동화를 만들어줘.

        - 총 7개의 장면으로 구성된 완결형 이야기
        - 각 장면은 서술문 중심 (캐릭터 대사 최소화 또는 생략)
        - 전체 이야기 흐름은 기-승-전-결 구조를 갖추고, 감정의 흐름이 자연스럽게 이어질 것
        - 반드시 OpenAI의 content policy를 위반하지 않아야 함
        - 갈등 상황이 있을 경우에도 평화적이고 창의적인 방식으로 해결하도록 묘사할 것
        - 긍정적이고 교훈적인 메시지를 포함

        ### 출력 형식
        반드시 아래와 같이 출력할 것. (꼭 영어, 한글 버전 모두 포함)

        [
            { "page": 1, "content_kr": "한글로 된 장면 설명", "content_en": "영어로 된 장면의 상세한 행동/표정/배경 묘사" },
            ...
            { "page": 7, "content_kr": "...", "content_en": "..." }
        ]

        - JSON 외의 다른 텍스트, 설명, 주석은 절대 포함하지 마라.
        - ```json``` 등 코드 블록 표시도 넣지 마라.
        - content_en에는 "누가, 어디서, 무엇을, 어떤 표정/감정/배경"이 드러나게 영어로 묘사체로 작성하라. 대사, 따옴표, 직접화법, 영어 문장은 절대 넣지 말고, 묘사로만 써라.
        """, age);
    }

    public static String makeCustomFairyTaleImagePrompt(String characterGuide, String pageContent) {
        return String.format("""
        Please create an illustration for a children's picture book page.

        Use the following character description as a guide for all images, so the character looks as consistent as possible across the story:
        %s

        The scene to illustrate:
        %s

        Style:
        - Gentle watercolor or pastel
        - Soft, warm, dreamy feeling, suitable for young children
        - Please do not include any text or letters in the image

        The focus should be on the characters and the scene. Thank you!
        """, characterGuide, pageContent);
    }

}
