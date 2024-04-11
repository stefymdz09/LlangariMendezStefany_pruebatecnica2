
package com.development.appointmentmanagement.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Administrator extends User implements Serializable {

    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "admin")
    @JoinColumn(name = "procedures_admin")
    private List<ProcedureTable> procedures;

    public Administrator() {
    }

    public Administrator(List<ProcedureTable> procedures, Long id, String username, String password) {
        super(id, username, password);
        this.procedures = procedures;
    }

    public Administrator(String firstName, String lastName, List<ProcedureTable> procedures, Long id, String username, String password) {
        super(id, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.procedures = procedures;
    }

    public Administrator(String firstName, String lastName, List<ProcedureTable> procedures) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.procedures = procedures;
    }

    public Administrator(String firstName, String lastName, List<ProcedureTable> procedures, Long id, String username, String password, Role role) {
        super(id, username, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.procedures = procedures;
    }

    public Administrator(String firstName, String lastName, String username, String password, Role role) {
        super(username, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProcedureTable> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<ProcedureTable> procedures) {
        this.procedures = procedures;
    }

}
