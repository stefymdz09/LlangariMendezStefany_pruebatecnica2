
package com.development.appointmentmanagement.persistences;

import com.development.appointmentmanagement.models.Administrator;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.models.ProcedureTable;
import com.development.appointmentmanagement.models.Role;
import com.development.appointmentmanagement.models.User;
import com.development.appointmentmanagement.persistences.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenceController {

    AdministratorJpaController adminJpa = new AdministratorJpaController();
    ProcedureTableJpaController procedureJpa = new ProcedureTableJpaController();
    AppointmentJpaController appointmentJpa = new AppointmentJpaController();
    CitizenJpaController citizenJpa = new CitizenJpaController();
    UserJpaController userJpa = new UserJpaController();
    RoleJpaController roleJpa = new RoleJpaController();

    // ADMIN ----------------------------------------------------------------------------------------
    public void createAdmin(Administrator admin) {
        adminJpa.create(admin);
    }

    //  ------------PROCEDURES---------------------------------------------
    public void createProcedure(ProcedureTable procedure) {
        procedureJpa.create(procedure);
    }

    public List<ProcedureTable> getListProcedures() {
        return procedureJpa.findProcedureTableEntities();
    }

    public void deleteProcedure(Long idProcedureDelete) throws NonexistentEntityException {
        procedureJpa.destroy(idProcedureDelete);
    }

    public ProcedureTable getProcedureById(Long idProcedure) {
        return procedureJpa.findProcedureTable(idProcedure);
    }

    public void modifyProcedure(ProcedureTable procedure) {
        try {
            procedureJpa.edit(procedure);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //  ---------------APPOINTMENTS--------------------------------------------
    public Appointment getAppointmentById(Long idAppointment) {
        return appointmentJpa.findAppointment(idAppointment);
    }

    public void modifyAppointment(Appointment appointment) {
        try {
            appointmentJpa.edit(appointment);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createAppointment(Appointment appointment) {
        appointmentJpa.create(appointment);
    }

    public List<Appointment> getListAppointments() {
        return appointmentJpa.findAppointmentEntities();
    }

    public void deleteAppointment(Long idAppointment) {
        try {
            appointmentJpa.destroy(idAppointment);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //  ---------------------------------------------------------------------------------------------

    // CITIZEN --------------------------------------------------------------------------------------
    public void registerCitizen(Citizen citizen) {
        citizenJpa.create(citizen);
    }

    //  ---------------------------------------------------------------------------------------------

    public void registerUser(User user) {
        userJpa.create(user);
    }

    public void registerUser(Administrator admin) {
        userJpa.create(admin);
    }
    public Administrator authenticateUser(String username, String password) {
        return adminJpa.authenticateUser(username, password);
    }

    public Citizen authenticateCitizen(String username, String password) {
        return citizenJpa.authenticateUser(username, password);
    }
    
    public void createCitizen(Citizen citizen) {
        citizenJpa.create(citizen);
    }

    /*------------------------ROLES--------------------------------------*/
    public List<Role> getRoles() {
        return roleJpa.findRoleEntities();
    }   
    public Role findRoleByName(String roleName) {
    List<Role> roles = roleJpa.findRoleEntities(); 
      Optional<Role> matchingRole = roles.stream()
            .filter(role -> role.getName().equals(roleName))
            .findFirst();
    return matchingRole.orElse(null);
}

}
