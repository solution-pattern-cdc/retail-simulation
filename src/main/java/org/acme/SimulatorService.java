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
    public String simulate(Long customer, Integer count) {
        getCustomers();
        getProducts();
        log.info("Found " + customers.size() + " customers");
        log.info("Found " + products.size() + " products");
        if (count == null) {
            count = 0;
        }
        long customerId;
        if (customer == null) {
            customerId = randomCustomer();
        } else {
            customerId = customers.stream().map(c -> c.customerId).filter(l -> l.equals(customer)).findFirst().orElseGet(this::randomCustomer);
        }
        log.info("Generating " + count + " sales for customer " + customerId);
        for (int i = 0; i < count; i++) {
            createSale(customerId);
        }
        log.info("Complete!");
        return "Generated " + count + " sales for customer " + customerId;
    }

    private long randomCustomer() {
        return customers.get(random.nextInt(customers.size() - 1)).customerId;
    }

    private void createSale(long customer) {
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
