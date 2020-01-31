package com.epam.elastic_kafka_rnd.storage.web.rest;

import com.epam.elastic_kafka_rnd.storage.service.InstanceService;
import com.epam.elastic_kafka_rnd.storage.web.rest.errors.BadRequestAlertException;
import com.epam.elastic_kafka_rnd.storage.service.dto.InstanceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epam.elastic_kafka_rnd.storage.domain.Instance}.
 */
@RestController
@RequestMapping("/api")
public class InstanceResource {

    private final Logger log = LoggerFactory.getLogger(InstanceResource.class);

    private static final String ENTITY_NAME = "storageInstance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstanceService instanceService;

    public InstanceResource(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    /**
     * {@code POST  /instances} : Create a new instance.
     *
     * @param instanceDTO the instanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instanceDTO, or with status {@code 400 (Bad Request)} if the instance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instances")
    public ResponseEntity<InstanceDTO> createInstance(@RequestBody InstanceDTO instanceDTO) throws URISyntaxException {
        log.debug("REST request to save Instance : {}", instanceDTO);
        if (instanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new instance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstanceDTO result = instanceService.save(instanceDTO);
        return ResponseEntity.created(new URI("/api/instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instances} : Updates an existing instance.
     *
     * @param instanceDTO the instanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instanceDTO,
     * or with status {@code 400 (Bad Request)} if the instanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instances")
    public ResponseEntity<InstanceDTO> updateInstance(@RequestBody InstanceDTO instanceDTO) throws URISyntaxException {
        log.debug("REST request to update Instance : {}", instanceDTO);
        if (instanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstanceDTO result = instanceService.save(instanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instances} : get all the instances.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instances in body.
     */
    @GetMapping("/instances")
    public List<InstanceDTO> getAllInstances() {
        log.debug("REST request to get all Instances");
        return instanceService.findAll();
    }

    /**
     * {@code GET  /instances/:id} : get the "id" instance.
     *
     * @param id the id of the instanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instances/{id}")
    public ResponseEntity<InstanceDTO> getInstance(@PathVariable String id) {
        log.debug("REST request to get Instance : {}", id);
        Optional<InstanceDTO> instanceDTO = instanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instanceDTO);
    }

    /**
     * {@code DELETE  /instances/:id} : delete the "id" instance.
     *
     * @param id the id of the instanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instances/{id}")
    public ResponseEntity<Void> deleteInstance(@PathVariable String id) {
        log.debug("REST request to delete Instance : {}", id);
        instanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
