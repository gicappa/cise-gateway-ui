package eu.cise.console.repository.search;

import eu.cise.console.domain.CiseService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CiseService entity.
 */
public interface CiseServiceSearchRepository extends ElasticsearchRepository<CiseService, Long> {
}
