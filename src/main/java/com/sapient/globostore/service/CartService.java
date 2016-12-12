package com.sapient.globostore.service;

import com.sapient.globostore.entity.Product;

/**
 * Created by dpadal on 12/12/2016.
 */
public interface CartService {

    boolean add(Product product);

    boolean delete(Product product);
}
