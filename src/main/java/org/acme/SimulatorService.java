package org.acme;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SimulatorService {

    private static final Logger log = LoggerFactory.getLogger(SimulatorService.class);

    private volatile List<Customer> customers;

    private volatile List<Product> products;

    private final Random random = new Random();

    @Transactional
    public void simulate(int count) {
        getCustomers();
        getProducts();
        log.info("Found " + customers.size() + " customers");
        log.info("Found " + products.size() + " products");
        log.info("Generating " + count + " sales");
        for (int i = 0; i < count; i++) {
            createSale();
        }
        log.info("Complete!");
    }

    private void createSale() {
        long customer = customers.get(random.nextInt(customers.size() - 1)).customerId;
        log.info("Creating sale for customer " + customer);
        Sale sale = new Sale.Builder().customer(customer).date(new Date()).build();
        sale.persist();
        int numProducts = random.nextInt(5) + 1;
        Set<Product> productSet = new HashSet<>();
        while (productSet.size() < numProducts) {
            Product p = products.get(random.nextInt(products.size() - 1));
            productSet.add(p);
        }
        List<LineItem> lineItems = productSet.stream().map(p -> new LineItem.Builder().sale(sale.saleId)
                .product(p.productId).price(p.price).quantity(random.nextInt(3) + 1).build()).collect(Collectors.toList());
        LineItem.persist(lineItems);
    }

    private List<Customer> getCustomers() {
        List<Customer> result = customers;
        if (result == null) {
            synchronized (this) {
                if (customers == null) {
                    customers = result = Customer.listAll();
                }
            }
        }
        return result;
    }

    private List<Product> getProducts() {
        List<Product> result = products;
        if (result == null) {
            synchronized (this) {
                if (products == null) {
                    products = result = Product.listAll();
                }
            }
        }
        return result;
    }

}
