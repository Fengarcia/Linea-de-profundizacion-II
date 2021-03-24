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
import Entities.UsuarioImplementos;
import Entities.UsuarioTurno;
import Entities.Salario;
import Entities.Usuario;
import Entities.Ventas;
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
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioImplementos usuarioImplementos = usuario.getUsuarioImplementos();
            if (usuarioImplementos != null) {
                usuarioImplementos = em.getReference(usuarioImplementos.getClass(), usuarioImplementos.getIdUsuImp());
                usuario.setUsuarioImplementos(usuarioImplementos);
            }
            UsuarioTurno usuarioTurno = usuario.getUsuarioTurno();
            if (usuarioTurno != null) {
                usuarioTurno = em.getReference(usuarioTurno.getClass(), usuarioTurno.getIdUsuTurn());
                usuario.setUsuarioTurno(usuarioTurno);
            }
            Salario salario = usuario.getSalario();
            if (salario != null) {
                salario = em.getReference(salario.getClass(), salario.getIdSalario());
                usuario.setSalario(salario);
            }
            Ventas ventas = usuario.getVentas();
            if (ventas != null) {
                ventas = em.getReference(ventas.getClass(), ventas.getIdVenta());
                usuario.setVentas(ventas);
            }
            UsuarioDotacion usuarioDotacion = usuario.getUsuarioDotacion();
            if (usuarioDotacion != null) {
                usuarioDotacion = em.getReference(usuarioDotacion.getClass(), usuarioDotacion.getIdUsuDota());
                usuario.setUsuarioDotacion(usuarioDotacion);
            }
            em.persist(usuario);
            if (usuarioImplementos != null) {
                Usuario oldIdUsuarioOfUsuarioImplementos = usuarioImplementos.getIdUsuario();
                if (oldIdUsuarioOfUsuarioImplementos != null) {
                    oldIdUsuarioOfUsuarioImplementos.setUsuarioImplementos(null);
                    oldIdUsuarioOfUsuarioImplementos = em.merge(oldIdUsuarioOfUsuarioImplementos);
                }
                usuarioImplementos.setIdUsuario(usuario);
                usuarioImplementos = em.merge(usuarioImplementos);
            }
            if (usuarioTurno != null) {
                Usuario oldIdUsuarioOfUsuarioTurno = usuarioTurno.getIdUsuario();
                if (oldIdUsuarioOfUsuarioTurno != null) {
                    oldIdUsuarioOfUsuarioTurno.setUsuarioTurno(null);
                    oldIdUsuarioOfUsuarioTurno = em.merge(oldIdUsuarioOfUsuarioTurno);
                }
                usuarioTurno.setIdUsuario(usuario);
                usuarioTurno = em.merge(usuarioTurno);
            }
            if (salario != null) {
                Usuario oldIdUsuarioOfSalario = salario.getIdUsuario();
                if (oldIdUsuarioOfSalario != null) {
                    oldIdUsuarioOfSalario.setSalario(null);
                    oldIdUsuarioOfSalario = em.merge(oldIdUsuarioOfSalario);
                }
                salario.setIdUsuario(usuario);
                salario = em.merge(salario);
            }
            if (ventas != null) {
                Usuario oldIdUsuarioOfVentas = ventas.getIdUsuario();
                if (oldIdUsuarioOfVentas != null) {
                    oldIdUsuarioOfVentas.setVentas(null);
                    oldIdUsuarioOfVentas = em.merge(oldIdUsuarioOfVentas);
                }
                ventas.setIdUsuario(usuario);
                ventas = em.merge(ventas);
            }
            if (usuarioDotacion != null) {
                Usuario oldIdUsuarioOfUsuarioDotacion = usuarioDotacion.getIdUsuario();
                if (oldIdUsuarioOfUsuarioDotacion != null) {
                    oldIdUsuarioOfUsuarioDotacion.setUsuarioDotacion(null);
                    oldIdUsuarioOfUsuarioDotacion = em.merge(oldIdUsuarioOfUsuarioDotacion);
                }
                usuarioDotacion.setIdUsuario(usuario);
                usuarioDotacion = em.merge(usuarioDotacion);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuario(usuario.getIdUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            UsuarioImplementos usuarioImplementosOld = persistentUsuario.getUsuarioImplementos();
            UsuarioImplementos usuarioImplementosNew = usuario.getUsuarioImplementos();
            UsuarioTurno usuarioTurnoOld = persistentUsuario.getUsuarioTurno();
            UsuarioTurno usuarioTurnoNew = usuario.getUsuarioTurno();
            Salario salarioOld = persistentUsuario.getSalario();
            Salario salarioNew = usuario.getSalario();
            Ventas ventasOld = persistentUsuario.getVentas();
            Ventas ventasNew = usuario.getVentas();
            UsuarioDotacion usuarioDotacionOld = persistentUsuario.getUsuarioDotacion();
            UsuarioDotacion usuarioDotacionNew = usuario.getUsuarioDotacion();
            List<String> illegalOrphanMessages = null;
            if (usuarioImplementosOld != null && !usuarioImplementosOld.equals(usuarioImplementosNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UsuarioImplementos " + usuarioImplementosOld + " since its idUsuario field is not nullable.");
            }
            if (usuarioTurnoOld != null && !usuarioTurnoOld.equals(usuarioTurnoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UsuarioTurno " + usuarioTurnoOld + " since its idUsuario field is not nullable.");
            }
            if (salarioOld != null && !salarioOld.equals(salarioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Salario " + salarioOld + " since its idUsuario field is not nullable.");
            }
            if (ventasOld != null && !ventasOld.equals(ventasNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Ventas " + ventasOld + " since its idUsuario field is not nullable.");
            }
            if (usuarioDotacionOld != null && !usuarioDotacionOld.equals(usuarioDotacionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain UsuarioDotacion " + usuarioDotacionOld + " since its idUsuario field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioImplementosNew != null) {
                usuarioImplementosNew = em.getReference(usuarioImplementosNew.getClass(), usuarioImplementosNew.getIdUsuImp());
                usuario.setUsuarioImplementos(usuarioImplementosNew);
            }
            if (usuarioTurnoNew != null) {
                usuarioTurnoNew = em.getReference(usuarioTurnoNew.getClass(), usuarioTurnoNew.getIdUsuTurn());
                usuario.setUsuarioTurno(usuarioTurnoNew);
            }
            if (salarioNew != null) {
                salarioNew = em.getReference(salarioNew.getClass(), salarioNew.getIdSalario());
                usuario.setSalario(salarioNew);
            }
            if (ventasNew != null) {
                ventasNew = em.getReference(ventasNew.getClass(), ventasNew.getIdVenta());
                usuario.setVentas(ventasNew);
            }
            if (usuarioDotacionNew != null) {
                usuarioDotacionNew = em.getReference(usuarioDotacionNew.getClass(), usuarioDotacionNew.getIdUsuDota());
                usuario.setUsuarioDotacion(usuarioDotacionNew);
            }
            usuario = em.merge(usuario);
            if (usuarioImplementosNew != null && !usuarioImplementosNew.equals(usuarioImplementosOld)) {
                Usuario oldIdUsuarioOfUsuarioImplementos = usuarioImplementosNew.getIdUsuario();
                if (oldIdUsuarioOfUsuarioImplementos != null) {
                    oldIdUsuarioOfUsuarioImplementos.setUsuarioImplementos(null);
                    oldIdUsuarioOfUsuarioImplementos = em.merge(oldIdUsuarioOfUsuarioImplementos);
                }
                usuarioImplementosNew.setIdUsuario(usuario);
                usuarioImplementosNew = em.merge(usuarioImplementosNew);
            }
            if (usuarioTurnoNew != null && !usuarioTurnoNew.equals(usuarioTurnoOld)) {
                Usuario oldIdUsuarioOfUsuarioTurno = usuarioTurnoNew.getIdUsuario();
                if (oldIdUsuarioOfUsuarioTurno != null) {
                    oldIdUsuarioOfUsuarioTurno.setUsuarioTurno(null);
                    oldIdUsuarioOfUsuarioTurno = em.merge(oldIdUsuarioOfUsuarioTurno);
                }
                usuarioTurnoNew.setIdUsuario(usuario);
                usuarioTurnoNew = em.merge(usuarioTurnoNew);
            }
            if (salarioNew != null && !salarioNew.equals(salarioOld)) {
                Usuario oldIdUsuarioOfSalario = salarioNew.getIdUsuario();
                if (oldIdUsuarioOfSalario != null) {
                    oldIdUsuarioOfSalario.setSalario(null);
                    oldIdUsuarioOfSalario = em.merge(oldIdUsuarioOfSalario);
                }
                salarioNew.setIdUsuario(usuario);
                salarioNew = em.merge(salarioNew);
            }
            if (ventasNew != null && !ventasNew.equals(ventasOld)) {
                Usuario oldIdUsuarioOfVentas = ventasNew.getIdUsuario();
                if (oldIdUsuarioOfVentas != null) {
                    oldIdUsuarioOfVentas.setVentas(null);
                    oldIdUsuarioOfVentas = em.merge(oldIdUsuarioOfVentas);
                }
                ventasNew.setIdUsuario(usuario);
                ventasNew = em.merge(ventasNew);
            }
            if (usuarioDotacionNew != null && !usuarioDotacionNew.equals(usuarioDotacionOld)) {
                Usuario oldIdUsuarioOfUsuarioDotacion = usuarioDotacionNew.getIdUsuario();
                if (oldIdUsuarioOfUsuarioDotacion != null) {
                    oldIdUsuarioOfUsuarioDotacion.setUsuarioDotacion(null);
                    oldIdUsuarioOfUsuarioDotacion = em.merge(oldIdUsuarioOfUsuarioDotacion);
                }
                usuarioDotacionNew.setIdUsuario(usuario);
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
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            UsuarioImplementos usuarioImplementosOrphanCheck = usuario.getUsuarioImplementos();
            if (usuarioImplementosOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioImplementos " + usuarioImplementosOrphanCheck + " in its usuarioImplementos field has a non-nullable idUsuario field.");
            }
            UsuarioTurno usuarioTurnoOrphanCheck = usuario.getUsuarioTurno();
            if (usuarioTurnoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioTurno " + usuarioTurnoOrphanCheck + " in its usuarioTurno field has a non-nullable idUsuario field.");
            }
            Salario salarioOrphanCheck = usuario.getSalario();
            if (salarioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Salario " + salarioOrphanCheck + " in its salario field has a non-nullable idUsuario field.");
            }
            Ventas ventasOrphanCheck = usuario.getVentas();
            if (ventasOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Ventas " + ventasOrphanCheck + " in its ventas field has a non-nullable idUsuario field.");
            }
            UsuarioDotacion usuarioDotacionOrphanCheck = usuario.getUsuarioDotacion();
            if (usuarioDotacionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioDotacion " + usuarioDotacionOrphanCheck + " in its usuarioDotacion field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
