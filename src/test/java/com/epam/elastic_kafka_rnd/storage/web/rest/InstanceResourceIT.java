package com.epam.elastic_kafka_rnd.storage.web.rest;

import com.epam.elastic_kafka_rnd.storage.StorageApp;
import com.epam.elastic_kafka_rnd.storage.config.SecurityBeanOverrideConfiguration;
import com.epam.elastic_kafka_rnd.storage.domain.Instance;
import com.epam.elastic_kafka_rnd.storage.repository.InstanceRepository;
import com.epam.elastic_kafka_rnd.storage.service.InstanceService;
import com.epam.elastic_kafka_rnd.storage.service.dto.InstanceDTO;
import com.epam.elastic_kafka_rnd.storage.service.mapper.InstanceMapper;
import com.epam.elastic_kafka_rnd.storage.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.epam.elastic_kafka_rnd.storage.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InstanceResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, StorageApp.class})
public class InstanceResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_PUBLICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUBLICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InstanceRepository instanceRepository;

    @Autowired
    private InstanceMapper instanceMapper;

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restInstanceMockMvc;

    private Instance instance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstanceResource instanceResource = new InstanceResource(instanceService);
        this.restInstanceMockMvc = MockMvcBuilders.standaloneSetup(instanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instance createEntity() {
        Instance instance = new Instance()
            .type(DEFAULT_TYPE)
            .title(DEFAULT_TITLE)
            .language(DEFAULT_LANGUAGE)
            .publicationDate(DEFAULT_PUBLICATION_DATE);
        return instance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instance createUpdatedEntity() {
        Instance instance = new Instance()
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .language(UPDATED_LANGUAGE)
            .publicationDate(UPDATED_PUBLICATION_DATE);
        return instance;
    }

    @BeforeEach
    public void initTest() {
        instanceRepository.deleteAll();
        instance = createEntity();
    }

    @Test
    public void createInstance() throws Exception {
        int databaseSizeBeforeCreate = instanceRepository.findAll().size();

        // Create the Instance
        InstanceDTO instanceDTO = instanceMapper.toDto(instance);
        restInstanceMockMvc.perform(post("/api/instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Instance in the database
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeCreate + 1);
        Instance testInstance = instanceList.get(instanceList.size() - 1);
        assertThat(testInstance.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInstance.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testInstance.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testInstance.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
    }

    @Test
    public void createInstanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instanceRepository.findAll().size();

        // Create the Instance with an existing ID
        instance.setId("existing_id");
        InstanceDTO instanceDTO = instanceMapper.toDto(instance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstanceMockMvc.perform(post("/api/instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Instance in the database
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllInstances() throws Exception {
        // Initialize the database
        instanceRepository.save(instance);

        // Get all the instanceList
        restInstanceMockMvc.perform(get("/api/instances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instance.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())));
    }
    
    @Test
    public void getInstance() throws Exception {
        // Initialize the database
        instanceRepository.save(instance);

        // Get the instance
        restInstanceMockMvc.perform(get("/api/instances/{id}", instance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instance.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()));
    }

    @Test
    public void getNonExistingInstance() throws Exception {
        // Get the instance
        restInstanceMockMvc.perform(get("/api/instances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInstance() throws Exception {
        // Initialize the database
        instanceRepository.save(instance);

        int databaseSizeBeforeUpdate = instanceRepository.findAll().size();

        // Update the instance
        Instance updatedInstance = instanceRepository.findById(instance.getId()).get();
        updatedInstance
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .language(UPDATED_LANGUAGE)
            .publicationDate(UPDATED_PUBLICATION_DATE);
        InstanceDTO instanceDTO = instanceMapper.toDto(updatedInstance);

        restInstanceMockMvc.perform(put("/api/instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceDTO)))
            .andExpect(status().isOk());

        // Validate the Instance in the database
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeUpdate);
        Instance testInstance = instanceList.get(instanceList.size() - 1);
        assertThat(testInstance.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInstance.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testInstance.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testInstance.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
    }

    @Test
    public void updateNonExistingInstance() throws Exception {
        int databaseSizeBeforeUpdate = instanceRepository.findAll().size();

        // Create the Instance
        InstanceDTO instanceDTO = instanceMapper.toDto(instance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstanceMockMvc.perform(put("/api/instances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Instance in the database
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteInstance() throws Exception {
        // Initialize the database
        instanceRepository.save(instance);

        int databaseSizeBeforeDelete = instanceRepository.findAll().size();

        // Delete the instance
        restInstanceMockMvc.perform(delete("/api/instances/{id}", instance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Instance> instanceList = instanceRepository.findAll();
        assertThat(instanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
