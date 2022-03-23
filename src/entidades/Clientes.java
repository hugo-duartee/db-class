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
@Table(name = "clientes")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findById", query = "SELECT c FROM Clientes c WHERE c.id = :id")
    , @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clientes.findByRfc", query = "SELECT c FROM Clientes c WHERE c.rfc = :rfc")
    , @NamedQuery(name = "Clientes.findByTelefono", query = "SELECT c FROM Clientes c WHERE c.telefono = :telefono")
})
public class Clientes implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "rfc")
    private String rfc;
    @Column(name = "telefono")
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcliente")
    private List<Ventas> ventasList;

    public Clientes()
    {
    }

    public Clientes(Integer id)
    {
        this.id = id;
    }

    public Clientes(Integer id, String direccion, String nombre, String rfc)
    {
        this.id = id;
        this.direccion = direccion;
        this.nombre = nombre;
        this.rfc = rfc;
    }
     public Clientes(Integer id, String direccion, String nombre, String rfc, String telefono)
    {
        this.id = id;
        this.direccion = direccion;
        this.nombre = nombre;
        this.rfc = rfc;
        this.telefono = telefono;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getRfc()
    {
        return rfc;
    }

    public void setRfc(String rfc)
    {
        this.rfc = rfc;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    @XmlTransient
    public List<Ventas> getVentasList()
    {
        return ventasList;
    }

    public void setVentasList(List<Ventas> ventasList)
    {
        this.ventasList = ventasList;
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
        if (!(object instanceof Clientes))
        {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entidades.Clientes[ id=" + id + " ]";
    }
    
}
