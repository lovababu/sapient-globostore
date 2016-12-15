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
@JsonDeserialize(builder = ApiResponse.Builder.class)
@Getter
public class ApiResponse {

    private int statusCode;
    private String message;
    private CartVO cartVO;

    private ApiResponse(Builder builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.cartVO = builder.cartVO;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private int statusCode;
        private String message;
        private CartVO cartVO;

        public ApiResponse build() {
            return new ApiResponse(this);
        }

        public Builder withStatusCode(int input) {
            this.statusCode = input;
            return this;
        }

        public Builder withMessage(String input) {
            this.message = input;
            return this;
        }

        public Builder withCartVO(CartVO cartVO) {
            this.cartVO = cartVO;
            return this;
        }
    }
}
