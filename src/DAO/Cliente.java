package DAO;

import java.util.List;
import controladores.ClientesJpaController;

/**
 *
 * @author he-ds
 */
public class Cliente implements Crud
{
    private ClientesJpaController controlador;
    private entidades.Clientes entidad;
    String msj=null;
    String prueba ="Esta es la primera prueba de GitHub";//pruebaGitHub

    public Cliente()
    {
    }
    
    public Cliente(int id, String nombre, String direccion, String rfc, String telefono)
    {
        controlador = new ClientesJpaController();
        entidad = new entidades.Clientes(id, direccion, nombre, rfc, telefono);
    }
    
    @Override
    public String inserta()
    {        
        try
        {
            //System.out.println(modeloCliente.getNombre()+modeloCliente.getId()+modeloCliente.getDireccion()+modeloCliente.getRfc()+modeloCliente.getTelefono());
            controlador.create(entidad);
            msj = "guardado con exito";
        }
        catch(Exception e)
        {
            msj = "no se pudo agregar";
            //System.out.println("error:"+e.getMessage()+ "Causa: "+  e.getCause());//+ e.getCause() + e.getLocalizedMessage());
            msj = msj +" " + e.getMessage();
        }        
        
        return msj;
    }
    
    @Override
    public String actualiza()
    {
        try
        {
            
        }catch(Exception ex)
        {
            System.out.println("Error: "+ ex.getMessage());
        }
        return msj;
    }

    @Override
    public String elimina()
    {
        try
        {
            controlador.destroy(entidad.getId());

        }
        catch(Exception e)
        {
            System.out.println("error: "+e.getMessage());
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Object obtieneLista()
    {
        List<entidades.Clientes> results = null;
       try
       {
           results = controlador.findClientesEntities();
           msj = "lista obtenida exitosamente";
       } catch (Exception ex)
       {
           System.out.println("Error: "+ex.getMessage());
           msj = "No se pudo obtener la lista";

       }
       return results;
    }
    
    @Override
    public String obtiene()
    {
        try
        {
            controlador.findClientes(entidad.getId());
            msj = "Cliente econtrado";
        } catch (Exception ex)
        {
            msj = "No se encontro el Cliente";
            System.out.println("Error: "+ ex.getMessage());
        }
        return msj;
    }
}
