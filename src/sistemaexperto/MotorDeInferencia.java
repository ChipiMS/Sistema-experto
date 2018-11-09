package sistemaexperto;

import java.io.IOException;
import java.util.ArrayList;

public class MotorDeInferencia {
    BaseDeConocimientos BC;
    BaseDeHechos BH;
    Clausula[] clausulas;
    String[] variables,hechos;
    ElementoCC R;
    ConjuntoConflicto conjuntoConflicto;
    ElementoCC elementoCC;
    ArrayList<String> nombreVariablesClausula;
    ArrayList<String> valoresVariablesClausula;
    boolean[] reglasUsadas;

    public MotorDeInferencia(BaseDeConocimientos BC, BaseDeHechos BH) {
        this.BC = BC;
        this.BH = BH;
    }
    
    public void inferir() throws IOException{
        int i;
        hechos=BH.regresaHechos();
        clausulas=BC.recuperarSecuencial();
        reglasUsadas = new boolean[clausulas.length];
        for(i = 0; i < reglasUsadas.length; i++){
            reglasUsadas[i] = false;
        }
        conjuntoConflicto = new ConjuntoConflicto();
        String meta="";
        while(!contenida(meta,hechos) && !conjuntoConflicto.estaVacio()){
            equiparar();
            if(!conjuntoConflicto.estaVacio()){
                String[] nuevosHechos;
                R=resolver();
                nuevosHechos=aplicar(R);
                actualizar(hechos,nuevosHechos);
            }
        }
    }
    
    private void equiparar(){
        /*Regla por regla hacer*/
        /*si regla no esta marcada o no esta en el conjunto conflicto analisar*/
        int i;
        for(i = 0; i < clausulas.length; i++){
            if(!reglasUsadas[i]){
                analisar(clausulas[i]);
                if(elementoCC.nombreVariables != null){ //si hay que agregar se agrega
                    reglasUsadas[i] = true;
                    conjuntoConflicto.agregarElemento(elementoCC); //agregar elemento al conjunto conflicto
                }
            }
        }
    }
    
    private String[] analisar(Clausula clausula){
        /*Por cada predicado negado de una regla traer los hechos*/
        /*Todos los hechos del primer predicado ejecutar comparar*/
        int i, j;
        String predicadoNegado, nombrePredicadoNegado, nombreHecho;
        ArrayList<ArrayList<String>> hechosInvolucrados = new ArrayList<ArrayList<String>>();
        ArrayList<String> hechosDelPredicado, hechosDelPrimerPredicado;
        elementoCC = new ElementoCC();
        elementoCC.clausula = clausula;
        for(i = 0; i < clausula.predicadosNegados.length; i++){
            hechosDelPredicado = new ArrayList<String>();
            hechosInvolucrados.add(hechosDelPredicado);
            predicadoNegado = clausula.predicadosNegados[i];
            nombrePredicadoNegado = predicadoNegado.split("\\(")[0];
            if(predicadoNegado.charAt(0) != '*' && predicadoNegado.charAt(0) != ' '){
                for(j = 0; j < hechos.length; j++){
                    nombreHecho = hechos[j].split("\\(")[0];
                    if(nombreHecho.equals(nombrePredicadoNegado)){
                        hechosDelPredicado.add(hechos[j]);
                    }
                }
            }
        }
        hechosDelPrimerPredicado = hechosInvolucrados.get(0);
        for(i = 0; i < hechosDelPrimerPredicado.size(); i++){
            nombreVariablesClausula = new ArrayList<String>();
            valoresVariablesClausula = new ArrayList<String>();
            comparar(hechosDelPrimerPredicado.get(i), 0, hechosInvolucrados);
        }
        return variables;
    }
    
    private void comparar(String hecho, int predicado, ArrayList<ArrayList<String>> hechosInvolucrados){
        /*variable por variable del hecho*/
        /*si la variable no esta agregada*/
            /*se agrega*/
        /*si no*//////////////////////////////////////////////////////////aqui voy
            /*se compara si es igual
              si no son iguales
              regresar error no se pudo*/
        /*fin variable por variable*/
        /*si el hecho es el ultimo predicado negado de la regla*/
            /*se aplica*/
        /*si no*/
            /*vuelve a comparar*/
        int i, j, k, agregados = 0;
        String[] variablesHecho = hecho.split("\\(")[1].split("\\)")[0].split(",");
        String[] nombresVariables = elementoCC.clausula.predicadosNegados[predicado].split("\\(")[1].split("\\)")[0].split(",");
                ArrayList<String> hechosDelSiguientePredicado;
        boolean variableEncontrada = false;
        for(i = 0; i < nombresVariables.length; i++) {
            for(j = 0; j < nombreVariablesClausula.size(); j++){
                if(nombresVariables[i].equals(nombreVariablesClausula.get(j))){
                    if(!variablesHecho[i].equals(valoresVariablesClausula.get(j))){ //no coinciden
                        for(k = 0; k < agregados; k++){
                            nombreVariablesClausula.remove(nombreVariablesClausula.size()-1);
                            valoresVariablesClausula.remove(valoresVariablesClausula.size()-1);
                        }
                        return;
                    }
                    variableEncontrada = true;
                }
            }
            if(!variableEncontrada){ //la variable no está agregada
                nombreVariablesClausula.add(nombresVariables[i]);
                valoresVariablesClausula.add(variablesHecho[i]);
                agregados++;
            }
        }
        if(predicado == hechosInvolucrados.size()-1){ //si es el último predicado negado
            elementoCC.nombreVariables = (String[])nombreVariablesClausula.toArray();
            elementoCC.variables.add((String[])valoresVariablesClausula.toArray());
        }
        else{
            hechosDelSiguientePredicado = hechosInvolucrados.get(predicado+1);
            for(i = 0; i < hechosDelSiguientePredicado.size(); i++){
                comparar(hechosDelSiguientePredicado.get(i), predicado+1, hechosInvolucrados);
            }
        }
    }
    
    private boolean contenida(String meta,String[] hechos){
        for(int i=0;i<hechos.length;i++){
            if(hechos[i].equals(meta))
                return true;
        }
        return false;
    }
    
    private ElementoCC resolver(){
        ElementoCC R;
        R = conjuntoConflicto.elementos.get(0);
        return R;
    }
    
    private String[] aplicar(ElementoCC R){
        String[] nHechos={};
        /*sustituir variable del predicado no negado
          con las variables[] eliminar regla usada del conjunto conflicto*/
        conjuntoConflicto.eliminarElemento(R); //eliminar regla usada del conjunto conflicto
        return nHechos;
    }
    
    private void actualizar(String[] hechos,String[] nuevosHechos){
        for (int i = 0; i < nuevosHechos.length; i++) {
            hechos[hechos.length]=nuevosHechos[i];
        }
    }
}
