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
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeMap = new EmployeeMapper();
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Object> getEmployees(){
        List<EmployeeDTO> listDto = new ArrayList<>();
        employeeService.getEmployees().forEach(employee -> {listDto.add(employeeMap.convertEntityToDTO(employee));});
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable("id") int id) throws Exception {
        EmployeeDTO emplDTO = employeeMap.convertEntityToDTO(employeeService.getEmployee(id));
        return ResponseEntity.status(HttpStatus.OK).body(emplDTO);
    }

    @PostMapping
    public ResponseEntity<Object> addEmployee(@RequestBody EmployeeDTO newEmployeeDTO) throws Exception {
        EmployeeDTO addedEmplDTO = employeeMap.convertEntityToDTO(employeeService.addEmployee(employeeMap.convertDTOToEntity(newEmployeeDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(addedEmplDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody EmployeeDTO updatedEmplDTO) throws Exception {
        Employee updatedEmpl = employeeMap.convertDTOToEntity(updatedEmplDTO);
        updatedEmpl.setId(id);
        EmployeeDTO returnedEmplDTO = employeeMap.convertEntityToDTO(employeeService.updateEmployee(updatedEmpl));
        return ResponseEntity.status(HttpStatus.OK).body(returnedEmplDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id) throws Exception {
        String responseMessage = employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
