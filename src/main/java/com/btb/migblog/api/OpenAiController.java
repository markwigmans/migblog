package com.btb.migblog.api;

import com.btb.migblog.services.AIService;
import com.btb.migblog.services.perplexity.PerplexityAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OpenAiController {

    private final AIService aiService;

    @GetMapping("/job-reasons")
    public ResponseEntity<String> jobReasons(@RequestParam(value = "count", required = false, defaultValue = "3") int count,
                                             @RequestParam("job") String job,
                                             @RequestParam("location") String location) {
        PerplexityAPI.ChatCompletion result = aiService.jobReasons(count, job, location);

        return ResponseEntity.ok(result.toString());
    }

    @GetMapping("/question")
    public ResponseEntity<String> question() {
        PerplexityAPI.ChatCompletion result = aiService.question();
        return ResponseEntity.ok(result.toString());
    }
}