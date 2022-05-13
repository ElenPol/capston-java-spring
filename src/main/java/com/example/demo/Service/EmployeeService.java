package com.example.demo.Service;

import com.example.demo.Exception.NoCompanyFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.EmployeeRepository;
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
        return employeeRepo.findById(id).get();
    }
    public Employee addEmployee(Employee employee) throws Exception {
        companyRepo.findById(employee.getCompany().getId()).orElseThrow(()
                -> new NoCompanyFoundException("No Company with ID : " + employee.getCompany().getId()));
        return employeeRepo.save(employee);
    }
    public void updateEmployee(Employee employee) throws Exception{
        companyRepo.findById(employee.getCompany().getId()).orElseThrow(()
                -> new NoCompanyFoundException("No Company with ID : " + employee.getCompany().getId()));
        employeeRepo.save(employee);
    }
    public void deleteEmployee(int id){
        employeeRepo.deleteById(id);
    }

}
