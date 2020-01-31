package com.epam.elastic_kafka_rnd.storage.web.rest;

import com.epam.elastic_kafka_rnd.storage.StorageApp;
import com.epam.elastic_kafka_rnd.storage.config.SecurityBeanOverrideConfiguration;
import com.epam.elastic_kafka_rnd.storage.domain.Work;
import com.epam.elastic_kafka_rnd.storage.repository.WorkRepository;
import com.epam.elastic_kafka_rnd.storage.service.WorkService;
import com.epam.elastic_kafka_rnd.storage.service.dto.WorkDTO;
import com.epam.elastic_kafka_rnd.storage.service.mapper.WorkMapper;
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


import java.util.List;

import static com.epam.elastic_kafka_rnd.storage.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, StorageApp.class})
public class WorkResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private WorkService workService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restWorkMockMvc;

    private Work work;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkResource workResource = new WorkResource(workService);
        this.restWorkMockMvc = MockMvcBuilders.standaloneSetup(workResource)
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
    public static Work createEntity() {
        Work work = new Work()
            .type(DEFAULT_TYPE)
            .genre(DEFAULT_GENRE)
            .title(DEFAULT_TITLE);
        return work;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Work createUpdatedEntity() {
        Work work = new Work()
            .type(UPDATED_TYPE)
            .genre(UPDATED_GENRE)
            .title(UPDATED_TITLE);
        return work;
    }

    @BeforeEach
    public void initTest() {
        workRepository.deleteAll();
        work = createEntity();
    }

    @Test
    public void createWork() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work
        WorkDTO workDTO = workMapper.toDto(work);
        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isCreated());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate + 1);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWork.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testWork.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    public void createWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workRepository.findAll().size();

        // Create the Work with an existing ID
        work.setId("existing_id");
        WorkDTO workDTO = workMapper.toDto(work);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkMockMvc.perform(post("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllWorks() throws Exception {
        // Initialize the database
        workRepository.save(work);

        // Get all the workList
        restWorkMockMvc.perform(get("/api/works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(work.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    public void getWork() throws Exception {
        // Initialize the database
        workRepository.save(work);

        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", work.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(work.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    public void getNonExistingWork() throws Exception {
        // Get the work
        restWorkMockMvc.perform(get("/api/works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWork() throws Exception {
        // Initialize the database
        workRepository.save(work);

        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Update the work
        Work updatedWork = workRepository.findById(work.getId()).get();
        updatedWork
            .type(UPDATED_TYPE)
            .genre(UPDATED_GENRE)
            .title(UPDATED_TITLE);
        WorkDTO workDTO = workMapper.toDto(updatedWork);

        restWorkMockMvc.perform(put("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isOk());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
        Work testWork = workList.get(workList.size() - 1);
        assertThat(testWork.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWork.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testWork.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    public void updateNonExistingWork() throws Exception {
        int databaseSizeBeforeUpdate = workRepository.findAll().size();

        // Create the Work
        WorkDTO workDTO = workMapper.toDto(work);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkMockMvc.perform(put("/api/works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Work in the database
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteWork() throws Exception {
        // Initialize the database
        workRepository.save(work);

        int databaseSizeBeforeDelete = workRepository.findAll().size();

        // Delete the work
        restWorkMockMvc.perform(delete("/api/works/{id}", work.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Work> workList = workRepository.findAll();
        assertThat(workList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
