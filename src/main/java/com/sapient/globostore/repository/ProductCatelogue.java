package com.sapient.globostore.repository;

import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.Product;

import java.util.Map;
import java.util.Set;

/**
 * Created by dpadal on 12/12/2016.
 */
public interface ProductCatelogue {

    Map<String, Product> fetchAll();

    Set<Discount> getDiscount(long productId);
}
