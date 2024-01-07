/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Entidades.Consultas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Triaje;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alessandra
 */
public class ConsultasJpaController implements Serializable {

    public ConsultasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ConsultasJpaController() throws NamingException{
        this.emf = Persistence.createEntityManagerFactory("PostaMedicaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Consultas consultas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Triaje triajecons = consultas.getTriajecons();
            if (triajecons != null) {
                triajecons = em.getReference(triajecons.getClass(), triajecons.getIdtri());
                consultas.setTriajecons(triajecons);
            }
            em.persist(consultas);
            if (triajecons != null) {
                triajecons.getConsultasList().add(consultas);
                triajecons = em.merge(triajecons);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Consultas consultas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consultas persistentConsultas = em.find(Consultas.class, consultas.getIdcons());
            Triaje triajeconsOld = persistentConsultas.getTriajecons();
            Triaje triajeconsNew = consultas.getTriajecons();
            if (triajeconsNew != null) {
                triajeconsNew = em.getReference(triajeconsNew.getClass(), triajeconsNew.getIdtri());
                consultas.setTriajecons(triajeconsNew);
            }
            consultas = em.merge(consultas);
            if (triajeconsOld != null && !triajeconsOld.equals(triajeconsNew)) {
                triajeconsOld.getConsultasList().remove(consultas);
                triajeconsOld = em.merge(triajeconsOld);
            }
            if (triajeconsNew != null && !triajeconsNew.equals(triajeconsOld)) {
                triajeconsNew.getConsultasList().add(consultas);
                triajeconsNew = em.merge(triajeconsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = consultas.getIdcons();
                if (findConsultas(id) == null) {
                    throw new NonexistentEntityException("The consultas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consultas consultas;
            try {
                consultas = em.getReference(Consultas.class, id);
                consultas.getIdcons();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consultas with id " + id + " no longer exists.", enfe);
            }
            Triaje triajecons = consultas.getTriajecons();
            if (triajecons != null) {
                triajecons.getConsultasList().remove(consultas);
                triajecons = em.merge(triajecons);
            }
            em.remove(consultas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    //Método para listar con el indicador
    public List<Consultas> tablaConsultas(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c from Consultas c where c.activecons='A'");
        List <Consultas> list = (List<Consultas>) query.getResultList();
        return list;
    }

    public List<Consultas> findConsultasEntities() {
        return findConsultasEntities(true, -1, -1);
    }

    public List<Consultas> findConsultasEntities(int maxResults, int firstResult) {
        return findConsultasEntities(false, maxResults, firstResult);
    }

    private List<Consultas> findConsultasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Consultas.class));
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

    public Consultas findConsultas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Consultas.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsultasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Consultas> rt = cq.from(Consultas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
