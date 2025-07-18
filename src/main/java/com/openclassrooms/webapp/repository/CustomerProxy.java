package com.openclassrooms.webapp.repository;

import com.openclassrooms.webapp.configuration.CustomProperties;
import com.openclassrooms.webapp.model.Customer;
import com.openclassrooms.webapp.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class CustomerProxy {

    @Autowired
    private CustomProperties customProperties;

    // Get all customers
    public Iterable<Customer> getCustomers() {
        String baseApiUrl = customProperties.getApiUrl();
        String getAllCustomersUrl = baseApiUrl + "/customers";

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Iterable<Customer>> response = restTemplate.exchange(
                    getAllCustomersUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            log.debug("Get Customers call {}", response.getStatusCode());

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Get all customers Status: {}", ex.getStatusCode());
            log.error("Get all customers Body: {}", ex.getResponseBodyAsString());
            throw ex;
        }
    }

    // Get a customer by id
    public Customer getcustomer(int id) {
        String baseApiUrl = customProperties.getApiUrl();
        String getCustomerUrl = baseApiUrl + "/customer/" + id;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Customer> response = restTemplate.exchange(
                    getCustomerUrl,
                    HttpMethod.GET,
                    null,
                    Customer.class
            );

            log.debug("Get Customer call {}", response.getStatusCode());

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Get a customer Status: {}", ex.getStatusCode());
            log.error("Get a customer Body: {}", ex.getResponseBodyAsString());
            throw ex;
        }
    }

    // Create a new customer
    public Customer createCustomer(Customer customer) {
        String baseApiUrl = customProperties.getApiUrl();
        String createCustomerUrl = baseApiUrl + "/customer";

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpEntity<Customer> request = new HttpEntity<>(customer);
            ResponseEntity<Customer> response = restTemplate.exchange(
                    createCustomerUrl,
                    HttpMethod.POST,
                    request,
                    Customer.class
            );

            log.debug("Create Customer call {}", response.getStatusCode());

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Create customer Status: {}", ex.getStatusCode());
            log.error("Create customer Body: {}", ex.getResponseBodyAsString());
            throw ex;
        }

    }

    // Delete a customer
    public void deleteCustomer(int id) {
        String baseApiUrl = customProperties.getApiUrl();
        String deleteCustomerUrl = baseApiUrl + "/customer/" + id;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    deleteCustomerUrl,
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );

            log.debug("Delete Customer call {}", response.getStatusCode());

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Delete customer Status: {}", ex.getStatusCode());
            log.error("Delete customer Body: {}", ex.getResponseBodyAsString());
            throw ex;
        }
    }

    // Update a customer
    public Customer updateCustomer(Customer customer) {
        String baseApiUrl = customProperties.getApiUrl();
        String updateCustomerUrl = baseApiUrl + "/customer/" + customer.getId();

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpEntity<Customer> request = new HttpEntity<>(customer);
            ResponseEntity<Customer> response = restTemplate.exchange(
                    updateCustomerUrl,
                    HttpMethod.PUT,
                    request,
                    Customer.class
            );
            log.debug("Update Employee call {}", response.getStatusCode());

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Update customer Status: {}", ex.getStatusCode());
            log.error("Update customer Body: {}", ex.getResponseBodyAsString());
            throw ex;
        }
    }
}
