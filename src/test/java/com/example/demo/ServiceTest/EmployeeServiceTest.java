package com.example.demo.ServiceTest;

import com.example.demo.Model.Company;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;
    private EmployeeService employeeService;
    private Employee empl;
    private  Company employeeCompany;
    private Optional<Company> optEmployeeCompany;
    private List<Employee> employeeList;

    @BeforeEach
    public void setUp(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        companyRepository = Mockito.mock(CompanyRepository.class);
        employeeService = new EmployeeService(employeeRepository, companyRepository);
        this.employeeList = new ArrayList<>();
        this.empl = new Employee();
        this.employeeCompany = new Company();
        this.employeeCompany.setId(1);
        this.employeeCompany.setName("Onelity");
        this.employeeCompany.setAddress("G&G");
        this.empl.setId(1);
        this.empl.setName("Giorgos Papadopoulos");
        this.empl.setEmail("giorgos.papadopoulos@gmail.com");
        this.empl.setCompany(employeeCompany);
        this.optEmployeeCompany = Optional.of(this.employeeCompany);
        Employee employee1 = new Employee();
        employee1.setId(2);
        employee1.setName("Kostas Pavlidis");
        employee1.setEmail("kostas.pavlidis@gmail.com");
        this.employeeList.add(employee1);
        Employee employee2 = new Employee();
        employee2.setId(3);
        employee2.setName("Maria Panagiotou");
        employee2.setEmail("maria.panagiotou@gmail.com");
        this.employeeList.add(employee2);

    }

    @Test
    public void GivenGetAllEmployeesShouldReturnListOfAllEmployees(){
        employeeRepository.save(this.empl);
        when(employeeRepository.findAll()).thenReturn(this.employeeList);
        List<Employee> employeeListFromService = employeeService.getEmployees();
        assertEquals(employeeListFromService, this.employeeList);
        verify(employeeRepository,times(1)).save(this.empl);
        verify(employeeRepository,times(1)).findAll();
    }

    @Test
    public void givenIdThenShouldReturnEmployeeOfThatId() throws Exception {
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(this.empl));
        assertThat(employeeService.getEmployee(this.empl.getId())).isEqualTo(this.empl);
    }

    @Test
    void givenEmployeeToAddShouldReturnAddedEmployee() throws Exception {
        Mockito.when(employeeRepository.save(any())).thenReturn(this.empl);
        Mockito.when(companyRepository.findById(1)).thenReturn(this.optEmployeeCompany);
        employeeService.addEmployee(this.empl);
        verify(employeeRepository,times(1)).save(any());
    }

    @Test
    void givenEmployeeToUpdateShouldReturnUpdatedEmployee() throws Exception {
        Mockito.when(employeeRepository.save(any())).thenReturn(this.empl);
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(this.empl));
        Mockito.when(companyRepository.findById(1)).thenReturn(this.optEmployeeCompany);
        employeeService.updateEmployee(this.empl);
        verify(employeeRepository,times(1)).save(any());
    }

    @Test
    public void givenIdToDeleteThenShouldDeleteTheEmployee() throws Exception {
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(this.empl));
        String result = employeeService.deleteEmployee(this.empl.getId());
        assertEquals(result, "Employee is deleted");
    }


}
