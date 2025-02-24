package org.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product extends BaseModel {
    String name;
    String description;
    String imageUrl;
    Double price;
    Category category;
    Boolean isPrime;

}
