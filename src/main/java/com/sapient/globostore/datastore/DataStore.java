package com.sapient.globostore.datastore;

import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.Product;
import com.sapient.globostore.enums.DiscountType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Data holder class, It holds the Product/Discounts information in inmemory map.
 * <p>
 * Assuming this data getting from backend data store.
 * <p>
 * Created by dpadal on 12/15/2016.
 */
public final class DataStore {

    private static final AtomicLong productId = new AtomicLong(1);
    private static final AtomicInteger discountId = new AtomicInteger(1);
    private static final Map<Long, Product> PRODUCT_DATA = new HashMap<>(3);

    /**
     * Initialize the data.
     */
    static {
        initialize();
    }

    /**
     * Returns the Unmodifiable products map.
     * @return
     */
    public static Map<Long, Product> fetchAllProducts() {
        return Collections.unmodifiableMap(PRODUCT_DATA);
    }

    /**
     * Initializing Product and Discount data, dummy data.
     * <p>
     * assuming this data loading from Data store, and cached if required.
     */
    private static void initialize() {
        Product productA = product("A", new BigDecimal(5));
        loadDiscount(productA, DiscountType.QUANTITIVE, 3, new BigDecimal(13));
        PRODUCT_DATA.put(productA.getId(), productA);

        Product productB = product("B", new BigDecimal(15));
        loadDiscount(productB, DiscountType.QUANTITIVE, 2, new BigDecimal(25));
        PRODUCT_DATA.put(productB.getId(), productB);

        Product productC = product("C", new BigDecimal(10));
        loadDiscount(productC, DiscountType.QUANTITIVE, 5, new BigDecimal(40));
        PRODUCT_DATA.put(productC.getId(), productC);
    }

    private static Product product(String productName, BigDecimal unitPrice) {
        Product product = new Product();
        product.setId(productId.getAndIncrement());
        product.setName(productName);
        product.setCategory("Electronic");
        product.setUnitPrice(unitPrice);
        return product;
    }

    private static Discount loadDiscount(Product product, DiscountType discountType, int forItems, BigDecimal discValue) {
        Discount discount = new Discount();
        discount.setDiscountType(discountType);
        discount.setId(discountId.getAndIncrement());
        discount.setForItems(forItems);
        discount.setDiscountValue(discValue);
        discount.setStartDate(new Date());
        discount.setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(8)));
        discount.setProductId(product);
        product.setDiscounts(new HashSet<Discount>() {
            {
                add(discount);
            }
        });
        return discount;
    }
}
