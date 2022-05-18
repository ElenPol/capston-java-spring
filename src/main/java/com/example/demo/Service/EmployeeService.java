package com.example.demo.Service;

import com.example.demo.Exception.*;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Employee getEmployee(int id) throws Exception {
        employeeRepo.findById(id).orElseThrow(()
                -> new NoIdFoundException("No employee with id : " + id + " found"));
        return employeeRepo.findById(id).get();
    }
    public Employee addEmployee(Employee employee) throws Exception {
        if (Optional.ofNullable(employee).isEmpty()){ throw new NullObjectException("employee is null");}
         companyRepo.findById(employee.getCompany().getId()).orElseThrow(()
                -> new NoCompanyFoundException("No Company with ID : " + employee.getCompany().getId()));
        return employeeRepo.save(employee);
    }
    public Employee updateEmployee(Employee employee) throws Exception{
        employeeRepo.findById(employee.getId()).orElseThrow(()
                -> new NoIdFoundException("No employee with id : " + employee.getId() + " found"));
       companyRepo.findById(employee.getCompany().getId()).orElseThrow(()
                -> new NoCompanyFoundException("No Company with ID : " + employee.getCompany().getId()));
        return employeeRepo.save(employee);
    }
    public String deleteEmployee(int id) throws Exception {
        employeeRepo.findById(id).orElseThrow(()
                -> new NoIdFoundException("No employee with id : " + id + " found"));
        employeeRepo.deleteById(id);
        return "Employee is deleted";
    }

}
