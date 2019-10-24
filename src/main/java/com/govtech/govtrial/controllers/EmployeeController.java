package com.govtech.govtrial.controllers;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
 
import com.govtech.govtrial.models.Employee;
import com.govtech.govtrial.services.EmployeeService;
import com.govtech.govtrial.services.PageResource;
 
@RestController
@RequestMapping(
		value= "/employees", 
		produces = "application/json"
	)
public class EmployeeController {
 
    @Autowired
    EmployeeService employeeService;
 
    // Fetch employee details on the basis of employee id.
    @GetMapping(value= "/{id}")
    public EntityModel<Employee> get(@PathVariable(name= "id") int id) {
 
        // Fetching employee details from the mocked service.
        Employee employee = employeeService.get(id).get();
	 
	        // Creating links as per the hateoas principle.
	    EntityModel<Employee> employeeSingle = new EntityModel<Employee>(employee);
	    employeeSingle.add(linkTo(methodOn(EmployeeController.class).get(id))
	    		.withRel("_self"));
	    
	    return employeeSingle;
        
    }
    
    // Fetch all employees.
    @GetMapping(value= "")
    public PageResource<Employee> getAllEmployees(
	    		@RequestParam(defaultValue = "0") Integer page,
	            @RequestParam(defaultValue = "10") Integer size,
	            @RequestParam(required=false) Double minimumSalary,
	            @RequestParam(required=false) Double maximumSalary
    		) {
    	
        Page<Employee> pageResult = employeeService.find( page, size, minimumSalary, maximumSalary );
        
        return new PageResource<Employee>(
        		pageResult,
        		"page",
        		"size",
        		"minimumSalary",
        		minimumSalary,
        		"maximumSalary",
        		maximumSalary
        	);
    }
 
}