package com.govtech.govtrial.repositories;

import java.util.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.govtech.govtrial.models.Employee;
 
@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {
	
	@Query("SELECT employee FROM Employee employee "
			+ "WHERE employee.salary >= isnull(:minimumSalary, 0) "
			+ "AND employee.salary <= isnull(:maximumSalary, 999999)")
	Page<Employee> findBySalary( @Param("minimumSalary") Double minimumSalary,
	                             @Param("maximumSalary") Double maximumSalary,
	                             Pageable pageable);
	
}