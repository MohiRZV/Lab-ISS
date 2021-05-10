package Domain;

public class Product extends Entity<Long>{
    private Double quantity;
    private String name;
    private Double price;
    private String producer;
    private Long id;

    public Product(Long id, Double quantity, String name, Double price, String producer) {
        super(id);
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.producer = producer;
    }

    public Product(Double quantity, String name, Double price, String producer) {
        super(0L);
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.producer = producer;
    }

    public Product() {
        super(0L);
    }

    public Product(Product p){
        super(p.getId());
        this.id = p.getId();
        this.quantity = p.getQuantity();
        this.name = p.getName();
        this.price = p.getPrice();
        this.producer = p.getProducer();
    }

    public Long getId(){
        return super.getId();
    }
    public void setId(Long id){
        super.setId(id);
        this.id = id;
    }
    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "Product{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", producer='" + producer + '\'' +
                '}';
    }
}
