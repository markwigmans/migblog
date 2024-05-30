package com.btb.migblog.repository;

import com.btb.migblog.model.RssItem;
import com.btb.migblog.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RssItemRepository extends JpaRepository<RssItem, Long> {

    RssItem findByUrl(String url);
    List<RssItem> findByStatus(Status status);
}
