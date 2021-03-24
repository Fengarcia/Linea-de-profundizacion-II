/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import Controlador.exceptions.RollbackFailureException;
import Entities.Dotacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.UsuarioDotacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ASUS
 */
public class DotacionJpaController implements Serializable {

    public DotacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dotacion dotacion) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioDotacion usuarioDotacion = dotacion.getUsuarioDotacion();
            if (usuarioDotacion != null) {
                usuarioDotacion = em.getReference(usuarioDotacion.getClass(), usuarioDotacion.getIdUsuDota());
                dotacion.setUsuarioDotacion(usuarioDotacion);
            }
            em.persist(dotacion);
            if (usuarioDotacion != null) {
                Dotacion oldIdDotaOfUsuarioDotacion = usuarioDotacion.getIdDota();
                if (oldIdDotaOfUsuarioDotacion != null) {
                    oldIdDotaOfUsuarioDotacion.setUsuarioDotacion(null);
                    oldIdDotaOfUsuarioDotacion = em.merge(oldIdDotaOfUsuarioDotacion);
                }
                usuarioDotacion.setIdDota(dotacion);
                usuarioDotacion = em.merge(usuarioDotacion);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDotacion(dotacion.getIdDotacion()) != null) {
                throw new PreexistingEntityException("Dotacion " + dotacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dotacion dotacion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Dotacion persistentDotacion = em.find(Dotacion.class, dotacion.getIdDotacion());
            UsuarioDotacion usuarioDotacionOld = persistentDotacion.getUsuarioDotacion();
            UsuarioDotacion usuarioDotacionNew = dotacion.getUsuarioDotacion();
            List<String> illegalOrphanMessages = null;
            if (usuarioDotacionOld != null && !usuarioDotacionOld.equals(usuarioDotacionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UsuarioDotacion " + usuarioDotacionOld + " since its idDota field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioDotacionNew != null) {
                usuarioDotacionNew = em.getReference(usuarioDotacionNew.getClass(), usuarioDotacionNew.getIdUsuDota());
                dotacion.setUsuarioDotacion(usuarioDotacionNew);
            }
            dotacion = em.merge(dotacion);
            if (usuarioDotacionNew != null && !usuarioDotacionNew.equals(usuarioDotacionOld)) {
                Dotacion oldIdDotaOfUsuarioDotacion = usuarioDotacionNew.getIdDota();
                if (oldIdDotaOfUsuarioDotacion != null) {
                    oldIdDotaOfUsuarioDotacion.setUsuarioDotacion(null);
                    oldIdDotaOfUsuarioDotacion = em.merge(oldIdDotaOfUsuarioDotacion);
                }
                usuarioDotacionNew.setIdDota(dotacion);
                usuarioDotacionNew = em.merge(usuarioDotacionNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = dotacion.getIdDotacion();
                if (findDotacion(id) == null) {
                    throw new NonexistentEntityException("The dotacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Dotacion dotacion;
            try {
                dotacion = em.getReference(Dotacion.class, id);
                dotacion.getIdDotacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dotacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            UsuarioDotacion usuarioDotacionOrphanCheck = dotacion.getUsuarioDotacion();
            if (usuarioDotacionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dotacion (" + dotacion + ") cannot be destroyed since the UsuarioDotacion " + usuarioDotacionOrphanCheck + " in its usuarioDotacion field has a non-nullable idDota field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(dotacion);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dotacion> findDotacionEntities() {
        return findDotacionEntities(true, -1, -1);
    }

    public List<Dotacion> findDotacionEntities(int maxResults, int firstResult) {
        return findDotacionEntities(false, maxResults, firstResult);
    }

    private List<Dotacion> findDotacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dotacion.class));
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

    public Dotacion findDotacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dotacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDotacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dotacion> rt = cq.from(Dotacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
