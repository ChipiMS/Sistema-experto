package sistemaexperto;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class GUI extends JFrame{
    BaseDeConocimientos baseDeConocimientos;
    JTextArea messages;
    JPanel panelHechos;
    Predicado predicados[] = new Predicado[]{
        new Predicado("x es amigo de y", "p(x,y)", new String[][]{
            new String[]{},
            new String[]{"Chipi", "ABC", "Felipe"}
        }),
        new Predicado("x es amigo de y", "p(x,y)", new String[][]{
            new String[]{},
            new String[]{"Chipi", "ABC", "Felipe"}
        }),
        new Predicado("x es amigo de y", "p(x,y)", new String[][]{
            new String[]{},
            new String[]{"Chipi", "ABC", "Felipe"}
        })
    };
    JButton botonesHechos[];
    JPanel panelesHechos[];
    Component variablesHechos[][];
    BaseDeHechos baseDeHechos;
    /*
      ____ _   _ ___ 
     / ___| | | |_ _|
    | |  _| | | || | 
    | |_| | |_| || | 
     \____|\___/|___|*/
    GUI(BaseDeConocimientos baseDeConocimientos, BaseDeHechos baseDeHechos){
        this.baseDeConocimientos = baseDeConocimientos;
        this.baseDeHechos = baseDeHechos;
        Container cp = getContentPane();
        setSize(600, 600);
        setTitle("Compilador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        cp.add(createMenu(cp), BorderLayout.NORTH);

        panelHechos = new JPanel();
        interfazHechos(panelHechos);
        JScrollPane codeScrollPane = new JScrollPane(panelHechos);
        cp.add(codeScrollPane, BorderLayout.CENTER);
        
        messages = new JTextArea("Mensajes");
        messages.setEnabled(false);
        JScrollPane messagesScrollPane = new JScrollPane(messages);
        messagesScrollPane.setMinimumSize(new Dimension(100, 200));
        messagesScrollPane.setPreferredSize(new Dimension(100, 200));
        cp.add(messagesScrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }
    /*
     __  __              __  
    |  \/  | ___ _ __  _/_/_ 
    | |\/| |/ _ \ '_ \| | | |
    | |  | |  __/ | | | |_| |
    |_|  |_|\___|_| |_|\__,_|*/
    JMenuBar createMenu(Container cp){
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JButton buttonCompile;
        JFrame frame = (JFrame)this;
        menuBar = new JMenuBar();
        //Base de conocimientos-------------------------------------------------------------------------------------------------------------------------------------------------------------------
        menu = new JMenu("Base de conocimientos");
        menuBar.add(menu);
        menuItem = new JMenuItem("Mostrar cláusulas");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                Clausula clausulas[] = null;
                int i;
                String reglas = "";
                try {
                    clausulas = baseDeConocimientos.recuperarSecuencial();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(clausulas != null){
                    for(i = 0; i < clausulas.length; i++){
                        reglas += clausulas[i].muestraClausula()+"\n";
                    }
                }
                else{
                    reglas = "Hubo un error al recuperar las cláusulas.";
                }
                JOptionPane.showMessageDialog(cp, reglas);
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Actualizar cláusula");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                try {
                    int llave = Integer.parseInt( JOptionPane.showInputDialog(cp, "Llave de la cláusula a actualizar:", JOptionPane.INPUT_VALUE_PROPERTY));
                    Clausula clausula = baseDeConocimientos.recuperarAleatorio(llave);
                    if(clausula != null){
                        new FormularioClausula(clausula, baseDeConocimientos, true);
                    }
                    else{
                        JOptionPane.showMessageDialog(cp, "No existe una cláusula con esa llave.");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Nueva cláusula");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    int llave = Integer.parseInt( JOptionPane.showInputDialog(cp, "Llave de la nueva cláusula", JOptionPane.INPUT_VALUE_PROPERTY));
                    Clausula clausula = baseDeConocimientos.recuperarAleatorio(llave);
                    if(clausula == null){
                        clausula = new Clausula();
                        clausula.llave = llave;
                        new FormularioClausula(clausula, baseDeConocimientos, false);
                    }
                    else{
                        JOptionPane.showMessageDialog(cp, "Ya existe una cláusula con esa llave.");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Borrar cláusula");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    int llave = Integer.parseInt( JOptionPane.showInputDialog(cp, "Llave de la cláusula que se va a borrar", JOptionPane.INPUT_VALUE_PROPERTY));
                    Clausula clausula = baseDeConocimientos.recuperarAleatorio(llave);
                    if(clausula != null){
                        baseDeConocimientos.borrar(llave);
                    }
                    else{
                        JOptionPane.showMessageDialog(cp, "No existe una cláusula con esa llave.");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Ver predicados");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(cp, "No implementado.");
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Modificar predicado");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(cp, "No implementado.");
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Ver dominios");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(cp, "No implementado.");
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Modificar dominio");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(cp, "No implementado.");
            }
        });
        menu.add(menuItem);
        //Inferir-------------------------------------------------------------------------------------------------------------------------------------------------------------------
        buttonCompile = new JButton("Inferir");
        buttonCompile.setContentAreaFilled(false);
        buttonCompile.setBorderPainted(false);
        buttonCompile.setFocusPainted(false);
        buttonCompile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(cp, "No implementado.");
            }
        });
        menuBar.add(buttonCompile);
        
        return menuBar;
    }
    
    private void interfazHechos(JPanel panelHechos){
        panelHechos.setLayout(new BoxLayout(panelHechos, BoxLayout.PAGE_AXIS));
        int i, j, k;
        JPanel predicado;
        panelesHechos = new JPanel[predicados.length];
        botonesHechos = new JButton[predicados.length];
        variablesHechos = new Component[predicados.length][10];
        JComboBox combo;
        for(i = 0; i < predicados.length; i++){
            panelesHechos[i] = new JPanel();
            panelesHechos[i].setLayout(new BoxLayout(panelesHechos[i], BoxLayout.PAGE_AXIS));
            panelesHechos[i].add(new JLabel(predicados[i].nombre));
            predicado = new JPanel();
            predicado.setLayout(new FlowLayout());
            for(j = 0; j < predicados[i].variables.length; j++){
                if(predicados[i].variables[j].length == 0){
                    variablesHechos[i][j] = new JTextField();
                    variablesHechos[i][j].setPreferredSize(new Dimension(100, 30));
                }
                else{
                    combo = new JComboBox<>();
                    for(k = 0; k < predicados[i].variables[j].length; k++){
                        combo.addItem(predicados[i].variables[j][k]);
                    }
                    variablesHechos[i][j] = combo;
                }
                predicado.add(variablesHechos[i][j]);
            }
            panelesHechos[i].add(predicado);
            botonesHechos[i] = new JButton("Agregar hecho");
            botonesHechos[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae){
                    int i, indicePredicado = 0;
                    String hecho;
                    for(i = 0; i < predicados.length; i++){
                        if(ae.getSource() == botonesHechos[i]){
                            indicePredicado = i;
                        }
                    }
                    hecho = predicados[indicePredicado].predicado.split("\\(")[0];
                    hecho += "(";
                    for(i = 0; i < predicados[indicePredicado].variables.length; i++){
                        if(i > 0){
                            hecho += ",";
                        }
                        if(predicados[indicePredicado].variables[i].length == 0){
                            hecho += ((JTextField)variablesHechos[indicePredicado][i]).getText();
                        }
                        else{
                            hecho += ((JComboBox)variablesHechos[indicePredicado][i]).getSelectedItem();
                        }
                    }
                    hecho += ")";
                    panelesHechos[indicePredicado].add(new JLabel(hecho));
                    baseDeHechos.agregarHecho(hecho);
                    panelHechos.repaint();
                    panelHechos.revalidate();
                }
            });
            panelesHechos[i].add(botonesHechos[i]);
            panelHechos.add(panelesHechos[i]);
        }
    }
}