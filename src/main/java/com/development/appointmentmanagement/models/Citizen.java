package com.development.appointmentmanagement.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
public class Citizen extends User implements Serializable {


    private String name;
    private String lastName;
    private String document;

    @OneToMany(mappedBy = "citizen")
    private List<Appointment> appointments;

    public Citizen() {
    }

    public Citizen(String name, String lastName, String document, List<Appointment> appointments, Long id, String username, String password, Role role) {
        super(id, username, password, role);
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.appointments = appointments;
    }

    public Citizen(String name, String lastName, String document, List<Appointment> appointments, Long id, String username, String password) {
        super(id, username, password);
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.appointments = appointments;
    }


    public Citizen(String name, String lastName, String document, String username, String password, Role role) {
        super(username, password, role);
        this.name = name;
        this.lastName = lastName;
        this.document = document;
    }

    public Citizen(String name, String lastName, String document) {
        this.name = name;
        this.lastName = lastName;
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
