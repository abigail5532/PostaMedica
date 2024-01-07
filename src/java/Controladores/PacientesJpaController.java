/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Historias;
import Entidades.Citas;
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
public class PacientesJpaController implements Serializable {

    public PacientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public PacientesJpaController() throws NamingException{
        this.emf = Persistence.createEntityManagerFactory("PostaMedicaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pacientes pacientes) throws PreexistingEntityException, Exception {
        if (pacientes.getCitasList() == null) {
            pacientes.setCitasList(new ArrayList<Citas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historias historiapac = pacientes.getHistoriapac();
            if (historiapac != null) {
                historiapac = em.getReference(historiapac.getClass(), historiapac.getIdlista());
                pacientes.setHistoriapac(historiapac);
            }
            List<Citas> attachedCitasList = new ArrayList<Citas>();
            for (Citas citasListCitasToAttach : pacientes.getCitasList()) {
                citasListCitasToAttach = em.getReference(citasListCitasToAttach.getClass(), citasListCitasToAttach.getIdcita());
                attachedCitasList.add(citasListCitasToAttach);
            }
            pacientes.setCitasList(attachedCitasList);
            em.persist(pacientes);
            if (historiapac != null) {
                historiapac.getPacientesList().add(pacientes);
                historiapac = em.merge(historiapac);
            }
            for (Citas citasListCitas : pacientes.getCitasList()) {
                Pacientes oldPacientecitaOfCitasListCitas = citasListCitas.getPacientecita();
                citasListCitas.setPacientecita(pacientes);
                citasListCitas = em.merge(citasListCitas);
                if (oldPacientecitaOfCitasListCitas != null) {
                    oldPacientecitaOfCitasListCitas.getCitasList().remove(citasListCitas);
                    oldPacientecitaOfCitasListCitas = em.merge(oldPacientecitaOfCitasListCitas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPacientes(pacientes.getIdpac()) != null) {
                throw new PreexistingEntityException("Pacientes " + pacientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pacientes pacientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pacientes persistentPacientes = em.find(Pacientes.class, pacientes.getIdpac());
            Historias historiapacOld = persistentPacientes.getHistoriapac();
            Historias historiapacNew = pacientes.getHistoriapac();
            List<Citas> citasListOld = persistentPacientes.getCitasList();
            List<Citas> citasListNew = pacientes.getCitasList();
            List<String> illegalOrphanMessages = null;
            for (Citas citasListOldCitas : citasListOld) {
                if (!citasListNew.contains(citasListOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasListOldCitas + " since its pacientecita field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (historiapacNew != null) {
                historiapacNew = em.getReference(historiapacNew.getClass(), historiapacNew.getIdlista());
                pacientes.setHistoriapac(historiapacNew);
            }
            List<Citas> attachedCitasListNew = new ArrayList<Citas>();
            for (Citas citasListNewCitasToAttach : citasListNew) {
                citasListNewCitasToAttach = em.getReference(citasListNewCitasToAttach.getClass(), citasListNewCitasToAttach.getIdcita());
                attachedCitasListNew.add(citasListNewCitasToAttach);
            }
            citasListNew = attachedCitasListNew;
            pacientes.setCitasList(citasListNew);
            pacientes = em.merge(pacientes);
            if (historiapacOld != null && !historiapacOld.equals(historiapacNew)) {
                historiapacOld.getPacientesList().remove(pacientes);
                historiapacOld = em.merge(historiapacOld);
            }
            if (historiapacNew != null && !historiapacNew.equals(historiapacOld)) {
                historiapacNew.getPacientesList().add(pacientes);
                historiapacNew = em.merge(historiapacNew);
            }
            for (Citas citasListNewCitas : citasListNew) {
                if (!citasListOld.contains(citasListNewCitas)) {
                    Pacientes oldPacientecitaOfCitasListNewCitas = citasListNewCitas.getPacientecita();
                    citasListNewCitas.setPacientecita(pacientes);
                    citasListNewCitas = em.merge(citasListNewCitas);
                    if (oldPacientecitaOfCitasListNewCitas != null && !oldPacientecitaOfCitasListNewCitas.equals(pacientes)) {
                        oldPacientecitaOfCitasListNewCitas.getCitasList().remove(citasListNewCitas);
                        oldPacientecitaOfCitasListNewCitas = em.merge(oldPacientecitaOfCitasListNewCitas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pacientes.getIdpac();
                if (findPacientes(id) == null) {
                    throw new NonexistentEntityException("The pacientes with id " + id + " no longer exists.");
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
            Pacientes pacientes;
            try {
                pacientes = em.getReference(Pacientes.class, id);
                pacientes.getIdpac();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pacientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Citas> citasListOrphanCheck = pacientes.getCitasList();
            for (Citas citasListOrphanCheckCitas : citasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pacientes (" + pacientes + ") cannot be destroyed since the Citas " + citasListOrphanCheckCitas + " in its citasList field has a non-nullable pacientecita field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Historias historiapac = pacientes.getHistoriapac();
            if (historiapac != null) {
                historiapac.getPacientesList().remove(pacientes);
                historiapac = em.merge(historiapac);
            }
            em.remove(pacientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    //Método editar
    public void editPac(Pacientes pacientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pacientes = em.merge(pacientes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = pacientes.getIdpac();
                if (findPacientes(id) == null) {
                    throw new NonexistentEntityException("The pacientes with id " + id + " no longer exists.");
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
    public List<Pacientes> tablaPacientes(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT p from Pacientes p where p.activepac='A'");
        List <Pacientes> list = (List<Pacientes>) query.getResultList();
        return list;
    }

    public List<Pacientes> findPacientesEntities() {
        return findPacientesEntities(true, -1, -1);
    }

    public List<Pacientes> findPacientesEntities(int maxResults, int firstResult) {
        return findPacientesEntities(false, maxResults, firstResult);
    }

    private List<Pacientes> findPacientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pacientes.class));
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

    public Pacientes findPacientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pacientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pacientes> rt = cq.from(Pacientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
