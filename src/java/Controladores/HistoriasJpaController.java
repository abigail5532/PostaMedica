/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Historias;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Pacientes;
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
public class HistoriasJpaController implements Serializable {

    public HistoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public HistoriasJpaController() throws NamingException{
        this.emf = Persistence.createEntityManagerFactory("PostaMedicaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historias historias) throws PreexistingEntityException, Exception {
        if (historias.getPacientesList() == null) {
            historias.setPacientesList(new ArrayList<Pacientes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pacientes> attachedPacientesList = new ArrayList<Pacientes>();
            for (Pacientes pacientesListPacientesToAttach : historias.getPacientesList()) {
                pacientesListPacientesToAttach = em.getReference(pacientesListPacientesToAttach.getClass(), pacientesListPacientesToAttach.getIdpac());
                attachedPacientesList.add(pacientesListPacientesToAttach);
            }
            historias.setPacientesList(attachedPacientesList);
            em.persist(historias);
            for (Pacientes pacientesListPacientes : historias.getPacientesList()) {
                Historias oldHistoriapacOfPacientesListPacientes = pacientesListPacientes.getHistoriapac();
                pacientesListPacientes.setHistoriapac(historias);
                pacientesListPacientes = em.merge(pacientesListPacientes);
                if (oldHistoriapacOfPacientesListPacientes != null) {
                    oldHistoriapacOfPacientesListPacientes.getPacientesList().remove(pacientesListPacientes);
                    oldHistoriapacOfPacientesListPacientes = em.merge(oldHistoriapacOfPacientesListPacientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistorias(historias.getIdlista()) != null) {
                throw new PreexistingEntityException("Historias " + historias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historias historias) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historias persistentHistorias = em.find(Historias.class, historias.getIdlista());
            List<Pacientes> pacientesListOld = persistentHistorias.getPacientesList();
            List<Pacientes> pacientesListNew = historias.getPacientesList();
            List<String> illegalOrphanMessages = null;
            for (Pacientes pacientesListOldPacientes : pacientesListOld) {
                if (!pacientesListNew.contains(pacientesListOldPacientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pacientes " + pacientesListOldPacientes + " since its historiapac field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pacientes> attachedPacientesListNew = new ArrayList<Pacientes>();
            for (Pacientes pacientesListNewPacientesToAttach : pacientesListNew) {
                pacientesListNewPacientesToAttach = em.getReference(pacientesListNewPacientesToAttach.getClass(), pacientesListNewPacientesToAttach.getIdpac());
                attachedPacientesListNew.add(pacientesListNewPacientesToAttach);
            }
            pacientesListNew = attachedPacientesListNew;
            historias.setPacientesList(pacientesListNew);
            historias = em.merge(historias);
            for (Pacientes pacientesListNewPacientes : pacientesListNew) {
                if (!pacientesListOld.contains(pacientesListNewPacientes)) {
                    Historias oldHistoriapacOfPacientesListNewPacientes = pacientesListNewPacientes.getHistoriapac();
                    pacientesListNewPacientes.setHistoriapac(historias);
                    pacientesListNewPacientes = em.merge(pacientesListNewPacientes);
                    if (oldHistoriapacOfPacientesListNewPacientes != null && !oldHistoriapacOfPacientesListNewPacientes.equals(historias)) {
                        oldHistoriapacOfPacientesListNewPacientes.getPacientesList().remove(pacientesListNewPacientes);
                        oldHistoriapacOfPacientesListNewPacientes = em.merge(oldHistoriapacOfPacientesListNewPacientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historias.getIdlista();
                if (findHistorias(id) == null) {
                    throw new NonexistentEntityException("The historias with id " + id + " no longer exists.");
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
            Historias historias;
            try {
                historias = em.getReference(Historias.class, id);
                historias.getIdlista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historias with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pacientes> pacientesListOrphanCheck = historias.getPacientesList();
            for (Pacientes pacientesListOrphanCheckPacientes : pacientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Historias (" + historias + ") cannot be destroyed since the Pacientes " + pacientesListOrphanCheckPacientes + " in its pacientesList field has a non-nullable historiapac field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(historias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    //Método editar
    public void editLista(Historias historias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historias = em.merge(historias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id =  historias.getIdlista();
                if (findHistorias(id) == null) {
                    throw new NonexistentEntityException("The historias with id " + id + " no longer exists.");
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
    public List<Historias> tablaHistorias(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT h from Historias h where h.activelista='A'");
        List <Historias> list = (List<Historias>) query.getResultList();
        return list;
    }

    public List<Historias> findHistoriasEntities() {
        return findHistoriasEntities(true, -1, -1);
    }

    public List<Historias> findHistoriasEntities(int maxResults, int firstResult) {
        return findHistoriasEntities(false, maxResults, firstResult);
    }

    private List<Historias> findHistoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historias.class));
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

    public Historias findHistorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historias.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historias> rt = cq.from(Historias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
