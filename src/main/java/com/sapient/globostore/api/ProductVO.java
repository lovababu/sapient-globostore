package com.sapient.globostore.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;

/**
 * Created by dpadal on 12/15/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = ProductVO.Builder.class)
@Getter
public class ProductVO {

    private long id;
    private String name;
    private int quantity;
    private int kioskId;

    private ProductVO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.kioskId = builder.kioskId;
        this.quantity = builder.quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private long id;
        private String name;
        private int quantity;
        private int kioskId;

        public ProductVO build() {
            return new ProductVO(this);
        }

        public Builder withId(long input) {
            this.id = input;
            return this;
        }

        public Builder withName(String input) {
            this.name = input;
            return this;
        }

        public Builder withKioskId(int input) {
            this.kioskId = input;
            return this;
        }

        public Builder withQuantity(int input) {
            this.quantity = input;
            return this;
        }
    }
}
