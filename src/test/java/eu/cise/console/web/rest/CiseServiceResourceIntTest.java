package eu.cise.console.web.rest;

import eu.cise.console.GatewayuiApp;

import eu.cise.console.domain.CiseService;
import eu.cise.console.repository.CiseServiceRepository;
import eu.cise.console.repository.search.CiseServiceSearchRepository;
import eu.cise.console.service.CiseServiceService;
import eu.cise.console.service.dto.CiseServiceDTO;
import eu.cise.console.service.mapper.CiseServiceMapper;
import eu.cise.console.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static eu.cise.console.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import eu.cise.console.domain.enumeration.CiseServiceType;
import eu.cise.console.domain.enumeration.CiseServiceOperationType;
/**
 * Test class for the CiseServiceResource REST controller.
 *
 * @see CiseServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayuiApp.class)
public class CiseServiceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final CiseServiceType DEFAULT_SERVICE_TYPE = CiseServiceType.VESSEL_DOCUMENT_SERVICE;
    private static final CiseServiceType UPDATED_SERVICE_TYPE = CiseServiceType.ORGANIZATION_DOCUMENT_SERVICE;

    private static final CiseServiceOperationType DEFAULT_SERVICE_OPERATION = CiseServiceOperationType.PUSH;
    private static final CiseServiceOperationType UPDATED_SERVICE_OPERATION = CiseServiceOperationType.SUBSCRIBE;

    @Autowired
    private CiseServiceRepository ciseServiceRepository;

    @Autowired
    private CiseServiceMapper ciseServiceMapper;
    
    @Autowired
    private CiseServiceService ciseServiceService;

    /**
     * This repository is mocked in the eu.cise.console.repository.search test package.
     *
     * @see eu.cise.console.repository.search.CiseServiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CiseServiceSearchRepository mockCiseServiceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCiseServiceMockMvc;

    private CiseService ciseService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiseServiceResource ciseServiceResource = new CiseServiceResource(ciseServiceService);
        this.restCiseServiceMockMvc = MockMvcBuilders.standaloneSetup(ciseServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CiseService createEntity(EntityManager em) {
        CiseService ciseService = new CiseService()
            .name(DEFAULT_NAME)
            .serviceType(DEFAULT_SERVICE_TYPE)
            .serviceOperation(DEFAULT_SERVICE_OPERATION);
        return ciseService;
    }

    @Before
    public void initTest() {
        ciseService = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiseService() throws Exception {
        int databaseSizeBeforeCreate = ciseServiceRepository.findAll().size();

        // Create the CiseService
        CiseServiceDTO ciseServiceDTO = ciseServiceMapper.toDto(ciseService);
        restCiseServiceMockMvc.perform(post("/api/cise-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the CiseService in the database
        List<CiseService> ciseServiceList = ciseServiceRepository.findAll();
        assertThat(ciseServiceList).hasSize(databaseSizeBeforeCreate + 1);
        CiseService testCiseService = ciseServiceList.get(ciseServiceList.size() - 1);
        assertThat(testCiseService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCiseService.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
        assertThat(testCiseService.getServiceOperation()).isEqualTo(DEFAULT_SERVICE_OPERATION);

        // Validate the CiseService in Elasticsearch
        verify(mockCiseServiceSearchRepository, times(1)).save(testCiseService);
    }

    @Test
    @Transactional
    public void createCiseServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ciseServiceRepository.findAll().size();

        // Create the CiseService with an existing ID
        ciseService.setId(1L);
        CiseServiceDTO ciseServiceDTO = ciseServiceMapper.toDto(ciseService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiseServiceMockMvc.perform(post("/api/cise-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseService in the database
        List<CiseService> ciseServiceList = ciseServiceRepository.findAll();
        assertThat(ciseServiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the CiseService in Elasticsearch
        verify(mockCiseServiceSearchRepository, times(0)).save(ciseService);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciseServiceRepository.findAll().size();
        // set the field null
        ciseService.setName(null);

        // Create the CiseService, which fails.
        CiseServiceDTO ciseServiceDTO = ciseServiceMapper.toDto(ciseService);

        restCiseServiceMockMvc.perform(post("/api/cise-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceDTO)))
            .andExpect(status().isBadRequest());

        List<CiseService> ciseServiceList = ciseServiceRepository.findAll();
        assertThat(ciseServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciseServiceRepository.findAll().size();
        // set the field null
        ciseService.setServiceType(null);

        // Create the CiseService, which fails.
        CiseServiceDTO ciseServiceDTO = ciseServiceMapper.toDto(ciseService);

        restCiseServiceMockMvc.perform(post("/api/cise-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceDTO)))
            .andExpect(status().isBadRequest());

        List<CiseService> ciseServiceList = ciseServiceRepository.findAll();
        assertThat(ciseServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceOperationIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciseServiceRepository.findAll().size();
        // set the field null
        ciseService.setServiceOperation(null);

        // Create the CiseService, which fails.
        CiseServiceDTO ciseServiceDTO = ciseServiceMapper.toDto(ciseService);

        restCiseServiceMockMvc.perform(post("/api/cise-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceDTO)))
            .andExpect(status().isBadRequest());

        List<CiseService> ciseServiceList = ciseServiceRepository.findAll();
        assertThat(ciseServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCiseServices() throws Exception {
        // Initialize the database
        ciseServiceRepository.saveAndFlush(ciseService);

        // Get all the ciseServiceList
        restCiseServiceMockMvc.perform(get("/api/cise-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceOperation").value(hasItem(DEFAULT_SERVICE_OPERATION.toString())));
    }
    
    @Test
    @Transactional
    public void getCiseService() throws Exception {
        // Initialize the database
        ciseServiceRepository.saveAndFlush(ciseService);

        // Get the ciseService
        restCiseServiceMockMvc.perform(get("/api/cise-services/{id}", ciseService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ciseService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()))
            .andExpect(jsonPath("$.serviceOperation").value(DEFAULT_SERVICE_OPERATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCiseService() throws Exception {
        // Get the ciseService
        restCiseServiceMockMvc.perform(get("/api/cise-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiseService() throws Exception {
        // Initialize the database
        ciseServiceRepository.saveAndFlush(ciseService);

        int databaseSizeBeforeUpdate = ciseServiceRepository.findAll().size();

        // Update the ciseService
        CiseService updatedCiseService = ciseServiceRepository.findById(ciseService.getId()).get();
        // Disconnect from session so that the updates on updatedCiseService are not directly saved in db
        em.detach(updatedCiseService);
        updatedCiseService
            .name(UPDATED_NAME)
            .serviceType(UPDATED_SERVICE_TYPE)
            .serviceOperation(UPDATED_SERVICE_OPERATION);
        CiseServiceDTO ciseServiceDTO = ciseServiceMapper.toDto(updatedCiseService);

        restCiseServiceMockMvc.perform(put("/api/cise-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceDTO)))
            .andExpect(status().isOk());

        // Validate the CiseService in the database
        List<CiseService> ciseServiceList = ciseServiceRepository.findAll();
        assertThat(ciseServiceList).hasSize(databaseSizeBeforeUpdate);
        CiseService testCiseService = ciseServiceList.get(ciseServiceList.size() - 1);
        assertThat(testCiseService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCiseService.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
        assertThat(testCiseService.getServiceOperation()).isEqualTo(UPDATED_SERVICE_OPERATION);

        // Validate the CiseService in Elasticsearch
        verify(mockCiseServiceSearchRepository, times(1)).save(testCiseService);
    }

    @Test
    @Transactional
    public void updateNonExistingCiseService() throws Exception {
        int databaseSizeBeforeUpdate = ciseServiceRepository.findAll().size();

        // Create the CiseService
        CiseServiceDTO ciseServiceDTO = ciseServiceMapper.toDto(ciseService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiseServiceMockMvc.perform(put("/api/cise-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseService in the database
        List<CiseService> ciseServiceList = ciseServiceRepository.findAll();
        assertThat(ciseServiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CiseService in Elasticsearch
        verify(mockCiseServiceSearchRepository, times(0)).save(ciseService);
    }

    @Test
    @Transactional
    public void deleteCiseService() throws Exception {
        // Initialize the database
        ciseServiceRepository.saveAndFlush(ciseService);

        int databaseSizeBeforeDelete = ciseServiceRepository.findAll().size();

        // Get the ciseService
        restCiseServiceMockMvc.perform(delete("/api/cise-services/{id}", ciseService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CiseService> ciseServiceList = ciseServiceRepository.findAll();
        assertThat(ciseServiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CiseService in Elasticsearch
        verify(mockCiseServiceSearchRepository, times(1)).deleteById(ciseService.getId());
    }

    @Test
    @Transactional
    public void searchCiseService() throws Exception {
        // Initialize the database
        ciseServiceRepository.saveAndFlush(ciseService);
        when(mockCiseServiceSearchRepository.search(queryStringQuery("id:" + ciseService.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ciseService), PageRequest.of(0, 1), 1));
        // Search the ciseService
        restCiseServiceMockMvc.perform(get("/api/_search/cise-services?query=id:" + ciseService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceOperation").value(hasItem(DEFAULT_SERVICE_OPERATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseService.class);
        CiseService ciseService1 = new CiseService();
        ciseService1.setId(1L);
        CiseService ciseService2 = new CiseService();
        ciseService2.setId(ciseService1.getId());
        assertThat(ciseService1).isEqualTo(ciseService2);
        ciseService2.setId(2L);
        assertThat(ciseService1).isNotEqualTo(ciseService2);
        ciseService1.setId(null);
        assertThat(ciseService1).isNotEqualTo(ciseService2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseServiceDTO.class);
        CiseServiceDTO ciseServiceDTO1 = new CiseServiceDTO();
        ciseServiceDTO1.setId(1L);
        CiseServiceDTO ciseServiceDTO2 = new CiseServiceDTO();
        assertThat(ciseServiceDTO1).isNotEqualTo(ciseServiceDTO2);
        ciseServiceDTO2.setId(ciseServiceDTO1.getId());
        assertThat(ciseServiceDTO1).isEqualTo(ciseServiceDTO2);
        ciseServiceDTO2.setId(2L);
        assertThat(ciseServiceDTO1).isNotEqualTo(ciseServiceDTO2);
        ciseServiceDTO1.setId(null);
        assertThat(ciseServiceDTO1).isNotEqualTo(ciseServiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ciseServiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ciseServiceMapper.fromId(null)).isNull();
    }
}
