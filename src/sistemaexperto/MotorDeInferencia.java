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
            analisar();
            /*si hay que agregar se agrega*/
        return x;
    }
    
    private String[] analisar(){
        for (int i = 0; i < hechos.length; i++) {
            /*Buscar en la base de hechos el primer predicado negado
            y agregar los que encontraste a variables*/
            /*mientras existan predicados negados y variables.leng>0*/
            /*Buscar en la base de hechos
              si no estan quitalos de variables*/
        }
        return variables;
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
        String[] nHechos;
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
