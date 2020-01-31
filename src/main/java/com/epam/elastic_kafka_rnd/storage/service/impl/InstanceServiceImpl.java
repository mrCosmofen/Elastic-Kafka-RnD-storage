package com.epam.elastic_kafka_rnd.storage.service.impl;

import com.epam.elastic_kafka_rnd.storage.service.InstanceService;
import com.epam.elastic_kafka_rnd.storage.domain.Instance;
import com.epam.elastic_kafka_rnd.storage.repository.InstanceRepository;
import com.epam.elastic_kafka_rnd.storage.service.dto.InstanceDTO;
import com.epam.elastic_kafka_rnd.storage.service.mapper.InstanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Instance}.
 */
@Service
public class InstanceServiceImpl implements InstanceService {

    private final Logger log = LoggerFactory.getLogger(InstanceServiceImpl.class);

    private final InstanceRepository instanceRepository;

    private final InstanceMapper instanceMapper;

    public InstanceServiceImpl(InstanceRepository instanceRepository, InstanceMapper instanceMapper) {
        this.instanceRepository = instanceRepository;
        this.instanceMapper = instanceMapper;
    }

    /**
     * Save a instance.
     *
     * @param instanceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InstanceDTO save(InstanceDTO instanceDTO) {
        log.debug("Request to save Instance : {}", instanceDTO);
        Instance instance = instanceMapper.toEntity(instanceDTO);
        instance = instanceRepository.save(instance);
        return instanceMapper.toDto(instance);
    }

    /**
     * Get all the instances.
     *
     * @return the list of entities.
     */
    @Override
    public List<InstanceDTO> findAll() {
        log.debug("Request to get all Instances");
        return instanceRepository.findAll().stream()
            .map(instanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one instance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<InstanceDTO> findOne(String id) {
        log.debug("Request to get Instance : {}", id);
        return instanceRepository.findById(id)
            .map(instanceMapper::toDto);
    }

    /**
     * Delete the instance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Instance : {}", id);
        instanceRepository.deleteById(id);
    }
}
