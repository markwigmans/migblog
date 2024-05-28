package com.btb.migblog.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RssItem extends AbstractEntity {

    public final static int DESCRIPTION_SIZE = 4000;

    private String title;
    private String url;
    private Date pubDate;
    @Column(length = DESCRIPTION_SIZE)
    private String description;
    @ManyToOne
    private Feed feed;
}