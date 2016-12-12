package com.sapient.globostore.service.impl;

import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.Product;
import com.sapient.globostore.repository.ProductCatalogueRepository;
import com.sapient.globostore.service.ProductCatalogueService;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

/**
 * Created by dpadal on 12/12/2016.
 */
public class ProductCatalogueServiceImpl implements ProductCatalogueService {

    @Setter
    private ProductCatalogueRepository productCatalogueRepository;

    @Override
    public Map<Long, Product> fetchAll() {
        return productCatalogueRepository.fetchAll();
    }

    @Override
    public Optional<Discount> getDiscount(long productId) {
        boolean isProductExist = productCatalogueRepository.isProductExist(productId);
        if (isProductExist) {
            return productCatalogueRepository.getDiscount(productId);
        } else {
            return Optional.empty();
        }
    }
}
