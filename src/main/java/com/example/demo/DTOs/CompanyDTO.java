package com.example.demo.DTOs;

import com.example.demo.Entities.Device;
import com.example.demo.Entities.Employee;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyDTO {

    private String name;
    private String address;
    private List<Employee> employees;
    private List<Device> devices;

}
