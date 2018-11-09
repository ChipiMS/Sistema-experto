package sistemaexperto;

import java.io.IOException;

public class MotorDeInferencia {
    BaseDeConocimientos BC;
    BaseDeHechos BH;
    Clausula[] clausulas;
    String[] variables,hechos;
    Clausula R;
    int[] conjuntoConflicto;

    public MotorDeInferencia(BaseDeConocimientos BC, BaseDeHechos BH) {
        this.BC = BC;
        this.BH = BH;
    }
    
    public void inferir() throws IOException{
        hechos=BH.regresaHechos();
        clausulas=BC.recuperarSecuencial();
        conjuntoConflicto[0]=1;
        String meta="";
        while( !contenida(meta,hechos) && conjuntoConflicto.length>0){
            conjuntoConflicto=equiparar();
            if(conjuntoConflicto.length>0){
                String[] nuevosHechos;
                R=resolver(conjuntoConflicto);
                nuevosHechos=aplicar(R,hechos);
                actualizar(hechos,nuevosHechos);
            }
        }
    }
    
    private int[] equiparar(){
        int[] x={0};
        /*Regla por regla hacer*/
        /*si regla no esta marcada o no esta en el conjunto conflicto*/
        for (int i = 0; i < clausulas.length; i++) {
            analisar();
        }
            /*si hay que agregar se agrega*/
        return x;
    }
    
    private String[] analisar(){
        /*Por cada predicado negado de una regla traer los hechos*/
        /*Todos los hechos del primer predicado ejecutar comparar*/
        for (int i = 0; i < clausulas.length; i++) {
            String predicadoNegado=clausulas[i].predicadosNegados[i];
            String nombrepredicado = predicadoNegado.split("\\(")[0];
            for (int j = 0; j < hechos.length; j++) {
                String nombreHecho=hechos[j].split("\\(")[0];
                if(nombreHecho.equals(nombrepredicado)){
                    comparar(hechos[j],variables);
                }
            }
        }
        return variables;
    }
    
    private void comparar(String hecho,String[] variables){
        /*variable por variable del hecho*/
        /*si la variable no esta agregada*/
            /*se agrega*/
        /*si no*/
            /*se compara si es igual
              si no son iguales
              regresar error no se pudo*/
        /*fin variable por variable*/
        /*si el hecho es el ultimo predicado negado de la regla*/
            /*se aplica*/
        /*si no*/
            /*vuelve a comparar*/
        String[] variableshecho = hecho.split("\\(")[1].split("\\)")[0].split(",");
        for (int i = 0; i < variables.length; i++) {
            for (int j = 0; j < variableshecho.length; j++) {
                if(!variables[i].equals(variableshecho[j])){
                    variables[variables.length]=variableshecho[j];
                }
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
    
    private Clausula resolver(int[] conjuntoConflicto){
        int menor=0;
        for(int i=0; i<conjuntoConflicto.length; i++){
            if(conjuntoConflicto[i]<menor){
                menor = conjuntoConflicto[i];
            }
        }
        R=clausulas[menor];
        return R;
    }
    
    private String[] aplicar(Clausula R,String[] hechos){
        String[] nHechos={};
        /*sustituir variable del predicado no negado
          con las variables[] eliminar regla usada del conjunto conflicto*/
        return nHechos;
    }
    
    private void actualizar(String[] hechos,String[] nuevosHechos){
        for (int i = 0; i < nuevosHechos.length; i++) {
            hechos[hechos.length]=nuevosHechos[i];
        }
    }
}
