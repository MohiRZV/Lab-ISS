package Services;

import Domain.Product;
import Repo.ProductRepoInterface;

public class ProductService {
    private ProductRepoInterface productRepo;

    public ProductService(ProductRepoInterface productRepo) {
        this.productRepo = productRepo;
    }

    public Product getOne(Long id){
        return productRepo.getOne(id);
    }

    public Product addProduct(Double quantity, String name, Double price, String producer){
        Product product = new Product(quantity,name,price,producer);
        return productRepo.add(product);
    }

    public Iterable<Product> getAll() {
        return productRepo.getAll();
    }

    public void updateProduct(Product product) {
        productRepo.update(product);
    }
}
