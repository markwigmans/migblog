package com.btb.migblog.services;

import com.btb.migblog.model.RssItem;
import com.btb.migblog.services.leonardo.LeonardoService;
import com.btb.migblog.services.perplexity.PerplexityAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {

    private final RssFeedService rssFeedService;
    private final AIService aiService;

    public void generate() {
        List<RssItem> newRssFeeds = rssFeedService.findNewRssFeeds();
        ChatResponse chatCompletion = aiService.topRssItems(newRssFeeds);
        log.info("Top RSS Feeds found: {} : {}", newRssFeeds.size(), chatCompletion.getResult().getOutput().getContent());
    }
}
