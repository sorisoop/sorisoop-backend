package com.futurenet.sorisoopbackend.customfairytale.infrastructure.util;

public class OpenAIPromptUtil {

    private OpenAIPromptUtil() {}

    public static String makeStorySynopsisPrompt(int age) {
        return String.format("""
            You are a creative children's book writer.
            
            Look at the provided image, and imagine 3 completely different story synopses (short summaries) suitable for a children's fairy tale book.
            
            For each synopsis, do the following:
            - Write in two languages: first in Korean (for children/parents), then in English (for reference)
            - Clearly introduce the main character, the setting, and a key problem, adventure, or special experience
            - Each story must be unique, creative, positive, and suitable for children aged %d
            - Each story should be a single paragraph, 3-4 sentences, not just variations of the same plot
            
            Output format:
            [
              { "id": 1, "synopsis_kr": "한글 요약", "synopsis_en": "English summary" },
              { "id": 2, "synopsis_kr": "한글 요약", "synopsis_en": "English summary" },
              { "id": 3, "synopsis_kr": "한글 요약", "synopsis_en": "English summary" }
            ]
            
            **IMPORTANT:**
            - Output ONLY valid JSON in the exact format above.
            - Do NOT add any comments, explanations, or code block symbols like ``` or similar.
            - Only the pure JSON array should be returned.
            """, age);
    }


    public static String makeCharacterInfoPrompt() {
        return """
            You are a professional children’s book illustrator.
            
            Analyze the following image and extract a detailed, creative character guide in English.
            For each main and side character (including animals, insects, etc.),
            write a separate paragraph describing ONLY their physical and visual features.
            
            - Gender/Age group (e.g., a young girl around 6-7)
            - Face (e.g., big round eyes, small nose, freckles, bright smile)
            - Hair (e.g., short light brown bob, bangs to the left)
            - Clothing/Accessories (e.g., blue and white striped shirt, overalls, red sneakers, holding a teddy bear)
            - Unique features (e.g., yellow star pattern on shirt, net for collecting bugs, wings, tail, etc.)
            
            * DO NOT describe art style, color tone, background, or overall mood.
            * If visual info is missing, supplement with creative, fairy-tale appropriate details.
            * Write naturally, one paragraph per character/animal/insect.
            * Keep the language friendly, imaginative, and suitable for a children’s storybook.
            """;
    }


    public static String makeCustomFairyTaleScriptPrompt(int age) {
        return String.format("""
            You are a professional children's book author.
            
            Create a complete fairy tale for children aged %d, based on the provided image.
            
            - The story must have 7 scenes, each as a separate story beat.
            - Each scene must be described in a narrative style (no direct speech or dialogue!).
            - The flow must follow a classic story arc: introduction – development – climax – resolution – ending.
            - Each scene must include: who is present, what they are doing, their emotional state, and key visual/background details.
            - Emphasize positive, creative, and warm messages.
            - If conflict occurs, resolve it peacefully and creatively.
            - Do not violate OpenAI content policy.
            
            Output format (JSON, English + Korean):
            
            [
              {
                "page": 1,
                "content_kr": "장면 설명 (서술체, 감정 포함)",
                "content_en": "Scene description in English (narrative, focus on actions, emotions, and background)",
                "scene_type": "introduction", // e.g., introduction, development, climax, resolution, ending
                "emotion": "hope" // e.g., happy, curious, scared, surprised, relieved
              },
              ...
              {
                "page": 7,
                "content_kr": "...",
                "content_en": "...",
                "scene_type": "...",
                "emotion": "..."
              }
            ]
            
            * Output ONLY valid JSON. No explanations, comments, or code blocks.
            * For each page, always include scene_type and emotion for later use.
            * Use imaginative, child-appropriate language.
            """, age);
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
