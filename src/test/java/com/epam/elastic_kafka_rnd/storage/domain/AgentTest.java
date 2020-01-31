package com.epam.elastic_kafka_rnd.storage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epam.elastic_kafka_rnd.storage.web.rest.TestUtil;

public class AgentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agent.class);
        Agent agent1 = new Agent();
        agent1.setId("id1");
        Agent agent2 = new Agent();
        agent2.setId(agent1.getId());
        assertThat(agent1).isEqualTo(agent2);
        agent2.setId("id2");
        assertThat(agent1).isNotEqualTo(agent2);
        agent1.setId(null);
        assertThat(agent1).isNotEqualTo(agent2);
    }
}
