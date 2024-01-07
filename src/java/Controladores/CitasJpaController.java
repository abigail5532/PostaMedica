/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Citas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Pacientes;
import Entidades.Medicos;
import Entidades.Triaje;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alessandra
 */
public class CitasJpaController implements Serializable {

    public CitasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CitasJpaController() throws NamingException{
        this.emf = Persistence.createEntityManagerFactory("PostaMedicaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Citas citas) throws PreexistingEntityException, Exception {
        if (citas.getTriajeList() == null) {
            citas.setTriajeList(new ArrayList<Triaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pacientes pacientecita = citas.getPacientecita();
            if (pacientecita != null) {
                pacientecita = em.getReference(pacientecita.getClass(), pacientecita.getIdpac());
                citas.setPacientecita(pacientecita);
            }
            Medicos especialidadcita = citas.getEspecialidadcita();
            if (especialidadcita != null) {
                especialidadcita = em.getReference(especialidadcita.getClass(), especialidadcita.getIdmed());
                citas.setEspecialidadcita(especialidadcita);
            }
            List<Triaje> attachedTriajeList = new ArrayList<Triaje>();
            for (Triaje triajeListTriajeToAttach : citas.getTriajeList()) {
                triajeListTriajeToAttach = em.getReference(triajeListTriajeToAttach.getClass(), triajeListTriajeToAttach.getIdtri());
                attachedTriajeList.add(triajeListTriajeToAttach);
            }
            citas.setTriajeList(attachedTriajeList);
            em.persist(citas);
            if (pacientecita != null) {
                pacientecita.getCitasList().add(citas);
                pacientecita = em.merge(pacientecita);
            }
            if (especialidadcita != null) {
                especialidadcita.getCitasList().add(citas);
                especialidadcita = em.merge(especialidadcita);
            }
            for (Triaje triajeListTriaje : citas.getTriajeList()) {
                Citas oldCitatriOfTriajeListTriaje = triajeListTriaje.getCitatri();
                triajeListTriaje.setCitatri(citas);
                triajeListTriaje = em.merge(triajeListTriaje);
                if (oldCitatriOfTriajeListTriaje != null) {
                    oldCitatriOfTriajeListTriaje.getTriajeList().remove(triajeListTriaje);
                    oldCitatriOfTriajeListTriaje = em.merge(oldCitatriOfTriajeListTriaje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCitas(citas.getIdcita()) != null) {
                throw new PreexistingEntityException("Citas " + citas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Citas citas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citas persistentCitas = em.find(Citas.class, citas.getIdcita());
            Pacientes pacientecitaOld = persistentCitas.getPacientecita();
            Pacientes pacientecitaNew = citas.getPacientecita();
            Medicos especialidadcitaOld = persistentCitas.getEspecialidadcita();
            Medicos especialidadcitaNew = citas.getEspecialidadcita();
            List<Triaje> triajeListOld = persistentCitas.getTriajeList();
            List<Triaje> triajeListNew = citas.getTriajeList();
            List<String> illegalOrphanMessages = null;
            for (Triaje triajeListOldTriaje : triajeListOld) {
                if (!triajeListNew.contains(triajeListOldTriaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Triaje " + triajeListOldTriaje + " since its citatri field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pacientecitaNew != null) {
                pacientecitaNew = em.getReference(pacientecitaNew.getClass(), pacientecitaNew.getIdpac());
                citas.setPacientecita(pacientecitaNew);
            }
            if (especialidadcitaNew != null) {
                especialidadcitaNew = em.getReference(especialidadcitaNew.getClass(), especialidadcitaNew.getIdmed());
                citas.setEspecialidadcita(especialidadcitaNew);
            }
            List<Triaje> attachedTriajeListNew = new ArrayList<Triaje>();
            for (Triaje triajeListNewTriajeToAttach : triajeListNew) {
                triajeListNewTriajeToAttach = em.getReference(triajeListNewTriajeToAttach.getClass(), triajeListNewTriajeToAttach.getIdtri());
                attachedTriajeListNew.add(triajeListNewTriajeToAttach);
            }
            triajeListNew = attachedTriajeListNew;
            citas.setTriajeList(triajeListNew);
            citas = em.merge(citas);
            if (pacientecitaOld != null && !pacientecitaOld.equals(pacientecitaNew)) {
                pacientecitaOld.getCitasList().remove(citas);
                pacientecitaOld = em.merge(pacientecitaOld);
            }
            if (pacientecitaNew != null && !pacientecitaNew.equals(pacientecitaOld)) {
                pacientecitaNew.getCitasList().add(citas);
                pacientecitaNew = em.merge(pacientecitaNew);
            }
            if (especialidadcitaOld != null && !especialidadcitaOld.equals(especialidadcitaNew)) {
                especialidadcitaOld.getCitasList().remove(citas);
                especialidadcitaOld = em.merge(especialidadcitaOld);
            }
            if (especialidadcitaNew != null && !especialidadcitaNew.equals(especialidadcitaOld)) {
                especialidadcitaNew.getCitasList().add(citas);
                especialidadcitaNew = em.merge(especialidadcitaNew);
            }
            for (Triaje triajeListNewTriaje : triajeListNew) {
                if (!triajeListOld.contains(triajeListNewTriaje)) {
                    Citas oldCitatriOfTriajeListNewTriaje = triajeListNewTriaje.getCitatri();
                    triajeListNewTriaje.setCitatri(citas);
                    triajeListNewTriaje = em.merge(triajeListNewTriaje);
                    if (oldCitatriOfTriajeListNewTriaje != null && !oldCitatriOfTriajeListNewTriaje.equals(citas)) {
                        oldCitatriOfTriajeListNewTriaje.getTriajeList().remove(triajeListNewTriaje);
                        oldCitatriOfTriajeListNewTriaje = em.merge(oldCitatriOfTriajeListNewTriaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = citas.getIdcita();
                if (findCitas(id) == null) {
                    throw new NonexistentEntityException("The citas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citas citas;
            try {
                citas = em.getReference(Citas.class, id);
                citas.getIdcita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Triaje> triajeListOrphanCheck = citas.getTriajeList();
            for (Triaje triajeListOrphanCheckTriaje : triajeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Citas (" + citas + ") cannot be destroyed since the Triaje " + triajeListOrphanCheckTriaje + " in its triajeList field has a non-nullable citatri field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pacientes pacientecita = citas.getPacientecita();
            if (pacientecita != null) {
                pacientecita.getCitasList().remove(citas);
                pacientecita = em.merge(pacientecita);
            }
            Medicos especialidadcita = citas.getEspecialidadcita();
            if (especialidadcita != null) {
                especialidadcita.getCitasList().remove(citas);
                especialidadcita = em.merge(especialidadcita);
            }
            em.remove(citas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    //Método editar
    public void editCita(Citas citas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            citas = em.merge(citas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = citas.getIdcita();
                if (findCitas(id) == null) {
                    throw new NonexistentEntityException("The citas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    //Método para listar con el indicador (citas realizadas y activas)
    public List<Citas> tablaCitas(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c from Citas c where c.activecita IN ('R', 'A')");
        List <Citas> list = (List<Citas>) query.getResultList();
        return list;
    }
    
    //Método para listar con el indicador (citas no realizadas)
    public List<Citas> tablaCitasNoRealizadas(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c from Citas c where c.activecita='A'");
        List <Citas> list = (List<Citas>) query.getResultList();
        return list;
    }

    public List<Citas> findCitasEntities() {
        return findCitasEntities(true, -1, -1);
    }

    public List<Citas> findCitasEntities(int maxResults, int firstResult) {
        return findCitasEntities(false, maxResults, firstResult);
    }

    private List<Citas> findCitasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citas.class));
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

    public Citas findCitas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citas> rt = cq.from(Citas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
