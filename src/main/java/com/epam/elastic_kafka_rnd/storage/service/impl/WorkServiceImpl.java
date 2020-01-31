package com.epam.elastic_kafka_rnd.storage.service.impl;

import com.epam.elastic_kafka_rnd.storage.service.WorkService;
import com.epam.elastic_kafka_rnd.storage.domain.Work;
import com.epam.elastic_kafka_rnd.storage.repository.WorkRepository;
import com.epam.elastic_kafka_rnd.storage.service.dto.WorkDTO;
import com.epam.elastic_kafka_rnd.storage.service.mapper.WorkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Work}.
 */
@Service
public class WorkServiceImpl implements WorkService {

    private final Logger log = LoggerFactory.getLogger(WorkServiceImpl.class);

    private final WorkRepository workRepository;

    private final WorkMapper workMapper;

    public WorkServiceImpl(WorkRepository workRepository, WorkMapper workMapper) {
        this.workRepository = workRepository;
        this.workMapper = workMapper;
    }

    /**
     * Save a work.
     *
     * @param workDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkDTO save(WorkDTO workDTO) {
        log.debug("Request to save Work : {}", workDTO);
        Work work = workMapper.toEntity(workDTO);
        work = workRepository.save(work);
        return workMapper.toDto(work);
    }

    /**
     * Get all the works.
     *
     * @return the list of entities.
     */
    @Override
    public List<WorkDTO> findAll() {
        log.debug("Request to get all Works");
        return workRepository.findAll().stream()
            .map(workMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one work by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<WorkDTO> findOne(String id) {
        log.debug("Request to get Work : {}", id);
        return workRepository.findById(id)
            .map(workMapper::toDto);
    }

    /**
     * Delete the work by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Work : {}", id);
        workRepository.deleteById(id);
    }
}
