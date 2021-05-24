package Services;

import Domain.Customer;
import Repo.CustomerRepoInterface;

public class CustomerService {
    private CustomerRepoInterface customerRepo;

    public CustomerService(CustomerRepoInterface customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Iterable<Customer> getAll(){
        return customerRepo.getAll();
    }

    public Customer getOne(Long id){
        return customerRepo.getOne(id);
    }
}
