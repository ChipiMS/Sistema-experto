package sistemaexperto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SistemaExperto {
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        BaseDeConocimientos baseDeConocimientos = new BaseDeConocimientos();
        int accion = 0;
        int llave;
        Clausula clausula;
        while(accion != 8){
            System.out.println("Escribe 1 para mostrar base de conocimientos");
            System.out.println("Escribe 2 para actualizar una regla");
            System.out.println("Escribe 3 para borrar una regla");
            System.out.println("Escribe 4 para insertar una nueva regla");
            System.out.println("Escribe 5 para agregar un nuevo hecho");
            System.out.println("Escribe 6 para agregar una nueva meta");
            System.out.println("Escribe 7 para inferir");
            System.out.println("Escribe 8 para salir");
            accion = sc.nextInt();
            switch(accion){
                case 1:
                    Clausula clausulas[] = baseDeConocimientos.recuperarSecuencial();
                    for(int i = 0; i < clausulas.length; i++){
                        System.out.println(clausulas[i].muestraClausula());
                    }
                    break;
                case 2:
                    System.out.println("Llave de la regla que se va a actualizar:");
                    llave = sc.nextInt();
                    clausula = baseDeConocimientos.recuperarAleatorio(llave);
                    if(clausula != null){
                        System.out.println(clausula.muestraClausula());
                        clausula.leeClausula(llave);
                        baseDeConocimientos.actualizar(clausula);
                    }
                    break;
                case 3:
                    System.out.println("Llave de la regla que se va a borrar:");
                    llave = sc.nextInt();
                    baseDeConocimientos.borrar(llave);
                    break;
                case 4:
                    clausula = new Clausula();
                    clausula.leeClausula();
                    baseDeConocimientos.insertar(clausula);
                    break;
                case 5:
                    System.out.println("No implementado");
                    break;
                case 6:
                    System.out.println("No implementado");
                    break;
                case 7:
                    System.out.println("No implementado");
                    break;
                case 8:
                    System.out.println("Adiós");
                    break;
                default:
                    System.out.println("No existe la opción "+accion);
                    break;
            }
        }
    }
}
