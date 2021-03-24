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
import Entities.Implementos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.UsuarioImplementos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ASUS
 */
public class ImplementosJpaController implements Serializable {

    public ImplementosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Implementos implementos) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioImplementos usuarioImplementos = implementos.getUsuarioImplementos();
            if (usuarioImplementos != null) {
                usuarioImplementos = em.getReference(usuarioImplementos.getClass(), usuarioImplementos.getIdUsuImp());
                implementos.setUsuarioImplementos(usuarioImplementos);
            }
            em.persist(implementos);
            if (usuarioImplementos != null) {
                Implementos oldTipoImplementoOfUsuarioImplementos = usuarioImplementos.getTipoImplemento();
                if (oldTipoImplementoOfUsuarioImplementos != null) {
                    oldTipoImplementoOfUsuarioImplementos.setUsuarioImplementos(null);
                    oldTipoImplementoOfUsuarioImplementos = em.merge(oldTipoImplementoOfUsuarioImplementos);
                }
                usuarioImplementos.setTipoImplemento(implementos);
                usuarioImplementos = em.merge(usuarioImplementos);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findImplementos(implementos.getImplemento()) != null) {
                throw new PreexistingEntityException("Implementos " + implementos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Implementos implementos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Implementos persistentImplementos = em.find(Implementos.class, implementos.getImplemento());
            UsuarioImplementos usuarioImplementosOld = persistentImplementos.getUsuarioImplementos();
            UsuarioImplementos usuarioImplementosNew = implementos.getUsuarioImplementos();
            List<String> illegalOrphanMessages = null;
            if (usuarioImplementosOld != null && !usuarioImplementosOld.equals(usuarioImplementosNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UsuarioImplementos " + usuarioImplementosOld + " since its tipoImplemento field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioImplementosNew != null) {
                usuarioImplementosNew = em.getReference(usuarioImplementosNew.getClass(), usuarioImplementosNew.getIdUsuImp());
                implementos.setUsuarioImplementos(usuarioImplementosNew);
            }
            implementos = em.merge(implementos);
            if (usuarioImplementosNew != null && !usuarioImplementosNew.equals(usuarioImplementosOld)) {
                Implementos oldTipoImplementoOfUsuarioImplementos = usuarioImplementosNew.getTipoImplemento();
                if (oldTipoImplementoOfUsuarioImplementos != null) {
                    oldTipoImplementoOfUsuarioImplementos.setUsuarioImplementos(null);
                    oldTipoImplementoOfUsuarioImplementos = em.merge(oldTipoImplementoOfUsuarioImplementos);
                }
                usuarioImplementosNew.setTipoImplemento(implementos);
                usuarioImplementosNew = em.merge(usuarioImplementosNew);
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
                String id = implementos.getImplemento();
                if (findImplementos(id) == null) {
                    throw new NonexistentEntityException("The implementos with id " + id + " no longer exists.");
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
            Implementos implementos;
            try {
                implementos = em.getReference(Implementos.class, id);
                implementos.getImplemento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The implementos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            UsuarioImplementos usuarioImplementosOrphanCheck = implementos.getUsuarioImplementos();
            if (usuarioImplementosOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Implementos (" + implementos + ") cannot be destroyed since the UsuarioImplementos " + usuarioImplementosOrphanCheck + " in its usuarioImplementos field has a non-nullable tipoImplemento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(implementos);
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

    public List<Implementos> findImplementosEntities() {
        return findImplementosEntities(true, -1, -1);
    }

    public List<Implementos> findImplementosEntities(int maxResults, int firstResult) {
        return findImplementosEntities(false, maxResults, firstResult);
    }

    private List<Implementos> findImplementosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Implementos.class));
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

    public Implementos findImplementos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Implementos.class, id);
        } finally {
            em.close();
        }
    }

    public int getImplementosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Implementos> rt = cq.from(Implementos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
