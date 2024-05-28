package com.btb.migblog.services;

import com.btb.migblog.model.Feed;
import com.btb.migblog.repository.FeedRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitFeedService {

    private final FeedRepository feedRepository;

    @Transactional
    @PostConstruct
    void storeFeeds() {
        List<Feed> feeds = new ArrayList<>();
        feeds.add(new Feed("Ben Morris", "https://www.ben-morris.com/rss.xml"));
        feeds.add(new Feed("InfoQ Software Architecture", "https://feed.infoq.com/architecture/articles/"));
        feeds.add(new Feed("Software Architecture & Design", "https://www.umlzone.com/feed/"));
        feeds.add(new Feed("Mathias Verraes", "https://verraes.net/feed.atom"));
        feeds.add(new Feed("Vedcraft", "https://vedcraft.com/feed/"));
        feeds.add(new Feed("R&A IT Strategy", "https://ea.rna.nl/feed/"));
        feeds.add(new Feed("MIT Technology Review", "https://www.technologyreview.com/feed/"));
        feeds.add(new Feed("Microservices", "https://microservices.io/feed.xml"));

        feeds.forEach(feed -> {
            if (feedRepository.findByUrl(feed.getUrl()) == null) {
                feedRepository.save(feed);
            }
        });
    }
}
