package Domain;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Order extends Entity<Long>{
    /*
    * order mapping: many to many between order and products, on ids
    * intermediary table also has a quantity
    * */
    private Set products;

//    public Set<OrderProduct> getOrderProducts() {
//        return orderProducts;
//    }
//
//    public void setOrderProducts(Set<OrderProduct> orderProducts) {
//        this.orderProducts = orderProducts;
//    }

    //private Set<OrderProduct> orderProducts;
    private LocalDateTime date;
    private OrderStatus status;

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }

    private String salesmanID;
    private Long customerID;
    private Long id;

    public Order(){super(0L);}
    public Order(Long id, Set products, LocalDateTime date, OrderStatus status, Long customerID, String salesmanID) {
        super(id);
        this.products = products;
        this.date = date;
        this.status = status;
        this.customerID=customerID;
        this.salesmanID=salesmanID;
    }
    public Order(Long id, LocalDateTime date, OrderStatus status, Long customerID, String salesmanID) {
        super(id);
        this.date = date;
        this.status = status;
        this.customerID=customerID;
        this.salesmanID=salesmanID;
    }
    public Order(Set products, LocalDateTime date, OrderStatus status, Long customerID, String salesmanID) {
        super(0L);
        this.products = products;
        this.date = date;
        this.status = status;
        this.customerID=customerID;
        this.salesmanID=salesmanID;
    }

    public Order(LocalDateTime date, OrderStatus status, Long customerID, String salesmanID) {
        super(0L);
        this.date = date;
        this.status = status;
        this.customerID=customerID;
        this.salesmanID=salesmanID;
    }

    public Set getProducts() {
        return products;
    }

    public void setProducts(Set products) {
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

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public void setId(Long id){
        super.setId(id);
        this.id=id;
    }
    public Long getId(Long id){
        return super.getId();
    }

    @Override
    public String toString() {
        return "Order{" +
                //"products=" + products +
                ", date=" + date +
                ", status=" + status +
                ", cid=" + customerID +
                '}';
    }
}
