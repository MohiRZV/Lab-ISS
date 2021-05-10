package Services;

import Domain.Cart;
import Domain.Product;

public class Service {
    private ProductService productService;

    public Service(ProductService productService) {
        this.productService = productService;
    }

    public Product getOne(Long id){
        return productService.getOne(id);
    }

    public Product addProduct(Double quantity, String name, Double price, String producer){
        return productService.addProduct(quantity,name,price,producer);
    }

    public Iterable<Product> getAllProducts() {
        return productService.getAll();
    }

    public void updateProduct(Product product) {
        productService.updateProduct(product);
    }

    public void placeOrder(Cart cart) throws ServiceException{

    }
}
