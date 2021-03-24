/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import Controlador.exceptions.PreexistingEntityException;
import Controlador.exceptions.RollbackFailureException;
import Entities.CustomerDemographics;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author ASUS
 */
public class CustomerDemographicsJpaController implements Serializable {

    public CustomerDemographicsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CustomerDemographics customerDemographics) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(customerDemographics);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCustomerDemographics(customerDemographics.getCustomerTypeID()) != null) {
                throw new PreexistingEntityException("CustomerDemographics " + customerDemographics + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CustomerDemographics customerDemographics) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            customerDemographics = em.merge(customerDemographics);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = customerDemographics.getCustomerTypeID();
                if (findCustomerDemographics(id) == null) {
                    throw new NonexistentEntityException("The customerDemographics with id " + id + " no longer exists.");
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
            CustomerDemographics customerDemographics;
            try {
                customerDemographics = em.getReference(CustomerDemographics.class, id);
                customerDemographics.getCustomerTypeID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customerDemographics with id " + id + " no longer exists.", enfe);
            }
            em.remove(customerDemographics);
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

    public List<CustomerDemographics> findCustomerDemographicsEntities() {
        return findCustomerDemographicsEntities(true, -1, -1);
    }

    public List<CustomerDemographics> findCustomerDemographicsEntities(int maxResults, int firstResult) {
        return findCustomerDemographicsEntities(false, maxResults, firstResult);
    }

    private List<CustomerDemographics> findCustomerDemographicsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CustomerDemographics.class));
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

    public CustomerDemographics findCustomerDemographics(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CustomerDemographics.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerDemographicsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CustomerDemographics> rt = cq.from(CustomerDemographics.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
