/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    
    private String nombre;
    private int edad;
    private int aniosExperiencia;
    private List<Testeo> listaTesteos;
    
    public Tester(String nombre, int edad, int aniosExperiencia) {
        this.nombre = nombre;
        this.edad = edad;
        this.aniosExperiencia = aniosExperiencia;
        listaTesteos = new ArrayList<>();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public int getAniosExperiencia() {
        return aniosExperiencia;
    }
    
    public String obtenerListaResumidaTesteos() {
        StringBuilder testeosResumidos = new StringBuilder();
        for (int i = 0; i < listaTesteos.size(); i++) {
            testeosResumidos.append("Numero: ")
                    .append(listaTesteos.get(i).getNumero())
                    .append(" Caso: ")
                    .append(listaTesteos.get(i).getCaso())
                    .append("\n");
        }
        return testeosResumidos.toString();
    }
    
    public String obtenerTesteoPorNumero(int numero) {
        for (int i = 0; i < listaTesteos.size(); i++) {
            if (listaTesteos.get(i).getNumero() == numero) {
                return listaTesteos.get(i).toString();
            }
        }
        return "No existe";
    }
    
    public int obtenerCantidadTesteos() {
        return listaTesteos.size();
    }
    
    public void agregarTesteo(Testeo t) {
        listaTesteos.add(t);
    }
    
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        texto.append("Nombre del Tester: ")
                .append(nombre)
                .append("\n")
                .append("Edad del Tester: ")
                .append(edad)
                .append("\n")
                .append("Años de experiencia del Tester: ")
                .append(aniosExperiencia)
                .append("\n")
                .append("Testeos: \n")
                .append(prepararTesteos(listaTesteos));
        return texto.toString();
    }
    
    private String prepararTesteos(List<Testeo> listaTesteos) {
        StringBuilder testeosPreparados = new StringBuilder();
        for (int i = 0; i < listaTesteos.size(); i++) {
            testeosPreparados.append(listaTesteos.get(i).toString());
        }
        return testeosPreparados.toString();
    }
    
}
