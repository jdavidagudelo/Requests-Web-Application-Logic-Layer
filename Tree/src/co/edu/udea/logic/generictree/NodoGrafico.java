/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Daniel F Agudelo
 */
public class NodoGrafico extends JComponent implements Comparable, MouseMotionListener, MouseListener, FocusListener, Cloneable
{
    private String text = "";
    private Object dato = null;
    private NodoDoble nodoDoble = null;
    private NodoLg nodoLg = null;
    public void setDato(Object dato)
    {
        this.dato = dato;
    }

    public Object getDato()
    {
        return dato;
    }
    public NodoGrafico()
    {

    }
    public NodoGrafico(Object dato)
    {
        this.addFocusListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.dato = dato;
    }
    public void setText(String text)
    {
        this.text = text;
    }
    public String getText()
    {
        return text;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        int x = 0, y = 0, width = 0, height = 0;
        g.setFont(new Font("Arial", Font.BOLD, 12));
        if(g.getClipBounds() != null)
        {
            //se obtienen la posicion y las dimensiones del graficador ingresado como argumento
            x = (int)g.getClipBounds(this.getBounds()).getX();
            y = (int)g.getClipBounds(this.getBounds()).getY();
            width = getWidth();
            height = getHeight();
            if(this.isVisible())
            {
                g.setColor(this.getBackground());
                g.fill3DRect(x, y, width, height, this.isFocusOwner());
                g.setColor(this.getForeground());
                g.drawString(dato != null? dato.toString() : "xxx", x+width/4, y+3*height/4);
            }
            else
            {
                g.setColor(this.getBackground());
                g.fillRect(x, y, width, height);
            }
            //se establece el color del graficador
        }
    }

    public void setNodoLg(NodoLg nodoLg) {
        this.nodoLg = nodoLg;
    }


    public NodoLg getNodoLg() {
        return nodoLg;
    }

    public NodoDoble getNodoDoble()
    {
        return nodoDoble;
    }
    public void setNodoDoble(NodoDoble nodoDoble)
    {
        this.nodoDoble = nodoDoble;
    }
    public int compareTo(Object o)
    {
        int compareTo = -1;
        if(dato instanceof String & o instanceof NodoGrafico)
        {
            NodoGrafico otro = (NodoGrafico)o;
            if(otro.getDato() instanceof String)
            {
                String x = (String)dato;
                String y = (String)otro.getDato();
                compareTo = x.compareTo(y);
            }
        }
        if(dato instanceof Integer & o instanceof Integer)
        {
            Integer x = (Integer)dato;
            Integer y = (Integer)o;
            compareTo = x.compareTo(y);
        }
        if(dato instanceof Integer & o instanceof NodoGrafico)
        {
            NodoGrafico otro = (NodoGrafico)o;
            if(otro.getDato() instanceof Integer)
            {
                Integer x = (Integer)dato;
                Integer y = (Integer)otro.getDato();
                compareTo = x.compareTo(y);
            }
        }
        return compareTo;
    }

    public void mouseDragged(MouseEvent e)
    {

    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void focusGained(FocusEvent e) 
    {
    }

    public void focusLost(FocusEvent e) {
    }
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        NodoGrafico grafico = new NodoGrafico(this.getDato());
        for(int j = 0; j < this.getMouseListeners().length; j++)
        {
            grafico.addMouseListener(this.getMouseListeners()[j]);
        }
        for(int j = 0; j < this.getMouseMotionListeners().length; j++)
        {
            grafico.addMouseMotionListener(this.getMouseMotionListeners()[j]);
        }
        for(int j = 0; j < this.getKeyListeners().length; j++)
        {
            grafico.addKeyListener(this.getKeyListeners()[j]);
        }
        grafico.setBackground(this.getBackground());
        grafico.setForeground(this.getForeground());
        grafico.setComponentPopupMenu(this.getComponentPopupMenu());
        return grafico;
    }

}
