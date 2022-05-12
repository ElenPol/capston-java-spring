package com.example.demo.Mapper;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.Model.Employee;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeMapper {

    public EmployeeDTO convertEntityToDTO(Employee e){
        return EmployeeDTO.builder()
                .name(e.getName())
                .email(e.getEmail())
                .company(e.getCompany())
                .devices(e.getDevices())
                .build();
    }

    public Employee convertDTOToEntity(EmployeeDTO eDTO){
        return Employee.builder()
                .name(eDTO.getName())
                .email(eDTO.getEmail())
                .company(eDTO.getCompany())
                .devices(eDTO.getDevices())
                .build();
    }
}
