package org.theleakycauldron.thepenisivescheduler.daos.documents;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(indexName = "products")
@ToString
public class ElasticProduct {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private UUID uuid;
    @Field(type = FieldType.Text)
    private String manufacturerName;
    @Field(type = FieldType.Text)
    private String productName;
    @Field(type = FieldType.Text)
    private String productDescription;
    @Field(type = FieldType.Text)
    private String imageUrl;
    @Field(type = FieldType.Text)
    private String productCategory;
    @Field(type = FieldType.Double)
    private double productPrice;
    @Field(type = FieldType.Double)
    private double discount;
    @Field(type = FieldType.Double)
    private double rating;
    @MultiField(mainField = @Field(type = FieldType.Text),
            otherFields = { @InnerField(suffix = "keyword", type = FieldType.Keyword) })
    private List<String> tags;
}
