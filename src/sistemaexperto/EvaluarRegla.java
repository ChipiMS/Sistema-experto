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
    boolean flag =true;
    static String meta, predicado_negado;
    
    public static boolean evaluarRegla(BaseDeConocimientos BC, BaseDeHechos BH, String meta, Clausula clausula)
    {
       String [] hechos = BH.regresaHechos();
       clausula = sustituir(clausula,meta);//metodo para sustuir variables de la meta en la regla
       for(int i=0;i<clausula.predicadosNegados.length;i++)
       {
           predicado_negado = clausula.predicadosNegados[i];
           if(predicado_negado.charAt(0) != '*' && predicado_negado.charAt(0) != ' '){
               if(!evaluarPredicado( BC,  BH,  predicado_negado))
                   return false; //return false on evaluarRegla
           }    
       }
       return true; //todas pasaron
    }
    public static Clausula sustituir(Clausula regla, String meta)
    {
        return null;
    }
    public static boolean evaluarPredicado(BaseDeConocimientos BC, BaseDeHechos BH, String meta)
    {
        return false;
    }
}
