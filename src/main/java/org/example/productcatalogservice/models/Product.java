package org.example.productcatalogservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name= "products")
public class Product extends BaseModel {
    String name;
    String description;
    String imageUrl;
    Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    Category category;
}
