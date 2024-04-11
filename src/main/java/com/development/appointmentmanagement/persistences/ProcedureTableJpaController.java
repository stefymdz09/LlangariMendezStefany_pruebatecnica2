/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.development.appointmentmanagement.persistences;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.development.appointmentmanagement.models.Administrator;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.models.ProcedureTable;
import com.development.appointmentmanagement.persistences.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stefy
 */
public class ProcedureTableJpaController implements Serializable {

    public ProcedureTableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
   public ProcedureTableJpaController() {
        emf = Persistence.createEntityManagerFactory("appointmentmanagementPU");
    }
    public void create(ProcedureTable procedureTable) {
        if (procedureTable.getAppointments() == null) {
            procedureTable.setAppointments(new ArrayList<Appointment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrator admin = procedureTable.getAdmin();
            if (admin != null) {
                admin = em.getReference(admin.getClass(), admin.getId());
                procedureTable.setAdmin(admin);
            }
            List<Appointment> attachedAppointments = new ArrayList<Appointment>();
            for (Appointment appointmentsAppointmentToAttach : procedureTable.getAppointments()) {
                appointmentsAppointmentToAttach = em.getReference(appointmentsAppointmentToAttach.getClass(), appointmentsAppointmentToAttach.getIdAppointment());
                attachedAppointments.add(appointmentsAppointmentToAttach);
            }
            procedureTable.setAppointments(attachedAppointments);
            em.persist(procedureTable);
            if (admin != null) {
                admin.getProcedures().add(procedureTable);
                admin = em.merge(admin);
            }
            for (Appointment appointmentsAppointment : procedureTable.getAppointments()) {
                ProcedureTable oldProcedureOfAppointmentsAppointment = appointmentsAppointment.getProcedure();
                appointmentsAppointment.setProcedure(procedureTable);
                appointmentsAppointment = em.merge(appointmentsAppointment);
                if (oldProcedureOfAppointmentsAppointment != null) {
                    oldProcedureOfAppointmentsAppointment.getAppointments().remove(appointmentsAppointment);
                    oldProcedureOfAppointmentsAppointment = em.merge(oldProcedureOfAppointmentsAppointment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProcedureTable procedureTable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProcedureTable persistentProcedureTable = em.find(ProcedureTable.class, procedureTable.getIdProcedure());
            Administrator adminOld = persistentProcedureTable.getAdmin();
            Administrator adminNew = procedureTable.getAdmin();
            List<Appointment> appointmentsOld = persistentProcedureTable.getAppointments();
            List<Appointment> appointmentsNew = procedureTable.getAppointments();
            if (adminNew != null) {
                adminNew = em.getReference(adminNew.getClass(), adminNew.getId());
                procedureTable.setAdmin(adminNew);
            }
            List<Appointment> attachedAppointmentsNew = new ArrayList<Appointment>();
            for (Appointment appointmentsNewAppointmentToAttach : appointmentsNew) {
                appointmentsNewAppointmentToAttach = em.getReference(appointmentsNewAppointmentToAttach.getClass(), appointmentsNewAppointmentToAttach.getIdAppointment());
                attachedAppointmentsNew.add(appointmentsNewAppointmentToAttach);
            }
            appointmentsNew = attachedAppointmentsNew;
            procedureTable.setAppointments(appointmentsNew);
            procedureTable = em.merge(procedureTable);
            if (adminOld != null && !adminOld.equals(adminNew)) {
                adminOld.getProcedures().remove(procedureTable);
                adminOld = em.merge(adminOld);
            }
            if (adminNew != null && !adminNew.equals(adminOld)) {
                adminNew.getProcedures().add(procedureTable);
                adminNew = em.merge(adminNew);
            }
            for (Appointment appointmentsOldAppointment : appointmentsOld) {
                if (!appointmentsNew.contains(appointmentsOldAppointment)) {
                    appointmentsOldAppointment.setProcedure(null);
                    appointmentsOldAppointment = em.merge(appointmentsOldAppointment);
                }
            }
            for (Appointment appointmentsNewAppointment : appointmentsNew) {
                if (!appointmentsOld.contains(appointmentsNewAppointment)) {
                    ProcedureTable oldProcedureOfAppointmentsNewAppointment = appointmentsNewAppointment.getProcedure();
                    appointmentsNewAppointment.setProcedure(procedureTable);
                    appointmentsNewAppointment = em.merge(appointmentsNewAppointment);
                    if (oldProcedureOfAppointmentsNewAppointment != null && !oldProcedureOfAppointmentsNewAppointment.equals(procedureTable)) {
                        oldProcedureOfAppointmentsNewAppointment.getAppointments().remove(appointmentsNewAppointment);
                        oldProcedureOfAppointmentsNewAppointment = em.merge(oldProcedureOfAppointmentsNewAppointment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = procedureTable.getIdProcedure();
                if (findProcedureTable(id) == null) {
                    throw new NonexistentEntityException("The procedureTable with id " + id + " no longer exists.");
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
            ProcedureTable procedureTable;
            try {
                procedureTable = em.getReference(ProcedureTable.class, id);
                procedureTable.getIdProcedure();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procedureTable with id " + id + " no longer exists.", enfe);
            }
            Administrator admin = procedureTable.getAdmin();
            if (admin != null) {
                admin.getProcedures().remove(procedureTable);
                admin = em.merge(admin);
            }
            List<Appointment> appointments = procedureTable.getAppointments();
            for (Appointment appointmentsAppointment : appointments) {
                appointmentsAppointment.setProcedure(null);
                appointmentsAppointment = em.merge(appointmentsAppointment);
            }
            em.remove(procedureTable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProcedureTable> findProcedureTableEntities() {
        return findProcedureTableEntities(true, -1, -1);
    }

    public List<ProcedureTable> findProcedureTableEntities(int maxResults, int firstResult) {
        return findProcedureTableEntities(false, maxResults, firstResult);
    }

    private List<ProcedureTable> findProcedureTableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProcedureTable.class));
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

    public ProcedureTable findProcedureTable(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProcedureTable.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcedureTableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProcedureTable> rt = cq.from(ProcedureTable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
