package com.btb.migblog.services.perplexity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class PerplexityAPI {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletionRequest(@JsonProperty("messages") List<ChatCompletionMessage> messages, @JsonProperty("model") String model) { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletionMessage(@JsonProperty("content") Object content, @JsonProperty("role") String role) { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletion(@JsonProperty("id") String id, @JsonProperty("choices") List<Choice> choices,
                              @JsonProperty("created") Long created, @JsonProperty("model") String model,
                              @JsonProperty("object") String object, @JsonProperty("usage") Usage usage) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Choice(@JsonProperty("finish_reason") ChatCompletionFinishReason finishReason,
                         @JsonProperty("index") Integer index,
                         @JsonProperty("message") ChatCompletionMessage message) { }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Usage(@JsonProperty("completion_tokens") Integer completionTokens,
                        @JsonProperty("prompt_tokens") Integer promptTokens,
                        @JsonProperty("total_tokens") Integer totalTokens) { }


    public enum ChatCompletionFinishReason {
        @JsonProperty("stop")
        STOP,
        @JsonProperty("length")
        LENGTH,
        @JsonProperty("content_filter")
        CONTENT_FILTER,
        @JsonProperty("tool_calls")
        TOOL_CALLS,
        @JsonProperty("function_call")
        FUNCTION_CALL,
        @JsonProperty("tool_call")
        TOOL_CALL;

        ChatCompletionFinishReason() {
        }
    }

    @Getter
    public enum ChatModel {
        LLAMA_3_SONAR_SMALL_32K_CHAT("llama-3-sonar-small-32k-chat"),
        LLAMA_3_SONAR_SMALL_32K_ONLINE("llama-3-sonar-small-32k-online"),
        LLAMA_3_SONAR_LARGE_32K_CHAT("llama-3-sonar-large-32k-chat"),
        LLAMA_3_SONAR_LARGE_32K_ONLINE("llama-3-sonar-large-32k-online"),
        LLAMA_3_8B_INSTRUCT("llama-3-8b-instruct,"),
        LLAMA_3_70B_INSTRUCT("llama-3-70b-instruct"),
        MIXTRAL_8X7B_INSTRUCT("mixtral-8x7b-instruct");

        private final String value;

        ChatModel(String value) {
            this.value = value;
        }
    }
}
