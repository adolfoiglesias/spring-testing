package com.calarix.sample.testing.common.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class Model {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    public Long id;
}
