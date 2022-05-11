package com.example.demo.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "serial_numer", updatable = false)
    private String serialNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false,
            referencedColumnName = "id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false,
            referencedColumnName = "id")
    private Employee employee;

    public Device() {}

    public Device(String serialNumber, String name, String type, Company company, Employee employee) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.type = type;
        this.company = company;
        this.employee = employee;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
