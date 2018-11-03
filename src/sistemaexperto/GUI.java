package sistemaexperto;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class GUI extends JFrame{
    BaseDeConocimientos baseDeConocimientos;
    JTextArea messages;
    JPanel panelHechos;
    /*
      ____ _   _ ___ 
     / ___| | | |_ _|
    | |  _| | | || | 
    | |_| | |_| || | 
     \____|\___/|___|*/
    GUI(BaseDeConocimientos baseDeConocimientos){
        this.baseDeConocimientos = baseDeConocimientos;
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
    
    private void interfazHechos(JPanel panelHechos) {
        
    }
}