package com.openclassrooms.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.webapp.model.Employee;
import com.openclassrooms.webapp.service.EmployeeService;

import lombok.Data;

@Data
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Employee> listEmployee = employeeService.getEmployees();
        model.addAttribute("employees", listEmployee);
        return "home";
    }

    @GetMapping("/createEmployee")
    public String createEmployee(Model model) {
        Employee e = new Employee();
        model.addAttribute("employee", e);
        return "formNewEmployee";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") final int id, Model model) {
        Employee e = employeeService.getEmployee(id);
        model.addAttribute("employee", e);
        return "formUpdateEmployee";
    }

    @GetMapping("/deleteEmployee/{id}")
    public ModelAndView deleteEmployee(@PathVariable("id") final int id) {
        employeeService.deleteEmployee(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveEmployee")
    public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
        if(employee.getId() != null) {
            // Employee from update form has the password field not filled,
            // so we fill it with the current password.
            Employee current = employeeService.getEmployee(employee.getId());
            employee.setPassword(current.getPassword());
        }
        employeeService.saveEmployee(employee);
        return new ModelAndView("redirect:/");
    }
}
