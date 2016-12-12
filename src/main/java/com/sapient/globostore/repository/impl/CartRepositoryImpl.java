package com.sapient.globostore.repository.impl;

import com.sapient.globostore.entity.Product;
import com.sapient.globostore.repository.CartRepository;

import java.util.Date;

/**
 * CartRepository class responsible for lock and unlock the product when the product get added/deleted to/from cart.
 *
 * Assuming the Products get updated in Backend Data store.
 *
 * For now it is kind of legless design, where we don't have any data store.
 *
 * Created by dpadal on 12/12/2016.
 */
public class CartRepositoryImpl implements CartRepository{

    /**
     * Mark the product as locked
     *  case 1: if not already locked and set <code>{@link Product#setLockedAt(Date)}</code>
     *  case 2: if locked, verify <code>{@link Product#getLockedAt()}</code> if older than 30 min, unlock  and
     *  set reset time.
     *
     * And then Update in DB.
     *
     * <code>{@link Product#setLock(boolean)}</code>
     *
     * @param product
     * @return true if product Available and updateCount > 0, else false.
     */
    public boolean add(Product product) {
        //Fetch the Product from DB.
        product.setLock(true);
        return true;
    }

    /**
     * Unlock the Product if it is locked and update <code>{@link Product#setLockedAt(Date)}</code> to null.
     * And update Product in DB.
     * @param product
     * @return true if updateCount > 0
     */
    public boolean delete(Product product) {
        //Fetch the Product from DB.
        product.setLock(false);
        return true;
    }
}
