package com.epam.elastic_kafka_rnd.storage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epam.elastic_kafka_rnd.storage.web.rest.TestUtil;

public class WorkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Work.class);
        Work work1 = new Work();
        work1.setId("id1");
        Work work2 = new Work();
        work2.setId(work1.getId());
        assertThat(work1).isEqualTo(work2);
        work2.setId("id2");
        assertThat(work1).isNotEqualTo(work2);
        work1.setId(null);
        assertThat(work1).isNotEqualTo(work2);
    }
}
