package Services;

import Domain.*;
import Repo.OrderRepo;
import Repo.OrderRepoInterface;
import Repo.ProductRepo;
import Repo.ProductRepoInterface;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderService {
    private OrderRepoInterface orderRepo;
    private ProductRepoInterface productRepo;

    public OrderService(OrderRepoInterface orderRepo, ProductRepoInterface productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }


    public void placeOrder(Cart cart, Customer currentCustomer, String salesmanID) {
        Set<Product> set = new HashSet<>(cart.getProducts());
        Order order = new Order(set, LocalDateTime.now(), OrderStatus.Processing, currentCustomer.getId(), salesmanID);
        orderRepo.add(order);
        updateProductQuantities(cart.getProducts());
    }

    private void updateProductQuantities(List<Product> products) {
        for(Product p : products){
            Product dbProduct = productRepo.getOne(p.getId());
            dbProduct.setQuantity(dbProduct.getQuantity() - p.getQuantity());
            productRepo.update(dbProduct);
        }
    }

    public Iterable<Order> getAll() {
        return orderRepo.getAll();
    }

    public Iterable<Order> getAllBySalesman(String id) {
        return orderRepo.getAllBySalesman(id);
    }

    public void cancelOrder(Order order) {
        orderRepo.delete(order.getId());
    }


    public void checkAvailability(List<Product> products) throws ServiceException {
        for(Product product : products){
            Product dbProduct = productRepo.getOne(product.getId());
            if(dbProduct.getQuantity()<product.getQuantity())
                throw new ServiceException("Produsul "+product.getName()+" nu este disponibil in cantitatea dorita!");
        }
    }

    public Iterable<SalesmanDTO> getTopSalesman() {
        return orderRepo.getTopSalesman();
    }

    public Iterable<ClientDTO> getTopClients() {
        return orderRepo.getTopClients();
    }

    public Iterable<Product> getMostFrqOrderedProduct(Long id){
        return orderRepo.getMostFrqOrderedProducts(id);
    }

    public Long getNumberOfOrders(Long clientId){
        return orderRepo.getNumberOfOrders(clientId);
    }
}
