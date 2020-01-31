package com.epam.elastic_kafka_rnd.storage.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class WorkMapperTest {

    private WorkMapper workMapper;

    @BeforeEach
    public void setUp() {
        workMapper = new WorkMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id2";
        assertThat(workMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workMapper.fromId(null)).isNull();
    }
}
