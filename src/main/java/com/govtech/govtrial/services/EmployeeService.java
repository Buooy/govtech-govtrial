package com.govtech.govtrial.services;
 
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
 
import com.govtech.govtrial.repositories.EmployeeRepository;
import com.govtech.govtrial.models.Employee;
 
@Service
public class EmployeeService {
 
    @Autowired
    EmployeeRepository employeeRepository;
 
    // Service method to fetch employee details from the repository.
    public Optional<Employee> get(int id) {
    	return employeeRepository.findById(id);
    }
 
    // Service method to fetch the employee list.
    public PageResource<Employee> find( int page, int limit, Double minimumSalary, Double maximumSalary ) {
    	
    	Pageable pageable = PageRequest.of(
	      page,
	      limit
	    );
        Page<Employee> employeeResult = employeeRepository.findBySalary(minimumSalary, maximumSalary, pageable);
        
        return new PageResource<Employee>(
        		employeeResult,
        		"page",
        		"limit",
        		"minimumSalary",
        		minimumSalary,
        		"maximumSalary",
        		maximumSalary
        	);
        
    }
}