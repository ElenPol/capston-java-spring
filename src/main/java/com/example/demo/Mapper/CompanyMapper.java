package com.example.demo.Mapper;

import com.example.demo.DTO.CompanyDTO;
import com.example.demo.Model.Company;

public class CompanyMapper {

    private CompanyDTO convertEntityToDTO(Company c){
        return CompanyDTO.builder()
                .name(c.getName())
                .address(c.getAddress())
                .employees(c.getEmployees())
                .devices(c.getDevices())
                .build();
    }

    private Company convertDTOToEntity(CompanyDTO cDTO){
        return Company.builder()
                .name(cDTO.getName())
                .address(cDTO.getAddress())
                .employees(cDTO.getEmployees())
                .devices(cDTO.getDevices())
                .build();
    }
}
