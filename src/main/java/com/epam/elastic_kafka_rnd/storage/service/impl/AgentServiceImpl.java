package com.epam.elastic_kafka_rnd.storage.service.impl;

import com.epam.elastic_kafka_rnd.storage.service.AgentService;
import com.epam.elastic_kafka_rnd.storage.domain.Agent;
import com.epam.elastic_kafka_rnd.storage.repository.AgentRepository;
import com.epam.elastic_kafka_rnd.storage.service.dto.AgentDTO;
import com.epam.elastic_kafka_rnd.storage.service.mapper.AgentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Agent}.
 */
@Service
public class AgentServiceImpl implements AgentService {

    private final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);

    private final AgentRepository agentRepository;

    private final AgentMapper agentMapper;

    public AgentServiceImpl(AgentRepository agentRepository, AgentMapper agentMapper) {
        this.agentRepository = agentRepository;
        this.agentMapper = agentMapper;
    }

    /**
     * Save a agent.
     *
     * @param agentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AgentDTO save(AgentDTO agentDTO) {
        log.debug("Request to save Agent : {}", agentDTO);
        Agent agent = agentMapper.toEntity(agentDTO);
        agent = agentRepository.save(agent);
        return agentMapper.toDto(agent);
    }

    /**
     * Get all the agents.
     *
     * @return the list of entities.
     */
    @Override
    public List<AgentDTO> findAll() {
        log.debug("Request to get all Agents");
        return agentRepository.findAll().stream()
            .map(agentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one agent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<AgentDTO> findOne(String id) {
        log.debug("Request to get Agent : {}", id);
        return agentRepository.findById(id)
            .map(agentMapper::toDto);
    }

    /**
     * Delete the agent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Agent : {}", id);
        agentRepository.deleteById(id);
    }
}
