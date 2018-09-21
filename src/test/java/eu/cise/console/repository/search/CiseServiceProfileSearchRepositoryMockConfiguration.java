package eu.cise.console.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CiseServiceProfileSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CiseServiceProfileSearchRepositoryMockConfiguration {

    @MockBean
    private CiseServiceProfileSearchRepository mockCiseServiceProfileSearchRepository;

}
