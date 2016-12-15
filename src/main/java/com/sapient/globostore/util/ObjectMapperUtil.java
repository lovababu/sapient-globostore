package com.sapient.globostore.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.globostore.api.ProductVO;
import com.sapient.globostore.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by dpadal on 12/15/2016.
 */
@Slf4j
public class ObjectMapperUtil {

    public static Product toProduct(ProductVO productVO) throws InvocationTargetException, IllegalAccessException {
        Product product = new Product();
        BeanUtils.copyProperties(product, productVO);
        return product;
    }

    public static ProductVO toProductVO(Product product) {
        ProductVO productVO = null;
        if (product != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                productVO = mapper.readValue(mapper.writeValueAsString(product), ProductVO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            log.error("unable to parse the object that is NULL");
        }
        return productVO;
    }
}
