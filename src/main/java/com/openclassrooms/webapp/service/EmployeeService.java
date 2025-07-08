package com.openclassrooms.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.webapp.model.Employee;
import com.openclassrooms.webapp.repository.EmployeeProxy;

import lombok.Data;

@Data
@Service
public class EmployeeService {

    @Autowired
    private EmployeeProxy employeeProxy;

    public Iterable<Employee> getEmployees() {
        return employeeProxy.getEmployees();
    }

    public Employee getEmployee(final int id) {
        return employeeProxy.getEmployee(id);
    }

    public Employee saveEmployee(Employee employee) {
        Employee savedEmployee;

        employee.setLastName(employee.getLastName().toUpperCase());

        if(employee.getId() == null) {
            // Si l'id est null, on cree un nouvel employee
            savedEmployee = employeeProxy.createEmployee(employee);
        } else {
            // Sinon, on update
            savedEmployee = employeeProxy.updateEmployee(employee);
        }

        return savedEmployee;
    }

    public void deleteEmployee(final int id) {
        employeeProxy.deleteEmployee(id);
    }
}
