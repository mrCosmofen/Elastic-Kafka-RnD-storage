package com.epam.elastic_kafka_rnd.storage.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class InstanceMapperTest {

    private InstanceMapper instanceMapper;

    @BeforeEach
    public void setUp() {
        instanceMapper = new InstanceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id2";
        assertThat(instanceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(instanceMapper.fromId(null)).isNull();
    }
}
