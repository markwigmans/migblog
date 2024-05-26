package com.btb.migblog.services.leonardo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.ai.image.ImageOptions;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class LeonardoImageOptions implements ImageOptions {

    @JsonProperty("n")
    private Integer n;

    @JsonProperty("modelId")
    private String model;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("response_format")
    private String responseFormat;

    // Enable to use Alchemy. Note: The appropriate Alchemy version is selected for the specified model. For example, XL models will use Alchemy V2.
    @JsonProperty("alchemy")
    private Boolean alchemy;

    // Contrast Ratio to use with Alchemy. Must be a float between 0 and 1 inclusive.
    @JsonProperty("contrastRatio")
    private Float contrastRatio;

    // Enable to use ControlNet. Requires an init image to be provided. Requires a model based on SD v1.5
    @JsonProperty("controlNet")
    private Boolean controlNet;

    // The type of ControlNet to use.
    @JsonProperty("controlNetType")
    private String controlNetType;

    // Enable to use the Expanded Domain feature of Alchemy.
    @JsonProperty("expandedDomain")
    private Boolean expandedDomain;

    //   Enable to use the Fantasy Avatar feature.
    @JsonProperty("fantasyAvatar")
    private Boolean fantasyAvatar;

    //  How strongly the generation should reflect the prompt. 7 is recommended. Must be between 1 and 20.
    @JsonProperty("guidance_scale")
    private Integer guidanceScale;

    // Enable to use the High Contrast feature of Prompt Magic. Note: Controls RAW mode. Set to false to enable RAW mode.
    @JsonProperty("highContrast")
    private Boolean highContrast;

    //  Enable to use the High Resolution feature of Prompt Magic.
    @JsonProperty("highResolution")
    private Boolean highResolution;

    // The negative prompt used for the image generation
    @JsonProperty("negative_prompt")
    private String negativePrompt;

    // The number of images to generate. Must be between 1 and 8. If either width or height is over 768, must be between 1 and 4.
    @JsonProperty("num_images")
    private Integer numImages;

    // The Step Count to use for the generation. Must be between 10 and 60. Default is 15.
    @JsonProperty("num_inference_steps")
    private Integer numInferenceSteps;

    //  Enable the photoReal feature. Requires enabling alchemy and unspecifying modelId (for photoRealVersion V1).
    @JsonProperty("photoReal")
    private Boolean photoReal;

    // The version of photoReal to use. Must be v1 or v2.
    @JsonProperty("photoRealVersion")
    private String photoRealVersion;

    // Depth of field of photoReal. Must be 0.55 for low, 0.5 for medium, or 0.45 for high. Defaults to 0.55 if not specified.
    @JsonProperty("photoRealStrength")
    private Float photoRealStrength;

    // The style to generate images with. When photoReal is enabled, refer to the Guide section for a full list. When alchemy is disabled, use LEONARDO or NONE. When alchemy is enabled, use ANIME, CREATIVE, DYNAMIC, ENVIRONMENT, GENERAL, ILLUSTRATION, PHOTOGRAPHY, RAYTRACED, RENDER_3D, SKETCH_BW, SKETCH_COLOR, or NONE.
    // Default: DYNAMIC
    @JsonProperty("presetStyle")
    private String presetStyle;

    //  The prompt used to generate images
    @JsonProperty("prompt")
    private String prompt;

    //  Enable to use Prompt Magic.
    @JsonProperty("promptMagic")
    private Boolean promptMagic;

    // Strength of prompt magic. Must be a float between 0.1 and 1.0
    @JsonProperty("promptMagicStrength")
    private Float promptMagicStrength;

    // Prompt magic version v2 or v3, for use when promptMagic: true
    @JsonProperty("promptMagicVersion")
    private String promptMagicVersion;

    //Whether the generated images should show in the community feed.
    @JsonProperty("public")
    private Boolean isPublic;

    //  The scheduler to generate images with. Defaults to EULER_DISCRETE if not specified.
    @JsonProperty("scheduler")
    private String scheduler;

    // The base version of stable diffusion to use if not using a custom model. v1_5 is 1.5, v2 is 2.1, if not specified it will default to v1_5. Also includes SDXL and SDXL Lightning models
    @JsonProperty("sd_version")
    private String sdVersion;

    @JsonProperty("seed")
    private Integer seed;

    //  Whether the generated images should tile on all axis.
    @JsonProperty("tiling")
    private Boolean tiling;

    // Which type of transparency this image should use
    @JsonProperty("transparency")
    private String transparency;

    // Whether the generated images should be unzoomed (requires unzoomAmount and init_image_id to be set).
    @JsonProperty("unzoom")
    private Boolean unzoom;

    // How much the image should be unzoomed (requires an init_image_id and unzoom to be set to true).
    @JsonProperty("unzoomAmount")
    private Float unzoomAmount;

    // How much the image should be upscaled. (Enterprise Only)
    @JsonProperty("upscaleRatio")
    private Float upscaleRatio;

    // How much weighting to use for ControlNet. This parameter works with controlNet and controlNetType.
    @JsonProperty("weighting")
    private Float weighting;
}
