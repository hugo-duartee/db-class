package DAO;

import java.util.Date;
import java.util.List;
import entidades.Clientes;
import entidades.Ventas;
import controladores.VentasJpaController;

/**
 *
 * @author he-ds
 */
public class Venta implements Crud
{
    VentasJpaController controlador;
    Ventas entidad;
    String msj=null;

    public Venta(int id, float descuento, Date fecha, float montoTotal, Clientes idCliente)
    {       
        controlador = new VentasJpaController();
        entidad = new Ventas(id, descuento, fecha, montoTotal, idCliente);
    }

//    public Ventas()
//    {
//        controlador = new VentasJpaController();
//        entidad = new VentasEntity();
//    }

    @Override
    public String inserta()
    {
        try
        {
            controlador.create(entidad);
            msj = "Guardado con exito!";
        }catch(Exception ex)
        {
            msj = "No se pudo guardar";
        }
        return msj;
    }

    @Override
    public String elimina()//modelo.Clientes modelo)
    {
        try
        {
            
            controlador.destroy(entidad.getId());
            msj = "Venta: "+entidad.getId()+", Eliminado con exito";
        }catch(Exception ex)
        {
            System.out.println("Error:"+ ex.getStackTrace());
            msj = "No se pudo eliminar";
        }
        
        return msj;
    }

    @Override
    public String actualiza()
    {
        try
        {
            controlador.edit(entidad);
            msj = "actualizado exitosamente!";
        }catch(Exception ex)
        {
            System.out.println("Error: "+ex.getMessage());
            msj = "No se pudo actualizar";
        }
        
        return msj;
    }
    
    @Override
    public Object obtieneLista()
    {
        List<Ventas> results = null;
        try
        {
            results = controlador.findVentasEntities();
            msj = "Lista obtenida con exito";
        }catch(Exception ex)
        {
            msj = "No se pudo obtener la lista";
            System.out.println("Error: "+ ex.getMessage());
        }
        return results;
    }

    @Override
    public Object obtiene()
    {
        try
        {
            controlador.findVentas(entidad.getId());
            msj = "encontrado!";
        }catch(Exception ex)
        {
            msj = "no encontrado!";
            System.out.println("Error: "+ex.getStackTrace());
        }
        return msj;
    }    
}
