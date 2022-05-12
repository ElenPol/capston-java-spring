package com.example.demo.Service;

import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;

    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> getEmployees(){
        return employeeRepo.findAll();
    }
    public Employee getEmployee(int id){
        return employeeRepo.findById(id).get();
    }
    public Employee addEmployee(Employee employee){
        return employeeRepo.save(employee);
    }
    public void updateEmployee(Employee employee){
        employeeRepo.save(employee);
    }
    public void deleteEmployee(int id){
        employeeRepo.deleteById(id);
    }

}
