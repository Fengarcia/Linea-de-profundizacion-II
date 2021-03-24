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
import Entities.Ruta;
import Entities.Ventas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ASUS
 */
public class VentasJpaController implements Serializable {

    public VentasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Usuario idUsuarioOrphanCheck = ventas.getIdUsuario();
        if (idUsuarioOrphanCheck != null) {
            Ventas oldVentasOfIdUsuario = idUsuarioOrphanCheck.getVentas();
            if (oldVentasOfIdUsuario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + idUsuarioOrphanCheck + " already has an item of type Ventas whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
            }
        }
        Ruta idRutaOrphanCheck = ventas.getIdRuta();
        if (idRutaOrphanCheck != null) {
            Ventas oldVentasOfIdRuta = idRutaOrphanCheck.getVentas();
            if (oldVentasOfIdRuta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Ruta " + idRutaOrphanCheck + " already has an item of type Ventas whose idRuta column cannot be null. Please make another selection for the idRuta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario idUsuario = ventas.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                ventas.setIdUsuario(idUsuario);
            }
            Ruta idRuta = ventas.getIdRuta();
            if (idRuta != null) {
                idRuta = em.getReference(idRuta.getClass(), idRuta.getIdRuta());
                ventas.setIdRuta(idRuta);
            }
            em.persist(ventas);
            if (idUsuario != null) {
                idUsuario.setVentas(ventas);
                idUsuario = em.merge(idUsuario);
            }
            if (idRuta != null) {
                idRuta.setVentas(ventas);
                idRuta = em.merge(idRuta);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findVentas(ventas.getIdVenta()) != null) {
                throw new PreexistingEntityException("Ventas " + ventas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getIdVenta());
            Usuario idUsuarioOld = persistentVentas.getIdUsuario();
            Usuario idUsuarioNew = ventas.getIdUsuario();
            Ruta idRutaOld = persistentVentas.getIdRuta();
            Ruta idRutaNew = ventas.getIdRuta();
            List<String> illegalOrphanMessages = null;
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                Ventas oldVentasOfIdUsuario = idUsuarioNew.getVentas();
                if (oldVentasOfIdUsuario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + idUsuarioNew + " already has an item of type Ventas whose idUsuario column cannot be null. Please make another selection for the idUsuario field.");
                }
            }
            if (idRutaNew != null && !idRutaNew.equals(idRutaOld)) {
                Ventas oldVentasOfIdRuta = idRutaNew.getVentas();
                if (oldVentasOfIdRuta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Ruta " + idRutaNew + " already has an item of type Ventas whose idRuta column cannot be null. Please make another selection for the idRuta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                ventas.setIdUsuario(idUsuarioNew);
            }
            if (idRutaNew != null) {
                idRutaNew = em.getReference(idRutaNew.getClass(), idRutaNew.getIdRuta());
                ventas.setIdRuta(idRutaNew);
            }
            ventas = em.merge(ventas);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.setVentas(null);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.setVentas(ventas);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idRutaOld != null && !idRutaOld.equals(idRutaNew)) {
                idRutaOld.setVentas(null);
                idRutaOld = em.merge(idRutaOld);
            }
            if (idRutaNew != null && !idRutaNew.equals(idRutaOld)) {
                idRutaNew.setVentas(ventas);
                idRutaNew = em.merge(idRutaNew);
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
                String id = ventas.getIdVenta();
                if (findVentas(id) == null) {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
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
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getIdVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = ventas.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.setVentas(null);
                idUsuario = em.merge(idUsuario);
            }
            Ruta idRuta = ventas.getIdRuta();
            if (idRuta != null) {
                idRuta.setVentas(null);
                idRuta = em.merge(idRuta);
            }
            em.remove(ventas);
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

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
