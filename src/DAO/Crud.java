/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author he-ds
 */
public interface Crud
{
    public String inserta();
    public String elimina();
    public String actualiza();
    public Object obtieneLista();
    public Object obtiene();
    //public Object query(String query);
}
