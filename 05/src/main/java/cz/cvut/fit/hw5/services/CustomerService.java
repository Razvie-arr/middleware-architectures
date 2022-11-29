package cz.cvut.fit.hw5.services;

import cz.cvut.fit.hw5.dto.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final List<Customer> customers = new ArrayList<Customer>();

    public Customer getCustomerById(String id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() { return customers; }

    public Customer updateCustomer(String id, Customer newCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(id)) {
                customers.set(i, newCustomer);
                return customers.get(i);
            }
        }
        return null;
    }

    public boolean deleteCustomer(String id) {
        return customers.removeIf(c -> c.getId().equals(id));
    }
}
