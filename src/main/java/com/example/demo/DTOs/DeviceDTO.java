package com.example.demo.DTOs;

public class DeviceDTO {
    private int id;
    private String name;
    private String type;
    private int companyId;
    private int employeeId;

    public DeviceDTO(String name, String type, int companyId, int employeeId){
        this.name = name;
        this.type = type;
        this.companyId = companyId;
        this.employeeId = employeeId;
    }

    public DeviceDTO(int id, String name, String type, int companyId, int employeeId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.companyId = companyId;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
