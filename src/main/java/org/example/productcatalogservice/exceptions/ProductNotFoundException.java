package org.example.productcatalogservice.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String error) {
        super(error);
    }
}
