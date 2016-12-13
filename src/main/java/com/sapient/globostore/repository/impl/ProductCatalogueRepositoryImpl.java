package com.sapient.globostore.repository.impl;

import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.enums.DiscountType;
import com.sapient.globostore.entity.Product;
import com.sapient.globostore.repository.ProductCatalogueRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ProductRepository class responsible to Perform CRUD operation on corresponding data table.
 * <p>
 * Assuming the Products get updated in Backend Data store.
 * <p>
 * For now it is kind of legless design, where we don't have any data store.
 * <p>
 * Created by dpadal on 12/12/2016.
 */
public class ProductCatalogueRepositoryImpl implements ProductCatalogueRepository {

    private final AtomicLong productId = new AtomicLong(1);
    private final AtomicInteger discountId = new AtomicInteger(1);
    private final Map<Long, Product> PRODUCT_DATA;

    private final Set<Discount> DISCOUNTS;


    /**
     * Initialize the data.
     */
    public ProductCatalogueRepositoryImpl() {
        PRODUCT_DATA = new HashMap<>(3);
        DISCOUNTS = new HashSet<>(3);
        initialize();
    }


    /**
     * Fetch available products and return.
     *
     * @return <code>{@link Map}</code>
     */
    public Map<Long, Product> fetchAll() {
        return PRODUCT_DATA;
    }

    @Override
    public Optional<Product> getProduct(String name) {
        return PRODUCT_DATA.values().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Initializing Product and Discount data, dummy data.
     * <p>
     * assuming this data loading from Data store, and cached if required.
     */
    private void initialize() {
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

    private Product product(String productName, BigDecimal unitPrice) {
        Product product = new Product();
        product.setId(productId.getAndIncrement());
        product.setName(productName);
        product.setCategory("Electronic");
        product.setUnitPrice(unitPrice);
        return product;
    }

    private Discount loadDiscount(Product product, DiscountType discountType, int forItems, BigDecimal discValue) {
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
        DISCOUNTS.add(discount);
        return discount;
    }
}
