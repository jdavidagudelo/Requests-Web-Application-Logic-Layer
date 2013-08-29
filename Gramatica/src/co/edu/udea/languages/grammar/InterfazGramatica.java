/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package co.edu.udea.languages.grammar;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import co.edu.udea.languages.automaton.Automata;

public class InterfazGramatica extends javax.swing.JFrame {

    private Gramatica gramatica;

    public void setGramatica(Gramatica gramatica) {
        this.gramatica = gramatica;
    }

    public Gramatica getGramatica() {
        return gramatica;
    }
    /**
     * Creates new form InterfazGramatica
     */
    public InterfazGramatica() {
        initComponents();
        textoSalida.setEditable(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textoEntrada = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        textoSalida = new javax.swing.JTextArea();
        botonProcesar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuAbrir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        itemManualUsuario = new javax.swing.JMenuItem();
        itemManualTecnico = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textoEntrada.setColumns(20);
        textoEntrada.setRows(5);
        jScrollPane1.setViewportView(textoEntrada);

        textoSalida.setColumns(20);
        textoSalida.setRows(5);
        jScrollPane2.setViewportView(textoSalida);

        botonProcesar.setText("Procesar Grámatica");
        botonProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProcesarActionPerformed(evt);
            }
        });

        jLabel1.setText("Haga clic en el botón para procesar la grámatica");

        jMenu1.setText("Archivo");

        menuAbrir.setText("Abrir gramática");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        jMenu1.add(menuAbrir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        itemManualUsuario.setText("Manual de usuario");
        itemManualUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemManualUsuarioActionPerformed(evt);
            }
        });
        jMenu2.add(itemManualUsuario);

        itemManualTecnico.setText("Manual técnico");
        itemManualTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemManualTecnicoActionPerformed(evt);
            }
        });
        jMenu2.add(itemManualTecnico);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonProcesar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonProcesar)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void procesarGramatica()
    {
        try
        {
            
            Gramatica g = new Gramatica(textoEntrada.getText());
            StringBuilder sb = new StringBuilder(textoSalida.getText());
            if(g.esMuerta())
            {
                sb.append("La Grámatica ingresada es Muerta, por lo tanto no se puede procesar.\n");
                textoSalida.setText(sb.toString());
                return;
            }   
            sb.append("La grámatica simplificada es: \n");
            g.eliminarInalcanzables();
            sb.append(g.toString()).append("\n");
            if(!g.isLinealDerecha())
            {
                sb.append("La grámatica no es lineal por la derecha.\n");
                textoSalida.setText(sb.toString());
                return;
            }
            sb.append("La grámatica convertida a la forma especial es: \n");
            g.convertirFormaEspecial();
            sb.append(g.toString()).append("\n");
            sb.append("Las secuencias mínimas generadas por la grámatica y su respectiva longitud son: \n");
            Automata aut = g.getAutomata();
            ArrayList<String> secuencias = aut.secuenciasMinimas();
            if(secuencias.isEmpty())
            {
                sb.append("").append(", de longitud = ").append("0").append("\n");
            }
            for(int i = 0; i < secuencias.size(); i++)
            {
                String s = secuencias.get(i);
                sb.append(s).append(", de longitud = ").append(s.length()).append("\n");
            }
            textoSalida.setText(sb.toString());
        }
        catch(Exception ex)
        {
            StringBuilder sb = new StringBuilder(textoSalida.getText());
            sb.append("La grámatica ingresada presenta errores, por favor modifiquela e intente nuevamente.\n");
            textoSalida.setText(sb.toString());
        }
    }
    private void botonProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonProcesarActionPerformed
        
        this.procesarGramatica();
    }//GEN-LAST:event_botonProcesarActionPerformed

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed

        JFileChooser jf = new JFileChooser();
        int x = jf.showOpenDialog(this);
        if(x == JFileChooser.APPROVE_OPTION)
        {
            File f = jf.getSelectedFile();
            try 
            {
                gramatica = Gramatica.fromFile(f);
                textoEntrada.setText(gramatica.toString());
            } 
            catch (Exception ex) 
            {
                StringBuilder sb = new StringBuilder(textoSalida.getText());
                sb.append("El archivo ingresado es invalido.\n");
                textoSalida.setText(sb.toString());
                return;
            }
        }
        this.procesarGramatica();
        // TODO add your handling code here:
    }//GEN-LAST:event_menuAbrirActionPerformed
    /**
     * Método utilizado para abrir un archivo tipo pdf que contiene el manual de usuario o el manual tecnico del juego de buscaminas
     * @param archivo el nombre del archivo que se desea abrir
     */
    public void mostrarAyuda(String archivo)
    {
        //se evalua si el archivo existe
        try 
        {
            Desktop.getDesktop().open(new File(archivo));
        } 
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(this, "El archivo: "+archivo+" no pudo ser hallado");
        }
    }

    private void itemManualUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemManualUsuarioActionPerformed
        String separator = System.getProperty("file.separator");
        this.mostrarAyuda("src"+separator+"images"+separator+"manualUsuario.pdf");
    }//GEN-LAST:event_itemManualUsuarioActionPerformed

    private void itemManualTecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemManualTecnicoActionPerformed

        String separator = System.getProperty("file.separator");
        this.mostrarAyuda("src"+separator+"javadoc"+separator+"index.html");
        // TODO add your handling code here:
    }//GEN-LAST:event_itemManualTecnicoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazGramatica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazGramatica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazGramatica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazGramatica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new InterfazGramatica().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonProcesar;
    private javax.swing.JMenuItem itemManualTecnico;
    private javax.swing.JMenuItem itemManualUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JTextArea textoEntrada;
    private javax.swing.JTextArea textoSalida;
    // End of variables declaration//GEN-END:variables
}
