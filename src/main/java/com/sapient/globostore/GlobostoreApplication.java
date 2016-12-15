package com.sapient.globostore;

import com.sapient.globostore.config.GlobostoreConfiguration;
import com.sapient.globostore.exception.mapper.GlobostoreExceptionMapper;
import com.sapient.globostore.repository.CartRepository;
import com.sapient.globostore.repository.ProductCatalogueRepository;
import com.sapient.globostore.repository.impl.CartRepositoryImpl;
import com.sapient.globostore.repository.impl.ProductCatalogueRepositoryImpl;
import com.sapient.globostore.resource.GlobostoreCheckoutResource;
import com.sapient.globostore.service.CartService;
import com.sapient.globostore.service.ProductCatalogueService;
import com.sapient.globostore.service.impl.CartServiceImpl;
import com.sapient.globostore.service.impl.ProductCatalogueServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * Dropwizard bootstrapping class.
 * <p>
 * Created by dpadal on 12/15/2016.
 */
public class GlobostoreApplication extends Application<GlobostoreConfiguration> {

    @Override
    public void run(GlobostoreConfiguration globostoreConfiguration, Environment environment) throws Exception {
        final ProductCatalogueRepository productCatalogueRepository = new ProductCatalogueRepositoryImpl();
        final ProductCatalogueService productCatalogueService = new ProductCatalogueServiceImpl(productCatalogueRepository);

        final CartRepository cartRepository = new CartRepositoryImpl();
        final CartService cartService = new CartServiceImpl(cartRepository, productCatalogueRepository);

        environment.jersey().register(new GlobostoreCheckoutResource(cartService, productCatalogueService));
        environment.jersey().register(GlobostoreExceptionMapper.class);
    }

    public static void main(String[] args) throws Exception {
        new GlobostoreApplication().run(args);
    }
}
