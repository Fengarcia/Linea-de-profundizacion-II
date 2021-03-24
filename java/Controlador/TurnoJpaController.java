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
import Entities.Turno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class TurnoJpaController implements Serializable {

    public TurnoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turno turno) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioTurno usuarioTurno = turno.getUsuarioTurno();
            if (usuarioTurno != null) {
                usuarioTurno = em.getReference(usuarioTurno.getClass(), usuarioTurno.getIdUsuTurn());
                turno.setUsuarioTurno(usuarioTurno);
            }
            em.persist(turno);
            if (usuarioTurno != null) {
                Turno oldIdTurnoOfUsuarioTurno = usuarioTurno.getIdTurno();
                if (oldIdTurnoOfUsuarioTurno != null) {
                    oldIdTurnoOfUsuarioTurno.setUsuarioTurno(null);
                    oldIdTurnoOfUsuarioTurno = em.merge(oldIdTurnoOfUsuarioTurno);
                }
                usuarioTurno.setIdTurno(turno);
                usuarioTurno = em.merge(usuarioTurno);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTurno(turno.getIdTurno()) != null) {
                throw new PreexistingEntityException("Turno " + turno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turno turno) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Turno persistentTurno = em.find(Turno.class, turno.getIdTurno());
            UsuarioTurno usuarioTurnoOld = persistentTurno.getUsuarioTurno();
            UsuarioTurno usuarioTurnoNew = turno.getUsuarioTurno();
            List<String> illegalOrphanMessages = null;
            if (usuarioTurnoOld != null && !usuarioTurnoOld.equals(usuarioTurnoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UsuarioTurno " + usuarioTurnoOld + " since its idTurno field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioTurnoNew != null) {
                usuarioTurnoNew = em.getReference(usuarioTurnoNew.getClass(), usuarioTurnoNew.getIdUsuTurn());
                turno.setUsuarioTurno(usuarioTurnoNew);
            }
            turno = em.merge(turno);
            if (usuarioTurnoNew != null && !usuarioTurnoNew.equals(usuarioTurnoOld)) {
                Turno oldIdTurnoOfUsuarioTurno = usuarioTurnoNew.getIdTurno();
                if (oldIdTurnoOfUsuarioTurno != null) {
                    oldIdTurnoOfUsuarioTurno.setUsuarioTurno(null);
                    oldIdTurnoOfUsuarioTurno = em.merge(oldIdTurnoOfUsuarioTurno);
                }
                usuarioTurnoNew.setIdTurno(turno);
                usuarioTurnoNew = em.merge(usuarioTurnoNew);
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
                String id = turno.getIdTurno();
                if (findTurno(id) == null) {
                    throw new NonexistentEntityException("The turno with id " + id + " no longer exists.");
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
            Turno turno;
            try {
                turno = em.getReference(Turno.class, id);
                turno.getIdTurno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            UsuarioTurno usuarioTurnoOrphanCheck = turno.getUsuarioTurno();
            if (usuarioTurnoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Turno (" + turno + ") cannot be destroyed since the UsuarioTurno " + usuarioTurnoOrphanCheck + " in its usuarioTurno field has a non-nullable idTurno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(turno);
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

    public List<Turno> findTurnoEntities() {
        return findTurnoEntities(true, -1, -1);
    }

    public List<Turno> findTurnoEntities(int maxResults, int firstResult) {
        return findTurnoEntities(false, maxResults, firstResult);
    }

    private List<Turno> findTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turno.class));
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

    public Turno findTurno(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turno.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turno> rt = cq.from(Turno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
