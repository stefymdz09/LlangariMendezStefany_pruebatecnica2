/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.development.appointmentmanagement.persistences;

import com.development.appointmentmanagement.models.Appointment;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.development.appointmentmanagement.models.ProcedureTable;
import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.persistences.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stefy
 */
public class AppointmentJpaController implements Serializable {

    public AppointmentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public AppointmentJpaController() {
        emf = Persistence.createEntityManagerFactory("appointmentmanagementPU");
    }

    public void create(Appointment appointment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProcedureTable procedure = appointment.getProcedure();
            if (procedure != null) {
                procedure = em.getReference(procedure.getClass(), procedure.getIdProcedure());
                appointment.setProcedure(procedure);
            }
            Citizen citizen = appointment.getCitizen();
            if (citizen != null) {
                citizen = em.getReference(citizen.getClass(), citizen.getId());
                appointment.setCitizen(citizen);
            }
            em.persist(appointment);
            if (procedure != null) {
                procedure.getAppointments().add(appointment);
                procedure = em.merge(procedure);
            }
            if (citizen != null) {
                citizen.getAppointments().add(appointment);
                citizen = em.merge(citizen);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Appointment appointment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Appointment persistentAppointment = em.find(Appointment.class, appointment.getIdAppointment());
            ProcedureTable procedureOld = persistentAppointment.getProcedure();
            ProcedureTable procedureNew = appointment.getProcedure();
            Citizen citizenOld = persistentAppointment.getCitizen();
            Citizen citizenNew = appointment.getCitizen();
            if (procedureNew != null) {
                procedureNew = em.getReference(procedureNew.getClass(), procedureNew.getIdProcedure());
                appointment.setProcedure(procedureNew);
            }
            if (citizenNew != null) {
                citizenNew = em.getReference(citizenNew.getClass(), citizenNew.getId());
                appointment.setCitizen(citizenNew);
            }
            appointment = em.merge(appointment);
            if (procedureOld != null && !procedureOld.equals(procedureNew)) {
                procedureOld.getAppointments().remove(appointment);
                procedureOld = em.merge(procedureOld);
            }
            if (procedureNew != null && !procedureNew.equals(procedureOld)) {
                procedureNew.getAppointments().add(appointment);
                procedureNew = em.merge(procedureNew);
            }
            if (citizenOld != null && !citizenOld.equals(citizenNew)) {
                citizenOld.getAppointments().remove(appointment);
                citizenOld = em.merge(citizenOld);
            }
            if (citizenNew != null && !citizenNew.equals(citizenOld)) {
                citizenNew.getAppointments().add(appointment);
                citizenNew = em.merge(citizenNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = appointment.getIdAppointment();
                if (findAppointment(id) == null) {
                    throw new NonexistentEntityException("The appointment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Appointment appointment;
            try {
                appointment = em.getReference(Appointment.class, id);
                appointment.getIdAppointment();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The appointment with id " + id + " no longer exists.", enfe);
            }
            ProcedureTable procedure = appointment.getProcedure();
            if (procedure != null) {
                procedure.getAppointments().remove(appointment);
                procedure = em.merge(procedure);
            }
            Citizen citizen = appointment.getCitizen();
            if (citizen != null) {
                citizen.getAppointments().remove(appointment);
                citizen = em.merge(citizen);
            }
            em.remove(appointment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Appointment> findAppointmentEntities() {
        return findAppointmentEntities(true, -1, -1);
    }

    public List<Appointment> findAppointmentEntities(int maxResults, int firstResult) {
        return findAppointmentEntities(false, maxResults, firstResult);
    }

    private List<Appointment> findAppointmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Appointment.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Appointment findAppointment(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Appointment.class, id);
        } finally {
            em.close();
        }
    }

    public int getAppointmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Appointment> rt = cq.from(Appointment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
