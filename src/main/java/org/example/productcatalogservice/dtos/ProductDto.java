package org.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {
    Long id;
    String name;
    String description;
    String imageUrl;
    Double price;
    Long categoryId;
}
