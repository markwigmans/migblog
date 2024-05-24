package com.btb.migblog.services.perplexity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
public class PerplexityService {

    private RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${perplexity.api.key}")
    private String apiToken;

    @PostConstruct
    void init() {
        restClient = RestClient.builder()
                .baseUrl("https://api.perplexity.ai/chat/completions")
                .defaultHeaders(headers -> {
                    headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
                    headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
                    headers.add("authorization", "Bearer " + apiToken);
                }).build();
    }

    public PerplexityAPI.ChatCompletion chatCompletion(Prompt prompt, PerplexityAPI.ChatModel model) {
        try {
            String message = objectMapper.writeValueAsString(createRequest(prompt, model));

            return restClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(message)
                    .retrieve()
                    .body(PerplexityAPI.ChatCompletion.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PerplexityAPI.ChatCompletionRequest createRequest(Prompt prompt, PerplexityAPI.ChatModel model) {
        return new PerplexityAPI.ChatCompletionRequest(prompt.getInstructions().stream().map(message -> new PerplexityAPI.ChatCompletionMessage(message.getContent(),
                message.getMessageType().getValue())).toList(), model.getValue());
    }
}
