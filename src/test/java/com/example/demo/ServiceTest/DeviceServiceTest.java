package com.example.demo.ServiceTest;

import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import com.example.demo.Service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeviceServiceTest {

    private DeviceRepository deviceRepository;
    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;
    private DeviceService deviceService;
    private Device dev;
    private Employee deviceEmployee;
    private Optional<Employee> optDeviceEmployee;
    private Company deviceCompany;
    private Optional<Company> optDeviceCompany;
    private List<Device> deviceList;

    @BeforeEach
    public void setUp() {
        deviceRepository = Mockito.mock(DeviceRepository.class);
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        companyRepository = Mockito.mock(CompanyRepository.class);
        deviceService = new DeviceService(deviceRepository, employeeRepository, companyRepository);
        this.deviceList = new ArrayList<>();
        this.dev = new Device();
        this.dev.setSerialNumber("abcde12345");
        this.dev.setName("Xiaomi 11t");
        this.dev.setType("mobile");
        this.deviceCompany = new Company();
        this.deviceCompany.setId(1);
        this.deviceCompany.setName("Onelity");
        this.deviceCompany.setAddress("G&G");
        this.dev.setCompany(this.deviceCompany);
        this.deviceEmployee = new Employee();
        this.deviceEmployee.setId(1);
        this.deviceEmployee.setName("Giorgos Papadopoulos");
        this.deviceEmployee.setEmail("giorgos.papadopoulos@gmail.com");
        this.deviceEmployee.setCompany(this.deviceCompany);
        this.dev.setEmployee(this.deviceEmployee);
        deviceList.add(this.dev);
        this.optDeviceEmployee = Optional.of(this.deviceEmployee);
        this.optDeviceCompany = Optional.of(this.deviceCompany);
        Device device1 = new Device();
        device1.setSerialNumber("abcde12347");
        device1.setName("Dell Vostro");
        device1.setType("laptop");
        deviceList.add(device1);
        Device device2 = new Device();
        device2.setSerialNumber("abcde12346");
        device2.setName("iPad 10");
        device2.setType("tablet");
        deviceList.add(device2);
    }

    @Test
    public void GivenGetAllDevicesShouldReturnListOfAllDevices(){
        deviceRepository.save(this.dev);
        when(deviceRepository.findAll()).thenReturn(this.deviceList);
        List<Device> deviceListFromService = deviceService.getDevices();
        assertEquals(deviceListFromService, this.deviceList);
        verify(deviceRepository,times(1)).save(this.dev);
        verify(deviceRepository,times(1)).findAll();
    }

    @Test
    public void givenSerialnumberThenShouldReturnDeviceOfThatSerialnumber() throws Exception {
        Mockito.when(deviceRepository.findById("abcde12345")).thenReturn(Optional.ofNullable(this.dev));
        assertThat(deviceService.getDevice(this.dev.getSerialNumber())).isEqualTo(this.dev);
    }

    @Test
    void givenDeviceToAddShouldReturnAddedDevice() throws Exception {
        Mockito.when(deviceRepository.save(any())).thenReturn(this.dev);
        Mockito.when(companyRepository.findById(1)).thenReturn(this.optDeviceCompany);
        Mockito.when(employeeRepository.findById(1)).thenReturn(this.optDeviceEmployee);
        deviceService.addDevice(this.dev);
        verify(deviceRepository,times(1)).save(any());

    }

    @Test
    void givenDeviceToUpdateShouldReturnUpdatedDevice() throws Exception {
        Mockito.when(deviceRepository.save(any())).thenReturn(this.dev);
        Mockito.when(deviceRepository.findById("abcde12345")).thenReturn(Optional.ofNullable(this.dev));
        Mockito.when(companyRepository.findById(1)).thenReturn(this.optDeviceCompany);
        Mockito.when(employeeRepository.findById(1)).thenReturn(this.optDeviceEmployee);
        deviceService.updateDevice(this.dev);
        verify(deviceRepository,times(1)).save(any());
    }

    @Test
    public void givenSerialnumberToDeleteThenShouldDeleteTheDevice() throws Exception {
        Mockito.when(deviceRepository.findById("abcde12345")).thenReturn(Optional.ofNullable(this.dev));
        String result = deviceService.deleteDevice(this.dev.getSerialNumber());
        assertEquals(result, "Device is deleted");
    }
}
