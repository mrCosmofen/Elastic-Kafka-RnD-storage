package com.epam.elastic_kafka_rnd.storage.service;

import com.epam.elastic_kafka_rnd.storage.service.dto.WorkDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epam.elastic_kafka_rnd.storage.domain.Work}.
 */
public interface WorkService {

    /**
     * Save a work.
     *
     * @param workDTO the entity to save.
     * @return the persisted entity.
     */
    WorkDTO save(WorkDTO workDTO);

    /**
     * Get all the works.
     *
     * @return the list of entities.
     */
    List<WorkDTO> findAll();


    /**
     * Get the "id" work.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkDTO> findOne(String id);

    /**
     * Delete the "id" work.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
