/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author he-ds
 */
@Entity
@Table(name = "productos")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p")
    , @NamedQuery(name = "Productos.findById", query = "SELECT p FROM Productos p WHERE p.id = :id")
    , @NamedQuery(name = "Productos.findByNombre", query = "SELECT p FROM Productos p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Productos.findByPrecioactual", query = "SELECT p FROM Productos p WHERE p.precioactual = :precioactual")
    , @NamedQuery(name = "Productos.findByStock", query = "SELECT p FROM Productos p WHERE p.stock = :stock")
})
public class Productos implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "precioactual")
    private float precioactual;
    @Basic(optional = false)
    @Column(name = "stock")
    private int stock;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idproducto")
    private List<RelProductosventas> relProductosventasList;

    public Productos()
    {
    }

    public Productos(Integer id)
    {
        this.id = id;
    }

    public Productos(Integer id, String nombre, float precioactual, int stock)
    {
        this.id = id;
        this.nombre = nombre;
        this.precioactual = precioactual;
        this.stock = stock;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public float getPrecioactual()
    {
        return precioactual;
    }

    public void setPrecioactual(float precioactual)
    {
        this.precioactual = precioactual;
    }

    public int getStock()
    {
        return stock;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    @XmlTransient
    public List<RelProductosventas> getRelProductosventasList()
    {
        return relProductosventasList;
    }

    public void setRelProductosventasList(List<RelProductosventas> relProductosventasList)
    {
        this.relProductosventasList = relProductosventasList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos))
        {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entidades.Productos[ id=" + id + " ]";
    }
    
}
