package com.sapient.globostore.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dpadal on 12/12/2016.
 */
@Setter @Getter
public class Product {

    private long id;
    private String name;
    private String category;
    private BigDecimal unitPrice;
    private String desc;
    private boolean lock = false;
    private Date lockedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if (id != product.id) {
            return false;
        }
        if (!name.equals(product.name)) {
            return false;
        }
        return category.equals(product.category);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}
