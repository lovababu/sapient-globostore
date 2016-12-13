package com.sapient.globostore;

import com.sapient.globostore.repository.CartRepository;
import com.sapient.globostore.repository.ProductCatalogueRepository;
import com.sapient.globostore.repository.impl.CartRepositoryImpl;
import com.sapient.globostore.repository.impl.ProductCatalogueRepositoryImpl;
import com.sapient.globostore.service.CartService;
import com.sapient.globostore.service.ProductCatalogueService;
import com.sapient.globostore.service.impl.CartServiceImpl;
import com.sapient.globostore.service.impl.ProductCatalogueServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test cases/Client for the Globostore interaface.
 * Created by dpadal on 12/12/2016.
 */
@RunWith(JUnit4.class)
public class GlobostoreCheckoutTest {

    private GlobostoreCheckout globostoreCheckout;
    private ProductCatalogueService productCatalogueService;
    private ProductCatalogueRepository productCatalogueRepository;
    private CartService cartService;
    private CartRepository cartRepository;


    @Before
    public void before() {
        productCatalogueRepository = new ProductCatalogueRepositoryImpl();
        cartRepository = new CartRepositoryImpl();
        productCatalogueService = new ProductCatalogueServiceImpl(productCatalogueRepository);
        cartService = new CartServiceImpl(cartRepository, productCatalogueRepository);
        globostoreCheckout = new GlobostoreCheckout(cartService, productCatalogueService);
    }

    @Test
    public void testScan() {
        Stream.of("A", "B", "A", "C").forEach(s -> globostoreCheckout.scan(s));
        assertNotNull(globostoreCheckout.getUSER_CART());
        assertEquals(globostoreCheckout.getUSER_CART().size(), 3);
        assertEquals(globostoreCheckout.getUSER_CART().get("A").intValue(), 2);
    }

    // Cart: {"A", "B", "A", "C", "A"} --> (13 + 15 + 10)
    @Test
    public void testCheckout1() throws ScriptException {
        //adding product to cart.
        Stream.of("A", "B", "A", "C", "A").forEach(s -> globostoreCheckout.scan(s));
        assertNotNull(globostoreCheckout.getUSER_CART());
        assertEquals(globostoreCheckout.getUSER_CART().size(), 3);
        assertEquals(globostoreCheckout.getUSER_CART().get("A").intValue(), 3);

        //checking out.
        BigDecimal totalAmountPayable = globostoreCheckout.checkout();
        assertEquals(new BigDecimal(13 + 15 + 10), totalAmountPayable);
    }

    //Cart: {"A", "B", "A", "C", "A", "A", "B"} --> (18 + 25 + 10)
    @Test
    public void testCheckout2() throws ScriptException {
        //adding product to cart.
        Stream.of("A", "B", "A", "C", "A", "A", "B").forEach(s -> globostoreCheckout.scan(s));
        assertNotNull(globostoreCheckout.getUSER_CART());
        assertEquals(globostoreCheckout.getUSER_CART().size(), 3);
        assertEquals(globostoreCheckout.getUSER_CART().get("A").intValue(), 4);

        //checking out.
        BigDecimal totalAmountPayable = globostoreCheckout.checkout();
        assertEquals(new BigDecimal(18 + 25 + 10), totalAmountPayable);
    }

    //Cart: {"A", "B", "A", "A", "A"} --> (18 + 15)
    @Test
    public void testCheckout3() throws ScriptException {
        //adding product to cart.
        Stream.of("A", "B", "A", "A", "A").forEach(s -> globostoreCheckout.scan(s));
        assertNotNull(globostoreCheckout.getUSER_CART());
        assertEquals(globostoreCheckout.getUSER_CART().size(), 2);
        assertEquals(globostoreCheckout.getUSER_CART().get("A").intValue(), 4);

        //checking out.
        BigDecimal totalAmountPayable = globostoreCheckout.checkout();
        assertEquals(new BigDecimal(18 + 15), totalAmountPayable);
    }

    @After
    public void tearDown() {

    }
}
