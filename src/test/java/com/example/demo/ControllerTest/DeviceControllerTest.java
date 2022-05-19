package com.example.demo.ControllerTest;

import com.example.demo.Controller.DeviceController;
import com.example.demo.Model.Device;
import com.example.demo.Service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DeviceController.class)
public class DeviceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    DeviceService deviceService;

    private static ObjectMapper mapper = new ObjectMapper();

    private Device dev;
    private List<Device> deviceList;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DeviceController(deviceService)).build();
        this.deviceList = new ArrayList<>();
        this.dev = new Device();
        this.dev.setSerialNumber("abcde12345");
        this.dev.setName("Xiaomi 11t");
        this.dev.setType("mobile");
        this.deviceList.add(this.dev);
        Device device = new Device();
        device.setName("iPad 10");
        device.setType("tablet");
        this.deviceList.add(device);
        Device device1 = new Device();
        device1.setName("Dell Vostro");
        device1.setType("laptop");
        this.deviceList.add(device1);

    }

    @Test
    public void getDevicesTest() throws Exception {
        Mockito.when(deviceService.getDevices()).thenReturn(this.deviceList);
        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Xiaomi 11t")))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo("iPad 10")))
                .andExpect(jsonPath("$[2].name", Matchers.equalTo("Dell Vostro")));
    }

    @Test
    public void getDeviceTest() throws Exception {
        Mockito.when(deviceService.getDevice(ArgumentMatchers.anyString())).thenReturn(this.dev);
        String json = mapper.writeValueAsString(this.dev);
        mockMvc.perform(get("/devices/abcde12345").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("Xiaomi 11t")))
                .andExpect(jsonPath("$.type", Matchers.equalTo("mobile")));
    }

    @Test
    public void addDeviceTest() throws Exception {
        Mockito.when(deviceService.addDevice(ArgumentMatchers.any())).thenReturn(this.dev);
        String json = mapper.writeValueAsString(this.dev);
        mockMvc.perform(post("/devices").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.equalTo("Xiaomi 11t")))
                .andExpect(jsonPath("$.type", Matchers.equalTo("mobile")));
    }

    @Test
    public void updateDeviceTest() throws Exception {
        Mockito.when(deviceService.updateDevice(ArgumentMatchers.any())).thenReturn(this.dev);
        String json = mapper.writeValueAsString(this.dev);
        mockMvc.perform(put("/devices/abcde12345").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("Xiaomi 11t")))
                .andExpect(jsonPath("$.type", Matchers.equalTo("mobile")));
    }

    @Test
    public void deleteDeviceTest() throws Exception {
        Mockito.when(deviceService.deleteDevice(ArgumentMatchers.anyString())).thenReturn("Device is deleted");
        MvcResult requestResult = mockMvc.perform(delete("/devices/abcde12345").param("serialNumber", "abcde12345"))
                .andExpect(status().isOk()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Device is deleted");
    }

}