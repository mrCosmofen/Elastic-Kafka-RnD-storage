package com.epam.elastic_kafka_rnd.storage.service;

import com.epam.elastic_kafka_rnd.storage.service.dto.AgentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.epam.elastic_kafka_rnd.storage.domain.Agent}.
 */
public interface AgentService {

    /**
     * Save a agent.
     *
     * @param agentDTO the entity to save.
     * @return the persisted entity.
     */
    AgentDTO save(AgentDTO agentDTO);

    /**
     * Get all the agents.
     *
     * @return the list of entities.
     */
    List<AgentDTO> findAll();


    /**
     * Get the "id" agent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgentDTO> findOne(String id);

    /**
     * Delete the "id" agent.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
