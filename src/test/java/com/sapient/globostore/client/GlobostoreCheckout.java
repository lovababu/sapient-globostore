package com.sapient.globostore.client;

import com.sapient.globostore.entity.Bill;
import com.sapient.globostore.entity.Product;
import com.sapient.globostore.service.CartService;
import com.sapient.globostore.service.ProductCatalogueService;
import lombok.Getter;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Common Interface class, expose the methods scan and generateBillAmount to the clients.
 *
 * This can be converted into Rest/SpringController entry point to expose as service.
 *
 * Created by dpadal on 12/12/2016.
 */
public class GlobostoreCheckout {

    @Getter
    private final Map<String, Integer> USER_CART;

    private ProductCatalogueService productCatalogueService;

    private CartService cartService;

    public GlobostoreCheckout(CartService cartService, ProductCatalogueService productCatalogueService) {
        this.productCatalogueService = productCatalogueService;
        this.cartService = cartService;
        this.USER_CART = new HashMap<>();
    }

    public Map<Long, Product> fetchAll() {
        return productCatalogueService.fetchAll();
    }

    public boolean scan(String productName) {
        Optional<Product> product = productCatalogueService.getProduct(productName);
        if (product.isPresent()) {
            if (cartService.add(product.get())){
                if (USER_CART.containsKey(product.get().getName())) {
                    int count = USER_CART.get(product.get().getName());
                    count++;
                    USER_CART.put(product.get().getName(), count);

                } else {
                    Set<Product> productSet = new HashSet<>(1);
                    productSet.add(product.get());
                    USER_CART.put(product.get().getName(), 1);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public BigDecimal checkout() throws ScriptException {
        Map<String, Bill> billMap = cartService.checkOut(USER_CART);
        System.out.println("--------Sapient Globostore--------------");
        System.out.println("----------- Bill Report  ---------------");
        BigDecimal totalAmountPayable = new BigDecimal(0);
        BigDecimal totalSavings = new BigDecimal(0);
        for (Map.Entry<String, Bill> bill: billMap.entrySet()) {
            Bill b = bill.getValue();
            System.out.println(String.format("%s    %d : %f", b.getProduct().getName(),
                    b.getQuantity(), b.getTotalAmountPayable()));
            totalAmountPayable = totalAmountPayable.add(b.getTotalAmountPayable());
            totalSavings = totalSavings.add(b.getTotalSavings());
        }
        System.out.println("----------------------------------------");
        System.out.println("Total Amount payable: " + totalAmountPayable);
        System.out.println("Your Total Savings  : " + totalSavings);
        return totalAmountPayable;
    }
}
