/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.Clientes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class ClientesJpaController implements Serializable
{

    public ClientesJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public ClientesJpaController()
    {
        emf=Persistence.createEntityManagerFactory("Proyecto2_165097PU");
    }

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws PreexistingEntityException, Exception
    {
        if (clientes.getVentasList() == null)
        {
            clientes.setVentasList(new ArrayList<Ventas>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ventas> attachedVentasList = new ArrayList<Ventas>();
            for (Ventas ventasListVentasToAttach : clientes.getVentasList())
            {
                ventasListVentasToAttach = em.getReference(ventasListVentasToAttach.getClass(), ventasListVentasToAttach.getId());
                attachedVentasList.add(ventasListVentasToAttach);
            }
            clientes.setVentasList(attachedVentasList);
            em.persist(clientes);
            for (Ventas ventasListVentas : clientes.getVentasList())
            {
                Clientes oldIdclienteOfVentasListVentas = ventasListVentas.getIdcliente();
                ventasListVentas.setIdcliente(clientes);
                ventasListVentas = em.merge(ventasListVentas);
                if (oldIdclienteOfVentasListVentas != null)
                {
                    oldIdclienteOfVentasListVentas.getVentasList().remove(ventasListVentas);
                    oldIdclienteOfVentasListVentas = em.merge(oldIdclienteOfVentasListVentas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findClientes(clientes.getId()) != null)
            {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
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

    public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getId());
            List<Ventas> ventasListOld = persistentClientes.getVentasList();
            List<Ventas> ventasListNew = clientes.getVentasList();
            List<String> illegalOrphanMessages = null;
            for (Ventas ventasListOldVentas : ventasListOld)
            {
                if (!ventasListNew.contains(ventasListOldVentas))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ventas " + ventasListOldVentas + " since its idcliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ventas> attachedVentasListNew = new ArrayList<Ventas>();
            for (Ventas ventasListNewVentasToAttach : ventasListNew)
            {
                ventasListNewVentasToAttach = em.getReference(ventasListNewVentasToAttach.getClass(), ventasListNewVentasToAttach.getId());
                attachedVentasListNew.add(ventasListNewVentasToAttach);
            }
            ventasListNew = attachedVentasListNew;
            clientes.setVentasList(ventasListNew);
            clientes = em.merge(clientes);
            for (Ventas ventasListNewVentas : ventasListNew)
            {
                if (!ventasListOld.contains(ventasListNewVentas))
                {
                    Clientes oldIdclienteOfVentasListNewVentas = ventasListNewVentas.getIdcliente();
                    ventasListNewVentas.setIdcliente(clientes);
                    ventasListNewVentas = em.merge(ventasListNewVentas);
                    if (oldIdclienteOfVentasListNewVentas != null && !oldIdclienteOfVentasListNewVentas.equals(clientes))
                    {
                        oldIdclienteOfVentasListNewVentas.getVentasList().remove(ventasListNewVentas);
                        oldIdclienteOfVentasListNewVentas = em.merge(oldIdclienteOfVentasListNewVentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = clientes.getId();
                if (findClientes(id) == null)
                {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try
            {
                clientes = em.getReference(Clientes.class, id);
                clientes.getId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ventas> ventasListOrphanCheck = clientes.getVentasList();
            for (Ventas ventasListOrphanCheckVentas : ventasListOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Ventas " + ventasListOrphanCheckVentas + " in its ventasList field has a non-nullable idcliente field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities()
    {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult)
    {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Clientes.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getClientesCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
