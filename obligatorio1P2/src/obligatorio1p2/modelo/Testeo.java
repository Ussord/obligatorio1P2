/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obligatorio1p2.modelo;

/**
 *
 * @author Camila
 */
public class Testeo {  

    private static int contador = 0; 

    private int numero; 
    private int caso; 
    private Tester tester; 
    private String parametros; 
    private String comentario; 
    private String resultado; 
    

    private char[][] matrizOriginal; 
    private char[][] matrizResultante; 
   

    public Testeo() { 
        contador++; 
        this.numero = contador; 
       
    }

    public int getNumero() { 
     
        return numero; 
    }

    public int getCaso() { 
      
        return caso; 
    }

    public void setCaso(int caso) { 
       
        this.caso = caso; 
    }

    public Tester getTester() { 
      
        return tester; 
    }

    public void setTester(Tester tester) { 
        
        this.tester = tester; 
    }

    public String getParametros() { 
       
        return parametros; 
    }

    public void setParametros(String parametros) { 
        
        this.parametros = parametros; 
    }

    public String getComentario() { 
       
        return comentario; 
    }

    public void setComentario(String comentario) { 
        
        this.comentario = comentario; 
    }

    public String getResultado() { 
      
        return resultado; 
    }

    public void setResultado(String resultado) { 
       
        this.resultado = resultado; 
    }

    public char[][] getMatrizOriginal() { 
      
        return matrizOriginal; 
    }

    public void setMatrizOriginal(char[][] matrizOriginal) { 
        
        this.matrizOriginal = matrizOriginal; 
    }

    public char[][] getMatrizResultante() { 
       
        return matrizResultante; 
    }

    public void setMatrizResultante(char[][] matrizResultante) { 
       
        this.matrizResultante = matrizResultante; 
    }
    
    
}
