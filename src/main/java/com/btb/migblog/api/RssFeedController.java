package com.btb.migblog.api;

import com.btb.migblog.services.RssFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RssFeedController {


    private final RssFeedService rssFeedService;

    @GetMapping("/rss/store")
    public void storeFeeds() {
        rssFeedService.storeRssFeeds();
    }
}
