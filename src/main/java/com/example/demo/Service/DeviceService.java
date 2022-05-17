package com.example.demo.Service;

import com.example.demo.Exception.*;
import com.example.demo.Model.Device;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepo;
    private final EmployeeRepository employeeRepo;
    private final CompanyRepository companyRepo;
    @Autowired
    public DeviceService(DeviceRepository deviceRepo, EmployeeRepository employeeRepo, CompanyRepository companyRepo) {
        this.deviceRepo = deviceRepo;
        this.employeeRepo = employeeRepo;
        this.companyRepo = companyRepo;
    }

    public List<Device> getDevices(){
        return deviceRepo.findAll();
    }
    public Device getDevice(String serialNumber) throws Exception {
        deviceRepo.findById(serialNumber).orElseThrow(()
                -> new NoSerialnumberFoundException("No device with serialNumber : " + serialNumber + " found"));
        return deviceRepo.findById(serialNumber).get();
    }
    public Device addDevice(Device device) throws Exception{
        if (Optional.ofNullable(device).isPresent()){ throw new NullObjectException("device is null");}
        this.checkIdsForException(device);
        return deviceRepo.save(device);
    }
    public Device updateDevice(Device device) throws Exception {
        deviceRepo.findById(device.getSerialNumber()).orElseThrow(()
                -> new NoSerialnumberFoundException("No device with serialNumber : " + device.getSerialNumber() + " found"));
        this.checkIdsForException(device);
        return deviceRepo.save(device);
    }
    public String deleteDevice(String serialNumber) throws Exception {
        deviceRepo.findById(serialNumber).orElseThrow(()
                -> new NoSerialnumberFoundException("No device with serialNumber : " + serialNumber + " found"));
        deviceRepo.deleteById(serialNumber);
        return "Device is deleted";
    }

    private void checkIdsForException(Device device) throws Exception {
        companyRepo.findById(device.getCompany().getId()).orElseThrow(()
                -> new NoCompanyFoundException("No Company with ID : " + device.getCompany().getId()));
        employeeRepo.findById(device.getEmployee().getId()).orElseThrow(()
                -> new NoEmployeeFoundException("No Employee with ID : " + device.getEmployee().getId()));
        if (device.getCompany().getId() != employeeRepo.findById(device.getEmployee().getId()).get().getCompany().getId()){
            throw new EmployeeMismatchCompanyException("No employee with id: " + device.getEmployee().getId() +
                    " in company: "+ device.getCompany().getName());
        }
    }

}
