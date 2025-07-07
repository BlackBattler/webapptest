package com.openclassrooms.webapp.service;

import com.openclassrooms.webapp.model.Employee;
import com.openclassrooms.webapp.repository.EmployeeProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class EmployeeService {

    @Autowired
    private EmployeeProxy employeeProxy;

    // GET all employee
    public Iterable<Employee> getEmployees() {
        return employeeProxy.getEmployees();
    }

    // GET an employee by id
    public Employee getEmployee(final int id) {
        return employeeProxy.getEmployee(id);
    }

    // CREATE or UPDATE an employee
    public Employee saveEmployee(Employee employee) {
        Employee saveEmployee;

        employee.setLastName(employee.getLastName().toUpperCase());

        if(employee.getId() == null) {
            // Si l'id est null, on cree un nouvel employee
            saveEmployee = employeeProxy.createEmployee(employee);
        } else {
            // Sinon, on update
            saveEmployee = employeeProxy.updateEmployee(employee);
        }

        return saveEmployee;
    }

    // DELETE an employee
    public void deleteEmployee(final int id) {
        employeeProxy.deleteEmployee(id);
    }
}
