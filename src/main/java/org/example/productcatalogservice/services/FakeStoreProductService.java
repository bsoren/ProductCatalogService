package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FakeStoreProductService implements IProductService {

    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate =  restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto =
                restTemplate.getForEntity("http://fakestoreapi.com/products/{id}",
                        FakeStoreProductDto.class,id);

        if(fakeStoreProductDto.getBody() != null &&
                fakeStoreProductDto.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return from(fakeStoreProductDto.getBody());
        }

        return null;
    }

    public <T> ResponseEntity<T> requestForEntity(
            String url,
            HttpMethod httpMethod,
            @Nullable Object request,
            Class<T> responseType,
            Map<String, ?> uriVariables) throws RestClientException {
        RestTemplate restTemplate =  restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate =  restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDto =
                restTemplate.getForEntity("http://fakestoreapi.com/products", FakeStoreProductDto[].class);

        if(fakeStoreProductDto.getBody() != null &&
                fakeStoreProductDto.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return fromList(fakeStoreProductDto.getBody());
        }

        return null;
    }

    @Override
    public Product replaceProduct(Long id, ProductDto productDto) {
        String url = "http://fakestoreapi.com/products/{id}";

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", id);

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto =
                this.requestForEntity(url, HttpMethod.PUT, from(productDto), FakeStoreProductDto.class, uriVariables);

        if(fakeStoreProductDto.getBody() != null &&
                fakeStoreProductDto.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return from(fakeStoreProductDto.getBody());
        }

        return null;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        String url = "http://fakestoreapi.com/products";

        FakeStoreProductDto fakeProductDto = from(productDto);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto =
                restTemplate.postForEntity(url, fakeProductDto, FakeStoreProductDto.class);

        if(fakeStoreProductDto.getBody() != null &&
                fakeStoreProductDto.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return from(fakeStoreProductDto.getBody());
        }

        return null;
    }

    @Override
    public Product delete(Long id) {
        String url = "http://fakestoreapi.com/products/{id}";
        RestTemplate restTemplate = restTemplateBuilder.build();

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", id);

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto =
                this.requestForEntity(url, HttpMethod.DELETE, null, FakeStoreProductDto.class, uriVariables);

        if(fakeStoreProductDto.getBody() != null &&
                fakeStoreProductDto.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            return from(fakeStoreProductDto.getBody());
        }

        return null;
    }

    private List<Product> fromList(FakeStoreProductDto[] fakeStoreProductDtos) {
        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDto fpDto : fakeStoreProductDtos) {
            products.add(this.from(fpDto));
        }

        return products;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto from(ProductDto productDto) {
        FakeStoreProductDto fakeProductDto = new FakeStoreProductDto();
        fakeProductDto.setTitle(productDto.getName());
        fakeProductDto.setPrice(productDto.getPrice());
        fakeProductDto.setDescription(productDto.getDescription());
        fakeProductDto.setCategory(productDto.getCategory().getName());
        fakeProductDto.setImage(productDto.getImageUrl());
        return fakeProductDto;
    }

}
