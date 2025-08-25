package com.futurenet.sorisoopbackend.customfairytale.infrastructure.util;

public class OpenAIPromptUtil {

    private OpenAIPromptUtil() {}


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
    - The story must have 7 scenes, each as a separate story beat.
    - Each scene must be described in a narrative style (no direct speech or dialogue!).
    - The flow must follow a classic story arc: introduction – development – climax – resolution – ending.
    - Each scene must include: who is present, what they are doing, their emotional state, and key visual/background details.
    - Emphasize positive, creative, and warm messages.
    - If conflict occurs, resolve it peacefully and creatively.

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
    * Do not wrap the output with any Markdown formatting such as ```json or ``` — output only plain JSON.
    """, age, concept);
    }


    public static String makeCustomFairyTaleImagePrompt(
            String characterGuide, String pageContent, String sceneType, String emotion
    ) {
        return String.format("""
            You are a professional illustrator for children’s picture books.
            
            Context:
            - This is one page of a 7-page fairy tale for young children.
            - All images in the book must look as if they belong together (same character style, same background atmosphere).
            - The main character’s appearance, clothing, accessories, and the background should remain exactly the same throughout the book, unless explicitly changed.
            
            Character Guide:
            %s
            
            Scene Description (for page, type: %s, main emotion: %s):
            %s
            
            Instructions:
            - Create a single illustration for this scene in a gentle watercolor or pastel style.
            - The image must feel warm, soft, and dreamy, suitable for a children’s storybook.
            - Do NOT include any text, logos, or letters in the image.
            - Focus on the main character(s) and the scene, maintaining absolute consistency with previous pages.
            
            Reminder:
            If any visual information is unclear, use your creativity but always keep the fairy tale mood and match the character guide.
            """, characterGuide, sceneType, emotion, pageContent);
    }


}
