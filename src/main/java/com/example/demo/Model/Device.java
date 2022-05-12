package com.example.demo.Model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

}