/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Clientes;
import entidades.RelProductosventas;
import entidades.Ventas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author he-ds
 */
public class VentasJpaController implements Serializable
{

    public VentasJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public VentasJpaController()
    {
        emf = Persistence.createEntityManagerFactory("Proyecto02_165097PU");
    }

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) throws PreexistingEntityException, Exception
    {
        if (ventas.getRelProductosventasList() == null)
        {
            ventas.setRelProductosventasList(new ArrayList<RelProductosventas>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes idcliente = ventas.getIdcliente();
            if (idcliente != null)
            {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getId());
                ventas.setIdcliente(idcliente);
            }
            List<RelProductosventas> attachedRelProductosventasList = new ArrayList<RelProductosventas>();
            for (RelProductosventas relProductosventasListRelProductosventasToAttach : ventas.getRelProductosventasList())
            {
                relProductosventasListRelProductosventasToAttach = em.getReference(relProductosventasListRelProductosventasToAttach.getClass(), relProductosventasListRelProductosventasToAttach.getId());
                attachedRelProductosventasList.add(relProductosventasListRelProductosventasToAttach);
            }
            ventas.setRelProductosventasList(attachedRelProductosventasList);
            em.persist(ventas);
            if (idcliente != null)
            {
                idcliente.getVentasList().add(ventas);
                idcliente = em.merge(idcliente);
            }
            for (RelProductosventas relProductosventasListRelProductosventas : ventas.getRelProductosventasList())
            {
                Ventas oldIdventaOfRelProductosventasListRelProductosventas = relProductosventasListRelProductosventas.getIdventa();
                relProductosventasListRelProductosventas.setIdventa(ventas);
                relProductosventasListRelProductosventas = em.merge(relProductosventasListRelProductosventas);
                if (oldIdventaOfRelProductosventasListRelProductosventas != null)
                {
                    oldIdventaOfRelProductosventasListRelProductosventas.getRelProductosventasList().remove(relProductosventasListRelProductosventas);
                    oldIdventaOfRelProductosventasListRelProductosventas = em.merge(oldIdventaOfRelProductosventasListRelProductosventas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findVentas(ventas.getId()) != null)
            {
                throw new PreexistingEntityException("Ventas " + ventas + " already exists.", ex);
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

    public void edit(Ventas ventas) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getId());
            Clientes idclienteOld = persistentVentas.getIdcliente();
            Clientes idclienteNew = ventas.getIdcliente();
            List<RelProductosventas> relProductosventasListOld = persistentVentas.getRelProductosventasList();
            List<RelProductosventas> relProductosventasListNew = ventas.getRelProductosventasList();
            List<String> illegalOrphanMessages = null;
            for (RelProductosventas relProductosventasListOldRelProductosventas : relProductosventasListOld)
            {
                if (!relProductosventasListNew.contains(relProductosventasListOldRelProductosventas))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RelProductosventas " + relProductosventasListOldRelProductosventas + " since its idventa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idclienteNew != null)
            {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getId());
                ventas.setIdcliente(idclienteNew);
            }
            List<RelProductosventas> attachedRelProductosventasListNew = new ArrayList<RelProductosventas>();
            for (RelProductosventas relProductosventasListNewRelProductosventasToAttach : relProductosventasListNew)
            {
                relProductosventasListNewRelProductosventasToAttach = em.getReference(relProductosventasListNewRelProductosventasToAttach.getClass(), relProductosventasListNewRelProductosventasToAttach.getId());
                attachedRelProductosventasListNew.add(relProductosventasListNewRelProductosventasToAttach);
            }
            relProductosventasListNew = attachedRelProductosventasListNew;
            ventas.setRelProductosventasList(relProductosventasListNew);
            ventas = em.merge(ventas);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew))
            {
                idclienteOld.getVentasList().remove(ventas);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld))
            {
                idclienteNew.getVentasList().add(ventas);
                idclienteNew = em.merge(idclienteNew);
            }
            for (RelProductosventas relProductosventasListNewRelProductosventas : relProductosventasListNew)
            {
                if (!relProductosventasListOld.contains(relProductosventasListNewRelProductosventas))
                {
                    Ventas oldIdventaOfRelProductosventasListNewRelProductosventas = relProductosventasListNewRelProductosventas.getIdventa();
                    relProductosventasListNewRelProductosventas.setIdventa(ventas);
                    relProductosventasListNewRelProductosventas = em.merge(relProductosventasListNewRelProductosventas);
                    if (oldIdventaOfRelProductosventasListNewRelProductosventas != null && !oldIdventaOfRelProductosventasListNewRelProductosventas.equals(ventas))
                    {
                        oldIdventaOfRelProductosventasListNewRelProductosventas.getRelProductosventasList().remove(relProductosventasListNewRelProductosventas);
                        oldIdventaOfRelProductosventasListNewRelProductosventas = em.merge(oldIdventaOfRelProductosventasListNewRelProductosventas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = ventas.getId();
                if (findVentas(id) == null)
                {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
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
            Ventas ventas;
            try
            {
                ventas = em.getReference(Ventas.class, id);
                ventas.getId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RelProductosventas> relProductosventasListOrphanCheck = ventas.getRelProductosventasList();
            for (RelProductosventas relProductosventasListOrphanCheckRelProductosventas : relProductosventasListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ventas (" + ventas + ") cannot be destroyed since the RelProductosventas " + relProductosventasListOrphanCheckRelProductosventas + " in its relProductosventasList field has a non-nullable idventa field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clientes idcliente = ventas.getIdcliente();
            if (idcliente != null)
            {
                idcliente.getVentasList().remove(ventas);
                idcliente = em.merge(idcliente);
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities()
    {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult)
    {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Ventas.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getVentasCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
