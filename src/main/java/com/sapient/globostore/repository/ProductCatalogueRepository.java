package com.sapient.globostore.repository;

import com.sapient.globostore.entity.Product;

import java.util.Map;
import java.util.Optional;

/**
 * Created by dpadal on 12/12/2016.
 */
public interface ProductCatalogueRepository {

    Map<Long, Product> fetchAll();

    Optional<Product> getProduct(String name);
}
