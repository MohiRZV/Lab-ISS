package Services;

import Domain.*;
import UI.utils.observer.Observable;

import java.util.List;

public class Service extends Observable {
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;

    public Service(ProductService productService, CustomerService customerService, OrderService orderService) {
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    public Product getOne(Long id){
        return productService.getOne(id);
    }

    public void addProduct(Double quantity, String name, Double price, String producer){
        productService.addProduct(quantity, name, price, producer);
    }

    public Iterable<Product> getAllProducts() {
        return productService.getAll();
    }

    public void updateProduct(Product product) {
        productService.updateProduct(product);
    }

    private void applyDiscount(Cart cart){
        cart.getProducts().forEach(p->p.setQuantity(p.getQuantity()-p.getQuantity()*1/10));
        System.out.println("Discount applied");
    }
    public void placeOrder(Cart cart, Customer currentCustomer, String salesmanID) throws ServiceException{
        checkAvailability(cart.getProducts());
        if(checkForDiscount(currentCustomer)){
            applyDiscount(cart);
        }
        orderService.placeOrder(cart, currentCustomer, salesmanID);
        notifyObservers();
    }

    public Iterable<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    public Customer getClient(Long id) {
        return customerService.getOne(id);
    }

    public Iterable<Order> getAllOrdersByID(String id) {
        return orderService.getAllBySalesman(id);
    }

    public void cancelOrder(Order order) {
        orderService.cancelOrder(order);
        notifyObservers();
    }

    public void checkAvailability(List<Product> products) throws ServiceException {
        orderService.checkAvailability(products);
    }

    public Iterable<SalesmanDTO> getTopSalesman() {
        return orderService.getTopSalesman();
    }

    public Iterable<ClientDTO> getTopClients() {
        return orderService.getTopClients();
    }
    public Iterable<Product> getMostFrqOrderedProduct(Long id){
        return orderService.getMostFrqOrderedProduct(id);
    }
    private Long getNumberOfOrders(Long id){
        return orderService.getNumberOfOrders(id);
    }
    public boolean checkForDiscount(Customer customer){
        return getNumberOfOrders(customer.getId()) + 1 % 10 == 0;
    }
}
