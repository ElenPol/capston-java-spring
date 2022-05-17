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
    private final DeviceService deviceService;
    private final DeviceMapper deviceMap;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceMap = new DeviceMapper();
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/{serialNumber}")
    public ResponseEntity<Object> getDevice(@PathVariable("serialNumber") String serialNumber) {
        DeviceDTO devDTO = deviceMap.convertEntityToDTO(deviceService.getDevice(serialNumber));
        return ResponseEntity.status(HttpStatus.OK).body(devDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getDevices(){
        List<DeviceDTO> listDto = new ArrayList<>();
        deviceService.getDevices().forEach(device -> {listDto.add(deviceMap.convertEntityToDTO(device));});
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> addDevice(@RequestBody DeviceDTO newDeviceDTO) throws Exception {
        DeviceDTO addedDevDTO = deviceMap.convertEntityToDTO(deviceService.addDevice(deviceMap.convertDTOToEntity(newDeviceDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(addedDevDTO);
    }

    @PutMapping(value = "/{serialNumber}")
    public ResponseEntity<Object> updateDevice(@PathVariable("serialNumber") String serialNumber, @RequestBody DeviceDTO updatedDevDTO) throws Exception {
        Device updatedDev = deviceMap.convertDTOToEntity(updatedDevDTO);
        updatedDev.setSerialNumber(serialNumber);
        return ResponseEntity.status(HttpStatus.OK).body(deviceMap.convertEntityToDTO(deviceService.updateDevice(updatedDev)));
    }

    @DeleteMapping(value = "/{serialNumber}")
    public ResponseEntity<Object> deleteDevice(@PathVariable("serialNumber") String serialNumber) {
        String responseMessage = deviceService.deleteDevice(serialNumber);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
