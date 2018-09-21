package eu.cise.console.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CiseAuthoritySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CiseAuthoritySearchRepositoryMockConfiguration {

    @MockBean
    private CiseAuthoritySearchRepository mockCiseAuthoritySearchRepository;

}
