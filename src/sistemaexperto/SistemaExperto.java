package sistemaexperto;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SistemaExperto {
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        System.out.println("p(abc,t)".split("\\(")[1].split("\\)")[0].split(",")[0]+" "+"p(abc,t)".split("\\(")[1].split("\\)")[0].split(",")[1]);
        /*BaseDeConocimientos baseDeConocimientos = new BaseDeConocimientos();
        BaseDeHechos baseDeHechos = new BaseDeHechos();
        MotorDeInferencia motorDeInferencia = new MotorDeInferencia(baseDeConocimientos,baseDeHechos);
        new GUI(baseDeConocimientos, baseDeHechos);*/
    }
}
