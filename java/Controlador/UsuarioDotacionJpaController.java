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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Usuario;
import Entities.Dotacion;
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
public class UsuarioDotacionJpaController implements Serializable {

    public UsuarioDotacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioDotacion usuarioDotacion) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Usuario idUsuarioOrphanCheck = usuarioDotacion.getIdUsuario();
        if (idUsuarioOrphanCheck != null) {
            UsuarioDotacion oldUsuarioDotacionOfIdUsuario = idUsuarioOrphanCheck.getUsuarioDotacion();
            if (oldUsuarioDotacionOfIdUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + idUsuarioOrphanCheck + " already has an item of type UsuarioDotacion whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
            }
        }
        Dotacion idDotaOrphanCheck = usuarioDotacion.getIdDota();
        if (idDotaOrphanCheck != null) {
            UsuarioDotacion oldUsuarioDotacionOfIdDota = idDotaOrphanCheck.getUsuarioDotacion();
            if (oldUsuarioDotacionOfIdDota != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Dotacion " + idDotaOrphanCheck + " already has an item of type UsuarioDotacion whose idDota column cannot be null. Please make another selection for the idDota field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario idUsuario = usuarioDotacion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                usuarioDotacion.setIdUsuario(idUsuario);
            }
            Dotacion idDota = usuarioDotacion.getIdDota();
            if (idDota != null) {
                idDota = em.getReference(idDota.getClass(), idDota.getIdDotacion());
                usuarioDotacion.setIdDota(idDota);
            }
            em.persist(usuarioDotacion);
            if (idUsuario != null) {
                idUsuario.setUsuarioDotacion(usuarioDotacion);
                idUsuario = em.merge(idUsuario);
            }
            if (idDota != null) {
                idDota.setUsuarioDotacion(usuarioDotacion);
                idDota = em.merge(idDota);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuarioDotacion(usuarioDotacion.getIdUsuDota()) != null) {
                throw new PreexistingEntityException("UsuarioDotacion " + usuarioDotacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioDotacion usuarioDotacion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioDotacion persistentUsuarioDotacion = em.find(UsuarioDotacion.class, usuarioDotacion.getIdUsuDota());
            Usuario idUsuarioOld = persistentUsuarioDotacion.getIdUsuario();
            Usuario idUsuarioNew = usuarioDotacion.getIdUsuario();
            Dotacion idDotaOld = persistentUsuarioDotacion.getIdDota();
            Dotacion idDotaNew = usuarioDotacion.getIdDota();
            List<String> illegalOrphanMessages = null;
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                UsuarioDotacion oldUsuarioDotacionOfIdUsuario = idUsuarioNew.getUsuarioDotacion();
                if (oldUsuarioDotacionOfIdUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + idUsuarioNew + " already has an item of type UsuarioDotacion whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
                }
            }
            if (idDotaNew != null && !idDotaNew.equals(idDotaOld)) {
                UsuarioDotacion oldUsuarioDotacionOfIdDota = idDotaNew.getUsuarioDotacion();
                if (oldUsuarioDotacionOfIdDota != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Dotacion " + idDotaNew + " already has an item of type UsuarioDotacion whose idDota column cannot be null. Please make another selection for the idDota field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                usuarioDotacion.setIdUsuario(idUsuarioNew);
            }
            if (idDotaNew != null) {
                idDotaNew = em.getReference(idDotaNew.getClass(), idDotaNew.getIdDotacion());
                usuarioDotacion.setIdDota(idDotaNew);
            }
            usuarioDotacion = em.merge(usuarioDotacion);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.setUsuarioDotacion(null);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.setUsuarioDotacion(usuarioDotacion);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idDotaOld != null && !idDotaOld.equals(idDotaNew)) {
                idDotaOld.setUsuarioDotacion(null);
                idDotaOld = em.merge(idDotaOld);
            }
            if (idDotaNew != null && !idDotaNew.equals(idDotaOld)) {
                idDotaNew.setUsuarioDotacion(usuarioDotacion);
                idDotaNew = em.merge(idDotaNew);
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
                String id = usuarioDotacion.getIdUsuDota();
                if (findUsuarioDotacion(id) == null) {
                    throw new NonexistentEntityException("The usuarioDotacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioDotacion usuarioDotacion;
            try {
                usuarioDotacion = em.getReference(UsuarioDotacion.class, id);
                usuarioDotacion.getIdUsuDota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioDotacion with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = usuarioDotacion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.setUsuarioDotacion(null);
                idUsuario = em.merge(idUsuario);
            }
            Dotacion idDota = usuarioDotacion.getIdDota();
            if (idDota != null) {
                idDota.setUsuarioDotacion(null);
                idDota = em.merge(idDota);
            }
            em.remove(usuarioDotacion);
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

    public List<UsuarioDotacion> findUsuarioDotacionEntities() {
        return findUsuarioDotacionEntities(true, -1, -1);
    }

    public List<UsuarioDotacion> findUsuarioDotacionEntities(int maxResults, int firstResult) {
        return findUsuarioDotacionEntities(false, maxResults, firstResult);
    }

    private List<UsuarioDotacion> findUsuarioDotacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioDotacion.class));
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

    public UsuarioDotacion findUsuarioDotacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioDotacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioDotacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioDotacion> rt = cq.from(UsuarioDotacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
