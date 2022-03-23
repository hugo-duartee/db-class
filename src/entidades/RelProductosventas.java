/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author he-ds
 */
@Entity
@Table(name = "rel_productosventas")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "RelProductosventas.findAll", query = "SELECT r FROM RelProductosventas r")
    , @NamedQuery(name = "RelProductosventas.findById", query = "SELECT r FROM RelProductosventas r WHERE r.id = :id")
    , @NamedQuery(name = "RelProductosventas.findByCantidad", query = "SELECT r FROM RelProductosventas r WHERE r.cantidad = :cantidad")
    , @NamedQuery(name = "RelProductosventas.findByPrecio", query = "SELECT r FROM RelProductosventas r WHERE r.precio = :precio")
    , @NamedQuery(name = "RelProductosventas.findByImporte", query = "SELECT r FROM RelProductosventas r WHERE r.importe = :importe")
})
public class RelProductosventas implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "precio")
    private float precio;
    @Basic(optional = false)
    @Column(name = "importe")
    private float importe;
    @JoinColumn(name = "idproducto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Productos idproducto;
    @JoinColumn(name = "idventa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ventas idventa;

    public RelProductosventas()
    {
    }

    public RelProductosventas(Integer id)
    {
        this.id = id;
    }

    public RelProductosventas(Integer id, int cantidad, float precio, float importe)
    {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.importe = importe;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }

    public float getPrecio()
    {
        return precio;
    }

    public void setPrecio(float precio)
    {
        this.precio = precio;
    }

    public float getImporte()
    {
        return importe;
    }

    public void setImporte(float importe)
    {
        this.importe = importe;
    }

    public Productos getIdproducto()
    {
        return idproducto;
    }

    public void setIdproducto(Productos idproducto)
    {
        this.idproducto = idproducto;
    }

    public Ventas getIdventa()
    {
        return idventa;
    }

    public void setIdventa(Ventas idventa)
    {
        this.idventa = idventa;
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
        if (!(object instanceof RelProductosventas))
        {
            return false;
        }
        RelProductosventas other = (RelProductosventas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "entidades.RelProductosventas[ id=" + id + " ]";
    }
    
}
