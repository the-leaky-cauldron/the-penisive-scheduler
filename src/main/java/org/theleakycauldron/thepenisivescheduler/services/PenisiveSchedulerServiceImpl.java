package org.theleakycauldron.thepenisivescheduler.services;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.theleakycauldron.thepenisivescheduler.daos.documents.ElasticProduct;
import org.theleakycauldron.thepenisivescheduler.repositories.PenisiveSchedulerOutboxRepository;
import org.theleakycauldron.thepenisivescheduler.daos.entities.Outbox;
import org.theleakycauldron.thepenisivescheduler.repositories.PensiveSchedulerElasticProductRepository;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya
 * (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Slf4j
@Service
public class PenisiveSchedulerServiceImpl implements PenisiveSchedulerService {
    private final PenisiveSchedulerOutboxRepository penisiveSchedulerOutboxRepository;
    private final PensiveSchedulerElasticProductRepository pensiveSchedulerElasticProductRepository;

    public PenisiveSchedulerServiceImpl(
            PenisiveSchedulerOutboxRepository penisiveSchedulerOutboxRepository, PensiveSchedulerElasticProductRepository pensiveSchedulerElasticProductRepository) {
        this.penisiveSchedulerOutboxRepository = penisiveSchedulerOutboxRepository;
        this.pensiveSchedulerElasticProductRepository = pensiveSchedulerElasticProductRepository;
    }

    @Scheduled(fixedDelay = (30 * 60 * 1000))
    public void scheduleProductPersist() {
        log.info("Penisive Task Scheduled");
        List<Outbox> outboxes = penisiveSchedulerOutboxRepository.findAllPersistedFalse();
        List<ElasticProduct> elasticProducts = new ArrayList<>();

        if (!outboxes.isEmpty()) {
            outboxes.forEach(outbox -> {
                ElasticProduct elasticProduct = ElasticProduct.builder()
                        .uuid(outbox.getProduct().getUuid())
                        .productName(outbox.getProduct().getName())
                        .productDescription(outbox.getProduct().getDescription())
                        .imageUrl(outbox.getProduct().getImageURL())
                        .manufacturerName(outbox.getProduct().getManufacturer().getName())
                        .productCategory(outbox.getProduct().getProductCategory().getName())
                        .productPrice(outbox.getProduct().getPrice().getAmount())
                        .discount(outbox.getProduct().getPrice().getDiscount())
                        .tags(outbox.getProduct().getTags())
                        .rating(outbox.getProduct().getRating())
                        .build();
                elasticProducts.add(elasticProduct);
                outbox.setPersisted(true);
            });

            pensiveSchedulerElasticProductRepository.saveAll(elasticProducts);
            penisiveSchedulerOutboxRepository.saveAll(outboxes);
        }
    }

    @Scheduled(fixedDelay = (30 * 60 * 1000))
    public void scheduleProductUpdate(){
        log.info("Update product task");
        List<Outbox> outboxes = penisiveSchedulerOutboxRepository.findAllUpdatedFalse();
        List<ElasticProduct> elasticProducts = new ArrayList<>();
        outboxes.forEach(outbox -> {
            ElasticProduct elasticProductSearchHit = pensiveSchedulerElasticProductRepository.findElasticProductByUuidEquals(outbox.getUuid());
            log.info("Product found: {}", elasticProductSearchHit);
            if (elasticProductSearchHit != null) {
                elasticProductSearchHit.setProductName(outbox.getProduct().getName());
                elasticProductSearchHit.setProductDescription(outbox.getProduct().getDescription());
                elasticProductSearchHit.setImageUrl(outbox.getProduct().getImageURL());
                elasticProductSearchHit.setManufacturerName(outbox.getProduct().getManufacturer().getName());
                elasticProductSearchHit.setProductCategory(outbox.getProduct().getProductCategory().getName());
                elasticProductSearchHit.setProductPrice(outbox.getProduct().getPrice().getAmount());
                elasticProductSearchHit.setDiscount(outbox.getProduct().getPrice().getDiscount());
                elasticProductSearchHit.setTags(outbox.getProduct().getTags());
                elasticProductSearchHit.setRating(outbox.getProduct().getRating());
                elasticProducts.add(elasticProductSearchHit);
                outbox.setUpdated(true);
            }
        });
        pensiveSchedulerElasticProductRepository.saveAll(elasticProducts);
        penisiveSchedulerOutboxRepository.saveAll(outboxes);
    }

    @Scheduled(fixedDelay = (30 * 60 * 1000))
    @Transactional
    public void scheduleProductDelete(){
        log.info("Delete product task");
        List<Outbox> outboxes = penisiveSchedulerOutboxRepository.findAllByDeletedTrue();
        outboxes.forEach(outbox -> {
            pensiveSchedulerElasticProductRepository.deleteElasticProductByUuidEquals(outbox.getUuid());
            penisiveSchedulerOutboxRepository.deleteByDeletedTrueAndUuidEquals(outbox.getUuid());
        });
    }

}
