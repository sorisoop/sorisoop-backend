package com.futurenet.sorisoopbackend.customfairytale.infrastructure.util;

public class OpenAIPromptUtil {

    private OpenAIPromptUtil() {}

    public static String makeCustomFairyTaleScriptPrompt(int age) {
        return String.format("""
        이 이미지를 보고 %d세 아동이 재미있게 읽을 수 있는 동화를 만들어줘.
        - 총 7개의 장면으로 구성된 완결형 이야기
        - 각 장면은 서술문 중심 (캐릭터 대사 최소화 또는 생략)
        - 전체 이야기 흐름은 기-승-전-결 구조를 갖추고, 감정의 흐름이 자연스럽게 이어질 것
        - 긍정적이고 교훈적인 메시지를 포함
        - 반드시 JSON 배열 형식만 출력: [{ "page": 1, "content": "..." }, ... ]
        - JSON 외의 다른 텍스트, 설명, 주석은 절대 포함하지 말 것
        -  ``` json ``` 이런거도 다 빼고 오로지 [{ "page": 1, "content": "..." }, ... ] 양식으로만
        """, age);
    }

    public static String makeCustomFairyTaleImagePrompt(String content) {
        return String.format("""
        Create a children's book style illustration for the following scene:

        "%s"

        - Use a warm, soft, and dreamy watercolor or pastel style
        - The illustration should match the atmosphere of the scene
        - Emphasize a magical and emotional tone suitable for kids
        - Keep the visual style consistent with the rest of the story
        """, content);
    }
}
