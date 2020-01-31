package com.epam.elastic_kafka_rnd.storage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epam.elastic_kafka_rnd.storage.web.rest.TestUtil;

public class InstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instance.class);
        Instance instance1 = new Instance();
        instance1.setId("id1");
        Instance instance2 = new Instance();
        instance2.setId(instance1.getId());
        assertThat(instance1).isEqualTo(instance2);
        instance2.setId("id2");
        assertThat(instance1).isNotEqualTo(instance2);
        instance1.setId(null);
        assertThat(instance1).isNotEqualTo(instance2);
    }
}
