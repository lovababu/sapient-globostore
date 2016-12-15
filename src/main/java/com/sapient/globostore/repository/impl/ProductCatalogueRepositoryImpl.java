package com.sapient.globostore.repository.impl;

import com.sapient.globostore.datastore.DataStore;
import com.sapient.globostore.entity.Discount;
import com.sapient.globostore.entity.Product;
import com.sapient.globostore.enums.DiscountType;
import com.sapient.globostore.repository.ProductCatalogueRepository;

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
 * ProductRepository class responsible to Perform CRUD operation on corresponding product data table.
 * <p>
 * Assuming the Products get updated in Backend Data store.
 * <p>
 * For now it is kind of legless design, where we don't have any data store.
 * <p>
 * Created by dpadal on 12/12/2016.
 */
public class ProductCatalogueRepositoryImpl implements ProductCatalogueRepository {

    /**
     * Fetch available products and return.
     *
     * @return <code>{@link Map}</code>
     */
    @Override
    public Map<Long, Product> fetchAll() {
        return DataStore.fetchAllProducts();
    }

    @Override
    public Optional<Product> getProduct(String name) {
        return DataStore.fetchAllProducts().values().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

}
