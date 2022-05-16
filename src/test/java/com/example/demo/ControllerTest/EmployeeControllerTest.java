package com.example.demo.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Controller.EmployeeController;
import com.example.demo.Model.Employee;
import com.example.demo.Service.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    EmployeeService employeeService;

    private static ObjectMapper mapper = new ObjectMapper();

    private Employee empl;
    private List<Employee> employeeList;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(employeeService)).build();

        this.employeeList = new ArrayList<>();
        this.empl = new Employee();
        this.empl.setId(1);
        this.empl.setName("Giorgos Papadopoulos");
        this.empl.setEmail("giorgos.papadopoulos@gmail.com");
        this.employeeList.add(this.empl);
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
    public void getEmployeesTest() throws Exception {
        Mockito.when(employeeService.getEmployees()).thenReturn(this.employeeList);
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].email", Matchers.equalTo("giorgos.papadopoulos@gmail.com")))
                .andExpect(jsonPath("$[1].email", Matchers.equalTo("kostas.pavlidis@gmail.com")))
                .andExpect(jsonPath("$[2].email", Matchers.equalTo("maria.panagiotou@gmail.com")));
    }

    @Test
    public void getEmployeeTest() throws Exception {
        Mockito.when(employeeService.getEmployee(ArgumentMatchers.anyInt())).thenReturn(this.empl);
        String json = mapper.writeValueAsString(this.empl);
        mockMvc.perform(get("/employees/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", Matchers.equalTo("Giorgos Papadopoulos")))
                         .andExpect(jsonPath("$.email", Matchers.equalTo("giorgos.papadopoulos@gmail.com")));
    }

    @Test
    public void addEmployeeTest() throws Exception {
        Mockito.when(employeeService.addEmployee(ArgumentMatchers.any())).thenReturn(this.empl);
        String json = mapper.writeValueAsString(this.empl);
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name", Matchers.equalTo("Giorgos Papadopoulos")))
                         .andExpect(jsonPath("$.email", Matchers.equalTo("giorgos.papadopoulos@gmail.com")));
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        Mockito.when(employeeService.updateEmployee(ArgumentMatchers.any())).thenReturn(this.empl);
        String json = mapper.writeValueAsString(this.empl);
        mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", Matchers.equalTo("Giorgos Papadopoulos")))
                          .andExpect(jsonPath("$.email", Matchers.equalTo("giorgos.papadopoulos@gmail.com")));
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        Mockito.when(employeeService.deleteEmployee(ArgumentMatchers.anyInt())).thenReturn("Employee is deleted");
        MvcResult requestResult = mockMvc.perform(delete("/employees/1").param("id", "1"))
                .andExpect(status().isOk()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Employee is deleted");
    }


}
