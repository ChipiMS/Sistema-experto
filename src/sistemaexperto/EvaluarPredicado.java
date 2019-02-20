package sistemaexperto;

import java.io.IOException;

public class EvaluarPredicado {

    BaseDeConocimientos BC;
    BaseDeHechos BH;
    String meta;

    public static boolean evaluarPredicado(BaseDeConocimientos BC, BaseDeHechos BH, String meta, String regla) throws IOException {
        boolean es_hecho = false;
        int j, k;
        String[] metas, hecho, variables;
        String[] hechos = BH.regresaHechos();
        String nombre_predicado,nombre_meta;

        for (j = 0; j < hechos.length; j++) {
            hecho = hechos[j].split("\\(")[1].split("\\)")[0].split(","); //Extraigo el hecho
            metas = meta.split("\\(")[1].split("\\)");  //Extraigo la meta suponiendo que asi viene
            nombre_meta = meta.split("\\(")[0];  //Nombre de la meta
            
            //Por cada hecho de la base de hechos
            if (hecho[j].equals(metas)) {
                nombre_predicado = regla.split("\\(")[0];
                
                //Extraer el nombre del predicado y si son los mismos
                if (nombre_predicado.equals(nombre_meta)) {
                    variables = regla.split("\\(")[1].split("\\)")[0].split(",");
                    
                    //Variable por variable-----Si es igual a la de la meta termina bien
                    for (int i = 0; i < variables.length; i++) {
                        if (variables[i].equals(metas)) {
                            es_hecho = true;
                        } else {
                            if (nombre_predicado.equals(metas)) {
                                  es_hecho = true;
                            }else
                            {
                                es_hecho=false;
                                EncadenamientoAtras.inferir(BC, BH, meta);
                            }
                        }

                    }

                }

            }

        }
        return es_hecho;
    }

}
