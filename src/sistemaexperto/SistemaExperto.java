package sistemaexperto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SistemaExperto {
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        ArchivoSecuencialIndexado archivo = new ArchivoSecuencialIndexado();
        int accion = 0;
        while(accion != 6){
            System.out.println("Escribe 1 para recuperar de manera secuencial");
            System.out.println("Escribe 2 para recuperar de manera aleatoria");
            System.out.println("Escribe 3 para actualizar un registro");
            System.out.println("Escribe 4 para borrar un registro");
            System.out.println("Escribe 5 para insertar nuevo registro");
            System.out.println("Escribe 6 para salir");
            accion = sc.nextInt();
            switch(accion){
                case 1:
                    archivo.recuperarSecuencial();
                    break;
                case 2:
                    archivo.recuperarAleatorio();
                    break;
                case 3:
                    archivo.actualizar();
                    break;
                case 4:
                    archivo.borrar();
                    break;
                case 5:
                    archivo.insertar();
                    break;
                case 6:
                    System.out.println("Adiós");
                    break;
                default:
                    System.out.println("No existe la opción "+accion);
                    break;
            }
        }
    }
}
