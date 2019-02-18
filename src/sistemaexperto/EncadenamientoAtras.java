package sistemaexperto;

import java.io.IOException;
import java.util.ArrayList;

public class EncadenamientoAtras {
    public static boolean inferir(BaseDeConocimientos BC,BaseDeHechos bh,String meta) throws IOException {
        ArrayList<Clausula> reglas=new ArrayList<>();
        int i;
        reglas=busquedaConsecuente(meta,BC);
        for(i=0;i<reglas.size();i++){

        }
        return false;
    }
    private static ArrayList<Clausula> busquedaConsecuente(String meta,BaseDeConocimientos BC) throws IOException {
        int posicion;
        ArrayList<Clausula> cc=new ArrayList<>();
        Clausula[]Conocimiento=BC.recuperarSecuencial();

        for (posicion=0;posicion<Conocimiento.length;posicion++){
            if(Conocimiento[posicion].predicado.equals(meta))
                cc.add(Conocimiento[posicion]);
        }
        return cc;
    }
}
