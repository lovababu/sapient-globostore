package com.sapient.globostore.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dpadal on 12/12/2016.
 */
@Setter @Getter
public class Discount {

    private long id;
    private Product productId;
    private DiscountType discountType;
    private int forItems;
    private BigDecimal discountValue;
    private Date startDate;
    private Date endDate;
}
