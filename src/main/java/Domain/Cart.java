package Domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Double totalPrice = 0.0;
    private List<Product> products = new ArrayList<>();

    public Cart() {
    }

    public void addProduct(Product product){
        products.add(product);
        totalPrice += product.getPrice();
    }

    public List<Product> getProducts(){
        return products;
    }

    public Double getPrice(){
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalPrice=" + totalPrice +
                ", products=" + products +
                '}';
    }
}
