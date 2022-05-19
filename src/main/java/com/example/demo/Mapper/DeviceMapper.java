package com.example.demo.Mapper;

import com.example.demo.DTO.DeviceDTO;
import com.example.demo.Model.Device;

public class DeviceMapper {

    public DeviceDTO convertEntityToDTO(Device d){
        return DeviceDTO.builder()
                .name(d.getName())
                .type(d.getType())
                .company(d.getCompany())
                .employee(d.getEmployee())
                .build();
    }

    public Device convertDTOToEntity(DeviceDTO dDTO){
        return Device.builder()
                .name(dDTO.getName())
                .type(dDTO.getType())
                .company(dDTO.getCompany())
                .employee(dDTO.getEmployee())
                .build();
    }

}
