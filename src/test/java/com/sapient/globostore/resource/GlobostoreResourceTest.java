package com.sapient.globostore.resource;

import com.sapient.globostore.GlobostoreApplication;
import com.sapient.globostore.api.ApiResponse;
import com.sapient.globostore.api.CartVO;
import com.sapient.globostore.api.ProductVO;
import com.sapient.globostore.client.GlobostoreCheckout;
import com.sapient.globostore.config.GlobostoreConfiguration;
import com.sapient.globostore.repository.CartRepository;
import com.sapient.globostore.repository.ProductCatalogueRepository;
import com.sapient.globostore.repository.impl.CartRepositoryImpl;
import com.sapient.globostore.repository.impl.ProductCatalogueRepositoryImpl;
import com.sapient.globostore.resource.GlobostoreCheckoutResource;
import com.sapient.globostore.service.CartService;
import com.sapient.globostore.service.ProductCatalogueService;
import com.sapient.globostore.service.impl.CartServiceImpl;
import com.sapient.globostore.service.impl.ProductCatalogueServiceImpl;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.script.ScriptException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test cases/Client for the Globostore interaface.
 * Created by dpadal on 12/12/2016.
 */
@RunWith(JUnit4.class)
public class GlobostoreResourceTest {

    @ClassRule
    public final static DropwizardAppRule<GlobostoreConfiguration> DROPWIZARD_APP_RULE =
            new DropwizardAppRule<>(GlobostoreApplication.class,
                    resourceFilePath("globostore-config.yml"));

    private Client client;
    private String baseURI;

    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
        baseURI = String.format("http://localhost:%d/globostore", DROPWIZARD_APP_RULE.getLocalPort());
    }

    @Test
    public void testScan() {
        ApiResponse response = client.target(UriBuilder.fromUri(baseURI).path("/scan"))
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(product("A"), MediaType.APPLICATION_JSON))
                .readEntity(ApiResponse.class);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void testCheckout() {
        Map<String, Integer> map = new HashMap<String, Integer>(){
            {
                put("A", 3);
                put("B", 1);
            }
        };

        ApiResponse response = client.target(UriBuilder.fromUri(baseURI).path("/checkout"))
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(cartVo(map), MediaType.APPLICATION_JSON))
                .readEntity(ApiResponse.class);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals(new BigDecimal(28), response.getCartVO().getTotalAmountPayable());
        assertEquals(new BigDecimal(2), response.getCartVO().getTotalSavings());
    }

    private CartVO cartVo(Map map) {
        CartVO cartVO = new CartVO();
        cartVO.setKioskId(1);
        cartVO.setProducts(map);
        return cartVO;
    }

    private ProductVO product(String a) {
        return ProductVO.builder().withName(a).withKioskId(1).build();
    }



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
    public void testScan1() {
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
