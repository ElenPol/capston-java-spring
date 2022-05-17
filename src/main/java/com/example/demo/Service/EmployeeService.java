package com.example.demo.Service;

import com.example.demo.Exception.NoCompanyFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final CompanyRepository companyRepo;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo, CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> getEmployees(){
        return employeeRepo.findAll();
    }
    public Employee getEmployee(int id){
        //TODO Ecxeption for id not found
        return employeeRepo.findById(id).get();
    }
    public Employee addEmployee(Employee employee) throws Exception {
        //TODO Exception for null employee
        companyRepo.findById(employee.getCompany().getId()).orElseThrow(()
                -> new NoCompanyFoundException("No Company with ID : " + employee.getCompany().getId()));
        return employeeRepo.save(employee);
    }
    public Employee updateEmployee(Employee employee) throws Exception{
        //TODO Exception for null employee
        companyRepo.findById(employee.getCompany().getId()).orElseThrow(()
                -> new NoCompanyFoundException("No Company with ID : " + employee.getCompany().getId()));
        return employeeRepo.save(employee);
    }
    public String deleteEmployee(int id){
        //TODO Ecxeption for id not found
        employeeRepo.deleteById(id);
        return "Employee is deleted";
    }

}
