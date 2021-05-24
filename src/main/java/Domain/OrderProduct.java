package Domain;

import java.io.Serializable;

public class OrderProduct extends Entity<Long> {
    private Long orderId;
    private Long productId;
    private Double quantity;
    private Long id;
    public OrderProduct(){
        super(0L);
    }
    public OrderProduct(Long id, Long orderId, Long productId, Double quantity) {
        super(id);
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderProduct(Long orderId, Long productId, Double quantity) {
        super(0L);
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getId(){
        return super.getId();
    }
    public void setId(Long id){
        super.setId(id);
    }
}
