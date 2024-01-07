/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Especialidades;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Medicos;
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
public class EspecialidadesJpaController implements Serializable {

    public EspecialidadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EspecialidadesJpaController() throws NamingException{
        this.emf = Persistence.createEntityManagerFactory("PostaMedicaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especialidades especialidades) throws PreexistingEntityException, Exception {
        if (especialidades.getMedicosList() == null) {
            especialidades.setMedicosList(new ArrayList<Medicos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Medicos> attachedMedicosList = new ArrayList<Medicos>();
            for (Medicos medicosListMedicosToAttach : especialidades.getMedicosList()) {
                medicosListMedicosToAttach = em.getReference(medicosListMedicosToAttach.getClass(), medicosListMedicosToAttach.getIdmed());
                attachedMedicosList.add(medicosListMedicosToAttach);
            }
            especialidades.setMedicosList(attachedMedicosList);
            em.persist(especialidades);
            for (Medicos medicosListMedicos : especialidades.getMedicosList()) {
                Especialidades oldEspecialidadmedOfMedicosListMedicos = medicosListMedicos.getEspecialidadmed();
                medicosListMedicos.setEspecialidadmed(especialidades);
                medicosListMedicos = em.merge(medicosListMedicos);
                if (oldEspecialidadmedOfMedicosListMedicos != null) {
                    oldEspecialidadmedOfMedicosListMedicos.getMedicosList().remove(medicosListMedicos);
                    oldEspecialidadmedOfMedicosListMedicos = em.merge(oldEspecialidadmedOfMedicosListMedicos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEspecialidades(especialidades.getIdesp()) != null) {
                throw new PreexistingEntityException("Especialidades " + especialidades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especialidades especialidades) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidades persistentEspecialidades = em.find(Especialidades.class, especialidades.getIdesp());
            List<Medicos> medicosListOld = persistentEspecialidades.getMedicosList();
            List<Medicos> medicosListNew = especialidades.getMedicosList();
            List<String> illegalOrphanMessages = null;
            for (Medicos medicosListOldMedicos : medicosListOld) {
                if (!medicosListNew.contains(medicosListOldMedicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Medicos " + medicosListOldMedicos + " since its especialidadmed field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Medicos> attachedMedicosListNew = new ArrayList<Medicos>();
            for (Medicos medicosListNewMedicosToAttach : medicosListNew) {
                medicosListNewMedicosToAttach = em.getReference(medicosListNewMedicosToAttach.getClass(), medicosListNewMedicosToAttach.getIdmed());
                attachedMedicosListNew.add(medicosListNewMedicosToAttach);
            }
            medicosListNew = attachedMedicosListNew;
            especialidades.setMedicosList(medicosListNew);
            especialidades = em.merge(especialidades);
            for (Medicos medicosListNewMedicos : medicosListNew) {
                if (!medicosListOld.contains(medicosListNewMedicos)) {
                    Especialidades oldEspecialidadmedOfMedicosListNewMedicos = medicosListNewMedicos.getEspecialidadmed();
                    medicosListNewMedicos.setEspecialidadmed(especialidades);
                    medicosListNewMedicos = em.merge(medicosListNewMedicos);
                    if (oldEspecialidadmedOfMedicosListNewMedicos != null && !oldEspecialidadmedOfMedicosListNewMedicos.equals(especialidades)) {
                        oldEspecialidadmedOfMedicosListNewMedicos.getMedicosList().remove(medicosListNewMedicos);
                        oldEspecialidadmedOfMedicosListNewMedicos = em.merge(oldEspecialidadmedOfMedicosListNewMedicos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = especialidades.getIdesp();
                if (findEspecialidades(id) == null) {
                    throw new NonexistentEntityException("The especialidades with id " + id + " no longer exists.");
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
            Especialidades especialidades;
            try {
                especialidades = em.getReference(Especialidades.class, id);
                especialidades.getIdesp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especialidades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Medicos> medicosListOrphanCheck = especialidades.getMedicosList();
            for (Medicos medicosListOrphanCheckMedicos : medicosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Especialidades (" + especialidades + ") cannot be destroyed since the Medicos " + medicosListOrphanCheckMedicos + " in its medicosList field has a non-nullable especialidadmed field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(especialidades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    //Método editar
    public void editEsp(Especialidades especialidades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            especialidades = em.merge(especialidades);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = especialidades.getIdesp();
                if (findEspecialidades(id) == null) {
                    throw new NonexistentEntityException("The especialidades with id " + id + " no longer exists.");
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
    public List<Especialidades> tablaEspecialidades(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT e from Especialidades e where e.activeesp='A'");
        List <Especialidades> list = (List<Especialidades>) query.getResultList();
        return list;
    }

    public List<Especialidades> findEspecialidadesEntities() {
        return findEspecialidadesEntities(true, -1, -1);
    }

    public List<Especialidades> findEspecialidadesEntities(int maxResults, int firstResult) {
        return findEspecialidadesEntities(false, maxResults, firstResult);
    }

    private List<Especialidades> findEspecialidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especialidades.class));
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

    public Especialidades findEspecialidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especialidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspecialidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especialidades> rt = cq.from(Especialidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
