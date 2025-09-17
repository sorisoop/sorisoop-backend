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
                    Do not wrap the output with any Markdown formatting such as json or  — output only plain JSON.

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
        * **Each paragraph must be no longer than 2 sentences.**
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

    Character Rule:
    - The **main character of the story must be the central figure described in the given concept**.
    - This character should appear in **every scene** and be the focus of the action and emotional journey.

    Each scene must describe:
    - Who is present
    - What is happening
    - Emotional state of the character(s)
    - Visual/background elements for illustration
    - **The main character's action or pose (e.g., 'The girl is reaching for a glowing star', 'The dragon is flying above the trees')**

    Emphasize:
    - Strong sense of change or progress between each page
    - Creative, child-friendly language and ideas
    - Positive and peaceful resolutions to any conflict

    Additional Requirement:
    - The story must convey a **clear and meaningful lesson** (moral/insight) throughout the entire arc — not only at the end.
    - As the story progresses, the **characters should learn, grow, or realize something important**, reflecting the concept above.
    - Do NOT insert the lesson as an explanation; instead, let it emerge naturally through the characters’ actions and events.
    - Make sure the final scene reinforces or completes this lesson in a satisfying and child-appropriate way.

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
          "emotion": "curious",
          "action": "The boy is holding a wooden sword and looking around the forest."
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

//    public static String makeCustomFairyTaleScriptPrompt(int age, String concept) {
//        return String.format("""
//            You are a professional children's book author.
//
//            Create a complete fairy tale for children aged %d, based on the provided image and the following concept:
//
//            "%s"
//
//            Requirements:
//            - The story must have 7 scenes (pages), each as a **distinct story beat** with clear progress or change.
//            - Each scene must be described in a narrative style (NO dialogue or direct speech).
//            - Follow a classic story arc:
//              1. Introduction (setting, character intro)
//              2. Inciting Incident (something unexpected happens)
//              3. Rising Action (challenge or decision emerges)
//              4. Climax (most intense or magical moment)
//              5. Falling Action (consequences or solution unfold)
//              6. Resolution (things settle, emotional closure)
//              7. Final Scene (positive ending or imaginative twist)
//
//            Each scene must describe:
//            - Who is present
//            - What is happening
//            - Emotional state of the character(s)
//            - Visual/background elements for illustration
//            **The main character's action or pose (e.g., 'The girl is reaching for a glowing star', 'The dragon is flying above the trees')**
//
//            Emphasize:
//            - Strong sense of change or progress between each page
//            - Creative, child-friendly language and ideas
//            - Positive and peaceful resolutions to any conflict
//
//            Categorize the story using one of the following categories, and output the corresponding number:
//            1 - Science
//            2 - Animals
//            3 - Adventure
//            4 - Daily Life
//            5 - History
//            6 - Food
//            7 - Nature
//
//            Output format (JSON, title in Korean, other fields in English and Korean):
//
//            {
//              "title": "동화 제목 (한글로)",
//              "categoryId": 3,
//              "pages": [
//                {
//                  "page": 1,
//                  "contentKr": "...",
//                  "contentEn": "...",
//                  "sceneType": "introduction",
//                  "emotion": "curious",
//                  "action": "The boy is holding a wooden sword and looking around the forest."
//                },
//                ...
//              ]
//            }
//
//            * Output ONLY valid JSON.
//            * Use imaginative, child-appropriate language.
//            * Do not include explanations or extra text.
//            * Do not wrap the output with any Markdown formatting such as json or  — output only plain JSON.
//            """, age, concept);
//    }


    public static String makeCustomFairyTaleImagePrompt(String characterGuide, String sceneType, String emotion, String content, String action) {
        return String.format("""
        You are a professional illustrator for children's picture books.

        Please create a full-scene illustration based on the following details.

        [Character Guide]
        %s

        [Scene Information]
        - Scene Type: %s
        - Character Emotion: %s
        - Character Action: %s
        - Story Description: %s

        [Illustration Requirements]
        - If a reference image is provided, the character must match its appearance and style. Otherwise, use the Character Guide as the design basis.
        - The character should be **performing the specified action** as naturally and clearly as possible.
        - Use the reference image as a **visual baseline**, and extend it to a dynamic scene where the character is in action.
        - The emotional state should be reflected through facial expression and body posture.
        - Use a warm, pastel watercolor style suitable for children’s fairy tales.
        - No text, watermark, or logo should appear in the image.
        - Keep the background simple or softly detailed to emphasize the character.
        - The main character must be prominently centered in the composition.
        """,
                characterGuide, sceneType, emotion, action, content
        );
    }

    public static String makeReferenceImagePrompt(String characterGuide) {
        return String.format("""
            You are a professional illustrator for children’s storybooks.
        
            Character Guide:
            %s
        
            Illustration Purpose:
            - This image will be used as a reference to maintain character consistency across all storybook pages.
            - It should clearly depict the character's full body, clothing, and overall visual style.
        
            Instructions:
            - Use a warm, pastel, watercolor-style illustration suitable for children's books.
            - Draw the character in a neutral standing pose, facing forward or slightly angled.
            - Keep the background simple or softly blurred—focus on the character.
            - Do not include any text, watermarks, or logos.
            - Ensure the character is centered in the image with good lighting and clean framing.
            """, characterGuide);
    }

}