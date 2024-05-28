package com.btb.migblog.repository;

import com.btb.migblog.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Feed findByUrl(String url);
}
