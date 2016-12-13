package com.sapient.globostore.service;

import com.sapient.globostore.entity.Bill;
import com.sapient.globostore.entity.Product;

import javax.script.ScriptException;
import java.util.Map;

/**
 * Created by dpadal on 12/12/2016.
 */
public interface CartService {

    boolean add(Product product);

    boolean delete(Product product);

    Map<String, Bill> checkOut(Map<String, Integer> cart) throws ScriptException;
}
