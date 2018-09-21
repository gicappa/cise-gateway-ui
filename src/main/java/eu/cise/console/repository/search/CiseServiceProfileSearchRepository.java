package eu.cise.console.repository.search;

import eu.cise.console.domain.CiseServiceProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CiseServiceProfile entity.
 */
public interface CiseServiceProfileSearchRepository extends ElasticsearchRepository<CiseServiceProfile, Long> {
}
