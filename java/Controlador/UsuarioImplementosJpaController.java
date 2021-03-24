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
import Entities.Implementos;
import Entities.Usuario;
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
public class UsuarioImplementosJpaController implements Serializable {

    public UsuarioImplementosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioImplementos usuarioImplementos) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Implementos tipoImplementoOrphanCheck = usuarioImplementos.getTipoImplemento();
        if (tipoImplementoOrphanCheck != null) {
            UsuarioImplementos oldUsuarioImplementosOfTipoImplemento = tipoImplementoOrphanCheck.getUsuarioImplementos();
            if (oldUsuarioImplementosOfTipoImplemento != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Implementos " + tipoImplementoOrphanCheck + " already has an item of type UsuarioImplementos whose tipoImplemento column cannot be null. Please make another selection for the tipoImplemento field.");
            }
        }
        Usuario idUsuarioOrphanCheck = usuarioImplementos.getIdUsuario();
        if (idUsuarioOrphanCheck != null) {
            UsuarioImplementos oldUsuarioImplementosOfIdUsuario = idUsuarioOrphanCheck.getUsuarioImplementos();
            if (oldUsuarioImplementosOfIdUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + idUsuarioOrphanCheck + " already has an item of type UsuarioImplementos whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Implementos tipoImplemento = usuarioImplementos.getTipoImplemento();
            if (tipoImplemento != null) {
                tipoImplemento = em.getReference(tipoImplemento.getClass(), tipoImplemento.getImplemento());
                usuarioImplementos.setTipoImplemento(tipoImplemento);
            }
            Usuario idUsuario = usuarioImplementos.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                usuarioImplementos.setIdUsuario(idUsuario);
            }
            em.persist(usuarioImplementos);
            if (tipoImplemento != null) {
                tipoImplemento.setUsuarioImplementos(usuarioImplementos);
                tipoImplemento = em.merge(tipoImplemento);
            }
            if (idUsuario != null) {
                idUsuario.setUsuarioImplementos(usuarioImplementos);
                idUsuario = em.merge(idUsuario);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuarioImplementos(usuarioImplementos.getIdUsuImp()) != null) {
                throw new PreexistingEntityException("UsuarioImplementos " + usuarioImplementos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioImplementos usuarioImplementos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioImplementos persistentUsuarioImplementos = em.find(UsuarioImplementos.class, usuarioImplementos.getIdUsuImp());
            Implementos tipoImplementoOld = persistentUsuarioImplementos.getTipoImplemento();
            Implementos tipoImplementoNew = usuarioImplementos.getTipoImplemento();
            Usuario idUsuarioOld = persistentUsuarioImplementos.getIdUsuario();
            Usuario idUsuarioNew = usuarioImplementos.getIdUsuario();
            List<String> illegalOrphanMessages = null;
            if (tipoImplementoNew != null && !tipoImplementoNew.equals(tipoImplementoOld)) {
                UsuarioImplementos oldUsuarioImplementosOfTipoImplemento = tipoImplementoNew.getUsuarioImplementos();
                if (oldUsuarioImplementosOfTipoImplemento != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Implementos " + tipoImplementoNew + " already has an item of type UsuarioImplementos whose tipoImplemento column cannot be null. Please make another selection for the tipoImplemento field.");
                }
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                UsuarioImplementos oldUsuarioImplementosOfIdUsuario = idUsuarioNew.getUsuarioImplementos();
                if (oldUsuarioImplementosOfIdUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + idUsuarioNew + " already has an item of type UsuarioImplementos whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoImplementoNew != null) {
                tipoImplementoNew = em.getReference(tipoImplementoNew.getClass(), tipoImplementoNew.getImplemento());
                usuarioImplementos.setTipoImplemento(tipoImplementoNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                usuarioImplementos.setIdUsuario(idUsuarioNew);
            }
            usuarioImplementos = em.merge(usuarioImplementos);
            if (tipoImplementoOld != null && !tipoImplementoOld.equals(tipoImplementoNew)) {
                tipoImplementoOld.setUsuarioImplementos(null);
                tipoImplementoOld = em.merge(tipoImplementoOld);
            }
            if (tipoImplementoNew != null && !tipoImplementoNew.equals(tipoImplementoOld)) {
                tipoImplementoNew.setUsuarioImplementos(usuarioImplementos);
                tipoImplementoNew = em.merge(tipoImplementoNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.setUsuarioImplementos(null);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.setUsuarioImplementos(usuarioImplementos);
                idUsuarioNew = em.merge(idUsuarioNew);
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
                String id = usuarioImplementos.getIdUsuImp();
                if (findUsuarioImplementos(id) == null) {
                    throw new NonexistentEntityException("The usuarioImplementos with id " + id + " no longer exists.");
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
            UsuarioImplementos usuarioImplementos;
            try {
                usuarioImplementos = em.getReference(UsuarioImplementos.class, id);
                usuarioImplementos.getIdUsuImp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioImplementos with id " + id + " no longer exists.", enfe);
            }
            Implementos tipoImplemento = usuarioImplementos.getTipoImplemento();
            if (tipoImplemento != null) {
                tipoImplemento.setUsuarioImplementos(null);
                tipoImplemento = em.merge(tipoImplemento);
            }
            Usuario idUsuario = usuarioImplementos.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.setUsuarioImplementos(null);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(usuarioImplementos);
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

    public List<UsuarioImplementos> findUsuarioImplementosEntities() {
        return findUsuarioImplementosEntities(true, -1, -1);
    }

    public List<UsuarioImplementos> findUsuarioImplementosEntities(int maxResults, int firstResult) {
        return findUsuarioImplementosEntities(false, maxResults, firstResult);
    }

    private List<UsuarioImplementos> findUsuarioImplementosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioImplementos.class));
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

    public UsuarioImplementos findUsuarioImplementos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioImplementos.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioImplementosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioImplementos> rt = cq.from(UsuarioImplementos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
