package com.example.demo.DTO;

import com.example.demo.Model.Device;
import com.example.demo.Model.Employee;
import lombok.*;
import java.util.List;

@Builder
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
