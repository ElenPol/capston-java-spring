package com.example.demo.DTO;

import com.example.demo.Model.Company;
import com.example.demo.Model.Employee;
import lombok.*;

@Builder
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
