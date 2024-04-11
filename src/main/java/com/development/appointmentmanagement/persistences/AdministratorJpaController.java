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
import com.development.appointmentmanagement.models.ProcedureTable;
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
public class AdministratorJpaController implements Serializable {

    public AdministratorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
   public AdministratorJpaController() {
        emf = Persistence.createEntityManagerFactory("appointmentmanagementPU");
    }

    public void create(Administrator administrator) {
        if (administrator.getProcedures() == null) {
            administrator.setProcedures(new ArrayList<ProcedureTable>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ProcedureTable> attachedProcedures = new ArrayList<ProcedureTable>();
            for (ProcedureTable proceduresProcedureTableToAttach : administrator.getProcedures()) {
                proceduresProcedureTableToAttach = em.getReference(proceduresProcedureTableToAttach.getClass(), proceduresProcedureTableToAttach.getIdProcedure());
                attachedProcedures.add(proceduresProcedureTableToAttach);
            }
            administrator.setProcedures(attachedProcedures);
            em.persist(administrator);
            for (ProcedureTable proceduresProcedureTable : administrator.getProcedures()) {
                Administrator oldAdminOfProceduresProcedureTable = proceduresProcedureTable.getAdmin();
                proceduresProcedureTable.setAdmin(administrator);
                proceduresProcedureTable = em.merge(proceduresProcedureTable);
                if (oldAdminOfProceduresProcedureTable != null) {
                    oldAdminOfProceduresProcedureTable.getProcedures().remove(proceduresProcedureTable);
                    oldAdminOfProceduresProcedureTable = em.merge(oldAdminOfProceduresProcedureTable);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administrator administrator) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrator persistentAdministrator = em.find(Administrator.class, administrator.getId());
            List<ProcedureTable> proceduresOld = persistentAdministrator.getProcedures();
            List<ProcedureTable> proceduresNew = administrator.getProcedures();
            List<ProcedureTable> attachedProceduresNew = new ArrayList<ProcedureTable>();
            for (ProcedureTable proceduresNewProcedureTableToAttach : proceduresNew) {
                proceduresNewProcedureTableToAttach = em.getReference(proceduresNewProcedureTableToAttach.getClass(), proceduresNewProcedureTableToAttach.getIdProcedure());
                attachedProceduresNew.add(proceduresNewProcedureTableToAttach);
            }
            proceduresNew = attachedProceduresNew;
            administrator.setProcedures(proceduresNew);
            administrator = em.merge(administrator);
            for (ProcedureTable proceduresOldProcedureTable : proceduresOld) {
                if (!proceduresNew.contains(proceduresOldProcedureTable)) {
                    proceduresOldProcedureTable.setAdmin(null);
                    proceduresOldProcedureTable = em.merge(proceduresOldProcedureTable);
                }
            }
            for (ProcedureTable proceduresNewProcedureTable : proceduresNew) {
                if (!proceduresOld.contains(proceduresNewProcedureTable)) {
                    Administrator oldAdminOfProceduresNewProcedureTable = proceduresNewProcedureTable.getAdmin();
                    proceduresNewProcedureTable.setAdmin(administrator);
                    proceduresNewProcedureTable = em.merge(proceduresNewProcedureTable);
                    if (oldAdminOfProceduresNewProcedureTable != null && !oldAdminOfProceduresNewProcedureTable.equals(administrator)) {
                        oldAdminOfProceduresNewProcedureTable.getProcedures().remove(proceduresNewProcedureTable);
                        oldAdminOfProceduresNewProcedureTable = em.merge(oldAdminOfProceduresNewProcedureTable);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = administrator.getId();
                if (findAdministrator(id) == null) {
                    throw new NonexistentEntityException("The administrator with id " + id + " no longer exists.");
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
            Administrator administrator;
            try {
                administrator = em.getReference(Administrator.class, id);
                administrator.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrator with id " + id + " no longer exists.", enfe);
            }
            List<ProcedureTable> procedures = administrator.getProcedures();
            for (ProcedureTable proceduresProcedureTable : procedures) {
                proceduresProcedureTable.setAdmin(null);
                proceduresProcedureTable = em.merge(proceduresProcedureTable);
            }
            em.remove(administrator);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administrator> findAdministratorEntities() {
        return findAdministratorEntities(true, -1, -1);
    }

    public List<Administrator> findAdministratorEntities(int maxResults, int firstResult) {
        return findAdministratorEntities(false, maxResults, firstResult);
    }

    private List<Administrator> findAdministratorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrator.class));
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

    public Administrator findAdministrator(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrator.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministratorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrator> rt = cq.from(Administrator.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    Administrator authenticateUser(String username, String password) {
    EntityManager em = null;
    try {
        em = getEntityManager();
        // Consulta para verificar si existe un usuario con el nombre de usuario y contraseña proporcionados
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        // Obtener el usuario autenticado
        List<User> users = query.getResultList();
        if (!users.isEmpty()) {
            User user = users.get(0);
            if (user instanceof Administrator) {
                return (Administrator) user;
            }
        }
        return null; // Si no se encuentra un administrador autenticado, devolver null
    } finally {
        if (em != null) {
            em.close();
        }
    }
    
}
}
