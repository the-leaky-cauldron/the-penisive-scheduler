package org.theleakycauldron.thepenisivescheduler.daos.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya
 *          (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ProductCategory extends BaseModel {
    private String name;
}
