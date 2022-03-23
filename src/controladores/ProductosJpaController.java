/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.Productos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.RelProductosventas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author he-ds
 */
public class ProductosJpaController implements Serializable
{

    public ProductosJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Productos productos) throws PreexistingEntityException, Exception
    {
        if (productos.getRelProductosventasList() == null)
        {
            productos.setRelProductosventasList(new ArrayList<RelProductosventas>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RelProductosventas> attachedRelProductosventasList = new ArrayList<RelProductosventas>();
            for (RelProductosventas relProductosventasListRelProductosventasToAttach : productos.getRelProductosventasList())
            {
                relProductosventasListRelProductosventasToAttach = em.getReference(relProductosventasListRelProductosventasToAttach.getClass(), relProductosventasListRelProductosventasToAttach.getId());
                attachedRelProductosventasList.add(relProductosventasListRelProductosventasToAttach);
            }
            productos.setRelProductosventasList(attachedRelProductosventasList);
            em.persist(productos);
            for (RelProductosventas relProductosventasListRelProductosventas : productos.getRelProductosventasList())
            {
                Productos oldIdproductoOfRelProductosventasListRelProductosventas = relProductosventasListRelProductosventas.getIdproducto();
                relProductosventasListRelProductosventas.setIdproducto(productos);
                relProductosventasListRelProductosventas = em.merge(relProductosventasListRelProductosventas);
                if (oldIdproductoOfRelProductosventasListRelProductosventas != null)
                {
                    oldIdproductoOfRelProductosventasListRelProductosventas.getRelProductosventasList().remove(relProductosventasListRelProductosventas);
                    oldIdproductoOfRelProductosventasListRelProductosventas = em.merge(oldIdproductoOfRelProductosventasListRelProductosventas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findProductos(productos.getId()) != null)
            {
                throw new PreexistingEntityException("Productos " + productos + " already exists.", ex);
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

    public void edit(Productos productos) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos persistentProductos = em.find(Productos.class, productos.getId());
            List<RelProductosventas> relProductosventasListOld = persistentProductos.getRelProductosventasList();
            List<RelProductosventas> relProductosventasListNew = productos.getRelProductosventasList();
            List<String> illegalOrphanMessages = null;
            for (RelProductosventas relProductosventasListOldRelProductosventas : relProductosventasListOld)
            {
                if (!relProductosventasListNew.contains(relProductosventasListOldRelProductosventas))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RelProductosventas " + relProductosventasListOldRelProductosventas + " since its idproducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RelProductosventas> attachedRelProductosventasListNew = new ArrayList<RelProductosventas>();
            for (RelProductosventas relProductosventasListNewRelProductosventasToAttach : relProductosventasListNew)
            {
                relProductosventasListNewRelProductosventasToAttach = em.getReference(relProductosventasListNewRelProductosventasToAttach.getClass(), relProductosventasListNewRelProductosventasToAttach.getId());
                attachedRelProductosventasListNew.add(relProductosventasListNewRelProductosventasToAttach);
            }
            relProductosventasListNew = attachedRelProductosventasListNew;
            productos.setRelProductosventasList(relProductosventasListNew);
            productos = em.merge(productos);
            for (RelProductosventas relProductosventasListNewRelProductosventas : relProductosventasListNew)
            {
                if (!relProductosventasListOld.contains(relProductosventasListNewRelProductosventas))
                {
                    Productos oldIdproductoOfRelProductosventasListNewRelProductosventas = relProductosventasListNewRelProductosventas.getIdproducto();
                    relProductosventasListNewRelProductosventas.setIdproducto(productos);
                    relProductosventasListNewRelProductosventas = em.merge(relProductosventasListNewRelProductosventas);
                    if (oldIdproductoOfRelProductosventasListNewRelProductosventas != null && !oldIdproductoOfRelProductosventasListNewRelProductosventas.equals(productos))
                    {
                        oldIdproductoOfRelProductosventasListNewRelProductosventas.getRelProductosventasList().remove(relProductosventasListNewRelProductosventas);
                        oldIdproductoOfRelProductosventasListNewRelProductosventas = em.merge(oldIdproductoOfRelProductosventasListNewRelProductosventas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = productos.getId();
                if (findProductos(id) == null)
                {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos productos;
            try
            {
                productos = em.getReference(Productos.class, id);
                productos.getId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RelProductosventas> relProductosventasListOrphanCheck = productos.getRelProductosventasList();
            for (RelProductosventas relProductosventasListOrphanCheckRelProductosventas : relProductosventasListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productos (" + productos + ") cannot be destroyed since the RelProductosventas " + relProductosventasListOrphanCheckRelProductosventas + " in its relProductosventasList field has a non-nullable idproducto field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(productos);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Productos> findProductosEntities()
    {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult)
    {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
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

    public Productos findProductos(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Productos.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getProductosCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
