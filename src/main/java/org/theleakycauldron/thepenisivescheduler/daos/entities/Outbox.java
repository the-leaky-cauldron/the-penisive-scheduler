package org.theleakycauldron.thepenisivescheduler.daos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
public class Outbox extends BaseModel {
    @OneToOne(fetch = FetchType.EAGER)
    private Product product;
    private boolean isPersisted;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isUpdated;
}
