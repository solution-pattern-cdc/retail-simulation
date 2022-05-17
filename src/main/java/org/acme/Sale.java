package org.acme;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "sale")
public class Sale extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    public long saleId;

    @Column(name = "customer_id")
    public long customerId;

    @Column(name = "date")
    public Date date;

    public static class Builder {

        private final Sale sale;

        public Builder() {
            sale = new Sale();
        }

        public Builder customer(long customer) {
            sale.customerId = customer;
            return this;
                }

        public Builder date(Date date) {
            sale.date = date;
            return this;
        }

        public Sale build() {
            return sale;
        }
    }
}
