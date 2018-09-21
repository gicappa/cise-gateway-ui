package eu.cise.console.repository.search;

import eu.cise.console.domain.CiseAuthority;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CiseAuthority entity.
 */
public interface CiseAuthoritySearchRepository extends ElasticsearchRepository<CiseAuthority, Long> {
}
