package eu.cise.console.web.rest;

import eu.cise.console.GatewayuiApp;

import eu.cise.console.domain.CiseRuleSet;
import eu.cise.console.repository.CiseRuleSetRepository;
import eu.cise.console.repository.search.CiseRuleSetSearchRepository;
import eu.cise.console.service.CiseRuleSetService;
import eu.cise.console.service.dto.CiseRuleSetDTO;
import eu.cise.console.service.mapper.CiseRuleSetMapper;
import eu.cise.console.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

/**
 * Test class for the CiseRuleSetResource REST controller.
 *
 * @see CiseRuleSetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayuiApp.class)
public class CiseRuleSetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CiseRuleSetRepository ciseRuleSetRepository;

    @Autowired
    private CiseRuleSetMapper ciseRuleSetMapper;
    
    @Autowired
    private CiseRuleSetService ciseRuleSetService;

    /**
     * This repository is mocked in the eu.cise.console.repository.search test package.
     *
     * @see eu.cise.console.repository.search.CiseRuleSetSearchRepositoryMockConfiguration
     */
    @Autowired
    private CiseRuleSetSearchRepository mockCiseRuleSetSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCiseRuleSetMockMvc;

    private CiseRuleSet ciseRuleSet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiseRuleSetResource ciseRuleSetResource = new CiseRuleSetResource(ciseRuleSetService);
        this.restCiseRuleSetMockMvc = MockMvcBuilders.standaloneSetup(ciseRuleSetResource)
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
    public static CiseRuleSet createEntity(EntityManager em) {
        CiseRuleSet ciseRuleSet = new CiseRuleSet()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return ciseRuleSet;
    }

    @Before
    public void initTest() {
        ciseRuleSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiseRuleSet() throws Exception {
        int databaseSizeBeforeCreate = ciseRuleSetRepository.findAll().size();

        // Create the CiseRuleSet
        CiseRuleSetDTO ciseRuleSetDTO = ciseRuleSetMapper.toDto(ciseRuleSet);
        restCiseRuleSetMockMvc.perform(post("/api/cise-rule-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleSetDTO)))
            .andExpect(status().isCreated());

        // Validate the CiseRuleSet in the database
        List<CiseRuleSet> ciseRuleSetList = ciseRuleSetRepository.findAll();
        assertThat(ciseRuleSetList).hasSize(databaseSizeBeforeCreate + 1);
        CiseRuleSet testCiseRuleSet = ciseRuleSetList.get(ciseRuleSetList.size() - 1);
        assertThat(testCiseRuleSet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCiseRuleSet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CiseRuleSet in Elasticsearch
        verify(mockCiseRuleSetSearchRepository, times(1)).save(testCiseRuleSet);
    }

    @Test
    @Transactional
    public void createCiseRuleSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ciseRuleSetRepository.findAll().size();

        // Create the CiseRuleSet with an existing ID
        ciseRuleSet.setId(1L);
        CiseRuleSetDTO ciseRuleSetDTO = ciseRuleSetMapper.toDto(ciseRuleSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiseRuleSetMockMvc.perform(post("/api/cise-rule-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseRuleSet in the database
        List<CiseRuleSet> ciseRuleSetList = ciseRuleSetRepository.findAll();
        assertThat(ciseRuleSetList).hasSize(databaseSizeBeforeCreate);

        // Validate the CiseRuleSet in Elasticsearch
        verify(mockCiseRuleSetSearchRepository, times(0)).save(ciseRuleSet);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciseRuleSetRepository.findAll().size();
        // set the field null
        ciseRuleSet.setName(null);

        // Create the CiseRuleSet, which fails.
        CiseRuleSetDTO ciseRuleSetDTO = ciseRuleSetMapper.toDto(ciseRuleSet);

        restCiseRuleSetMockMvc.perform(post("/api/cise-rule-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleSetDTO)))
            .andExpect(status().isBadRequest());

        List<CiseRuleSet> ciseRuleSetList = ciseRuleSetRepository.findAll();
        assertThat(ciseRuleSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCiseRuleSets() throws Exception {
        // Initialize the database
        ciseRuleSetRepository.saveAndFlush(ciseRuleSet);

        // Get all the ciseRuleSetList
        restCiseRuleSetMockMvc.perform(get("/api/cise-rule-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseRuleSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCiseRuleSet() throws Exception {
        // Initialize the database
        ciseRuleSetRepository.saveAndFlush(ciseRuleSet);

        // Get the ciseRuleSet
        restCiseRuleSetMockMvc.perform(get("/api/cise-rule-sets/{id}", ciseRuleSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ciseRuleSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCiseRuleSet() throws Exception {
        // Get the ciseRuleSet
        restCiseRuleSetMockMvc.perform(get("/api/cise-rule-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiseRuleSet() throws Exception {
        // Initialize the database
        ciseRuleSetRepository.saveAndFlush(ciseRuleSet);

        int databaseSizeBeforeUpdate = ciseRuleSetRepository.findAll().size();

        // Update the ciseRuleSet
        CiseRuleSet updatedCiseRuleSet = ciseRuleSetRepository.findById(ciseRuleSet.getId()).get();
        // Disconnect from session so that the updates on updatedCiseRuleSet are not directly saved in db
        em.detach(updatedCiseRuleSet);
        updatedCiseRuleSet
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        CiseRuleSetDTO ciseRuleSetDTO = ciseRuleSetMapper.toDto(updatedCiseRuleSet);

        restCiseRuleSetMockMvc.perform(put("/api/cise-rule-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleSetDTO)))
            .andExpect(status().isOk());

        // Validate the CiseRuleSet in the database
        List<CiseRuleSet> ciseRuleSetList = ciseRuleSetRepository.findAll();
        assertThat(ciseRuleSetList).hasSize(databaseSizeBeforeUpdate);
        CiseRuleSet testCiseRuleSet = ciseRuleSetList.get(ciseRuleSetList.size() - 1);
        assertThat(testCiseRuleSet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCiseRuleSet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CiseRuleSet in Elasticsearch
        verify(mockCiseRuleSetSearchRepository, times(1)).save(testCiseRuleSet);
    }

    @Test
    @Transactional
    public void updateNonExistingCiseRuleSet() throws Exception {
        int databaseSizeBeforeUpdate = ciseRuleSetRepository.findAll().size();

        // Create the CiseRuleSet
        CiseRuleSetDTO ciseRuleSetDTO = ciseRuleSetMapper.toDto(ciseRuleSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiseRuleSetMockMvc.perform(put("/api/cise-rule-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseRuleSet in the database
        List<CiseRuleSet> ciseRuleSetList = ciseRuleSetRepository.findAll();
        assertThat(ciseRuleSetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CiseRuleSet in Elasticsearch
        verify(mockCiseRuleSetSearchRepository, times(0)).save(ciseRuleSet);
    }

    @Test
    @Transactional
    public void deleteCiseRuleSet() throws Exception {
        // Initialize the database
        ciseRuleSetRepository.saveAndFlush(ciseRuleSet);

        int databaseSizeBeforeDelete = ciseRuleSetRepository.findAll().size();

        // Get the ciseRuleSet
        restCiseRuleSetMockMvc.perform(delete("/api/cise-rule-sets/{id}", ciseRuleSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CiseRuleSet> ciseRuleSetList = ciseRuleSetRepository.findAll();
        assertThat(ciseRuleSetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CiseRuleSet in Elasticsearch
        verify(mockCiseRuleSetSearchRepository, times(1)).deleteById(ciseRuleSet.getId());
    }

    @Test
    @Transactional
    public void searchCiseRuleSet() throws Exception {
        // Initialize the database
        ciseRuleSetRepository.saveAndFlush(ciseRuleSet);
        when(mockCiseRuleSetSearchRepository.search(queryStringQuery("id:" + ciseRuleSet.getId())))
            .thenReturn(Collections.singletonList(ciseRuleSet));
        // Search the ciseRuleSet
        restCiseRuleSetMockMvc.perform(get("/api/_search/cise-rule-sets?query=id:" + ciseRuleSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseRuleSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseRuleSet.class);
        CiseRuleSet ciseRuleSet1 = new CiseRuleSet();
        ciseRuleSet1.setId(1L);
        CiseRuleSet ciseRuleSet2 = new CiseRuleSet();
        ciseRuleSet2.setId(ciseRuleSet1.getId());
        assertThat(ciseRuleSet1).isEqualTo(ciseRuleSet2);
        ciseRuleSet2.setId(2L);
        assertThat(ciseRuleSet1).isNotEqualTo(ciseRuleSet2);
        ciseRuleSet1.setId(null);
        assertThat(ciseRuleSet1).isNotEqualTo(ciseRuleSet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseRuleSetDTO.class);
        CiseRuleSetDTO ciseRuleSetDTO1 = new CiseRuleSetDTO();
        ciseRuleSetDTO1.setId(1L);
        CiseRuleSetDTO ciseRuleSetDTO2 = new CiseRuleSetDTO();
        assertThat(ciseRuleSetDTO1).isNotEqualTo(ciseRuleSetDTO2);
        ciseRuleSetDTO2.setId(ciseRuleSetDTO1.getId());
        assertThat(ciseRuleSetDTO1).isEqualTo(ciseRuleSetDTO2);
        ciseRuleSetDTO2.setId(2L);
        assertThat(ciseRuleSetDTO1).isNotEqualTo(ciseRuleSetDTO2);
        ciseRuleSetDTO1.setId(null);
        assertThat(ciseRuleSetDTO1).isNotEqualTo(ciseRuleSetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ciseRuleSetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ciseRuleSetMapper.fromId(null)).isNull();
    }
}
