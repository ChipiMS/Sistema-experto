package sistemaexperto;

import java.io.IOException;

public class EvaluarPredicado {

    BaseDeConocimientos BC;
    BaseDeHechos BH;
    String meta;

    public static boolean evaluarPredicado(BaseDeConocimientos BC, BaseDeHechos BH, String meta, String regla) throws IOException {
        boolean es_hecho = false;
        int j, k;
        String[]   variables;
        String[] hechos = BH.regresaHechos();
        String nombre_predicado,nombre_meta,hecho,variables_meta, variable_regla;
        //Por cada hecho de la base de hechos
        for (j = 0; j < hechos.length; j++) {
            //Extraer el nombre de la meta y del hecho, y si son los mismos
            hecho = hechos[j].split("\\(")[0]; //nombre hecho                 .split("\\)")[0].split(",")
            nombre_meta = meta.split("\\(")[0];  //Nombre de la meta

            //Por cada hecho de la base de hechos
            if (hecho.equals(nombre_meta)) {
                //nombre_predicado = regla.split("\\(")[0];
                for (int v = 0; v < hechos.length; v++) {

                    variables_meta = meta.split("\\(")[0].split("\\)")[0]; // variable de la meta
                    variables = hechos[v].split("\\(")[1].split("\\)")[0].split(","); //variable del hecho
                    //Variable por variable de la meta
                    //Si es igual a la variable del hecho
                    if (variables[v].equals(variables_meta)) {
                        es_hecho = true; //Terminar bien
                    } else {
                        //Si es igual la variable de la meta y el nombre de la variable en la regla
                        variable_regla = regla.split("\\(")[0]; //variable de la regla ---AQUI NO ENTENDI MUY BIEN
                        if (variable_regla.equals(variables_meta)) {
                            es_hecho = true;//Terminar bien
                        } else {
                            es_hecho = false;
                            EncadenamientoAtras.inferir(BC, BH, meta);
                        }
                    }

                }
            }

        }
        return es_hecho;
    }

}
