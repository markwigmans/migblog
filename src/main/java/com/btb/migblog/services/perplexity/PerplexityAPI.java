package com.btb.migblog.services.perplexity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class PerplexityAPI {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletionRequest(List<ChatCompletionMessage> messages, String model) {

        public ChatCompletionRequest(@JsonProperty("messages") List<ChatCompletionMessage> messages, @JsonProperty("model") String model) {
            this.messages = messages;
            this.model = model;
        }

        @JsonProperty("messages")
        public List<ChatCompletionMessage> messages() {
            return this.messages;
        }

        @JsonProperty("model")
        public String model() {
            return this.model;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletionMessage(Object content, String role) {

        public ChatCompletionMessage(@JsonProperty("content") Object content, @JsonProperty("role") String role) {
            this.content = content;
            this.role = role;
        }

        @JsonProperty("content")
        public Object content() {
            return this.content;
        }

        @JsonProperty("role")
        public String role() {
            return this.role;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletion(String id, List<ChatCompletion.Choice> choices, Long created, String model, String object, Usage usage) {
        public ChatCompletion(@JsonProperty("id") String id, @JsonProperty("choices") List<ChatCompletion.Choice> choices,
                              @JsonProperty("created") Long created, @JsonProperty("model") String model,
                              @JsonProperty("object") String object, @JsonProperty("usage") Usage usage) {
            this.id = id;
            this.choices = choices;
            this.created = created;
            this.model = model;
            this.object = object;
            this.usage = usage;
        }

        @JsonProperty("id")
        public String id() {
            return this.id;
        }

        @JsonProperty("choices")
        public List<ChatCompletion.Choice> choices() {
            return this.choices;
        }

        @JsonProperty("created")
        public Long created() {
            return this.created;
        }

        @JsonProperty("model")
        public String model() {
            return this.model;
        }

        @JsonProperty("object")
        public String object() {
            return this.object;
        }

        @JsonProperty("usage")
        public Usage usage() {
            return this.usage;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record Choice(ChatCompletionFinishReason finishReason, Integer index, ChatCompletionMessage message) {
            public Choice(@JsonProperty("finish_reason") ChatCompletionFinishReason finishReason, @JsonProperty("index") Integer index, @JsonProperty("message") ChatCompletionMessage message) {
                this.finishReason = finishReason;
                this.index = index;
                this.message = message;
            }

            @JsonProperty("finish_reason")
            public ChatCompletionFinishReason finishReason() {
                return this.finishReason;
            }

            @JsonProperty("index")
            public Integer index() {
                return this.index;
            }

            @JsonProperty("message")
            public ChatCompletionMessage message() {
                return this.message;
            }
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Usage(Integer completionTokens, Integer promptTokens, Integer totalTokens) {
        public Usage(@JsonProperty("completion_tokens") Integer completionTokens, @JsonProperty("prompt_tokens") Integer promptTokens, @JsonProperty("total_tokens") Integer totalTokens) {
            this.completionTokens = completionTokens;
            this.promptTokens = promptTokens;
            this.totalTokens = totalTokens;
        }

        @JsonProperty("completion_tokens")
        public Integer completionTokens() {
            return this.completionTokens;
        }

        @JsonProperty("prompt_tokens")
        public Integer promptTokens() {
            return this.promptTokens;
        }

        @JsonProperty("total_tokens")
        public Integer totalTokens() {
            return this.totalTokens;
        }
    }

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
