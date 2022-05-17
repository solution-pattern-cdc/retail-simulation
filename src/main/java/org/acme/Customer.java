package org.acme;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "customer")
public class Customer extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    public long customerId;

    @Column(name = "name")
    public String name;

    @Column(name = "status")
    public String status;

    public static class Builder {

        private final Customer customer;

        public Builder() {
            customer = new Customer();
        }

        public Builder name(String name) {
            customer.name = name;
            return this;
        }

        public Builder status(String status) {
            customer.status = status;
            return this;
        }

        public Customer build() {
            return customer;
        }
    }

}
