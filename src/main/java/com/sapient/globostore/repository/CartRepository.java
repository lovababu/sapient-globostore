package com.sapient.globostore.repository;

import com.sapient.globostore.entity.Product;

import java.util.Map;
import java.util.Set;

/**
 * Created by dpadal on 12/12/2016.
 */
public interface CartRepository {

    boolean add(Product product);

    boolean delete(long productId);
}
