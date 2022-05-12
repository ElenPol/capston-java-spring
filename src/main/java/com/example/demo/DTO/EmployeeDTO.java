package com.example.demo.DTO;

import com.example.demo.Model.Company;
import com.example.demo.Model.Device;
import lombok.*;
import java.util.List;

@Builder
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
