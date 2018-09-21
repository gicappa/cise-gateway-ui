package eu.cise.console.web.rest;

import eu.cise.console.GatewayuiApp;

import eu.cise.console.domain.CiseServiceProfile;
import eu.cise.console.repository.CiseServiceProfileRepository;
import eu.cise.console.repository.search.CiseServiceProfileSearchRepository;
import eu.cise.console.service.CiseServiceProfileService;
import eu.cise.console.service.dto.CiseServiceProfileDTO;
import eu.cise.console.service.mapper.CiseServiceProfileMapper;
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

import eu.cise.console.domain.enumeration.CiseCommunityType;
import eu.cise.console.domain.enumeration.CiseDataFreshnessType;
import eu.cise.console.domain.enumeration.CiseFunctionType;
import eu.cise.console.domain.enumeration.CiseSeaBasinType;
import eu.cise.console.domain.enumeration.CiseServiceRoleType;
import eu.cise.console.domain.enumeration.CiseServiceType;
/**
 * Test class for the CiseServiceProfileResource REST controller.
 *
 * @see CiseServiceProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayuiApp.class)
public class CiseServiceProfileResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ID = "BBBBBBBBBB";

    private static final CiseCommunityType DEFAULT_COMMUNITY = CiseCommunityType.FISHERIES_CONTROL;
    private static final CiseCommunityType UPDATED_COMMUNITY = CiseCommunityType.GENERAL_LAW_ENFORCEMENT;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final CiseDataFreshnessType DEFAULT_DATA_FRESHNESS = CiseDataFreshnessType.REAL_TIME;
    private static final CiseDataFreshnessType UPDATED_DATA_FRESHNESS = CiseDataFreshnessType.NEARLY_REAL_TIME;

    private static final CiseFunctionType DEFAULT_SERVICE_FUNCTION = CiseFunctionType.LAW_ENFORCEMENT_MONITORING;
    private static final CiseFunctionType UPDATED_SERVICE_FUNCTION = CiseFunctionType.NON_SPECIFIED;

    private static final CiseSeaBasinType DEFAULT_SEA_BASIN = CiseSeaBasinType.ATLANTIC;
    private static final CiseSeaBasinType UPDATED_SEA_BASIN = CiseSeaBasinType.ARCTIC_OCEAN;

    private static final CiseServiceRoleType DEFAULT_SERVICE_ROLE = CiseServiceRoleType.CONSUMER;
    private static final CiseServiceRoleType UPDATED_SERVICE_ROLE = CiseServiceRoleType.PROVIDER;

    private static final CiseServiceType DEFAULT_SERVICE_TYPE = CiseServiceType.VESSEL_DOCUMENT_SERVICE;
    private static final CiseServiceType UPDATED_SERVICE_TYPE = CiseServiceType.ORGANIZATION_DOCUMENT_SERVICE;

    @Autowired
    private CiseServiceProfileRepository ciseServiceProfileRepository;

    @Autowired
    private CiseServiceProfileMapper ciseServiceProfileMapper;
    
    @Autowired
    private CiseServiceProfileService ciseServiceProfileService;

    /**
     * This repository is mocked in the eu.cise.console.repository.search test package.
     *
     * @see eu.cise.console.repository.search.CiseServiceProfileSearchRepositoryMockConfiguration
     */
    @Autowired
    private CiseServiceProfileSearchRepository mockCiseServiceProfileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCiseServiceProfileMockMvc;

    private CiseServiceProfile ciseServiceProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CiseServiceProfileResource ciseServiceProfileResource = new CiseServiceProfileResource(ciseServiceProfileService);
        this.restCiseServiceProfileMockMvc = MockMvcBuilders.standaloneSetup(ciseServiceProfileResource)
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
    public static CiseServiceProfile createEntity(EntityManager em) {
        CiseServiceProfile ciseServiceProfile = new CiseServiceProfile()
            .name(DEFAULT_NAME)
            .serviceId(DEFAULT_SERVICE_ID)
            .community(DEFAULT_COMMUNITY)
            .country(DEFAULT_COUNTRY)
            .dataFreshness(DEFAULT_DATA_FRESHNESS)
            .serviceFunction(DEFAULT_SERVICE_FUNCTION)
            .seaBasin(DEFAULT_SEA_BASIN)
            .serviceRole(DEFAULT_SERVICE_ROLE)
            .serviceType(DEFAULT_SERVICE_TYPE);
        return ciseServiceProfile;
    }

    @Before
    public void initTest() {
        ciseServiceProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiseServiceProfile() throws Exception {
        int databaseSizeBeforeCreate = ciseServiceProfileRepository.findAll().size();

        // Create the CiseServiceProfile
        CiseServiceProfileDTO ciseServiceProfileDTO = ciseServiceProfileMapper.toDto(ciseServiceProfile);
        restCiseServiceProfileMockMvc.perform(post("/api/cise-service-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the CiseServiceProfile in the database
        List<CiseServiceProfile> ciseServiceProfileList = ciseServiceProfileRepository.findAll();
        assertThat(ciseServiceProfileList).hasSize(databaseSizeBeforeCreate + 1);
        CiseServiceProfile testCiseServiceProfile = ciseServiceProfileList.get(ciseServiceProfileList.size() - 1);
        assertThat(testCiseServiceProfile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCiseServiceProfile.getServiceId()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testCiseServiceProfile.getCommunity()).isEqualTo(DEFAULT_COMMUNITY);
        assertThat(testCiseServiceProfile.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCiseServiceProfile.getDataFreshness()).isEqualTo(DEFAULT_DATA_FRESHNESS);
        assertThat(testCiseServiceProfile.getServiceFunction()).isEqualTo(DEFAULT_SERVICE_FUNCTION);
        assertThat(testCiseServiceProfile.getSeaBasin()).isEqualTo(DEFAULT_SEA_BASIN);
        assertThat(testCiseServiceProfile.getServiceRole()).isEqualTo(DEFAULT_SERVICE_ROLE);
        assertThat(testCiseServiceProfile.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);

        // Validate the CiseServiceProfile in Elasticsearch
        verify(mockCiseServiceProfileSearchRepository, times(1)).save(testCiseServiceProfile);
    }

    @Test
    @Transactional
    public void createCiseServiceProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ciseServiceProfileRepository.findAll().size();

        // Create the CiseServiceProfile with an existing ID
        ciseServiceProfile.setId(1L);
        CiseServiceProfileDTO ciseServiceProfileDTO = ciseServiceProfileMapper.toDto(ciseServiceProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiseServiceProfileMockMvc.perform(post("/api/cise-service-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseServiceProfile in the database
        List<CiseServiceProfile> ciseServiceProfileList = ciseServiceProfileRepository.findAll();
        assertThat(ciseServiceProfileList).hasSize(databaseSizeBeforeCreate);

        // Validate the CiseServiceProfile in Elasticsearch
        verify(mockCiseServiceProfileSearchRepository, times(0)).save(ciseServiceProfile);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciseServiceProfileRepository.findAll().size();
        // set the field null
        ciseServiceProfile.setName(null);

        // Create the CiseServiceProfile, which fails.
        CiseServiceProfileDTO ciseServiceProfileDTO = ciseServiceProfileMapper.toDto(ciseServiceProfile);

        restCiseServiceProfileMockMvc.perform(post("/api/cise-service-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceProfileDTO)))
            .andExpect(status().isBadRequest());

        List<CiseServiceProfile> ciseServiceProfileList = ciseServiceProfileRepository.findAll();
        assertThat(ciseServiceProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCiseServiceProfiles() throws Exception {
        // Initialize the database
        ciseServiceProfileRepository.saveAndFlush(ciseServiceProfile);

        // Get all the ciseServiceProfileList
        restCiseServiceProfileMockMvc.perform(get("/api/cise-service-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseServiceProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].community").value(hasItem(DEFAULT_COMMUNITY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].dataFreshness").value(hasItem(DEFAULT_DATA_FRESHNESS.toString())))
            .andExpect(jsonPath("$.[*].serviceFunction").value(hasItem(DEFAULT_SERVICE_FUNCTION.toString())))
            .andExpect(jsonPath("$.[*].seaBasin").value(hasItem(DEFAULT_SEA_BASIN.toString())))
            .andExpect(jsonPath("$.[*].serviceRole").value(hasItem(DEFAULT_SERVICE_ROLE.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getCiseServiceProfile() throws Exception {
        // Initialize the database
        ciseServiceProfileRepository.saveAndFlush(ciseServiceProfile);

        // Get the ciseServiceProfile
        restCiseServiceProfileMockMvc.perform(get("/api/cise-service-profiles/{id}", ciseServiceProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ciseServiceProfile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serviceId").value(DEFAULT_SERVICE_ID.toString()))
            .andExpect(jsonPath("$.community").value(DEFAULT_COMMUNITY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.dataFreshness").value(DEFAULT_DATA_FRESHNESS.toString()))
            .andExpect(jsonPath("$.serviceFunction").value(DEFAULT_SERVICE_FUNCTION.toString()))
            .andExpect(jsonPath("$.seaBasin").value(DEFAULT_SEA_BASIN.toString()))
            .andExpect(jsonPath("$.serviceRole").value(DEFAULT_SERVICE_ROLE.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCiseServiceProfile() throws Exception {
        // Get the ciseServiceProfile
        restCiseServiceProfileMockMvc.perform(get("/api/cise-service-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiseServiceProfile() throws Exception {
        // Initialize the database
        ciseServiceProfileRepository.saveAndFlush(ciseServiceProfile);

        int databaseSizeBeforeUpdate = ciseServiceProfileRepository.findAll().size();

        // Update the ciseServiceProfile
        CiseServiceProfile updatedCiseServiceProfile = ciseServiceProfileRepository.findById(ciseServiceProfile.getId()).get();
        // Disconnect from session so that the updates on updatedCiseServiceProfile are not directly saved in db
        em.detach(updatedCiseServiceProfile);
        updatedCiseServiceProfile
            .name(UPDATED_NAME)
            .serviceId(UPDATED_SERVICE_ID)
            .community(UPDATED_COMMUNITY)
            .country(UPDATED_COUNTRY)
            .dataFreshness(UPDATED_DATA_FRESHNESS)
            .serviceFunction(UPDATED_SERVICE_FUNCTION)
            .seaBasin(UPDATED_SEA_BASIN)
            .serviceRole(UPDATED_SERVICE_ROLE)
            .serviceType(UPDATED_SERVICE_TYPE);
        CiseServiceProfileDTO ciseServiceProfileDTO = ciseServiceProfileMapper.toDto(updatedCiseServiceProfile);

        restCiseServiceProfileMockMvc.perform(put("/api/cise-service-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceProfileDTO)))
            .andExpect(status().isOk());

        // Validate the CiseServiceProfile in the database
        List<CiseServiceProfile> ciseServiceProfileList = ciseServiceProfileRepository.findAll();
        assertThat(ciseServiceProfileList).hasSize(databaseSizeBeforeUpdate);
        CiseServiceProfile testCiseServiceProfile = ciseServiceProfileList.get(ciseServiceProfileList.size() - 1);
        assertThat(testCiseServiceProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCiseServiceProfile.getServiceId()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testCiseServiceProfile.getCommunity()).isEqualTo(UPDATED_COMMUNITY);
        assertThat(testCiseServiceProfile.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCiseServiceProfile.getDataFreshness()).isEqualTo(UPDATED_DATA_FRESHNESS);
        assertThat(testCiseServiceProfile.getServiceFunction()).isEqualTo(UPDATED_SERVICE_FUNCTION);
        assertThat(testCiseServiceProfile.getSeaBasin()).isEqualTo(UPDATED_SEA_BASIN);
        assertThat(testCiseServiceProfile.getServiceRole()).isEqualTo(UPDATED_SERVICE_ROLE);
        assertThat(testCiseServiceProfile.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);

        // Validate the CiseServiceProfile in Elasticsearch
        verify(mockCiseServiceProfileSearchRepository, times(1)).save(testCiseServiceProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingCiseServiceProfile() throws Exception {
        int databaseSizeBeforeUpdate = ciseServiceProfileRepository.findAll().size();

        // Create the CiseServiceProfile
        CiseServiceProfileDTO ciseServiceProfileDTO = ciseServiceProfileMapper.toDto(ciseServiceProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiseServiceProfileMockMvc.perform(put("/api/cise-service-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ciseServiceProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CiseServiceProfile in the database
        List<CiseServiceProfile> ciseServiceProfileList = ciseServiceProfileRepository.findAll();
        assertThat(ciseServiceProfileList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CiseServiceProfile in Elasticsearch
        verify(mockCiseServiceProfileSearchRepository, times(0)).save(ciseServiceProfile);
    }

    @Test
    @Transactional
    public void deleteCiseServiceProfile() throws Exception {
        // Initialize the database
        ciseServiceProfileRepository.saveAndFlush(ciseServiceProfile);

        int databaseSizeBeforeDelete = ciseServiceProfileRepository.findAll().size();

        // Get the ciseServiceProfile
        restCiseServiceProfileMockMvc.perform(delete("/api/cise-service-profiles/{id}", ciseServiceProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CiseServiceProfile> ciseServiceProfileList = ciseServiceProfileRepository.findAll();
        assertThat(ciseServiceProfileList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CiseServiceProfile in Elasticsearch
        verify(mockCiseServiceProfileSearchRepository, times(1)).deleteById(ciseServiceProfile.getId());
    }

    @Test
    @Transactional
    public void searchCiseServiceProfile() throws Exception {
        // Initialize the database
        ciseServiceProfileRepository.saveAndFlush(ciseServiceProfile);
        when(mockCiseServiceProfileSearchRepository.search(queryStringQuery("id:" + ciseServiceProfile.getId())))
            .thenReturn(Collections.singletonList(ciseServiceProfile));
        // Search the ciseServiceProfile
        restCiseServiceProfileMockMvc.perform(get("/api/_search/cise-service-profiles?query=id:" + ciseServiceProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciseServiceProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].community").value(hasItem(DEFAULT_COMMUNITY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].dataFreshness").value(hasItem(DEFAULT_DATA_FRESHNESS.toString())))
            .andExpect(jsonPath("$.[*].serviceFunction").value(hasItem(DEFAULT_SERVICE_FUNCTION.toString())))
            .andExpect(jsonPath("$.[*].seaBasin").value(hasItem(DEFAULT_SEA_BASIN.toString())))
            .andExpect(jsonPath("$.[*].serviceRole").value(hasItem(DEFAULT_SERVICE_ROLE.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseServiceProfile.class);
        CiseServiceProfile ciseServiceProfile1 = new CiseServiceProfile();
        ciseServiceProfile1.setId(1L);
        CiseServiceProfile ciseServiceProfile2 = new CiseServiceProfile();
        ciseServiceProfile2.setId(ciseServiceProfile1.getId());
        assertThat(ciseServiceProfile1).isEqualTo(ciseServiceProfile2);
        ciseServiceProfile2.setId(2L);
        assertThat(ciseServiceProfile1).isNotEqualTo(ciseServiceProfile2);
        ciseServiceProfile1.setId(null);
        assertThat(ciseServiceProfile1).isNotEqualTo(ciseServiceProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiseServiceProfileDTO.class);
        CiseServiceProfileDTO ciseServiceProfileDTO1 = new CiseServiceProfileDTO();
        ciseServiceProfileDTO1.setId(1L);
        CiseServiceProfileDTO ciseServiceProfileDTO2 = new CiseServiceProfileDTO();
        assertThat(ciseServiceProfileDTO1).isNotEqualTo(ciseServiceProfileDTO2);
        ciseServiceProfileDTO2.setId(ciseServiceProfileDTO1.getId());
        assertThat(ciseServiceProfileDTO1).isEqualTo(ciseServiceProfileDTO2);
        ciseServiceProfileDTO2.setId(2L);
        assertThat(ciseServiceProfileDTO1).isNotEqualTo(ciseServiceProfileDTO2);
        ciseServiceProfileDTO1.setId(null);
        assertThat(ciseServiceProfileDTO1).isNotEqualTo(ciseServiceProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ciseServiceProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ciseServiceProfileMapper.fromId(null)).isNull();
    }
}
