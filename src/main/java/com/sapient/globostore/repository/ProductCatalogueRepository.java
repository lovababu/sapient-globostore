package com.sapient.globostore.repository;

import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.Product;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by dpadal on 12/12/2016.
 */
public interface ProductCatalogueRepository {

    Map<Long, Product> fetchAll();

    Optional<Product> getProduct(String name);

    Set<Discount> fetchAllDiscounts();

    Optional<Discount> getDiscount(long productId);

    boolean isProductExist(Long productId);
}
