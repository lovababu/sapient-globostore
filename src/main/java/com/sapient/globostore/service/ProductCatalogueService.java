package com.sapient.globostore.service;

import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.Product;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by dpadal on 12/12/2016.
 */
public interface ProductCatalogueService {

    Map<Long, Product> fetchAll();

    //TODO: do we need this? since product has bundled with Discount.
    Optional<Discount> getDiscount(long productId);

    Set<Discount> fetchAllDiscounts();

    Optional<Product> getProduct(String name);
}
