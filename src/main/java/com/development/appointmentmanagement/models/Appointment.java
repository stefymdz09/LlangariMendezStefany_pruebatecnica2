
package com.development.appointmentmanagement.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointment;
    
    private LocalDateTime dateTime;
    private String description;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "idProcedure")
    private ProcedureTable procedure;

    @OneToOne
    @JoinColumn(name = "idCitizen")
    private Citizen citizen;

    public Appointment() {
    }


  
    public Appointment(Long idAppointment, LocalDateTime dateTime, String description, boolean status, ProcedureTable procedure, Citizen citizen) {
        this.idAppointment = idAppointment;
        this.dateTime = dateTime;
        this.description = description;
        this.status = status;
        this.procedure = procedure;
        this.citizen = citizen;
    }

    public Appointment(LocalDateTime dateTime, String description, boolean status, ProcedureTable procedure, Citizen citizen) {
        this.dateTime = dateTime;
        this.description = description;
        this.status = status;
        this.procedure = procedure;
        this.citizen = citizen;
    }
    public Appointment(LocalDateTime dateTime, String description, ProcedureTable procedure, Citizen citizen) {
        this.dateTime = dateTime;
        this.description = description;
        this.procedure = procedure;
        this.citizen = citizen;
    }

    public Long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

   public boolean esAtendido() {
        LocalDateTime ahora = LocalDateTime.now();
        return dateTime.isBefore(ahora); // Si la fecha y hora del turno es antes que la fecha y hora actual, está atendido
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public ProcedureTable getProcedure() {
        return procedure;
    }

    public void setProcedure(ProcedureTable procedure) {
        this.procedure = procedure;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }
    
    
    
    
}


