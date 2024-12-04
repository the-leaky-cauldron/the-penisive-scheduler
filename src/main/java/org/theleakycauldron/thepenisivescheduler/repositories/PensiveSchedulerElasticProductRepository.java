package org.theleakycauldron.thepenisivescheduler.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.theleakycauldron.thepenisivescheduler.daos.documents.ElasticProduct;

import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Repository
public interface PensiveSchedulerElasticProductRepository extends ElasticsearchRepository<ElasticProduct, String> {
    ElasticProduct findElasticProductByUuidEquals(UUID uuid);
    void deleteElasticProductByUuidEquals(UUID uuid);
}
