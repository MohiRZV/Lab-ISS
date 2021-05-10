package Domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order extends Entity<Long>{
    private List<Product> products;
    private LocalDateTime date;
    private OrderStatus status;

    public Order(Long id, List<Product> products, LocalDateTime date, OrderStatus status) {
        super(id);
        this.products = products;
        this.date = date;
        this.status = status;
    }
    public Order(List<Product> products, LocalDateTime date, OrderStatus status) {
        super(0L);
        this.products = products;
        this.date = date;
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
