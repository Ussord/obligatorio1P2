/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package obligatorio1p2;
import obligatorio1p2.modelo.*;

/**
 *
 * @author Camila
 */
public class Obligatorio1P2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 

        // Crear tester
        Tester t = new Tester();
        t.setNombre("Camila");
        t.setEdad(28);
        t.setAniosExperiencia(2);

        // ?Crear testeos
        Testeo test1 = new Testeo();
        test1.setCaso(1);
        test1.setTester(t);

        Testeo test2 = new Testeo();
        test2.setCaso(2);
        test2.setTester(t);
        
        Testeo test3 = new Testeo();
        test3.setCaso(3);
        test3.setTester(t);

     

        //  Agregar testeos al tester
        t.agregarTesteo(test1);
        t.agregarTesteo(test2);
        t.agregarTesteo(test3);

        // ?Mostrar resultados
        System.out.println("Tester: " + t.getNombre());
        System.out.println("Cantidad de testeos: " + t.getListaTesteos().size());

         for (int i = 0; i < t.getListaTesteos().size(); i++) {
        Testeo aux = t.getListaTesteos().get(i);
        System.out.println("Testeo " + (i + 1) +
                " -> Numero: " + aux.getNumero() +
                " | Caso: " + aux.getCaso());
         }
    
        // TODO code application logic here
    }
    
}
