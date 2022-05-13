package com.example.demo.Controller;

import com.example.demo.DTO.DeviceDTO;
import com.example.demo.Exception.EmployeeMismatchCompanyException;
import com.example.demo.Mapper.DeviceMapper;
import com.example.demo.Model.Device;
import com.example.demo.Repository.DeviceRepository;
import com.example.demo.Service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceRepository deviceRepo;
    private final DeviceService deviceService;
    private final DeviceMapper deviceMap;

    @Autowired
    public DeviceController(DeviceRepository deviceRepo, DeviceService deviceService) {
        this.deviceRepo = deviceRepo;
        this.deviceMap = new DeviceMapper();
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/{serialNumber}")
    public DeviceDTO getDevice(@PathVariable("serialNumber") String serialNumber) {
        return deviceMap.convertEntityToDTO(deviceService.getDevice(serialNumber));
    }

    @GetMapping
    public ResponseEntity<Object> getDevices(){
        List<DeviceDTO> listDto = new ArrayList<>();
        deviceService.getDevices().forEach(device -> {listDto.add(deviceMap.convertEntityToDTO(device));});
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @PostMapping
    @ResponseBody
    public DeviceDTO addDevice(@RequestBody DeviceDTO newDeviceDTO) throws Exception {
        return deviceMap.convertEntityToDTO(deviceService.addDevice(deviceMap.convertDTOToEntity(newDeviceDTO)));
    }

    @PutMapping(value = "/{serialNumber}")
    public void updateDevice(@PathVariable("serialNumber") String serialNumber, @RequestBody DeviceDTO updatedDevDTO) throws Exception {
        Device updatedDev = deviceMap.convertDTOToEntity(updatedDevDTO);
        updatedDev.setSerialNumber(serialNumber);
        deviceService.updateDevice(updatedDev);
    }

    @DeleteMapping(value = "/{serialNumber}")
    public void deleteDevice(@PathVariable("serialNumber") String serialNumber) {
        deviceService.deleteDevice(serialNumber);
    }
}
