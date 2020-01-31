package com.epam.elastic_kafka_rnd.storage.service.mapper;

import com.epam.elastic_kafka_rnd.storage.domain.*;
import com.epam.elastic_kafka_rnd.storage.service.dto.WorkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Work} and its DTO {@link WorkDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkMapper extends EntityMapper<WorkDTO, Work> {


    @Mapping(target = "agents", ignore = true)
    @Mapping(target = "removeAgent", ignore = true)
    @Mapping(target = "instances", ignore = true)
    @Mapping(target = "removeInstance", ignore = true)
    Work toEntity(WorkDTO workDTO);

    default Work fromId(String id) {
        if (id == null) {
            return null;
        }
        Work work = new Work();
        work.setId(id);
        return work;
    }
}
