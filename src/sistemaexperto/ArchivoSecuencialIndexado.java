package sistemaexperto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class ArchivoSecuencialIndexado{
    private final int registerLength;
    private Arbol arbol;
    private int direccionSiguiente, direccionActual, borrados, desbordados, ordenados, direccionReorganizados;
    private Indice indice;
    private Clausula clausula;
    ArchivoSecuencialIndexado() throws IOException, FileNotFoundException, ClassNotFoundException {
        registerLength = 1024;
        clausula = new Clausula();
        indice = new Indice();
        recuperarArbol();
        recuperarControl();
    }
    public void actualizar() throws FileNotFoundException, IOException{
        RandomAccessFile escritor;
        Scanner sc = new Scanner(System.in);
        System.out.println("Llave a actualizar:");
        indice.llave = clausula.llave = sc.nextInt();
        indice.direccion = arbol.buscar(indice.llave);
        if(indice.direccion == -1){
            System.out.println("No hay una clausula con la llave "+indice.llave);
        }
        else{
            leeClausula();
            escritor = new RandomAccessFile("maestroClausula", "rw");
            escritor.seek(indice.direccion*registerLength);
            escribeClausula(clausula, escritor);
            escritor.close();
        }
    }
    public void borrar() throws FileNotFoundException, IOException, ClassNotFoundException{
        RandomAccessFile escritor;
        Scanner sc = new Scanner(System.in);
        System.out.println("Llave a borrar:");
        int llave = indice.llave = clausula.llave = sc.nextInt();
        indice.direccion = arbol.buscar(indice.llave);
        if(indice.direccion == -1){
            System.out.println("No hay una clausula con la llave "+indice.llave);
        }
        else{
            clausula.llave = 0;
            clausula.predicadosNegados[0] = "******************************";
            clausula.predicado = "******************************";
            escritor = new RandomAccessFile("maestroClausula", "rw");
            escritor.seek(indice.direccion*registerLength);
            escribeClausula(clausula, escritor);
            escritor.close();
            marcarIndice(indice.llave);
            arbol.borrar(llave);
            borrados++;
            reescribirControl();
        }
    }
    private void escribeIndice(Indice aEscribir, RandomAccessFile raf) throws IOException{
        raf.writeInt(aEscribir.llave);
        raf.writeInt(aEscribir.direccion);
    }
    private void escribeClausula(Clausula aEscribir, RandomAccessFile raf) throws IOException{
        int i;
        raf.writeInt(aEscribir.llave);
        for(i = 0; i < 16; i++){
            if(aEscribir.predicadosNegados[i] == null){
                aEscribir.predicadosNegados[i] = "******************************";
            }
            raf.writeChars(aEscribir.predicadosNegados[i]);
        }
        raf.writeChars(aEscribir.predicado);
    }
    public void insertar() throws IOException{
        Scanner sc = new Scanner(System.in);
        boolean existe = true;
        RandomAccessFile lector = null, escritorIndice, escritor;
        System.out.println("Clave del clausula:");
        indice.llave = clausula.llave = sc.nextInt();
        indice.direccion = direccionSiguiente;
        if(arbol.buscar(clausula.llave) != -1){
            System.out.println("La clave ya existe");
        }
        else{
            leeClausula();
            try{
                lector = new RandomAccessFile("maestroClausula", "r");
            }
            catch(FileNotFoundException e){
                existe = false;
            }
            if(existe){
                lector.close();
                escritor = new RandomAccessFile("maestroClausula", "rw");
                escritorIndice = new RandomAccessFile("indiceClausula", "rw");
                escritor.seek(escritor.length());
                escribeClausula(clausula, escritor);
                escritorIndice.seek(escritorIndice.length());
                escribeIndice(indice, escritorIndice);
                escritor.close();
                escritorIndice.close();
                desbordados++;
            }
            else{
                escritor = new RandomAccessFile("maestroClausula", "rw");
                escritorIndice = new RandomAccessFile("indiceClausula", "rw");
                escribeClausula(clausula, escritor);
                escribeIndice(indice, escritorIndice);
                escritor.close();
                escritorIndice.close();
                ordenados++;
            }
            arbol.insertar(clausula.llave, direccionSiguiente);
            direccionSiguiente++;
            reescribirControl();
        }
    }
    public void leeClausula(){
        int predicados = 0;
        boolean leerMas = true;
        Scanner sc = new Scanner(System.in);
        StringBuffer buffer;
        String predicado;
        while(leerMas){
            System.out.println("Escribe un predicado negado, si no quieres agregar otro, escribe *");
            predicado = sc.next();
            buffer = new StringBuffer(predicado);
            buffer.setLength(30);
            clausula.predicadosNegados[predicados] = buffer.toString();
            if(clausula.predicadosNegados[predicados].charAt(0) == '*'){
                leerMas = false;
            }
            predicados++;
            if(predicados == 16){
                leerMas = false;
            }
        }
        System.out.println("Escribe un predicado no negado, si no quieres agregarlo, escribe *");
        predicado = sc.next();
        buffer = new StringBuffer(predicado);
        buffer.setLength(30);
        clausula.predicado = buffer.toString();
    }
    private void marcarIndice(int llave) throws IOException, ClassNotFoundException{
        boolean existe = true;
        RandomAccessFile lector = null;
        long apuntadorFinal, ultimoApuntador;
        boolean marcado = false;
        try{
            lector = new RandomAccessFile("indiceClausula", "rw");
        }
        catch(FileNotFoundException e){
            existe = false;
        }
        if(existe){
            apuntadorFinal = lector.length();
            while((ultimoApuntador = lector.getFilePointer()) != apuntadorFinal && !marcado){
                indice = recuperaIndice(lector);
                if(indice.llave == llave){
                    lector.seek(ultimoApuntador);
                    indice.direccion = -1;
                    escribeIndice(indice, lector);
                }
            }
            lector.close();
        }
    }
    private void muestraClausula(Clausula l) {
        boolean primero = true;
        int i;
        System.out.println();
        System.out.println(l.llave);
        for(i = 0; i < 16 && l.predicadosNegados[i].charAt(0) != '*'; i++){
            if(!primero){
                System.out.print(" v ");
            }
            else{
                primero = false;
            }
            System.out.print("¬"+l.predicadosNegados[i].trim());
        }
        if(l.predicado.charAt(0) != '*'){
            if(!primero){
                System.out.print(" v ");
            }
            else{
                primero = false;
            }
            System.out.print(l.predicado.trim());
        }
        System.out.println();
    }
    private void recorreArbol(Nodo nodo, RandomAccessFile raf) throws IOException{
        if(nodo.izquierda != null){
            recorreArbol(nodo.izquierda, raf);
        }
        if(direccionActual != nodo.direccion){
            raf.seek(nodo.direccion*registerLength);
            direccionActual = nodo.direccion;
        }
        clausula = recuperaClausula(raf);
        muestraClausula(clausula);
        direccionActual++;
        if(nodo.derecha != null){
            recorreArbol(nodo.derecha, raf);
        }
    }
    private void recorreArbolReestructurar(Nodo nodo, RandomAccessFile lector, RandomAccessFile escritor) throws IOException{
        
        if(nodo.izquierda != null){
            recorreArbolReestructurar(nodo.izquierda, lector, escritor);
        }
        //System.out.println(nodo.llave+" "+nodo.direccion);
        if(direccionActual != nodo.direccion){
            lector.seek(nodo.direccion*registerLength);
            direccionActual = nodo.direccion;
        }
        clausula = recuperaClausula(lector);
        escribeClausula(clausula, escritor);
        direccionActual++;
        nodo.direccion = direccionReorganizados;
        direccionReorganizados++;
        if(nodo.derecha != null){
            recorreArbolReestructurar(nodo.derecha, lector, escritor);
        }
    }
    public void recuperarAleatorio() throws FileNotFoundException, IOException{
        RandomAccessFile lector = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Llave a recuperar:");
        indice.llave = sc.nextInt();
        indice.direccion = arbol.buscar(indice.llave);
        if(indice.direccion == -1){
            System.out.println("No hay clausulas con la llave "+indice.llave);
        }
        else{
            lector = new RandomAccessFile("maestroClausula", "r");
            lector.seek(indice.direccion*registerLength);
            clausula = recuperaClausula(lector);
            muestraClausula(clausula);
            lector.close();
        }
    }
    private void recuperarArbol() throws IOException{
        boolean existe = true;
        RandomAccessFile lector = null;
        arbol = new Arbol();
        try{
            lector = new RandomAccessFile("indiceClausula", "r");
        }
        catch(FileNotFoundException e){
            existe = false;
        }
        if(existe){
            while(lector.getFilePointer() != lector.length()){
                indice = recuperaIndice(lector);
                if(indice.direccion != -1){
                    arbol.insertar(indice.llave, indice.direccion);
                }
            }
            lector.close();
        }
    }
    private void recuperarControl() throws IOException{
        boolean existe = true;
        RandomAccessFile lector = null;
        try{
            lector = new RandomAccessFile("controlClausula", "r");
        }
        catch(FileNotFoundException e){
            existe = false;
        }
        if(existe){
            direccionSiguiente = lector.readInt();
            borrados = lector.readInt();
            desbordados = lector.readInt();
            ordenados = lector.readInt();
            lector.close();
        }
        else{
            direccionSiguiente = borrados = desbordados = ordenados = 0;
        }
    }
    private Indice recuperaIndice(RandomAccessFile raf) throws IOException{
        Indice i = new Indice();
        i.llave = raf.readInt();
        i.direccion = raf.readInt();
        return i;
    }
    private Clausula recuperaClausula(RandomAccessFile lector) throws IOException{
        int i, c;
        char premisa[] = new char[30];
        Clausula l = new Clausula();
        l.llave = lector.readInt();
        for(i = 0; i < 16; i++){
            for(c = 0; c < premisa.length; c++){
                premisa[c] = lector.readChar();
            }
            l.predicadosNegados[i] = new String(premisa).replace('\0', ' ');
        }
        for(c = 0; c < premisa.length; c++){
            premisa[c] = lector.readChar();
        }
        l.predicado = new String(premisa).replace('\0', ' ');
        return l;
    }
    public void recuperarSecuencial() throws FileNotFoundException, IOException{
        boolean existe = true;
        RandomAccessFile lector = null;
        direccionActual = 0;
        try{
            lector = new RandomAccessFile("maestroClausula", "r");
        }
        catch(FileNotFoundException e){
            existe = false;
        }
        if(existe){
            if(arbol.raiz != null){
                recorreArbol(arbol.raiz, lector);
            }
            lector.close();
        }
    }
    private void reescribirControl() throws IOException{
        boolean existe = true;
        RandomAccessFile lector = null;
        try{
            lector = new RandomAccessFile("controlClausula", "rw");
        }
        catch(FileNotFoundException e){
            existe = false;
        }
        if(existe){
            lector.writeInt(direccionSiguiente);
            lector.writeInt(borrados);
            lector.writeInt(desbordados);
            lector.writeInt(ordenados);
            lector.close();
        }
        if(0.4*ordenados < borrados+desbordados){
            reestructurar();
        }
    }
    private void reestructurar() throws IOException{
        boolean existe = true;
        RandomAccessFile lector = null, escritor;
        File file, newName;
        direccionReorganizados = direccionActual = 0;
        try{
            lector = new RandomAccessFile("maestroClausula", "r");
        }
        catch(FileNotFoundException e){
            existe = false;
        }
        if(existe){
            escritor = new RandomAccessFile("tmp", "rw");
            if(arbol.raiz != null){
                recorreArbolReestructurar(arbol.raiz, lector, escritor);
            }
            lector.close();
            escritor.close();
            file = new File("tmp");
            newName = new File("maestroClausula");
            newName.delete();
            file.renameTo(newName);
            
            lector = new RandomAccessFile("indiceClausula", "rw");
            escritor = new RandomAccessFile("tmp", "rw");
            while(lector.getFilePointer() != lector.length()){
                indice = recuperaIndice(lector);
                indice.direccion = arbol.buscar(indice.llave);
                if(indice.direccion != -1){
                    escribeIndice(indice, escritor);
                }
            }
            lector.close();
            escritor.close();
            file = new File("tmp");
            newName = new File("indiceClausula");
            newName.delete();
            file.renameTo(newName);
        }
        ordenados = ordenados+desbordados-borrados;
        direccionSiguiente = ordenados;
        desbordados = borrados = 0;
        reescribirControl();
    }
}
