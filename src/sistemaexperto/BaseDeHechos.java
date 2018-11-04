package sistemaexperto;

import java.util.ArrayList;

public class BaseDeHechos {
    ArrayList<String> hechos;

    public BaseDeHechos() {
        hechos = new ArrayList<>();
    }
    
    void agregarHecho(String hecho){
        hechos.add(hecho);
    }
    String[] regresaHechos(){
        return (String[])hechos.toArray();
    }
}
