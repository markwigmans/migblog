package com.btb.migblog.services;

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
}
