package com.govtech.govtrial.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
 
import com.govtech.govtrial.controllers.EmployeeController;
import com.govtech.govtrial.models.Employee;
 
public class HelperEmployee {
 
    // Utility method to prepare the self link.
    public static EntityModel<Employee> getEmployeeEntityModel(Employee employee) {
        EntityModel<Employee> EntityModel = new EntityModel<Employee>(employee);
        EntityModel.add( linkTo( methodOn( EmployeeController.class ).get(employee.getId() ) )
        		.withRel("_self"));
        return EntityModel;
    }
}