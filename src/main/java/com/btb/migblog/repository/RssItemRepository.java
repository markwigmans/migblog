package com.btb.migblog.repository;

import com.btb.migblog.model.RssItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssItemRepository extends JpaRepository<RssItem, Long> {

    RssItem findByUrl(String url);
}
