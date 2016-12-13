package com.sapient.globostore.service.impl;

import com.sapient.globostore.entity.Product;
import com.sapient.globostore.repository.ProductCatalogueRepository;
import com.sapient.globostore.service.ProductCatalogueService;

import java.util.Map;
import java.util.Optional;

/**
 * Created by dpadal on 12/12/2016.
 */
public class ProductCatalogueServiceImpl implements ProductCatalogueService {

    private ProductCatalogueRepository productCatalogueRepository;

    public ProductCatalogueServiceImpl(ProductCatalogueRepository productCatalogueRepository) {
        this.productCatalogueRepository = productCatalogueRepository;
    }

    @Override
    public Map<Long, Product> fetchAll() {
        return productCatalogueRepository.fetchAll();
    }

    @Override
    public Optional<Product> getProduct(String name) {
        return productCatalogueRepository.getProduct(name);
    }
}
