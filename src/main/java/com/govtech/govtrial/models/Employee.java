package com.govtech.govtrial.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Employee Model
 */
@Entity
@Table(name = "employee")
public class Employee {
    
	@Id
	@GeneratedValue
    private int id;
    private String name;
    private double salary;	// Should avoid floating point arithmetic because it cause rounding errors.
    
    public Employee() {}
    
    public Employee( int id, String name, double salary ) {
    	
    	this.id = id;
    	this.name = name;
    	this.salary = salary;
    	
    }
    
	//	Id
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    //	Name
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    //	Salary 
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
    	//	Round to 2 decimal place
    	this.salary = Math.round(salary * Math.pow(10, 2)) / Math.pow(10, 2);
    }
 
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + "]";
    }
    
}