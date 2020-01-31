package com.epam.elastic_kafka_rnd.storage.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.epam.elastic_kafka_rnd.storage.web.rest.TestUtil;

public class WorkDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkDTO.class);
        WorkDTO workDTO1 = new WorkDTO();
        workDTO1.setId("id1");
        WorkDTO workDTO2 = new WorkDTO();
        assertThat(workDTO1).isNotEqualTo(workDTO2);
        workDTO2.setId(workDTO1.getId());
        assertThat(workDTO1).isEqualTo(workDTO2);
        workDTO2.setId("id2");
        assertThat(workDTO1).isNotEqualTo(workDTO2);
        workDTO1.setId(null);
        assertThat(workDTO1).isNotEqualTo(workDTO2);
    }
}
