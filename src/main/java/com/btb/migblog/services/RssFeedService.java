package com.btb.migblog.services;

import com.btb.migblog.model.Feed;
import com.btb.migblog.model.RssItem;
import com.btb.migblog.repository.FeedRepository;
import com.btb.migblog.repository.RssItemRepository;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestClient;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RssFeedService {

    private final RssItemRepository rssItemRepository;
    private final FeedRepository feedRepository;
    private final PlatformTransactionManager transactionManager;

    private final RestClient restClient = RestClient.create();

    public void storeRssFeeds() {
        feedRepository.findAll().forEach(feed -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    fetchRssFeed(feed).stream()
                            .filter(i -> rssItemRepository.findByUrl(i.getUrl()) == null)
                            .forEach(rssItemRepository::save);
                }
            });
        });
    }

    @SneakyThrows
    List<RssItem> fetchRssFeed(Feed feed) {
        List<RssItem> feedItems = new ArrayList<>();

        String response = restClient.get()
                .uri(feed.getUrl())
                .retrieve()
                .body(String.class);

        if (StringUtils.isNotEmpty(response)) {
            final SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(IOUtils.toInputStream(response, Charset.defaultCharset())));
            for (SyndEntry entry : syndFeed.getEntries()) {
                RssItem item = new RssItem(entry.getTitle(),
                        entry.getLink(),
                        entry.getPublishedDate(),
                        clean(entry.getDescription()), feed);
                feedItems.add(item);
            }
        }
        return feedItems;
    }

    String clean(SyndContent syndContent) {
        if (syndContent != null && StringUtils.isNotEmpty(syndContent.getValue())) {
            // Replace all non ascii chars in the string.
            String normalized = syndContent.getValue().replaceAll("[^\\x0A\\x0D\\x20-\\x7E]", "");
            return StringUtils.substring(Jsoup.parse(normalized).text(), 0, RssItem.DESCRIPTION_SIZE);
        } else {
            return null;
        }
    }

}
