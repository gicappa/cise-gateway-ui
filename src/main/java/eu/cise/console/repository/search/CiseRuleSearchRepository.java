package eu.cise.console.repository.search;

import eu.cise.console.domain.CiseRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CiseRule entity.
 */
public interface CiseRuleSearchRepository extends ElasticsearchRepository<CiseRule, Long> {
}
