/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaexperto;

/**
 *
 * @author Adrian Rodriguez
 */
public class EvaluarRegla {
    
    BaseDeConocimientos BC;
    BaseDeHechos BH;
    String meta;
    public static void evaluarRegla(BaseDeConocimientos BC, BaseDeHechos BH, String meta)
    {
       String [] hechos = BH.regresaHechos();
       sustituir( BC,  BH,  meta);//metodo para sustuir variables de la meta en la regla
       for(int i=0;i<hechos.length;i++)
       {
           evaluarPredicado( BC,  BH,  meta);
       }
    }
    public static void sustituir(BaseDeConocimientos BC, BaseDeHechos BH, String meta)
    {
        
    }
    public static void evaluarPredicado(BaseDeConocimientos BC, BaseDeHechos BH, String meta)
    {
        
    }
}
