package eu.cise.console.web.rest;

import eu.cise.console.GatewayuiApp;

import eu.cise.console.domain.CiseAuthority;
import eu.cise.console.repository.CiseAuthorityRepository;
import eu.cise.console.repository.search.CiseAuthoritySearchRepository;
import eu.cise.console.service.CiseAuthorityService;
import eu.cise.console.service.dto.CiseAuthorityDTO;
import eu.cise.console.service.mapper.CiseAuthorityMapper;
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

/**
 * Test class for the CiseAuthorityResource REST controller.
 *
 * @see CiseAuthorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayuiApp.class)
public class CiseAuthorityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CiseAuthorityRepository ciseAuthorityRepository;

    @Autowired
    private CiseAuthorityMapper ciseAuthorityMapper;
    
    @Autowired
    private CiseAuthorityService ciseAuthorityService;

    /**
     * This repository is mocked in the eu.cise.console.repository.search test package.
     *
     * @see eu.cise.console.repository.search.CiseAuthoritySearchRepositoryMockConfiguration
     */
    @Autowired
    private CiseAuthoritySearchRepository mockCiseAuthoritySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCiseAuthorityMockMvc;

    private CiseAuthority ciseAuthority;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiseAuthorityResource ciseAuthorityResource = new CiseAuthorityResource(ciseAuthorityService);
        this.restCiseAuthorityMockMvc = MockMvcBuilders.standaloneSetup(ciseAuthorityResource)
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
    public static CiseAuthority createEntity(EntityManager em) {
        CiseAuthority ciseAuthority = new CiseAuthority()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return ciseAuthority;
    }

    @Before
    public void initTest() {
        ciseAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiseAuthority() throws Exception {
        int databaseSizeBeforeCreate = ciseAuthorityRepository.findAll().size();

        // Create the CiseAuthority
        CiseAuthorityDTO ciseAuthorityDTO = ciseAuthorityMapper.toDto(ciseAuthority);
        restCiseAuthorityMockMvc.perform(post("/api/cise-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseAuthorityDTO)))
            .andExpect(status().isCreated());

        // Validate the CiseAuthority in the database
        List<CiseAuthority> ciseAuthorityList = ciseAuthorityRepository.findAll();
        assertThat(ciseAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        CiseAuthority testCiseAuthority = ciseAuthorityList.get(ciseAuthorityList.size() - 1);
        assertThat(testCiseAuthority.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCiseAuthority.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CiseAuthority in Elasticsearch
        verify(mockCiseAuthoritySearchRepository, times(1)).save(testCiseAuthority);
    }

    @Test
    @Transactional
    public void createCiseAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ciseAuthorityRepository.findAll().size();

        // Create the CiseAuthority with an existing ID
        ciseAuthority.setId(1L);
        CiseAuthorityDTO ciseAuthorityDTO = ciseAuthorityMapper.toDto(ciseAuthority);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiseAuthorityMockMvc.perform(post("/api/cise-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseAuthority in the database
        List<CiseAuthority> ciseAuthorityList = ciseAuthorityRepository.findAll();
        assertThat(ciseAuthorityList).hasSize(databaseSizeBeforeCreate);

        // Validate the CiseAuthority in Elasticsearch
        verify(mockCiseAuthoritySearchRepository, times(0)).save(ciseAuthority);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciseAuthorityRepository.findAll().size();
        // set the field null
        ciseAuthority.setName(null);

        // Create the CiseAuthority, which fails.
        CiseAuthorityDTO ciseAuthorityDTO = ciseAuthorityMapper.toDto(ciseAuthority);

        restCiseAuthorityMockMvc.perform(post("/api/cise-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseAuthorityDTO)))
            .andExpect(status().isBadRequest());

        List<CiseAuthority> ciseAuthorityList = ciseAuthorityRepository.findAll();
        assertThat(ciseAuthorityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCiseAuthorities() throws Exception {
        // Initialize the database
        ciseAuthorityRepository.saveAndFlush(ciseAuthority);

        // Get all the ciseAuthorityList
        restCiseAuthorityMockMvc.perform(get("/api/cise-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCiseAuthority() throws Exception {
        // Initialize the database
        ciseAuthorityRepository.saveAndFlush(ciseAuthority);

        // Get the ciseAuthority
        restCiseAuthorityMockMvc.perform(get("/api/cise-authorities/{id}", ciseAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ciseAuthority.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCiseAuthority() throws Exception {
        // Get the ciseAuthority
        restCiseAuthorityMockMvc.perform(get("/api/cise-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiseAuthority() throws Exception {
        // Initialize the database
        ciseAuthorityRepository.saveAndFlush(ciseAuthority);

        int databaseSizeBeforeUpdate = ciseAuthorityRepository.findAll().size();

        // Update the ciseAuthority
        CiseAuthority updatedCiseAuthority = ciseAuthorityRepository.findById(ciseAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedCiseAuthority are not directly saved in db
        em.detach(updatedCiseAuthority);
        updatedCiseAuthority
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        CiseAuthorityDTO ciseAuthorityDTO = ciseAuthorityMapper.toDto(updatedCiseAuthority);

        restCiseAuthorityMockMvc.perform(put("/api/cise-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseAuthorityDTO)))
            .andExpect(status().isOk());

        // Validate the CiseAuthority in the database
        List<CiseAuthority> ciseAuthorityList = ciseAuthorityRepository.findAll();
        assertThat(ciseAuthorityList).hasSize(databaseSizeBeforeUpdate);
        CiseAuthority testCiseAuthority = ciseAuthorityList.get(ciseAuthorityList.size() - 1);
        assertThat(testCiseAuthority.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCiseAuthority.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CiseAuthority in Elasticsearch
        verify(mockCiseAuthoritySearchRepository, times(1)).save(testCiseAuthority);
    }

    @Test
    @Transactional
    public void updateNonExistingCiseAuthority() throws Exception {
        int databaseSizeBeforeUpdate = ciseAuthorityRepository.findAll().size();

        // Create the CiseAuthority
        CiseAuthorityDTO ciseAuthorityDTO = ciseAuthorityMapper.toDto(ciseAuthority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiseAuthorityMockMvc.perform(put("/api/cise-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseAuthority in the database
        List<CiseAuthority> ciseAuthorityList = ciseAuthorityRepository.findAll();
        assertThat(ciseAuthorityList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CiseAuthority in Elasticsearch
        verify(mockCiseAuthoritySearchRepository, times(0)).save(ciseAuthority);
    }

    @Test
    @Transactional
    public void deleteCiseAuthority() throws Exception {
        // Initialize the database
        ciseAuthorityRepository.saveAndFlush(ciseAuthority);

        int databaseSizeBeforeDelete = ciseAuthorityRepository.findAll().size();

        // Get the ciseAuthority
        restCiseAuthorityMockMvc.perform(delete("/api/cise-authorities/{id}", ciseAuthority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CiseAuthority> ciseAuthorityList = ciseAuthorityRepository.findAll();
        assertThat(ciseAuthorityList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CiseAuthority in Elasticsearch
        verify(mockCiseAuthoritySearchRepository, times(1)).deleteById(ciseAuthority.getId());
    }

    @Test
    @Transactional
    public void searchCiseAuthority() throws Exception {
        // Initialize the database
        ciseAuthorityRepository.saveAndFlush(ciseAuthority);
        when(mockCiseAuthoritySearchRepository.search(queryStringQuery("id:" + ciseAuthority.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ciseAuthority), PageRequest.of(0, 1), 1));
        // Search the ciseAuthority
        restCiseAuthorityMockMvc.perform(get("/api/_search/cise-authorities?query=id:" + ciseAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseAuthority.class);
        CiseAuthority ciseAuthority1 = new CiseAuthority();
        ciseAuthority1.setId(1L);
        CiseAuthority ciseAuthority2 = new CiseAuthority();
        ciseAuthority2.setId(ciseAuthority1.getId());
        assertThat(ciseAuthority1).isEqualTo(ciseAuthority2);
        ciseAuthority2.setId(2L);
        assertThat(ciseAuthority1).isNotEqualTo(ciseAuthority2);
        ciseAuthority1.setId(null);
        assertThat(ciseAuthority1).isNotEqualTo(ciseAuthority2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseAuthorityDTO.class);
        CiseAuthorityDTO ciseAuthorityDTO1 = new CiseAuthorityDTO();
        ciseAuthorityDTO1.setId(1L);
        CiseAuthorityDTO ciseAuthorityDTO2 = new CiseAuthorityDTO();
        assertThat(ciseAuthorityDTO1).isNotEqualTo(ciseAuthorityDTO2);
        ciseAuthorityDTO2.setId(ciseAuthorityDTO1.getId());
        assertThat(ciseAuthorityDTO1).isEqualTo(ciseAuthorityDTO2);
        ciseAuthorityDTO2.setId(2L);
        assertThat(ciseAuthorityDTO1).isNotEqualTo(ciseAuthorityDTO2);
        ciseAuthorityDTO1.setId(null);
        assertThat(ciseAuthorityDTO1).isNotEqualTo(ciseAuthorityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ciseAuthorityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ciseAuthorityMapper.fromId(null)).isNull();
    }
}
