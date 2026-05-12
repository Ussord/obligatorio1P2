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
public class Tester {

    private String nombre;
    private int edad;
    private int aniosExperiencia;
    private ArrayList<Testeo> listaTesteos;

    public Tester() {
        listaTesteos = new ArrayList<>();
        // Inicializa la lista cuando se crea el tester
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public ArrayList<Testeo> getListaTesteos() {
        return listaTesteos;
    }

    public void agregarTesteo(Testeo t) {
        listaTesteos.add(t);
        // Agrega un testeo a la lista del tester
    }

    public int getCantidadTests() {
        return listaTesteos.size();
    }

    @Override
    public String toString() {
        return this.getNombre();
    }

}
