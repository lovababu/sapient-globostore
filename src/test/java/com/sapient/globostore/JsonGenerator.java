package com.sapient.globostore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.globostore.api.CartVO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dpadal on 12/15/2016.
 */
public class JsonGenerator {

    public static void main(String[] args) throws IOException {
        CartVO cartVO = new CartVO();
        Map<String, Integer> map = new HashMap<String, Integer>(){
            {
                put("A", 3);
                put("B", 1);
            }
        };

        cartVO.setKioskId(1);
        cartVO.setProducts(map);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(System.out, cartVO);
    }
}
