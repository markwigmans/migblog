package com.btb.migblog.services;

import com.btb.migblog.services.leonardo.LeonardoAPI;
import com.btb.migblog.services.leonardo.LeonardoImageOptions;
import com.btb.migblog.services.leonardo.LeonardoService;
import com.btb.migblog.services.perplexity.PerplexityAPI;
import com.btb.migblog.services.perplexity.PerplexityService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.btb.migblog.services.perplexity.PerplexityAPI.ChatModel;

@Service
@RequiredArgsConstructor
public class AIService {

    private final PerplexityService perplexityService;
    private final LeonardoService leonardoService;

    public PerplexityAPI.ChatCompletion question() {
        Message systemMessage = new SystemMessage("Be precise and concise.");
        Message userMessage = new UserMessage("ow many stars are there in our galaxy?");
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return perplexityService.chatCompletion(prompt, ChatModel.LLAMA_3_SONAR_SMALL_32K_ONLINE);
    }

    public PerplexityAPI.ChatCompletion jobReasons(int count, String domain, String location) {

        final String promptText = """
                Write {count} reasons why people in {location} should consider a {job} job.
                These reasons need to be short, so they fit on a poster.
                For instance, "{job} jobs are rewarding."
                """;

        final PromptTemplate promptTemplate = new PromptTemplate(promptText);
        promptTemplate.add("count", count);
        promptTemplate.add("job", domain);
        promptTemplate.add("location", location);

        return perplexityService.chatCompletion(promptTemplate.create(), ChatModel.LLAMA_3_SONAR_SMALL_32K_ONLINE);
    }

    public LeonardoAPI.Models models() {
        return leonardoService.updateModels();
    }

    public LeonardoAPI.PromptGenerationResponse improvePrompt() {
        return leonardoService.improvePrompt("generate a big cat");
    }

    public LeonardoAPI.GenerationsResponse createImage() {
        LeonardoAPI.SDGenerationResponse image = leonardoService.createImage(LeonardoImageOptions.builder()
                .width(512)
                .height(512)
                .numImages(1)
                .prompt("generate a big cat")
                .model("1e60896f-3c26-4296-8ecc-53e2afecc132")
                .build());
        return leonardoService.getImage(image.generation().generationId());
    }
}
