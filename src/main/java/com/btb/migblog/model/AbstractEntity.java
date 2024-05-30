package com.btb.migblog.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
}
