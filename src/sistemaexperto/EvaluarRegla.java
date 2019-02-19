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
    public static void evaluarRegla(BaseDeConocimientos BC, BaseDeHechos BH)
    {
       String [] hechos = BH.regresaHechos();
    }
}
