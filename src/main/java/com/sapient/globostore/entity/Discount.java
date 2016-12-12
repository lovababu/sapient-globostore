package com.sapient.globostore.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dpadal on 12/12/2016.
 */
public class Discount {

    private long id;
    private Product productId;
    private DiscountType discountType;
    private int forItems;
    private BigDecimal discountValue;
    private Date startDate;
    private Date endDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public int getForItems() {
        return forItems;
    }

    public void setForItems(int forItems) {
        this.forItems = forItems;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
