package com.sapient.globostore;

import com.sapient.globostore.client.GlobostoreCheckout;
import com.sapient.globostore.entity.Product;
import com.sapient.globostore.repository.CartRepository;
import com.sapient.globostore.repository.ProductCatalogueRepository;
import com.sapient.globostore.repository.impl.CartRepositoryImpl;
import com.sapient.globostore.repository.impl.ProductCatalogueRepositoryImpl;
import com.sapient.globostore.service.CartService;
import com.sapient.globostore.service.ProductCatalogueService;
import com.sapient.globostore.service.impl.CartServiceImpl;
import com.sapient.globostore.service.impl.ProductCatalogueServiceImpl;

import javax.script.ScriptException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by dpadal on 12/13/2016.
 */
public class InteractiveTest {

    private static GlobostoreCheckout globostoreCheckout;
    private static ProductCatalogueService productCatalogueService;
    private static ProductCatalogueRepository productCatalogueRepository;
    private static CartService cartService;
    private static CartRepository cartRepository;

    public static void main(String[] args) throws ScriptException {
        productCatalogueRepository = new ProductCatalogueRepositoryImpl();
        cartRepository = new CartRepositoryImpl();
        productCatalogueService = new ProductCatalogueServiceImpl(productCatalogueRepository);
        cartService = new CartServiceImpl(cartRepository, productCatalogueRepository);
        globostoreCheckout = new GlobostoreCheckout(cartService, productCatalogueService);

        Scanner scanner = new Scanner(System.in);
        Map<Long, Product> productMap = globostoreCheckout.fetchAll();
        System.out.print("Enter Product Name {");
        productMap.entrySet().stream().forEach(longProductEntry -> System.out.print("\"" + longProductEntry.getValue().getName() + "\" "));
        System.out.print("}");
        System.out.println();
        while (scanner.hasNextLine()) {
            String product = scanner.nextLine();
            if (product.isEmpty()) {
                break;
            }
            if (globostoreCheckout.scan(product)) {
                System.out.println(product + " added to cart.");
            }

        }

        System.out.println("Total Payable : " + globostoreCheckout.checkout());
    }
}
