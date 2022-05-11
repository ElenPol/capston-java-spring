package com.example.demo.DTOs;

import com.example.demo.Entities.Company;
import com.example.demo.Entities.Employee;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceDTO {

    private String name;
    private String type;
    private Company company;
    private Employee employee;

}
