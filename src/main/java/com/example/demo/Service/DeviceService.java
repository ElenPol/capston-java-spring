package com.example.demo.Service;

import com.example.demo.Exception.EmployeeMismatchCompanyException;
import com.example.demo.Exception.NoCompanyFoundException;
import com.example.demo.Exception.NoEmployeeFoundException;
import com.example.demo.Model.Device;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.DeviceRepository;
import com.example.demo.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Device getDevice(String serialNumber){
        return deviceRepo.findById(serialNumber).get();
    }
    public Device addDevice(Device device) throws Exception{
        this.checkIdsForException(device);
        return deviceRepo.save(device);
    }
    public void updateDevice(Device device) throws Exception {
        this.checkIdsForException(device);
        deviceRepo.save(device);
    }
    public void deleteDevice(String serialNumber){
        deviceRepo.deleteById(serialNumber);
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
