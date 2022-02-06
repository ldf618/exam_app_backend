/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter02;

/**
 *
 * @author Lo
 */
public class Inicio {
    
    public static void main (String[] args){
        Prueba prueba = new Prueba ();
        
        prueba.setup();
        prueba.saveMessage();
        prueba.readMessage();
    }
    
}
