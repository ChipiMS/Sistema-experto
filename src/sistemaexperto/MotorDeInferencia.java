package sistemaexperto;

public class MotorDeInferencia {
    BaseDeConocimientos BC;
    BaseDeHechos BH;
    Clausula R;

    public MotorDeInferencia(BaseDeConocimientos BC, BaseDeHechos BH) {
        this.BC = BC;
        this.BH = BH;
    }
    
    public void inferir(){
        String[] hechosIniciales=BH.regresaHechos();
        int[] conjuntoConflicto;
        
    }
    
    private void equiparar(){
        
    }
    
    private void extraeRegla(){
        
    }
    
    private boolean contenida(){
        
    }
    
    private boolean vacioCC(){
        
    }
    
    private Clausula resolver(String[] conjuntoConflicto){
        
    }
}
