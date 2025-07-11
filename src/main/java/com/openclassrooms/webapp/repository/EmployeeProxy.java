package com.openclassrooms.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.webapp.configuration.CustomProperties;
import com.openclassrooms.webapp.model.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmployeeProxy {

    @Autowired
    private CustomProperties customProperties;

    // Get all employees
    public Iterable<Employee> getEmployees() {
        String baseApiUrl = customProperties.getApiUrl();
        String getEmployeesUrl = baseApiUrl + "/employees";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Employee>> response = restTemplate.exchange(
                getEmployeesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        log.debug("Get Employees call {}", response.getStatusCode());

        return response.getBody();
    }

    // Get an employee by id
    public Employee getEmployee(int id) {
        String baseApiUrl = customProperties.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/employee/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.exchange(
                getEmployeeUrl,
                HttpMethod.GET,
                null,
                Employee.class
        );

        log.debug("Get Employee call {}", response.getStatusCode());

        return response.getBody();
    }

    // Create a new employee
    public Employee createEmployee(Employee e) {
        String baseApiUrl = customProperties.getApiUrl();
        String createEmployeeUrl = baseApiUrl + "/employee";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Employee> request = new HttpEntity<>(e);
        ResponseEntity<Employee> response = restTemplate.exchange(
                createEmployeeUrl,
                HttpMethod.POST,
                request,
                Employee.class
        );

        log.debug("Create Employee call {}", response.getStatusCode());

        return  response.getBody();
    }

    // Update an employee
    public Employee updateEmployee(Employee e) {
        String baseApiUrl = customProperties.getApiUrl();
        String updateEmployeeUrl = baseApiUrl + "/employee/" + e.getId();

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpEntity<Employee> request = new HttpEntity<>(e);
            ResponseEntity<Employee> response = restTemplate.exchange(
                    updateEmployeeUrl,
                    HttpMethod.PUT,
                    request,
                    Employee.class
            );
            log.debug("Update Employee call {}", response.getStatusCode());

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Updating employee Status: {}", ex.getStatusCode());
            log.error("Updating employee Body: {}", ex.getResponseBodyAsString());
            throw ex;
            }
    }

    // Delete an employee
    public void deleteEmployee(int id) {
        String baseApiUrl = customProperties.getApiUrl();
        String deleteEmployeeUrl = baseApiUrl + "/employee/" + id;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    deleteEmployeeUrl,
                    HttpMethod.DELETE,
                    null,
                    Void.class);

            log.debug("Delete Employee call {}", response.getStatusCode());

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Deleting employee Status: {}", ex.getStatusCode());
            log.error("Deleting employee Body: {}", ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting employee", ex);
        }
    }
}
