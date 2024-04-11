/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.development.appointmentmanagement.persistences;

import com.development.appointmentmanagement.models.Administrator;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.development.appointmentmanagement.models.Appointment;
import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.models.User;
import com.development.appointmentmanagement.persistences.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Stefy
 */
public class CitizenJpaController implements Serializable {

    public CitizenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
   public CitizenJpaController() {
        emf = Persistence.createEntityManagerFactory("appointmentmanagementPU");
    }
    public void create(Citizen citizen) {
        if (citizen.getAppointments() == null) {
            citizen.setAppointments(new ArrayList<Appointment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Appointment> attachedAppointments = new ArrayList<Appointment>();
            for (Appointment appointmentsAppointmentToAttach : citizen.getAppointments()) {
                appointmentsAppointmentToAttach = em.getReference(appointmentsAppointmentToAttach.getClass(), appointmentsAppointmentToAttach.getIdAppointment());
                attachedAppointments.add(appointmentsAppointmentToAttach);
            }
            citizen.setAppointments(attachedAppointments);
            em.persist(citizen);
            for (Appointment appointmentsAppointment : citizen.getAppointments()) {
                Citizen oldCitizenOfAppointmentsAppointment = appointmentsAppointment.getCitizen();
                appointmentsAppointment.setCitizen(citizen);
                appointmentsAppointment = em.merge(appointmentsAppointment);
                if (oldCitizenOfAppointmentsAppointment != null) {
                    oldCitizenOfAppointmentsAppointment.getAppointments().remove(appointmentsAppointment);
                    oldCitizenOfAppointmentsAppointment = em.merge(oldCitizenOfAppointmentsAppointment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Citizen citizen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citizen persistentCitizen = em.find(Citizen.class, citizen.getId());
            List<Appointment> appointmentsOld = persistentCitizen.getAppointments();
            List<Appointment> appointmentsNew = citizen.getAppointments();
            List<Appointment> attachedAppointmentsNew = new ArrayList<Appointment>();
            for (Appointment appointmentsNewAppointmentToAttach : appointmentsNew) {
                appointmentsNewAppointmentToAttach = em.getReference(appointmentsNewAppointmentToAttach.getClass(), appointmentsNewAppointmentToAttach.getIdAppointment());
                attachedAppointmentsNew.add(appointmentsNewAppointmentToAttach);
            }
            appointmentsNew = attachedAppointmentsNew;
            citizen.setAppointments(appointmentsNew);
            citizen = em.merge(citizen);
            for (Appointment appointmentsOldAppointment : appointmentsOld) {
                if (!appointmentsNew.contains(appointmentsOldAppointment)) {
                    appointmentsOldAppointment.setCitizen(null);
                    appointmentsOldAppointment = em.merge(appointmentsOldAppointment);
                }
            }
            for (Appointment appointmentsNewAppointment : appointmentsNew) {
                if (!appointmentsOld.contains(appointmentsNewAppointment)) {
                    Citizen oldCitizenOfAppointmentsNewAppointment = appointmentsNewAppointment.getCitizen();
                    appointmentsNewAppointment.setCitizen(citizen);
                    appointmentsNewAppointment = em.merge(appointmentsNewAppointment);
                    if (oldCitizenOfAppointmentsNewAppointment != null && !oldCitizenOfAppointmentsNewAppointment.equals(citizen)) {
                        oldCitizenOfAppointmentsNewAppointment.getAppointments().remove(appointmentsNewAppointment);
                        oldCitizenOfAppointmentsNewAppointment = em.merge(oldCitizenOfAppointmentsNewAppointment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = citizen.getId();
                if (findCitizen(id) == null) {
                    throw new NonexistentEntityException("The citizen with id " + id + " no longer exists.");
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
            Citizen citizen;
            try {
                citizen = em.getReference(Citizen.class, id);
                citizen.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citizen with id " + id + " no longer exists.", enfe);
            }
            List<Appointment> appointments = citizen.getAppointments();
            for (Appointment appointmentsAppointment : appointments) {
                appointmentsAppointment.setCitizen(null);
                appointmentsAppointment = em.merge(appointmentsAppointment);
            }
            em.remove(citizen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Citizen> findCitizenEntities() {
        return findCitizenEntities(true, -1, -1);
    }

    public List<Citizen> findCitizenEntities(int maxResults, int firstResult) {
        return findCitizenEntities(false, maxResults, firstResult);
    }

    private List<Citizen> findCitizenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citizen.class));
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

    public Citizen findCitizen(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citizen.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitizenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citizen> rt = cq.from(Citizen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    Citizen authenticateUser(String username, String password) {
    EntityManager em = null;
    try {
        em = getEntityManager();
        // Consulta para verificar si existe un usuario (citizen) con el nombre de usuario y contraseña proporcionados
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password AND TYPE(u) = Citizen", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        // Obtener el usuario autenticado
        List<User> users = query.getResultList();
        if (!users.isEmpty()) {
            return (Citizen) users.get(0); // Casting seguro a Citizen
        }
        return null; // Si no se encuentra un ciudadano autenticado, devolver null
    } finally {
        if (em != null) {
            em.close();
        }
    }
}
}
