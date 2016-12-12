package com.sapient.globostore.service;

import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.Product;

import java.util.Map;
import java.util.Optional;

/**
 * Created by dpadal on 12/12/2016.
 */
public interface ProductCatalogueService {

    Map<Long, Product> fetchAll();

    Optional<Discount> getDiscount(long productId);
}
