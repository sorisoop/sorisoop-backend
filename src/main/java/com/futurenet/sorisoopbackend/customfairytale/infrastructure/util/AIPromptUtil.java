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


    public static String makeCharacterInfoPrompt() {
        return """
            You are a professional children’s book illustrator.
    
            You will be given an image drawn by a child. Analyze the visual content of this image carefully and extract a detailed, creative character guide in English.
    
            Identify all visually distinct characters in the image (including humans, animals, insects, or fantasy creatures).
            For each character, write a separate paragraph describing ONLY their physical and visual features:
    
            - Gender/Age group (e.g., a young girl around 6-7)
            - Face (e.g., big round eyes, small nose, freckles, bright smile)
            - Hair (e.g., short light brown bob, bangs to the left)
            - Clothing/Accessories (e.g., blue and white striped shirt, overalls, red sneakers, holding a teddy bear)
            - Unique features (e.g., yellow star pattern on shirt, net for collecting bugs, wings, tail, etc.)
    
            * DO NOT describe art style, color tone, background, or overall mood.
            * If any visual detail is unclear or missing, add a whimsical and imaginative element that fits a children’s fantasy storybook.
            * Write naturally, one paragraph per character.
            * Keep the language friendly, imaginative, and suitable for a children’s picture book.
        """;
    }


    public static String makeCustomFairyTaleScriptPrompt(int age, String concept) {
        return String.format("""
    You are a professional children's book author.

    Create a complete fairy tale for children aged %d, based on the provided image and the following concept:

    "%s"

    Requirements:
    - The story must have 7 scenes (pages), each as a **distinct story beat** with clear progress or change.
    - Each scene must be described in a narrative style (NO dialogue or direct speech).
    - Follow a classic story arc:
      1. Introduction (setting, character intro)
      2. Inciting Incident (something unexpected happens)
      3. Rising Action (challenge or decision emerges)
      4. Climax (most intense or magical moment)
      5. Falling Action (consequences or solution unfold)
      6. Resolution (things settle, emotional closure)
      7. Final Scene (positive ending or imaginative twist)

    Each scene must describe:
    - Who is present
    - What is happening
    - Emotional state of the character(s)
    - Visual/background elements for illustration

    Emphasize:
    - Strong sense of change or progress between each page
    - Creative, child-friendly language and ideas
    - Positive and peaceful resolutions to any conflict

    Categorize the story using one of the following categories, and output the corresponding number:
    1 - Science
    2 - Animals
    3 - Adventure
    4 - Daily Life
    5 - History
    6 - Food
    7 - Nature

    Output format (JSON, title in Korean, other fields in English and Korean):

    {
      "title": "동화 제목 (한글로)",
      "categoryId": 3,
      "pages": [
        {
          "page": 1,
          "contentKr": "...",
          "contentEn": "...",
          "sceneType": "introduction",
          "emotion": "curious"
        },
        ...
      ]
    }

    * Output ONLY valid JSON.
    * Use imaginative, child-appropriate language.
    * Do not include explanations or extra text.
    * Do not wrap the output with any Markdown formatting such as json or  — output only plain JSON.
    """, age, concept);
    }


    public static String makeCustomFairyTaleImagePrompt(String characterGuide, String sceneType, String emotion, String content) {
        return String.format("""
        You are a professional illustrator for children’s storybooks.

        Character Guide:
        %s

        Scene Description (type: %s, emotion: %s):
        %s

        Instructions:
        - The illustration must use a warm, pastel watercolor style.
        - Focus on consistency of characters and background across all pages.
        - No text, no watermark, no logo in the image.
        - Center the main character(s) visually.
        """, characterGuide, sceneType, emotion, content);
    }

}
