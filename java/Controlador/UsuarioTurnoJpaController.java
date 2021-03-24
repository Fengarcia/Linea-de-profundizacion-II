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
import Entities.Turno;
import Entities.UsuarioTurno;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ASUS
 */
public class UsuarioTurnoJpaController implements Serializable {

    public UsuarioTurnoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioTurno usuarioTurno) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Usuario idUsuarioOrphanCheck = usuarioTurno.getIdUsuario();
        if (idUsuarioOrphanCheck != null) {
            UsuarioTurno oldUsuarioTurnoOfIdUsuario = idUsuarioOrphanCheck.getUsuarioTurno();
            if (oldUsuarioTurnoOfIdUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + idUsuarioOrphanCheck + " already has an item of type UsuarioTurno whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
            }
        }
        Turno idTurnoOrphanCheck = usuarioTurno.getIdTurno();
        if (idTurnoOrphanCheck != null) {
            UsuarioTurno oldUsuarioTurnoOfIdTurno = idTurnoOrphanCheck.getUsuarioTurno();
            if (oldUsuarioTurnoOfIdTurno != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Turno " + idTurnoOrphanCheck + " already has an item of type UsuarioTurno whose idTurno column cannot be null. Please make another selection for the idTurno field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario idUsuario = usuarioTurno.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                usuarioTurno.setIdUsuario(idUsuario);
            }
            Turno idTurno = usuarioTurno.getIdTurno();
            if (idTurno != null) {
                idTurno = em.getReference(idTurno.getClass(), idTurno.getIdTurno());
                usuarioTurno.setIdTurno(idTurno);
            }
            em.persist(usuarioTurno);
            if (idUsuario != null) {
                idUsuario.setUsuarioTurno(usuarioTurno);
                idUsuario = em.merge(idUsuario);
            }
            if (idTurno != null) {
                idTurno.setUsuarioTurno(usuarioTurno);
                idTurno = em.merge(idTurno);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuarioTurno(usuarioTurno.getIdUsuTurn()) != null) {
                throw new PreexistingEntityException("UsuarioTurno " + usuarioTurno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioTurno usuarioTurno) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioTurno persistentUsuarioTurno = em.find(UsuarioTurno.class, usuarioTurno.getIdUsuTurn());
            Usuario idUsuarioOld = persistentUsuarioTurno.getIdUsuario();
            Usuario idUsuarioNew = usuarioTurno.getIdUsuario();
            Turno idTurnoOld = persistentUsuarioTurno.getIdTurno();
            Turno idTurnoNew = usuarioTurno.getIdTurno();
            List<String> illegalOrphanMessages = null;
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                UsuarioTurno oldUsuarioTurnoOfIdUsuario = idUsuarioNew.getUsuarioTurno();
                if (oldUsuarioTurnoOfIdUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + idUsuarioNew + " already has an item of type UsuarioTurno whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
                }
            }
            if (idTurnoNew != null && !idTurnoNew.equals(idTurnoOld)) {
                UsuarioTurno oldUsuarioTurnoOfIdTurno = idTurnoNew.getUsuarioTurno();
                if (oldUsuarioTurnoOfIdTurno != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Turno " + idTurnoNew + " already has an item of type UsuarioTurno whose idTurno column cannot be null. Please make another selection for the idTurno field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                usuarioTurno.setIdUsuario(idUsuarioNew);
            }
            if (idTurnoNew != null) {
                idTurnoNew = em.getReference(idTurnoNew.getClass(), idTurnoNew.getIdTurno());
                usuarioTurno.setIdTurno(idTurnoNew);
            }
            usuarioTurno = em.merge(usuarioTurno);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.setUsuarioTurno(null);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.setUsuarioTurno(usuarioTurno);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idTurnoOld != null && !idTurnoOld.equals(idTurnoNew)) {
                idTurnoOld.setUsuarioTurno(null);
                idTurnoOld = em.merge(idTurnoOld);
            }
            if (idTurnoNew != null && !idTurnoNew.equals(idTurnoOld)) {
                idTurnoNew.setUsuarioTurno(usuarioTurno);
                idTurnoNew = em.merge(idTurnoNew);
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
                String id = usuarioTurno.getIdUsuTurn();
                if (findUsuarioTurno(id) == null) {
                    throw new NonexistentEntityException("The usuarioTurno with id " + id + " no longer exists.");
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
            UsuarioTurno usuarioTurno;
            try {
                usuarioTurno = em.getReference(UsuarioTurno.class, id);
                usuarioTurno.getIdUsuTurn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioTurno with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = usuarioTurno.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.setUsuarioTurno(null);
                idUsuario = em.merge(idUsuario);
            }
            Turno idTurno = usuarioTurno.getIdTurno();
            if (idTurno != null) {
                idTurno.setUsuarioTurno(null);
                idTurno = em.merge(idTurno);
            }
            em.remove(usuarioTurno);
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

    public List<UsuarioTurno> findUsuarioTurnoEntities() {
        return findUsuarioTurnoEntities(true, -1, -1);
    }

    public List<UsuarioTurno> findUsuarioTurnoEntities(int maxResults, int firstResult) {
        return findUsuarioTurnoEntities(false, maxResults, firstResult);
    }

    private List<UsuarioTurno> findUsuarioTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioTurno.class));
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

    public UsuarioTurno findUsuarioTurno(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioTurno.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioTurno> rt = cq.from(UsuarioTurno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
