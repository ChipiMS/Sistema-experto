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
       Clausula cl_sustituida = SustituirVariables.sustituye(clausula,meta);//metodo para sustuir variables de la meta en la regla
       for(int i=0;i<cl_sustituida.predicadosNegados.length;i++)
       {
           predicado_negado = cl_sustituida.predicadosNegados[i];
           if(predicado_negado.charAt(0) != '*' && predicado_negado.charAt(0) != ' '){
               System.out.println( predicado_negado);
               System.out.println(clausula.predicadosNegados[i]);
               if(!EvaluarPredicado.evaluarPredicado( BC,  BH,  predicado_negado,clausula.predicadosNegados[i]))
                   return false; //return false on evaluarRegla
           }    
       }
       return true; //todas pasaron
    }
    public static Clausula sustituir(Clausula regla, String meta)
    {
        return null;
    }
    public static boolean EvaluarPredicado(BaseDeConocimientos BC, BaseDeHechos BH, String meta,String cl)
    {
        return false;
    }
}
