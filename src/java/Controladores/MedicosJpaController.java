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
import Entidades.Especialidades;
import Entidades.Citas;
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
public class MedicosJpaController implements Serializable {

    public MedicosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public MedicosJpaController() throws NamingException{
        this.emf = Persistence.createEntityManagerFactory("PostaMedicaJPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medicos medicos) throws PreexistingEntityException, Exception {
        if (medicos.getCitasList() == null) {
            medicos.setCitasList(new ArrayList<Citas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especialidades especialidadmed = medicos.getEspecialidadmed();
            if (especialidadmed != null) {
                especialidadmed = em.getReference(especialidadmed.getClass(), especialidadmed.getIdesp());
                medicos.setEspecialidadmed(especialidadmed);
            }
            List<Citas> attachedCitasList = new ArrayList<Citas>();
            for (Citas citasListCitasToAttach : medicos.getCitasList()) {
                citasListCitasToAttach = em.getReference(citasListCitasToAttach.getClass(), citasListCitasToAttach.getIdcita());
                attachedCitasList.add(citasListCitasToAttach);
            }
            medicos.setCitasList(attachedCitasList);
            em.persist(medicos);
            if (especialidadmed != null) {
                especialidadmed.getMedicosList().add(medicos);
                especialidadmed = em.merge(especialidadmed);
            }
            for (Citas citasListCitas : medicos.getCitasList()) {
                Medicos oldEspecialidadcitaOfCitasListCitas = citasListCitas.getEspecialidadcita();
                citasListCitas.setEspecialidadcita(medicos);
                citasListCitas = em.merge(citasListCitas);
                if (oldEspecialidadcitaOfCitasListCitas != null) {
                    oldEspecialidadcitaOfCitasListCitas.getCitasList().remove(citasListCitas);
                    oldEspecialidadcitaOfCitasListCitas = em.merge(oldEspecialidadcitaOfCitasListCitas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedicos(medicos.getIdmed()) != null) {
                throw new PreexistingEntityException("Medicos " + medicos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medicos medicos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicos persistentMedicos = em.find(Medicos.class, medicos.getIdmed());
            Especialidades especialidadmedOld = persistentMedicos.getEspecialidadmed();
            Especialidades especialidadmedNew = medicos.getEspecialidadmed();
            List<Citas> citasListOld = persistentMedicos.getCitasList();
            List<Citas> citasListNew = medicos.getCitasList();
            List<String> illegalOrphanMessages = null;
            for (Citas citasListOldCitas : citasListOld) {
                if (!citasListNew.contains(citasListOldCitas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Citas " + citasListOldCitas + " since its especialidadcita field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (especialidadmedNew != null) {
                especialidadmedNew = em.getReference(especialidadmedNew.getClass(), especialidadmedNew.getIdesp());
                medicos.setEspecialidadmed(especialidadmedNew);
            }
            List<Citas> attachedCitasListNew = new ArrayList<Citas>();
            for (Citas citasListNewCitasToAttach : citasListNew) {
                citasListNewCitasToAttach = em.getReference(citasListNewCitasToAttach.getClass(), citasListNewCitasToAttach.getIdcita());
                attachedCitasListNew.add(citasListNewCitasToAttach);
            }
            citasListNew = attachedCitasListNew;
            medicos.setCitasList(citasListNew);
            medicos = em.merge(medicos);
            if (especialidadmedOld != null && !especialidadmedOld.equals(especialidadmedNew)) {
                especialidadmedOld.getMedicosList().remove(medicos);
                especialidadmedOld = em.merge(especialidadmedOld);
            }
            if (especialidadmedNew != null && !especialidadmedNew.equals(especialidadmedOld)) {
                especialidadmedNew.getMedicosList().add(medicos);
                especialidadmedNew = em.merge(especialidadmedNew);
            }
            for (Citas citasListNewCitas : citasListNew) {
                if (!citasListOld.contains(citasListNewCitas)) {
                    Medicos oldEspecialidadcitaOfCitasListNewCitas = citasListNewCitas.getEspecialidadcita();
                    citasListNewCitas.setEspecialidadcita(medicos);
                    citasListNewCitas = em.merge(citasListNewCitas);
                    if (oldEspecialidadcitaOfCitasListNewCitas != null && !oldEspecialidadcitaOfCitasListNewCitas.equals(medicos)) {
                        oldEspecialidadcitaOfCitasListNewCitas.getCitasList().remove(citasListNewCitas);
                        oldEspecialidadcitaOfCitasListNewCitas = em.merge(oldEspecialidadcitaOfCitasListNewCitas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = medicos.getIdmed();
                if (findMedicos(id) == null) {
                    throw new NonexistentEntityException("The medicos with id " + id + " no longer exists.");
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
            Medicos medicos;
            try {
                medicos = em.getReference(Medicos.class, id);
                medicos.getIdmed();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Citas> citasListOrphanCheck = medicos.getCitasList();
            for (Citas citasListOrphanCheckCitas : citasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicos (" + medicos + ") cannot be destroyed since the Citas " + citasListOrphanCheckCitas + " in its citasList field has a non-nullable especialidadcita field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Especialidades especialidadmed = medicos.getEspecialidadmed();
            if (especialidadmed != null) {
                especialidadmed.getMedicosList().remove(medicos);
                especialidadmed = em.merge(especialidadmed);
            }
            em.remove(medicos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    //Método editar
    public void editMed(Medicos medicos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            medicos = em.merge(medicos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = medicos.getIdmed();
                if (findMedicos(id) == null) {
                    throw new NonexistentEntityException("The medicos with id " + id + " no longer exists.");
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
    public List<Medicos> tablaMedicos(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT m from Medicos m where m.activemed='A'");
        List <Medicos> list = (List<Medicos>) query.getResultList();
        return list;
    }

    public List<Medicos> findMedicosEntities() {
        return findMedicosEntities(true, -1, -1);
    }

    public List<Medicos> findMedicosEntities(int maxResults, int firstResult) {
        return findMedicosEntities(false, maxResults, firstResult);
    }

    private List<Medicos> findMedicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medicos.class));
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

    public Medicos findMedicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medicos> rt = cq.from(Medicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
