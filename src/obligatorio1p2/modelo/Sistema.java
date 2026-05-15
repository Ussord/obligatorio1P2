/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obligatorio1p2.modelo;

import java.util.ArrayList;

/**
 *
 * @author Camila
 */
public class Sistema {

    private ArrayList<Tester> listaTesters;
    private Tablero tableroActual;

    public Sistema() {
        listaTesters = new ArrayList<>();
        tableroActual = new Tablero();
    }

    public ArrayList<Tester> getListaTesters() {
        return listaTesters;
    }

    public Tablero getTableroActual() {
        return tableroActual;
    }

    public void setTableroActual(Tablero tableroActual) {
        this.tableroActual = tableroActual;
    }

    public void agregarTester(Tester tester) {
        listaTesters.add(tester);
    }
    
   

}
