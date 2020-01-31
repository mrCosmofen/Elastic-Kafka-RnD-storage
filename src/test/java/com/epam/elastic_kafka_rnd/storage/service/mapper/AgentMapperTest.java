package com.epam.elastic_kafka_rnd.storage.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AgentMapperTest {

    private AgentMapper agentMapper;

    @BeforeEach
    public void setUp() {
        agentMapper = new AgentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id2";
        assertThat(agentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(agentMapper.fromId(null)).isNull();
    }
}
