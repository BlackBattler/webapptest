package com.openclassrooms.webapp.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.webapp.model.Customer;
import com.openclassrooms.webapp.repository.CustomerProxy;

import lombok.Data;

@Data
@Service
public class CustomerService {

    @Autowired
    private CustomerProxy customerProxy;

    public Iterable<Customer> getCustomers() {
        return customerProxy.getCustomers();
    }

    public Customer getCustomer(final int id) {
        return customerProxy.getcustomer(id);
    }

    public Customer saveCustomer(Customer customer) {
        Customer saveCustomer;

        customer.setLastName(customer.getLastName().toUpperCase());

        if(customer.getId() == null) {
            // Si l'id est null, on cree un nouvel employee
            saveCustomer = customerProxy.createCustomer(customer);
        } else {
            // Sinon, on update
            saveCustomer = customerProxy.updateCustomer(customer);
        }

        return saveCustomer;
    }

    public void deleteCustomer(final int id) {
        customerProxy.deleteCustomer(id);
    }
}
