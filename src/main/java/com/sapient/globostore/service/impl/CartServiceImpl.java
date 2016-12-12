package com.sapient.globostore.service.impl;

import com.sapient.globostore.entity.Product;
import com.sapient.globostore.repository.CartRepository;
import com.sapient.globostore.service.CartService;
import lombok.Setter;

/**
 * Created by dpadal on 12/12/2016.
 */
public class CartServiceImpl implements CartService {

    @Setter
    private CartRepository cartRepository;

    public boolean add(Product product) {
        return cartRepository.add(product);
    }

    public boolean delete(Product product) {
        return cartRepository.delete(product);
    }
}
