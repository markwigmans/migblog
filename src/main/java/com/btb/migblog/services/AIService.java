package com.btb.migblog.services;

import com.btb.migblog.model.RssItem;
import com.btb.migblog.services.leonardo.LeonardoAPI;
import com.btb.migblog.services.leonardo.LeonardoImageOptions;
import com.btb.migblog.services.leonardo.LeonardoService;
import com.btb.migblog.services.perplexity.PerplexityAPI;
import com.btb.migblog.services.perplexity.PerplexityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.btb.migblog.services.perplexity.PerplexityAPI.ChatModel;

@Service
@RequiredArgsConstructor
public class AIService {

    private final PerplexityService perplexityService;
    private final LeonardoService leonardoService;
    private final ObjectMapper objectMapper;

    public ChatResponse topRssItems(List<RssItem> rssItems) {

        final String promptText = """
                You are an expert in the field of software architecture.
                
                Create a ranked list of the top 3 from the given options related to 'software architecture'. Order them from title I'd be most likely to click on to least likely.
                                                
                The input is a JSON structure with an array of options. You must only consider the field 'title'.
                Format the output exclusively in JSON in the same structure as the input. Do not add any text before or after the generated JSON structure.
                                
                The options are: <options>
                
                Example output is:
                {
                  "options": [
                    {"title": "Creating Software Architecture with Modern Diagramming Tools", "refId": 5},
                    {"title": "How to make architecture decisions", "refId": 3},
                    {"title": "Article: 9 Steps towards an Agile Architecture", "refId": 4}
                  ]                
                }
                """;

        ST template = new ST(promptText, '<' , '>');
        template.add("options", inputOptionsToJson(rssItems));
        final PerplexityAPI.ChatCompletion chatCompletion = perplexityService.chatCompletion(new Prompt(template.render()), ChatModel.LLAMA_3_SONAR_LARGE_32K_ONLINE);
        return new ChatResponse(chatCompletion.choices().stream().map(r -> new Generation(r.message().content().toString())).toList());
    }

    record InputOptions(String title, long refId) {}

    @SneakyThrows
    private String inputOptionsToJson(List<RssItem> rssItems) {
        List<InputOptions> result = new ArrayList<>();
        if (rssItems != null) {
            result = rssItems.stream().map(r -> new InputOptions(r.getTitle(), r.getId())).toList();
        }
        return objectMapper.writeValueAsString(result);
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
