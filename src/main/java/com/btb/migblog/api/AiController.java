package com.btb.migblog.api;

import com.btb.migblog.services.AIService;
import com.btb.migblog.services.leonardo.LeonardoAPI;
import com.btb.migblog.services.perplexity.PerplexityAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiController {

    private final AIService aiService;

    @GetMapping("/job-reasons")
    public ResponseEntity<PerplexityAPI.ChatCompletion> jobReasons(@RequestParam(value = "count", required = false, defaultValue = "3") int count,
                                             @RequestParam("job") String job,
                                             @RequestParam("location") String location) {
        PerplexityAPI.ChatCompletion result = aiService.jobReasons(count, job, location);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/question")
    public ResponseEntity<PerplexityAPI.ChatCompletion> question() {
        PerplexityAPI.ChatCompletion result = aiService.question();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/image/models")
    public ResponseEntity<LeonardoAPI.Models> getModels() {
        LeonardoAPI.Models result = aiService.models();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/image/improve")
    public ResponseEntity<LeonardoAPI.PromptGenerationResponse> improvePrompt() {
        LeonardoAPI.PromptGenerationResponse result = aiService.improvePrompt();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/image/create")
    public ResponseEntity<LeonardoAPI.GenerationsResponse> createImage() {
        LeonardoAPI.GenerationsResponse result = aiService.createImage();
        return ResponseEntity.ok(result);
    }
}