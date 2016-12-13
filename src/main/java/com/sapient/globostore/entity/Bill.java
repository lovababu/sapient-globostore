package com.sapient.globostore.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by dpadal on 12/13/2016.
 */
@Setter @Getter
public class Bill {

    private Product product;
    private int quantity;
    private BigDecimal totalAmountPayable;
    private BigDecimal totalSavings;
}
