package com.sapient.globostore.resource;

import com.sapient.globostore.api.ApiResponse;
import com.sapient.globostore.api.CartVO;
import com.sapient.globostore.api.ProductVO;
import com.sapient.globostore.entity.Bill;
import com.sapient.globostore.entity.Product;
import com.sapient.globostore.service.CartService;
import com.sapient.globostore.service.ProductCatalogueService;
import com.sapient.globostore.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by dpadal on 12/15/2016.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class GlobostoreCheckoutResource {

    private final CartService cartService;
    private final ProductCatalogueService productCatalogueService;

    public GlobostoreCheckoutResource(CartService cartService, ProductCatalogueService productCatalogueService) {
        this.cartService = cartService;
        this.productCatalogueService = productCatalogueService;
    }

    @Path("scan")
    @POST
    public Response scan(ProductVO productVO) throws Exception {
        try {
            boolean isProductLocked = cartService.add(ObjectMapperUtil.toProduct(productVO));
            if (isProductLocked) {
                return Response.ok().entity(
                        ApiResponse.builder()
                                .withStatusCode(Response.Status.OK.getStatusCode())
                                .withMessage(productVO.getName() + " added to cart.")
                                .build()
                ).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity(
                                ApiResponse.builder()
                                        .withStatusCode(Response.Status.NOT_ACCEPTABLE.getStatusCode())
                                        .withMessage(productVO.getName() + " Not available.")
                                        .build()
                        ).build();
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new Exception(e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Path("checkout")
    @POST
    public Response checkout(CartVO cartVO) throws Exception {
        try {
            Map<String, Bill> billMap = cartService.checkOut(cartVO.getProducts());
            BigDecimal totalAmountPayable = new BigDecimal(0);
            BigDecimal totalSavings = new BigDecimal(0);
            for (Map.Entry<String, Bill> bill : billMap.entrySet()) {
                Bill b = bill.getValue();
                log.info(String.format("%s    %d : %f", b.getProduct().getName(),
                        b.getQuantity(), b.getTotalAmountPayable()));
                totalAmountPayable = totalAmountPayable.add(b.getTotalAmountPayable());
                totalSavings = totalSavings.add(b.getTotalSavings());
            }
            cartVO.setTotalAmountPayable(totalAmountPayable);
            cartVO.setTotalSavings(totalSavings);
            return Response.ok().entity(
                    ApiResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                            .withCartVO(cartVO).build()
            ).build();
        } catch (ScriptException e) {
            throw new Exception(e);
        }
    }

}
