package com.development.appointmentmanagement.logics;

import com.development.appointmentmanagement.models.Administrator;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.models.ProcedureTable;
import com.development.appointmentmanagement.models.Role;
import com.development.appointmentmanagement.models.User;
import com.development.appointmentmanagement.persistences.PersistenceController;
import com.development.appointmentmanagement.persistences.exceptions.NoAppointmentException;
import com.development.appointmentmanagement.persistences.exceptions.NonexistentEntityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Controller {

    PersistenceController persisController = new PersistenceController();

    //------------------------------ACCIONES DE REGISTRO DE USUARIOS: 1ADMINY 2 CITIZEN---------------------------
    public void registerAdmin(String firstName, String lastName, List<ProcedureTable> procedures, String username, String password) {
        Role roleAdmin = persisController.findRoleByName("ADMIN"); // Suponiendo que tienes un método para encontrar un role por su nombre
        Administrator admin = new Administrator(firstName, lastName, procedures, Long.MIN_VALUE, username, password, roleAdmin);
        persisController.createAdmin(admin);
    }

    public void createCitizen(String name, String lastName, String document, List<Appointment> appointments, String username, String password) {
        Role roleCiudadano = persisController.findRoleByName("CITIZEN");
        Citizen citizen = new Citizen(name, lastName, document, appointments, Long.MIN_VALUE, username, password, roleCiudadano);
        persisController.createCitizen(citizen);
    }

    /*----------------------PROCEDURES----------------------*/
    public void createProcedure(String name, String description, List<Appointment> turn, Administrator admin) {

        ProcedureTable procedure = new ProcedureTable(name, description, turn);
        procedure.setAdmin(admin);
        persisController.createProcedure(procedure);

    }

    public List<ProcedureTable> getListProcedures() {
        return persisController.getListProcedures();
    }

    public ProcedureTable getProcedureById(Long id) {
        return persisController.getProcedureById(id);
    }

    public void modifyProcedure(String title, String description, Administrator admin, List<Appointment> appointments, ProcedureTable procedure) {
        procedure.setName(title);
        procedure.setDescription(description);

        procedure.setAdmin(admin);
        procedure.setAppointments(appointments);
        persisController.modifyProcedure(procedure);
    }

    public void deleteProcedure(Long idDelete) {
        try {
            persisController.deleteProcedure(idDelete);

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateStatus() {
        List<Appointment> citas = persisController.getListAppointments();
        LocalDateTime ahora = LocalDateTime.now();

        for (Appointment cita : citas) {
            if (cita.esAtendido()) {
                cita.setStatus(true); 
                persisController.modifyAppointment(cita);
            } else {
                cita.setStatus(false);
                persisController.modifyAppointment(cita); 
            }
        }
    }

    //  --------------APPOINTMENTS--------------------------------------------------------------------------
    public void createAppointment(LocalDateTime dateTime, String description, ProcedureTable procedure, Citizen citizen) {
        Appointment appointment = new Appointment(dateTime, description, procedure, citizen);
        if (appointment.esAtendido()) {
            appointment.setStatus(true);
        } else {
            appointment.setStatus(false);
        }
        persisController.createAppointment(appointment);
    }

    public List<Appointment> getListAppointments() {
        return persisController.getListAppointments();
    }

    public Appointment getAppointmentById(Long idAppintment) {
        return persisController.getAppointmentById(idAppintment);
    }

    public void deleteAppointment(Long idAppointment) {
        persisController.deleteAppointment(idAppointment);
    }
    
    public void deleteAppointmentByAdmin(Long idAppointment, String documento) throws NoAppointmentException {
       
        List<Appointment> citas = persisController.getListAppointments();

        // verificamos si el turno es atendido , filtramos los turnos, y verificamos si el dni coincide para poderlo eliminar
        boolean findTurn = citas.stream()
                .filter(Appointment::esAtendido) 
                .anyMatch(cita -> cita.getCitizen().getDocument().equals(documento)); 

        if (findTurn) {
            persisController.deleteAppointment(idAppointment);
        } else {
       
            throw new NoAppointmentException("No se puede eliminar el turno."
                    + " El ciudadano no ha sido atendido o el documento del ciudadano no coincide con el la Base de datos.");
        }
    }

    public void modifyAppointment(LocalDateTime dateTime, boolean status, String description, ProcedureTable procedure, Citizen citizen, Appointment appointment) {
        appointment.setDateTime(dateTime);

        appointment.setDescription(description);
        appointment.setStatus(status);
        appointment.setProcedure(procedure);
        appointment.setCitizen(citizen);
        persisController.modifyAppointment(appointment);
    }

    //Filtro para que el ciudadano pueda ver solo sus turnos 
    public List<Appointment> filterAppointmentsByCitizen(List<Appointment> appointments, User citizen) {
        return appointments.stream()
                .filter(appointment -> Objects.equals(appointment.getCitizen().getId(), citizen.getId()))
                .collect(Collectors.toList());
    }

    //Filtro de turno por fecha-fecha y estado
    public List<Appointment> filterByDate(LocalDate fecha) {
        return getListAppointments().stream()
                .filter(appointment -> appointment.getDateTime().toLocalDate().isEqual(fecha))
                .collect(Collectors.toList());
    }

    public List<Appointment> filterByDateStatus(LocalDate fecha, boolean estado) {
        List<Appointment> appointmentFilter = filterByDate(fecha);
        appointmentFilter = appointmentFilter.stream().filter(p -> p.isStatus() == estado).collect(Collectors.toList());
        return appointmentFilter;
    }

}
