package com.sapient.globostore.repository.impl;

import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.DiscountType;
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
        PRODUCT_DATA = new HashMap<Long, Product>(3);
        DISCOUNTS = new HashSet<Discount>(3);
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


    /**
     * @param productId
     * @return <code>{@link Set}</code>
     */
    public Optional<Discount> getDiscount(final long productId) {
        if (CollectionUtils.isNotEmpty(DISCOUNTS)) {
            return Optional.empty();
        }
        return DISCOUNTS.stream()
                .filter(discount -> discount.getProductId().getId() == productId)
                .findFirst();
    }

    @Override
    public boolean isProductExist(Long productId) {
        return MapUtils.isNotEmpty(PRODUCT_DATA) && PRODUCT_DATA.containsKey(productId);
    }


    /**
     * Initializing Product and Discount data.
     * <p>
     * assuming this data loading from Data store, and cached if required.
     */
    private void initialize() {
        Product productA = product("A", new BigDecimal(5));
        PRODUCT_DATA.put(productId.get(), productA);
        Product productB = product("B", new BigDecimal(15));
        PRODUCT_DATA.put(productB.getId(), productB);
        Product productC = product("C", new BigDecimal(10));
        PRODUCT_DATA.put(productC.getId(), productC);

        DISCOUNTS.add(discount("A", DiscountType.QUANTITY, 3, new BigDecimal(13)));
        DISCOUNTS.add(discount("B", DiscountType.QUANTITY, 2, new BigDecimal(25)));
        DISCOUNTS.add(discount("C", DiscountType.QUANTITY, 5, new BigDecimal(40)));
    }

    private Product product(String productName, BigDecimal unitPrice) {
        Product product = new Product();
        product.setId(productId.getAndIncrement());
        product.setName(productName);
        product.setCategory("Electronic");
        product.setUnitPrice(unitPrice);
        return product;
    }

    private Discount discount(String productName, DiscountType discountType, int forItems, BigDecimal discValue) {
        Discount discount = new Discount();
        discount.setDiscountType(discountType);
        discount.setId(discountId.getAndIncrement());
        discount.setForItems(forItems);
        discount.setDiscountValue(discValue);
        discount.setStartDate(new Date());
        discount.setEndDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(8)));
        return discount;
    }
}
