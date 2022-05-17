package org.acme;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "line_item")
public class LineItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    public long lineItemId;

    @Column(name = "sale_id")
    public long sale;

    @Column(name = "product_id")
    public long product;

    @Column(name = "price")
    public BigDecimal price;

    @Column(name = "quantity")
    public int quantity;

    public static class Builder {

        private final LineItem lineItem;

        public Builder() {
            lineItem = new LineItem();
        }

        public Builder sale(long sale) {
            lineItem.sale = sale;
            return this;
        }

        public Builder product(long product) {
            lineItem.product = product;
            return this;
        }

        public Builder price(BigDecimal price) {
            lineItem.price = price;
            return this;
        }

        public Builder quantity(int quantity) {
            lineItem.quantity = quantity;
            return this;
        }

        public LineItem build() {
            return lineItem;
        }

    }
}
