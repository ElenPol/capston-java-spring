package com.example.demo.Service;

import com.example.demo.Model.Device;
import com.example.demo.Repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepo;

    public DeviceService(DeviceRepository deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    public List<Device> getDevices(){
        return deviceRepo.findAll();
    }
    public Device getDevice(String serialNumber){
        return deviceRepo.findById(serialNumber).get();
    }
    public Device addDevice(Device device){
        return deviceRepo.save(device);
    }
    public void updateDevice(Device device){
        deviceRepo.save(device);
    }
    public void deleteDevice(String serialNumber){
        deviceRepo.deleteById(serialNumber);
    }

}
