package com.sapient.globostore.entity;

import com.sapient.globostore.enums.DiscountType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Discount discount = (Discount) o;

        if (id != discount.id) {
            return false;
        }
        if (forItems != discount.forItems) {
            return false;
        }

        if (productId == null || discount.getProductId() == null) {
            return false;
        } else if (productId.getId() != discount.getProductId().getId()){
            return false;
        }

        if (discountType != discount.discountType) {
            return false;
        }
        return discountValue.equals(discount.discountValue);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + Long.valueOf(productId.getId()).hashCode();
        result = 31 * result + discountType.hashCode();
        result = 31 * result + forItems;
        result = 31 * result + discountValue.hashCode();
        return result;
    }
}
