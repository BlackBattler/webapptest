package com.openclassrooms.webapp.controller;


import com.openclassrooms.webapp.model.Customer;
import com.openclassrooms.webapp.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.webapp.service.CustomerService;

import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/gestionCustomer")
    public String gestionCustomer(Model model) {
        Iterable<Customer> listCustomer = customerService.getCustomers();
        model.addAttribute("customers", listCustomer);
        return "gestionCustomer";
    }

    @GetMapping("/createCustomer")
    public String createCustomer(Model model) {
        Customer c = new Customer();
        model.addAttribute("customer", c);
        return "formCreateCustomer";
    }

    @GetMapping("/updateCustomer/{id}")
    public String updateCustomer(@PathVariable("id") final int id, Model model) {
        Customer c = customerService.getCustomer(id);
        model.addAttribute("customer", c);
        return "formUpdateCustomer";
    }

    @GetMapping("/deleteCustomer/{id}")
    public ModelAndView deleteCustomer(@PathVariable("id") final int id) {
        customerService.deleteCustomer(id);
        return new ModelAndView("redirect:/gestionCustomer");
    }

    @PostMapping("/saveCustomer")
    public ModelAndView saveCustomer(@ModelAttribute Customer customer) {
        if(customer.getId() != null) {
            Customer current = customerService.getCustomer(customer.getId());
        }
        customerService.saveCustomer(customer);
        return new ModelAndView("redirect:/gestionCustomer");
    }
}
