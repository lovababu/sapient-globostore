package com.sapient.globostore.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by dpadal on 12/15/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter @Getter
public class CartVO {

    private String user;
    private Map<String, Integer> products;
    private int kioskId;
    private BigDecimal totalAmountPayable;
    private BigDecimal totalSavings;
}
