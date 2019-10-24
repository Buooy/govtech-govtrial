package com.govtech.govtrial.seeders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.util.Arrays;
import java.util.List;

import com.govtech.govtrial.repositories.EmployeeRepository;
import com.github.javafaker.Faker;
import com.govtech.govtrial.models.Employee;

@Component
public class DatabaseSeeder {

    private Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private EmployeeRepository employeeRepository;
    private ArrayList<Employee> employees;
    
    private int numberOfEmployees = 100;	// Number of employees to prepopulate
	private int minimumSalary = 800;
	private int maximumSalary = 10000;	
	private String folderUri = "src/main/resources/static/secrets/"; // Secrets Location
	private String fileName = "i-cant-believe-you-store-such-secrets-in-plain-text.csv"; // employee salary file name
    
    @Autowired
    public DatabaseSeeder(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedEmployeesTable();
    }
    
    private void seedEmployeesTable() {

    	boolean doesEmployeeListCSVExist = false;
		
		// Check if the employee list csv exists
		doesEmployeeListCSVExist = doesEmployeeListCSVExist();	// Uncomment this line if we want to regenerate the employee csv file
		
		if( !doesEmployeeListCSVExist ) {
			
			//	Create a list of employees
			employees = createEmployeeList();
			
			//	Load into csv
			saveEmployeeListToCSV(employees);
			
		}
		
		//	Load the employee list from CSV
		employees = loadEmployeeListFromCSV();
		
		//	Save into the database as a seed
		seedDatabase(employees);
    	
    }
    
    /**
     * Creates a new list of employees
     */
    public ArrayList<Employee> createEmployeeList() {
    	
    	ArrayList<Employee> employees = new ArrayList<Employee>();
        // To generate the dummy identity for the employees.
        Faker faker = new Faker();
        // Creating dummy employees.
        for(int employeeIndex=0; employeeIndex<numberOfEmployees; employeeIndex++) {
        	
            Employee employee = new Employee();
            employee.setName(faker.name().fullName());
            employee.setSalary(
            	faker.number().randomDouble(2, minimumSalary, maximumSalary)
            );
            
            // Adding the employee records to the list.
            employees.add(employee);
        }
        
        return employees;
        
    }
    
    /**
     * Checks if the employee list csv exists
     * 
     * @param none
     * @return boolean
     * 
     */
    public boolean doesEmployeeListCSVExist() {

    	final String pathFile = folderUri+fileName; 
    	
        try {
        	File file = new File( pathFile );
        	return file.exists();
        } catch (Exception e){
           e.printStackTrace();
        }
        
        return false;
    	
    }
   
    /**
     * Saves a list of employees to a designated folder-file
     * 
     * @param employees
     * @return boolean
     */
    public boolean saveEmployeeListToCSV( List<Employee> employees ){
    	
    	final String pathFile = folderUri+fileName; 
    	final String[] CSV_HEADER = { "id", "name", "salary" };
		
		FileWriter fileWriter = null;
		CSVPrinter csvPrinter = null;
 
		try {
			fileWriter = new FileWriter(pathFile);
			csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(CSV_HEADER));
 
			for (Employee employee : employees) {
				List<Object> data = Arrays.asList(
						employee.getId(),
						employee.getName(),
						employee.getSalary());
				csvPrinter.printRecord(data);
			}
		} catch (Exception e) {
			logger.error("Writing CSV error!");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
				csvPrinter.close();
			} catch (Exception e) {
				logger.error("Flushing/closing error!");
				e.printStackTrace();
			}
			
		}
    	
    	
    	return true;
    }
    
    /**
     * 	Reads a csv and adds a list of employees to stream
     *
     * @param void
     * @return employees
     *
     */
    public ArrayList<Employee> loadEmployeeListFromCSV(){
    	
    	final String pathFile = folderUri+fileName; 
    	
    	BufferedReader fileReader = null;
		CSVParser csvParser = null;

    	ArrayList<Employee> employees = new ArrayList<Employee>();
		
		try {
			fileReader = new BufferedReader(new FileReader(pathFile));
			csvParser = new CSVParser(fileReader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
 
			List<CSVRecord> csvRecords = csvParser.getRecords();
			
			for (CSVRecord csvRecord : csvRecords) {
				Employee employee = new Employee(
						Integer.parseInt(csvRecord.get("id")),
						csvRecord.get("name"),
						Double.parseDouble(csvRecord.get("salary"))  
						);
				
				employees.add(employee);
			}
 
		} catch (Exception e) {
			logger.error("Reading CSV Error!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				csvParser.close();
			} catch (IOException e) {
				logger.error("Closing fileReader/csvParser Error!");
				e.printStackTrace();
			}
		}
		
		return employees;
    	
    }
    
    /**
     * 	Reads a csv and adds a list of employees to stream
     *
     * @param void
     * @return employees
     *
     */
    public void seedDatabase( ArrayList<Employee> employees ) {
    	
    	try {
    		employeeRepository.saveAll(employees);
    	} catch (Exception e) {
			logger.error("Error saving to database");
			e.printStackTrace();
    	}
    	
    }
}