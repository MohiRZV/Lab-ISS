package Repo;

import Domain.ClientDTO;
import Domain.Order;
import Domain.Product;
import Domain.SalesmanDTO;

public interface OrderRepoInterface extends Repository<Long, Order> {
    default Iterable<Order> getAllBySalesman(String id){return null;}
    default Iterable<SalesmanDTO> getTopSalesman() {return null;}
    default Iterable<ClientDTO> getTopClients(){return null;}
    default Iterable<Product> getMostFrqOrderedProducts(Long clientID){return null;}
    default Long getNumberOfOrders(Long clientID){return null;}
}
