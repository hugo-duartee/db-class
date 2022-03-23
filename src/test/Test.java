/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import controladores.ClientesJpaController;
import entidades.Clientes;
import java.util.List;

/**
 *
 * @author he-ds
 */
public class Test
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        ClientesJpaController controlador = new ClientesJpaController();
        List<Clientes> client = controlador.findClientesEntities();
        
        for (int i = 0; i < client.size(); i++)
        {
            System.out.println("Id:"+client.get(i).getId()+", Nombre:"+client.get(i).getNombre()+"");
            
            
        }
    }
    
}
