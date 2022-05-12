package com.example.demo.Controller;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.Mapper.EmployeeMapper;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeMapper employeeMap;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepo;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
        this.employeeMap = new EmployeeMapper();
        this.employeeService = new EmployeeService(this.employeeRepo);
    }

    @GetMapping
    public ResponseEntity<Object> getEmployees(){
        List<EmployeeDTO> listDto = new ArrayList<>();
        employeeService.getEmployees().forEach(employee -> {listDto.add(employeeMap.convertEntityToDTO(employee));});
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @GetMapping(value = "/{id}")
    public EmployeeDTO getEmployee(@PathVariable("id") int id) {
        return employeeMap.convertEntityToDTO(employeeService.getEmployee(id));
    }

    @PostMapping
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO newEmployeeDTO) {
        return employeeMap.convertEntityToDTO(employeeService.addEmployee(employeeMap.convertDTOToEntity(newEmployeeDTO)));
    }

    @PutMapping(value = "/{id}")
    public void updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeDTO updatedEmplDTO) {
        Employee updatedEmpl = employeeMap.convertDTOToEntity(updatedEmplDTO);
        updatedEmpl.setId(id);
        employeeService.updateEmployee(updatedEmpl);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
    }

}
