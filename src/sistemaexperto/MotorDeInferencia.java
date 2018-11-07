package sistemaexperto;

import java.io.IOException;

public class MotorDeInferencia {
    BaseDeConocimientos BC;
    BaseDeHechos BH;
    Clausula[] clausulas;
    Clausula R;

    public MotorDeInferencia(BaseDeConocimientos BC, BaseDeHechos BH) {
        this.BC = BC;
        this.BH = BH;
    }
    
    public void inferir() throws IOException{
        String[] hechos=BH.regresaHechos();
        clausulas=BC.recuperarSecuencial();
        int[] conjuntoConflicto={1};
        String meta="";
        while( !contenida(meta,hechos) && conjuntoConflicto.length>0){
            conjuntoConflicto=equiparar();
            if(conjuntoConflicto.length>0){
                String nuevosHechos;
                R=resolver(conjuntoConflicto);
                nuevosHechos=aplicar();
                actualizar(hechos,nuevosHechos);
            }
        }
    }
    
    private int[] equiparar(){
        
    }
    
    private void extraeRegla(){
        
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
}
