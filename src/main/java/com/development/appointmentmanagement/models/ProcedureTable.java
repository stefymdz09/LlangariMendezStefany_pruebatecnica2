package com.development.appointmentmanagement.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
public class ProcedureTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProcedure;
    private String name;
    private String description;

    @OneToMany(mappedBy = "procedure", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @ManyToOne
    private Administrator admin;

    public ProcedureTable() {
    }

    public ProcedureTable(String name, String description, List<Appointment> appointments) {
        this.name = name;
        this.description = description;
        this.appointments = appointments;
    }

    public ProcedureTable(String name, String description, List<Appointment> appointments, Administrator admin) {
        this.name = name;
        this.description = description;
        this.appointments = appointments;
        this.admin = admin;
    }

    public ProcedureTable(Long idProcedure, String name, String description, List<Appointment> appointments, Administrator admin) {
        this.idProcedure = idProcedure;
        this.name = name;
        this.description = description;
        this.appointments = appointments;
        this.admin = admin;
    }

    public Long getIdProcedure() {
        return idProcedure;
    }

    public void setIdProcedure(Long idProcedure) {
        this.idProcedure = idProcedure;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
