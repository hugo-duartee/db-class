/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Productos;
import entidades.RelProductosventas;
import entidades.Ventas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author he-ds
 */
public class RelProductosventasJpaController implements Serializable
{

    public RelProductosventasJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(RelProductosventas relProductosventas) throws PreexistingEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos idproducto = relProductosventas.getIdproducto();
            if (idproducto != null)
            {
                idproducto = em.getReference(idproducto.getClass(), idproducto.getId());
                relProductosventas.setIdproducto(idproducto);
            }
            Ventas idventa = relProductosventas.getIdventa();
            if (idventa != null)
            {
                idventa = em.getReference(idventa.getClass(), idventa.getId());
                relProductosventas.setIdventa(idventa);
            }
            em.persist(relProductosventas);
            if (idproducto != null)
            {
                idproducto.getRelProductosventasList().add(relProductosventas);
                idproducto = em.merge(idproducto);
            }
            if (idventa != null)
            {
                idventa.getRelProductosventasList().add(relProductosventas);
                idventa = em.merge(idventa);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findRelProductosventas(relProductosventas.getId()) != null)
            {
                throw new PreexistingEntityException("RelProductosventas " + relProductosventas + " already exists.", ex);
            }
            throw ex;
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(RelProductosventas relProductosventas) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            RelProductosventas persistentRelProductosventas = em.find(RelProductosventas.class, relProductosventas.getId());
            Productos idproductoOld = persistentRelProductosventas.getIdproducto();
            Productos idproductoNew = relProductosventas.getIdproducto();
            Ventas idventaOld = persistentRelProductosventas.getIdventa();
            Ventas idventaNew = relProductosventas.getIdventa();
            if (idproductoNew != null)
            {
                idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getId());
                relProductosventas.setIdproducto(idproductoNew);
            }
            if (idventaNew != null)
            {
                idventaNew = em.getReference(idventaNew.getClass(), idventaNew.getId());
                relProductosventas.setIdventa(idventaNew);
            }
            relProductosventas = em.merge(relProductosventas);
            if (idproductoOld != null && !idproductoOld.equals(idproductoNew))
            {
                idproductoOld.getRelProductosventasList().remove(relProductosventas);
                idproductoOld = em.merge(idproductoOld);
            }
            if (idproductoNew != null && !idproductoNew.equals(idproductoOld))
            {
                idproductoNew.getRelProductosventasList().add(relProductosventas);
                idproductoNew = em.merge(idproductoNew);
            }
            if (idventaOld != null && !idventaOld.equals(idventaNew))
            {
                idventaOld.getRelProductosventasList().remove(relProductosventas);
                idventaOld = em.merge(idventaOld);
            }
            if (idventaNew != null && !idventaNew.equals(idventaOld))
            {
                idventaNew.getRelProductosventasList().add(relProductosventas);
                idventaNew = em.merge(idventaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = relProductosventas.getId();
                if (findRelProductosventas(id) == null)
                {
                    throw new NonexistentEntityException("The relProductosventas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            RelProductosventas relProductosventas;
            try
            {
                relProductosventas = em.getReference(RelProductosventas.class, id);
                relProductosventas.getId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The relProductosventas with id " + id + " no longer exists.", enfe);
            }
            Productos idproducto = relProductosventas.getIdproducto();
            if (idproducto != null)
            {
                idproducto.getRelProductosventasList().remove(relProductosventas);
                idproducto = em.merge(idproducto);
            }
            Ventas idventa = relProductosventas.getIdventa();
            if (idventa != null)
            {
                idventa.getRelProductosventasList().remove(relProductosventas);
                idventa = em.merge(idventa);
            }
            em.remove(relProductosventas);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<RelProductosventas> findRelProductosventasEntities()
    {
        return findRelProductosventasEntities(true, -1, -1);
    }

    public List<RelProductosventas> findRelProductosventasEntities(int maxResults, int firstResult)
    {
        return findRelProductosventasEntities(false, maxResults, firstResult);
    }

    private List<RelProductosventas> findRelProductosventasEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RelProductosventas.class));
            Query q = em.createQuery(cq);
            if (!all)
            {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally
        {
            em.close();
        }
    }

    public RelProductosventas findRelProductosventas(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(RelProductosventas.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getRelProductosventasCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RelProductosventas> rt = cq.from(RelProductosventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
