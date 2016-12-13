package com.sapient.globostore.service.impl;

import com.sapient.globostore.constant.Expressions;
import com.sapient.globostore.entity.Bill;
import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.Product;
import com.sapient.globostore.enums.DiscountType;
import com.sapient.globostore.repository.CartRepository;
import com.sapient.globostore.repository.ProductCatalogueRepository;
import com.sapient.globostore.service.CartService;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by dpadal on 12/12/2016.
 */
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    private ProductCatalogueRepository productCatalogueRepository;

    public CartServiceImpl(CartRepository  cartRepository, ProductCatalogueRepository productCatalogueRepository) {
        this.cartRepository = cartRepository;
        this.productCatalogueRepository = productCatalogueRepository;
    }

    public boolean add(Product product) {
        return cartRepository.add(product);
    }

    public boolean delete(Product product) {
        return cartRepository.delete(product);
    }

    @Override
    public Map<String, Bill> checkOut(Map<String, Integer> cart) throws ScriptException {
        Map<String, Bill> billMap = null;
        if (MapUtils.isNotEmpty(cart)) {
            billMap = new HashMap<>();
            for (Map.Entry<String, Integer> entrySet : cart.entrySet()) {
                billMap.put(entrySet.getKey(),
                        generateBill(entrySet.getKey(), entrySet.getValue()));
            }
        }
        return billMap;
    }

    private Bill generateBill(String productName, Integer quantity) throws ScriptException {
        Bill bill = null;
        Optional<Product> optional = productCatalogueRepository.getProduct(productName);
        if (optional.isPresent()) {
            Product product = optional.get();
            bill = new Bill();
            BigDecimal totalAmount = calculateTotalAmount(product, quantity);
            BigDecimal amountPayable = calculateAmountPayable(product, quantity);
            BigDecimal savings = totalAmount.subtract(amountPayable);
            bill.setQuantity(quantity);
            bill.setProduct(product);
            bill.setTotalAmountPayable(amountPayable);
            bill.setTotalSavings(savings);
        }
        return bill;
    }

    private BigDecimal calculateAmountPayable(Product product, int quantity) throws ScriptException {
        BigDecimal totalAmountPayable;
        Optional<Discount> optional = product.getDiscounts().stream().findFirst();
        if (optional.isPresent()) {
            Discount discount = optional.get();
            switch (discount.getDiscountType()) {
                case QUANTITIVE:
                    totalAmountPayable = eval(Expressions.EXPRESSION_QUANTITIVE, product, quantity);
                    break;
                case PERCENTILE:
                    totalAmountPayable = eval(Expressions.EXPRESSION_PERCENTAGE, product, quantity);
                    break;
                default:
                    totalAmountPayable = new BigDecimal(0);
            }
        } else {
            totalAmountPayable = new BigDecimal(0);
        }
        return totalAmountPayable;
    }

    private BigDecimal calculateTotalAmount(Product product, int quantity) throws ScriptException {
        return eval(Expressions.EXPRESSION_TOTAL, product, quantity);
    }

    private BigDecimal eval(String expression, Product product, Integer quantity) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
        Bindings bindings;
        //Get the Discount from the product.
        Optional<Discount> optional = product.getDiscounts().stream().findFirst();
        if (optional.isPresent()) {
            Discount discount = optional.get();
            bindings = scriptEngine.createBindings();
            bindings.put("Q", quantity);
            bindings.put("DQ", discount.getForItems());
            bindings.put("UP", product.getUnitPrice());
            bindings.put("DP", discount.getDiscountValue());
            return new BigDecimal((Double) scriptEngine.eval(expression, bindings));
        }
        return new BigDecimal(0);
    }

}
