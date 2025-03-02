package org.example.productcatalogservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name= "categories")
public class Category extends BaseModel {
    String name;
    String description;
    @OneToMany(mappedBy = "category")
    List<Product> products;
}
