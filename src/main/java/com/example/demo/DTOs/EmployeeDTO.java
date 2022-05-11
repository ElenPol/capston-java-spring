package com.example.demo.DTOs;

import com.example.demo.Entities.Company;
import com.example.demo.Entities.Device;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {

    private String name;
    private String email;
    private Company company;
    private List<Device> devices;

}
