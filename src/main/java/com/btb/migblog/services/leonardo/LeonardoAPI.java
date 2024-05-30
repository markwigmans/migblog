package com.btb.migblog.services.leonardo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeonardoAPI {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Models(@JsonProperty("custom_models") List<Model> models) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Model(@JsonProperty("id") String id, @JsonProperty("name") String name,
                        @JsonProperty("description") String description,
                        @JsonProperty("nsfw") Boolean nsfw, @JsonProperty("featured") Boolean featured,
                        @JsonProperty("generated_image") GeneratedImage generatedImage) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PromptGenerationResponse(@JsonProperty("promptGeneration") PromptGeneration prompt) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record PromptGeneration(@JsonProperty("prompt") String prompt,
                                   @JsonProperty("apiCreditCost") Integer apiCreditCost) {
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record SDGenerationResponse(@JsonProperty("sdGenerationJob") SDGeneration generation) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record SDGeneration(@JsonProperty("generationId") String generationId,
                               @JsonProperty("apiCreditCost") Integer apiCreditCost) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record GenerationsResponse(@JsonProperty("generations_by_pk") GenerationsByPk generationsByPk) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GenerationsByPk(
            @JsonProperty("createdAt") String createdAt,
            @JsonProperty("generated_images") List<GeneratedImage> generatedImages,
            @JsonProperty("guidanceScale") Float guidanceScale,
            @JsonProperty("id") String id,
            @JsonProperty("imageHeight") Integer imageHeight,
            @JsonProperty("imageWidth") Integer imageWidth,
            @JsonProperty("inferenceSteps") Integer inferenceSteps,
            @JsonProperty("initStrength") Float initStrength,
            @JsonProperty("modelId") String modelId,
            @JsonProperty("negativePrompt") String negativePrompt,
            @JsonProperty("photoReal") Boolean photoReal,
            @JsonProperty("photoRealStrength") Float photoRealStrength,
            @JsonProperty("presetStyle") String presetStyle,
            @JsonProperty("prompt") String prompt,
            @JsonProperty("promptMagic") Boolean promptMagic,
            @JsonProperty("promptMagicVersion") String promptMagicVersion,
            @JsonProperty("promptMagicStrength") Float promptMagicStrength,
            @JsonProperty("public") Boolean isPublic,
            @JsonProperty("scheduler") String scheduler,
            @JsonProperty("sdVersion") String sdVersion,
            @JsonProperty("seed") Integer seed,
            @JsonProperty("status") String status) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record GeneratedImage(
            @JsonProperty("url") String url,
            @JsonProperty("nsfw") Boolean nsfw,
            @JsonProperty("id") String id,
            @JsonProperty("likeCount") Integer likeCount,
            @JsonProperty("motionMP4URL") String motionMP4URL) {
    }
}

