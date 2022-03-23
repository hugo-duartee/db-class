/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author he-ds
 */
@Entity
@Table(name = "ventas")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v")
    , @NamedQuery(name = "Ventas.findById", query = "SELECT v FROM Ventas v WHERE v.id = :id")
    , @NamedQuery(name = "Ventas.findByDescuento", query = "SELECT v FROM Ventas v WHERE v.descuento = :descuento")
    , @NamedQuery(name = "Ventas.findByFecha", query = "SELECT v FROM Ventas v WHERE v.fecha = :fecha")
    , @NamedQuery(name = "Ventas.findByMontototal", query = "SELECT v FROM Ventas v WHERE v.montototal = :montototal")
})
public class Ventas implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descuento")
    private float descuento;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "montototal")
    private float montototal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idventa")
    private List<RelProductosventas> relProductosventasList;
    @JoinColumn(name = "idcliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clientes idcliente;

    public Ventas()
    {
    }

    public Ventas(Integer id)
    {
        this.id = id;
    }
    
     public Ventas(Integer id, float descuento, Date fecha, float montototal, Clientes idCliente)
    {
        this.id = id;
        this.descuento = descuento;
        this.fecha = fecha;
        this.montototal = montototal;
        this.idcliente = idCliente;
    }

    public Ventas(Integer id, float descuento, Date fecha, float montototal)
    {
        this.id = id;
        this.descuento = descuento;
        this.fecha = fecha;
        this.montototal = montototal;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public float getDescuento()
    {
        return descuento;
    }

    public void setDescuento(float descuento)
    {
        this.descuento = descuento;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public float getMontototal()
    {
        return montototal;
    }

    public void setMontototal(float montototal)
    {
        this.montototal = montototal;
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

    public Clientes getIdcliente()
    {
        return idcliente;
    }

    public void setIdcliente(Clientes idcliente)
    {
        this.idcliente = idcliente;
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
        if (!(object instanceof Ventas))
        {
            return false;
        }
        Ventas other = (Ventas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entidades.Ventas[ id=" + id + " ]";
    }
    
}
