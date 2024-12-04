package org.theleakycauldron.thepenisivescheduler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.theleakycauldron.thepenisivescheduler.daos.entities.Outbox;
import java.util.List;
import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya
 *          (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface PenisiveSchedulerOutboxRepository extends JpaRepository<Outbox, Long> {
    @Query("select o from Outbox o where o.isPersisted = false")
    List<Outbox> findAllPersistedFalse();

    @Query("select o from Outbox o where o.isUpdated = false")
    List<Outbox> findAllUpdatedFalse();

    @Query("select o from Outbox o where o.isDeleted = true")
    List<Outbox> findAllByDeletedTrue();

    @Modifying
    @Query("delete from Outbox o where o.isDeleted = true and o.uuid = ?1")
    void deleteByDeletedTrueAndUuidEquals(UUID uuid);
}
