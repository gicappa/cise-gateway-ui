package eu.cise.console.web.rest;

import eu.cise.console.GatewayuiApp;

import eu.cise.console.domain.CiseRule;
import eu.cise.console.repository.CiseRuleRepository;
import eu.cise.console.repository.search.CiseRuleSearchRepository;
import eu.cise.console.service.CiseRuleService;
import eu.cise.console.service.dto.CiseRuleDTO;
import eu.cise.console.service.mapper.CiseRuleMapper;
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

import eu.cise.console.domain.enumeration.CiseRuleType;
/**
 * Test class for the CiseRuleResource REST controller.
 *
 * @see CiseRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayuiApp.class)
public class CiseRuleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final CiseRuleType DEFAULT_RULE_TYPE = CiseRuleType.ALLOW_ALL_FILEDS;
    private static final CiseRuleType UPDATED_RULE_TYPE = CiseRuleType.ALLOW_SPECIFIC_FILEDS;

    private static final String DEFAULT_ENTITY_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TEMPLATE = "BBBBBBBBBB";

    @Autowired
    private CiseRuleRepository ciseRuleRepository;

    @Autowired
    private CiseRuleMapper ciseRuleMapper;
    
    @Autowired
    private CiseRuleService ciseRuleService;

    /**
     * This repository is mocked in the eu.cise.console.repository.search test package.
     *
     * @see eu.cise.console.repository.search.CiseRuleSearchRepositoryMockConfiguration
     */
    @Autowired
    private CiseRuleSearchRepository mockCiseRuleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCiseRuleMockMvc;

    private CiseRule ciseRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiseRuleResource ciseRuleResource = new CiseRuleResource(ciseRuleService);
        this.restCiseRuleMockMvc = MockMvcBuilders.standaloneSetup(ciseRuleResource)
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
    public static CiseRule createEntity(EntityManager em) {
        CiseRule ciseRule = new CiseRule()
            .name(DEFAULT_NAME)
            .ruleType(DEFAULT_RULE_TYPE)
            .entityTemplate(DEFAULT_ENTITY_TEMPLATE);
        return ciseRule;
    }

    @Before
    public void initTest() {
        ciseRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiseRule() throws Exception {
        int databaseSizeBeforeCreate = ciseRuleRepository.findAll().size();

        // Create the CiseRule
        CiseRuleDTO ciseRuleDTO = ciseRuleMapper.toDto(ciseRule);
        restCiseRuleMockMvc.perform(post("/api/cise-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the CiseRule in the database
        List<CiseRule> ciseRuleList = ciseRuleRepository.findAll();
        assertThat(ciseRuleList).hasSize(databaseSizeBeforeCreate + 1);
        CiseRule testCiseRule = ciseRuleList.get(ciseRuleList.size() - 1);
        assertThat(testCiseRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCiseRule.getRuleType()).isEqualTo(DEFAULT_RULE_TYPE);
        assertThat(testCiseRule.getEntityTemplate()).isEqualTo(DEFAULT_ENTITY_TEMPLATE);

        // Validate the CiseRule in Elasticsearch
        verify(mockCiseRuleSearchRepository, times(1)).save(testCiseRule);
    }

    @Test
    @Transactional
    public void createCiseRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ciseRuleRepository.findAll().size();

        // Create the CiseRule with an existing ID
        ciseRule.setId(1L);
        CiseRuleDTO ciseRuleDTO = ciseRuleMapper.toDto(ciseRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiseRuleMockMvc.perform(post("/api/cise-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseRule in the database
        List<CiseRule> ciseRuleList = ciseRuleRepository.findAll();
        assertThat(ciseRuleList).hasSize(databaseSizeBeforeCreate);

        // Validate the CiseRule in Elasticsearch
        verify(mockCiseRuleSearchRepository, times(0)).save(ciseRule);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciseRuleRepository.findAll().size();
        // set the field null
        ciseRule.setName(null);

        // Create the CiseRule, which fails.
        CiseRuleDTO ciseRuleDTO = ciseRuleMapper.toDto(ciseRule);

        restCiseRuleMockMvc.perform(post("/api/cise-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleDTO)))
            .andExpect(status().isBadRequest());

        List<CiseRule> ciseRuleList = ciseRuleRepository.findAll();
        assertThat(ciseRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuleTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciseRuleRepository.findAll().size();
        // set the field null
        ciseRule.setRuleType(null);

        // Create the CiseRule, which fails.
        CiseRuleDTO ciseRuleDTO = ciseRuleMapper.toDto(ciseRule);

        restCiseRuleMockMvc.perform(post("/api/cise-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleDTO)))
            .andExpect(status().isBadRequest());

        List<CiseRule> ciseRuleList = ciseRuleRepository.findAll();
        assertThat(ciseRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCiseRules() throws Exception {
        // Initialize the database
        ciseRuleRepository.saveAndFlush(ciseRule);

        // Get all the ciseRuleList
        restCiseRuleMockMvc.perform(get("/api/cise-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityTemplate").value(hasItem(DEFAULT_ENTITY_TEMPLATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCiseRule() throws Exception {
        // Initialize the database
        ciseRuleRepository.saveAndFlush(ciseRule);

        // Get the ciseRule
        restCiseRuleMockMvc.perform(get("/api/cise-rules/{id}", ciseRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ciseRule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ruleType").value(DEFAULT_RULE_TYPE.toString()))
            .andExpect(jsonPath("$.entityTemplate").value(DEFAULT_ENTITY_TEMPLATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCiseRule() throws Exception {
        // Get the ciseRule
        restCiseRuleMockMvc.perform(get("/api/cise-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiseRule() throws Exception {
        // Initialize the database
        ciseRuleRepository.saveAndFlush(ciseRule);

        int databaseSizeBeforeUpdate = ciseRuleRepository.findAll().size();

        // Update the ciseRule
        CiseRule updatedCiseRule = ciseRuleRepository.findById(ciseRule.getId()).get();
        // Disconnect from session so that the updates on updatedCiseRule are not directly saved in db
        em.detach(updatedCiseRule);
        updatedCiseRule
            .name(UPDATED_NAME)
            .ruleType(UPDATED_RULE_TYPE)
            .entityTemplate(UPDATED_ENTITY_TEMPLATE);
        CiseRuleDTO ciseRuleDTO = ciseRuleMapper.toDto(updatedCiseRule);

        restCiseRuleMockMvc.perform(put("/api/cise-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleDTO)))
            .andExpect(status().isOk());

        // Validate the CiseRule in the database
        List<CiseRule> ciseRuleList = ciseRuleRepository.findAll();
        assertThat(ciseRuleList).hasSize(databaseSizeBeforeUpdate);
        CiseRule testCiseRule = ciseRuleList.get(ciseRuleList.size() - 1);
        assertThat(testCiseRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCiseRule.getRuleType()).isEqualTo(UPDATED_RULE_TYPE);
        assertThat(testCiseRule.getEntityTemplate()).isEqualTo(UPDATED_ENTITY_TEMPLATE);

        // Validate the CiseRule in Elasticsearch
        verify(mockCiseRuleSearchRepository, times(1)).save(testCiseRule);
    }

    @Test
    @Transactional
    public void updateNonExistingCiseRule() throws Exception {
        int databaseSizeBeforeUpdate = ciseRuleRepository.findAll().size();

        // Create the CiseRule
        CiseRuleDTO ciseRuleDTO = ciseRuleMapper.toDto(ciseRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiseRuleMockMvc.perform(put("/api/cise-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseRule in the database
        List<CiseRule> ciseRuleList = ciseRuleRepository.findAll();
        assertThat(ciseRuleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CiseRule in Elasticsearch
        verify(mockCiseRuleSearchRepository, times(0)).save(ciseRule);
    }

    @Test
    @Transactional
    public void deleteCiseRule() throws Exception {
        // Initialize the database
        ciseRuleRepository.saveAndFlush(ciseRule);

        int databaseSizeBeforeDelete = ciseRuleRepository.findAll().size();

        // Get the ciseRule
        restCiseRuleMockMvc.perform(delete("/api/cise-rules/{id}", ciseRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CiseRule> ciseRuleList = ciseRuleRepository.findAll();
        assertThat(ciseRuleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CiseRule in Elasticsearch
        verify(mockCiseRuleSearchRepository, times(1)).deleteById(ciseRule.getId());
    }

    @Test
    @Transactional
    public void searchCiseRule() throws Exception {
        // Initialize the database
        ciseRuleRepository.saveAndFlush(ciseRule);
        when(mockCiseRuleSearchRepository.search(queryStringQuery("id:" + ciseRule.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ciseRule), PageRequest.of(0, 1), 1));
        // Search the ciseRule
        restCiseRuleMockMvc.perform(get("/api/_search/cise-rules?query=id:" + ciseRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityTemplate").value(hasItem(DEFAULT_ENTITY_TEMPLATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseRule.class);
        CiseRule ciseRule1 = new CiseRule();
        ciseRule1.setId(1L);
        CiseRule ciseRule2 = new CiseRule();
        ciseRule2.setId(ciseRule1.getId());
        assertThat(ciseRule1).isEqualTo(ciseRule2);
        ciseRule2.setId(2L);
        assertThat(ciseRule1).isNotEqualTo(ciseRule2);
        ciseRule1.setId(null);
        assertThat(ciseRule1).isNotEqualTo(ciseRule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseRuleDTO.class);
        CiseRuleDTO ciseRuleDTO1 = new CiseRuleDTO();
        ciseRuleDTO1.setId(1L);
        CiseRuleDTO ciseRuleDTO2 = new CiseRuleDTO();
        assertThat(ciseRuleDTO1).isNotEqualTo(ciseRuleDTO2);
        ciseRuleDTO2.setId(ciseRuleDTO1.getId());
        assertThat(ciseRuleDTO1).isEqualTo(ciseRuleDTO2);
        ciseRuleDTO2.setId(2L);
        assertThat(ciseRuleDTO1).isNotEqualTo(ciseRuleDTO2);
        ciseRuleDTO1.setId(null);
        assertThat(ciseRuleDTO1).isNotEqualTo(ciseRuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ciseRuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ciseRuleMapper.fromId(null)).isNull();
    }
}
