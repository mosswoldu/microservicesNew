package com.microProgram.productservice.service;

import com.microProgram.productservice.dto.ProductRequest;
import com.microProgram.productservice.dto.ProductResponse;
import com.microProgram.productservice.model.Product;
import com.microProgram.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }


    public void createProduct(ProductRequest productRequest) {
        Product product=Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("product {} is saved successfully",product.getId());

    }

    public List<ProductResponse> getAllProducts() {
        List<Product>products=productRepository.findAll();
       return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
      return   ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
//   @GteMapping
//    public ProductResponse getProduct("/{id}"){
//
//    }
}
