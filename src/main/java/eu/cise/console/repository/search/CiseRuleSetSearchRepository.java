package eu.cise.console.repository.search;

import eu.cise.console.domain.CiseRuleSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CiseRuleSet entity.
 */
public interface CiseRuleSetSearchRepository extends ElasticsearchRepository<CiseRuleSet, Long> {
}
