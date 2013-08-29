/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.Serializable;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
public class PanelTabla extends JPanel implements Serializable
{
    private Tabla tabla = null;
    private JTextArea texto = new JTextArea();
    private MenuTexto menu = new MenuTexto();
    public PanelTabla()
    {
        this.initComponents();
    }
    public void setTexto(JTextArea texto) {
        this.texto = texto;
    }

    public JTextArea getTexto() {
        return texto;
    }
    public void setExpresion(Expresion expresion) throws Exception
    {
        tabla.setExpresion(expresion);
    }
    public Expresion getExpresion()
    {
        return tabla.getExpresion();
    }
    public void setTabla(Tabla tabla)
    {
        this.tabla = tabla;
        this.texto.setText(tabla.getText().toString());
    }
    private void initComponents()
    {
        this.setLayout(new GridLayout(1, 1));
        this.add(texto);
        texto.setComponentPopupMenu(menu);
        texto.addMouseListener(menu);
        texto.setFont(new Font("Arial", 12, 12));
        texto.setEditable(false);
        this.tabla = new Tabla();
    }
    public Tabla getTabla()
    {
        return tabla;
    }
    public synchronized void crearTabla(JComponent progreso)
    {
        int max = 0;
        for(int i = 0; i < tabla.getN(); i++)
        {
            if(tabla.getVariables()[i].length() > max)
            {
                max = tabla.getVariables()[i].length();
            }
        }
        StringBuilder space = new StringBuilder("");
        space.append('\t');
        StringBuilder text = new StringBuilder("Tabla de verdad para la expresión booleana: ");
        text.append(tabla.getExpresion().getExpresion()).append("\n");
        for(int i = 0; i < tabla.getN(); i++)
        {
            text.append(tabla.getVariables()[i]);
            text.append("\t");
        }
        text.append("\n");
        int index = text.length();
        for(int i = 0; i < tabla.getM(); i++)
        {
            if(progreso instanceof JLabel)
            {
                JLabel l = (JLabel)progreso;
                l.setText("Avance de la evaluación: "+String.valueOf(100*i/this.getM())+"%");
            }
            int c = tabla.getCodigos()[i];
            int v = 0;
            while(v < tabla.getN())
            {
                if(c%2 == 1)
                {
                    text.insert(index, "T"+space);
                }
                else
                {
                    text.insert(index, "F"+space);
                }
                c = c/2;
                v = v + 1;
            }
            if(i < tabla.getM() - 1)
            {
                text.append("\n");
            }
            index = text.length();
        }
        if(progreso instanceof JLabel)
        {
            JLabel l = (JLabel)progreso;
            l.setText("Avance de la evaluación: 100%");
        }
        texto.setText(text.toString());
        tabla.setText(text);
    }
    public int[] getCodigos() 
    {
        return tabla.getCodigos();
    }
    public String[] getVariables() 
    {
        return tabla.getVariables();
    }
    public int getM() 
    {
        return tabla.getM();
    }
    public int getN() 
    {
        return tabla.getN();
    }
    public void setCodigos(int[] codigos) 
    {
        tabla.setCodigos(codigos);
    }
    public void setVariables(String[] variables) 
    {
        tabla.setVariables(variables);
    }
    public void setM(int m) 
    {
        tabla.setM(m);
    }
    public void setN(int n) 
    {
        tabla.setN(n);
    }
}
