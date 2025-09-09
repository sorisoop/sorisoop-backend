package com.futurenet.sorisoopbackend.customfairytale.infrastructure.util;

public class AIPromptUtil {

    private AIPromptUtil() {}


    public static String makeStorySynopsisPrompt(int age) {
        return String.format("""
                You are a creative children's book editor.

                    Look at the provided image and suggest 3 different story themes (short concepts or ideas) that would fit this picture for a children's fairy tale.

                    - Each theme should be a short phrase, not a full sentence. (e.g. "Mysterious Adventure", "Fantastic Friendship", "Magical Journey")
                    - Write first in Korean (for children/parents), then in English (for reference).
                    - Each theme must be positive, imaginative, and suitable for children aged %d.
                    - All three themes must be clearly different and directly inspired by something in the image.

                    Output ONLY the following JSON array.
                    No comments, no extra text, no code blocks.

            Output format:
            [
              {"concept_kr": "~~~", "concept_en": "~~~" },
              {"concept_kr": "~~~", "concept_en": "~~~" },
              {"concept_kr": "~~~", "concept_en": "~~~" },
            ]

            Example:
            [
                {"concept_kr": "신비로운 모험", "concept_en": "Mysterious Adventure"},
                {"concept_kr": "기발한 우정", "concept_en": "Fantastic Friendship"},
                {"concept_kr": "환상적인 여행", "concept_en": "Magical Journey"}
            ]
            """, age);
    }


//    public static String makeCharacterInfoPrompt() {
//        return """
//            You are a professional children’s book illustrator.
//
//            You will be given an image drawn by a child. Analyze the visual content of this image carefully and extract a detailed, creative character guide in English.
//
//            Identify all visually distinct characters in the image (including humans, animals, insects, or fantasy creatures).
//            For each character, write a separate paragraph describing ONLY their physical and visual features:
//
//            - Gender/Age group (e.g., a young girl around 6-7)
//            - Face (e.g., big round eyes, small nose, freckles, bright smile)
//            - Hair (e.g., short light brown bob, bangs to the left)
//            - Clothing/Accessories (e.g., blue and white striped shirt, overalls, red sneakers, holding a teddy bear)
//            - Unique features (e.g., yellow star pattern on shirt, net for collecting bugs, wings, tail, etc.)
//
//            * DO NOT describe art style, color tone, background, or overall mood.
//            * If any visual detail is unclear or missing, add a whimsical and imaginative element that fits a children’s fantasy storybook.
//            * Write naturally, one paragraph per character.
//            * Keep the language friendly, imaginative, and suitable for a children’s picture book.
//        """;
//    }

    public static String makeCharacterInfoPrompt(String theme) {
        return String.format(
                """
                당신은 전문 동화책 일러스트레이터입니다.
    
                지금부터 사용자가 직접 그린 그림과 함께, 동화의 주제를 함께 드릴 것입니다.
                그림 속에 표현된 등장인물(사람, 동물, 판타지 생물 등)의 외형을 꼼꼼히 분석하여, 상상력 있고 창의적인 '등장인물 가이드(character guide)'를 작성해 주세요.
    
                반드시 그림에 **실제로 표현된 요소**만 바탕으로 외형을 묘사해 주세요. 배경, 색감 등은 묘사하지 마세요.
    
                응답은 반드시 **한국어**로 작성해 주세요.
    
                [출력 방식]
                - 등장인물은 [1], [2], ... 형식으로 구분해 주세요.
                - 각 인물은 다음 5가지 항목을 포함한 **단락 형태**로 작성해 주세요:
                  - 연령대 / 성별
                  - 얼굴 생김새
                  - 머리 스타일
                  - 옷차림과 소품
                  - 특별한 특징 (예: 날개, 망토, 꼬리 등)
    
                [주인공 지정]
                - 인물 중 한 명을 주인공으로 선택하고 **[1]번**으로 작성해 주세요.
                - 주인공은 동화 전체를 이끄는 핵심 인물이므로, 외형 묘사를 더 자세히 해 주세요.
    
                [조연 구성]
                - 주인공 외에도 그림 속에서 시각적으로 구분 가능한 인물이 있다면 [2], [3], ... 형식으로 모두 작성해 주세요.
                - 조연도 동화에 반복 등장할 수 있으므로, 외형 정보를 명확하게 남겨 주세요.
    
                [외형 묘사 예시]
                - 6~7세 정도의 어린 소녀
                - 동그란 눈, 작은 코, 밝은 미소
                - 짧은 갈색 단발머리, 앞머리를 왼쪽으로 넘김
                - 파란 줄무늬 티셔츠, 멜빵바지, 빨간 운동화
                - 별무늬 망토, 곰인형을 안고 있음
    
                동화의 주제: %s
                """, theme
        );
    }


//    public static String makeCustomFairyTaleScriptPrompt(int age, String concept) {
//        return String.format("""
//    You are a professional children's book author.
//
//    Create a complete fairy tale for children aged %d, based on the provided image and the following concept:
//
//    "%s"
//
//    Requirements:
//    - The story must have 7 scenes (pages), each as a **distinct story beat** with clear progress or change.
//    - Each scene must be described in a narrative style (NO dialogue or direct speech).
//    - Follow a classic story arc:
//      1. Introduction (setting, character intro)
//      2. Inciting Incident (something unexpected happens)
//      3. Rising Action (challenge or decision emerges)
//      4. Climax (most intense or magical moment)
//      5. Falling Action (consequences or solution unfold)
//      6. Resolution (things settle, emotional closure)
//      7. Final Scene (positive ending or imaginative twist)
//
//    Each scene must describe:
//    - Who is present
//    - What is happening
//    - Emotional state of the character(s)
//    - Visual/background elements for illustration
//
//    Emphasize:
//    - Strong sense of change or progress between each page
//    - Creative, child-friendly language and ideas
//    - Positive and peaceful resolutions to any conflict
//
//    Categorize the story using one of the following categories, and output the corresponding number:
//    1 - Science
//    2 - Animals
//    3 - Adventure
//    4 - Daily Life
//    5 - History
//    6 - Food
//    7 - Nature
//
//    Output format (JSON, title in Korean, other fields in English and Korean):
//
//    {
//      "title": "동화 제목 (한글로)",
//      "categoryId": 3,
//      "pages": [
//        {
//          "page": 1,
//          "contentKr": "...",
//          "contentEn": "...",
//          "sceneType": "introduction",
//          "emotion": "curious"
//        },
//        ...
//      ]
//    }
//
//    * Output ONLY valid JSON.
//    * Use imaginative, child-appropriate language.
//    * Do not include explanations or extra text.
//    * Do not wrap the output with any Markdown formatting such as json or  — output only plain JSON.
//    """, age, concept);
//    }

    public static String makeCustomFairyTaleScriptPrompt(int age, String concept) {
        return String.format("""
    당신은 전문 동화 작가입니다.

    지금부터 제공된 **주인공 캐릭터의 이미지**와 아래 주제를 바탕으로, %d세 어린이를 위한 동화를 작성해 주세요.

    주제:
    "%s"

    작성 조건:
    - 전체 이야기는 7개의 장면(페이지)으로 구성되어야 합니다.
    - 각 장면은 이야기 전개에 따라 달라져야 하며, 명확한 변화와 흐름이 있어야 합니다.
    - 각 장면마다 주인공의 **주요 동작(action)** 을 명확히 묘사해 주세요.
    - 대화체 없이, **서술형**으로 작성해 주세요.

    이야기 구조는 다음 순서를 따르세요:
      1. 도입 (배경과 주인공 소개)
      2. 사건 발생 (무언가 특별한 일이 생김)
      3. 전개 (문제나 도전 등장)
      4. 절정 (가장 극적인 순간)
      5. 하강 (문제 해결 실마리)
      6. 결말 (감정적인 마무리)
      7. 에필로그 (상상력 있는 긍정적 마무리)

    각 장면에는 다음 정보를 반드시 포함해 주세요:
    - 등장인물: 누가 등장하는지
    - 상황: 어떤 일이 벌어지고 있는지
    - 감정: 등장인물(특히 주인공)의 감정 상태
    - 동작: **주인공이 하는 구체적인 동작**
    - 시각 요소: 배경, 소품 등 삽화를 위한 정보

    다음 카테고리 중 가장 잘 맞는 항목 번호(categoryId)를 골라 포함해 주세요:
    1 - 과학
    2 - 동물
    3 - 모험
    4 - 생활
    5 - 역사
    6 - 음식
    7 - 자연

    출력 형식 (JSON, 제목은 한글, 나머지는 한글 + 영어 병기):

    {
      "title": "동화 제목 (한글)",
      "categoryId": 3,
      "pages": [
        {
          "page": 1,
          "contentKr": "장면의 전체 설명",
          "contentEn": "Scene description in English",
          "sceneType": "도입",
          "emotion": "호기심",
          "action": "주인공이 무언가를 하고 있는 동작 설명"
        },
        ...
      ]
    }

    ※ 반드시 유효한 JSON만 출력하세요.
    ※ 불필요한 설명, 마크다운(```json 등)은 포함하지 마세요.
    """, age, concept);
    }


//    public static String makeCustomFairyTaleImagePrompt(String characterGuide, String sceneType, String emotion, String content) {
//        return String.format("""
//        You are a professional illustrator for children’s storybooks.
//
//        Character Guide:
//        %s
//
//        Scene Description (type: %s, emotion: %s):
//        %s
//
//        Instructions:
//        - The illustration must use a warm, pastel watercolor style.
//        - Focus on consistency of characters and background across all pages.
//        - No text, no watermark, no logo in the image.
//        - Center the main character(s) visually.
//        """, characterGuide, sceneType, emotion, content);
//    }

    public static String makeCustomFairyTaleImagePrompt(String characterGuide, String sceneType, String emotion, String content, String action) {
        return String.format("""
    당신은 전문 동화 일러스트레이터입니다.

    아래는 동화의 주요 등장인물에 대한 설명입니다:
    %s

    ※ 위 인물 설명 중에서 [1]번 캐릭터가 이 동화의 **주인공**입니다.

    지금부터 기준이 되는 주인공의 캐릭터 가이드 이미지를 참고하여, 다음 장면을 그려주세요.

    [장면 정보]
    - 장면 유형: %s
    - 감정 표현: %s
    - 장면 설명: %s
    - 주인공의 동작: %s

    [그림 지침]
    - **반드시 첨부된 주인공 이미지와 동일한 스타일, 색감, 외형(의상, 헤어스타일 등)을 유지하세요.**
    - 캐릭터들의 감정과 동작을 장면에 맞게 자연스럽게 표현하세요.
    - 주인공 외 등장인물들은 장면 분위기에 어울리는 행동과 표정을 하도록 자연스럽게 배치해주세요.
      예를 들어, 놀라는 장면에서는 주변 인물도 놀란 듯한 표정이나 제스처를 하고 있어야 합니다.
    - 주변 배경과 소품은 장면 설명에 따라 자유롭게 그리되, **과도하게 복잡하지 않게** 해 주세요.
    - 전반적으로 **부드럽고 따뜻한 파스텔 수채화 스타일**을 유지하세요.
    - 이미지에는 텍스트, 로고, 워터마크를 포함하지 마세요.
    """, characterGuide, sceneType, emotion, content, action);
    }

    public static String makeCharacterGuideImagePrompt(String characterGuide, String theme) {
        return String.format("""
        당신은 전문 동화책 일러스트레이터입니다.

        다음은 아이가 만든 동화의 등장인물에 대한 설명입니다.
        이 동화는 다음과 같은 주제를 가지고 있습니다: 「%s」

        이 정보를 바탕으로, 등장인물 중 [1]번에 해당하는 **주인공 캐릭터 1명만** 선택하여,
        그 인물이 **정면을 바라보며 서 있는 전신 이미지**를 하나 그려 주세요.
        이 이미지는 동화 전체에서 일관되게 사용될 **기준 이미지**입니다.

        반드시 아래 기준을 지켜 주세요:
        - 주인공 1명만 등장해야 하며, 조연은 포함되지 않아야 합니다.
        - 배경은 단순하거나 투명하게 처리해 주세요.
        - 주인공은 이미지 중심에 배치해 주세요.
        - 그림 스타일은 수채화 느낌, 따뜻한 색감, 부드러운 톤으로 해 주세요.

        등장인물 설명:
        %s
        """, theme, characterGuide);
    }

}
