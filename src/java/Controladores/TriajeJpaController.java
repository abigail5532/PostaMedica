/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Citas;
import Entidades.Consultas;
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
public class TriajeJpaController implements Serializable {

    public TriajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public TriajeJpaController() throws NamingException{
        this.emf = Persistence.createEntityManagerFactory("PostaMedicaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Triaje triaje) {
        if (triaje.getConsultasList() == null) {
            triaje.setConsultasList(new ArrayList<Consultas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Citas citatri = triaje.getCitatri();
            if (citatri != null) {
                citatri = em.getReference(citatri.getClass(), citatri.getIdcita());
                triaje.setCitatri(citatri);
            }
            List<Consultas> attachedConsultasList = new ArrayList<Consultas>();
            for (Consultas consultasListConsultasToAttach : triaje.getConsultasList()) {
                consultasListConsultasToAttach = em.getReference(consultasListConsultasToAttach.getClass(), consultasListConsultasToAttach.getIdcons());
                attachedConsultasList.add(consultasListConsultasToAttach);
            }
            triaje.setConsultasList(attachedConsultasList);
            em.persist(triaje);
            if (citatri != null) {
                citatri.getTriajeList().add(triaje);
                citatri = em.merge(citatri);
            }
            for (Consultas consultasListConsultas : triaje.getConsultasList()) {
                Triaje oldTriajeconsOfConsultasListConsultas = consultasListConsultas.getTriajecons();
                consultasListConsultas.setTriajecons(triaje);
                consultasListConsultas = em.merge(consultasListConsultas);
                if (oldTriajeconsOfConsultasListConsultas != null) {
                    oldTriajeconsOfConsultasListConsultas.getConsultasList().remove(consultasListConsultas);
                    oldTriajeconsOfConsultasListConsultas = em.merge(oldTriajeconsOfConsultasListConsultas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Triaje triaje) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Triaje persistentTriaje = em.find(Triaje.class, triaje.getIdtri());
            Citas citatriOld = persistentTriaje.getCitatri();
            Citas citatriNew = triaje.getCitatri();
            List<Consultas> consultasListOld = persistentTriaje.getConsultasList();
            List<Consultas> consultasListNew = triaje.getConsultasList();
            List<String> illegalOrphanMessages = null;
            for (Consultas consultasListOldConsultas : consultasListOld) {
                if (!consultasListNew.contains(consultasListOldConsultas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Consultas " + consultasListOldConsultas + " since its triajecons field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (citatriNew != null) {
                citatriNew = em.getReference(citatriNew.getClass(), citatriNew.getIdcita());
                triaje.setCitatri(citatriNew);
            }
            List<Consultas> attachedConsultasListNew = new ArrayList<Consultas>();
            for (Consultas consultasListNewConsultasToAttach : consultasListNew) {
                consultasListNewConsultasToAttach = em.getReference(consultasListNewConsultasToAttach.getClass(), consultasListNewConsultasToAttach.getIdcons());
                attachedConsultasListNew.add(consultasListNewConsultasToAttach);
            }
            consultasListNew = attachedConsultasListNew;
            triaje.setConsultasList(consultasListNew);
            triaje = em.merge(triaje);
            if (citatriOld != null && !citatriOld.equals(citatriNew)) {
                citatriOld.getTriajeList().remove(triaje);
                citatriOld = em.merge(citatriOld);
            }
            if (citatriNew != null && !citatriNew.equals(citatriOld)) {
                citatriNew.getTriajeList().add(triaje);
                citatriNew = em.merge(citatriNew);
            }
            for (Consultas consultasListNewConsultas : consultasListNew) {
                if (!consultasListOld.contains(consultasListNewConsultas)) {
                    Triaje oldTriajeconsOfConsultasListNewConsultas = consultasListNewConsultas.getTriajecons();
                    consultasListNewConsultas.setTriajecons(triaje);
                    consultasListNewConsultas = em.merge(consultasListNewConsultas);
                    if (oldTriajeconsOfConsultasListNewConsultas != null && !oldTriajeconsOfConsultasListNewConsultas.equals(triaje)) {
                        oldTriajeconsOfConsultasListNewConsultas.getConsultasList().remove(consultasListNewConsultas);
                        oldTriajeconsOfConsultasListNewConsultas = em.merge(oldTriajeconsOfConsultasListNewConsultas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = triaje.getIdtri();
                if (findTriaje(id) == null) {
                    throw new NonexistentEntityException("The triaje with id " + id + " no longer exists.");
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
            Triaje triaje;
            try {
                triaje = em.getReference(Triaje.class, id);
                triaje.getIdtri();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The triaje with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Consultas> consultasListOrphanCheck = triaje.getConsultasList();
            for (Consultas consultasListOrphanCheckConsultas : consultasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Triaje (" + triaje + ") cannot be destroyed since the Consultas " + consultasListOrphanCheckConsultas + " in its consultasList field has a non-nullable triajecons field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Citas citatri = triaje.getCitatri();
            if (citatri != null) {
                citatri.getTriajeList().remove(triaje);
                citatri = em.merge(citatri);
            }
            em.remove(triaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    //Método editar
    public void editTri(Triaje triaje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            triaje = em.merge(triaje);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = triaje.getIdtri();
                if (findTriaje(id) == null) {
                    throw new NonexistentEntityException("The triaje with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    //Método para listar con el indicador
    public List<Triaje> tablaTriaje(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT t from Triaje t where t.activetri='A'");
        List <Triaje> list = (List<Triaje>) query.getResultList();
        return list;
    }

    public List<Triaje> findTriajeEntities() {
        return findTriajeEntities(true, -1, -1);
    }

    public List<Triaje> findTriajeEntities(int maxResults, int firstResult) {
        return findTriajeEntities(false, maxResults, firstResult);
    }

    private List<Triaje> findTriajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Triaje.class));
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

    public Triaje findTriaje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Triaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getTriajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Triaje> rt = cq.from(Triaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
