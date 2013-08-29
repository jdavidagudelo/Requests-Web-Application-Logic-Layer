/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.JComponent;

public class NodoGrafico extends JComponent implements Comparable, Serializable, Clonable
{
    private Object dato = null;
    private NodoDoble nodoDoble = null;
    public void setDato(Object dato)
    {
        this.dato = dato;
    }
    public Object getDato()
    {
        return dato;
    }
    public NodoGrafico(NodoGrafico x)
    {
        super();
        int i = 0;
        for(i = 0; i < x.getComponentCount(); i++)
        {
            this.add(x.getComponent(i));
        }
        for(i = 0; i < x.getComponentListeners().length; i++)
        {
            this.addComponentListener(x.getComponentListeners()[i]);
        }
        for(i = 0; i < x.getAncestorListeners().length; i++)
        {
            this.addAncestorListener(x.getAncestorListeners()[i]);
        }
        for(i = 0; i < x.getFocusListeners().length; i++)
        {
            this.addFocusListener(x.getFocusListeners()[i]);
        }
        for(i = 0; i < x.getContainerListeners().length; i++)
        {
            this.addContainerListener(x.getContainerListeners()[i]);
        }
        for(i = 0; i < x.getHierarchyBoundsListeners().length; i++)
        {
            this.addHierarchyBoundsListener(x.getHierarchyBoundsListeners()[i]);
        }
        for(i = 0; i < x.getHierarchyListeners().length; i++)
        {
            this.addHierarchyListener(x.getHierarchyListeners()[i]);
        }
        for(i = 0; i < x.getInputMethodListeners().length; i++)
        {
            this.addInputMethodListener(x.getInputMethodListeners()[i]);
        }
        for(i = 0; i < x.getKeyListeners().length; i++)
        {
            this.addKeyListener(x.getKeyListeners()[i]);
        }
        for(i = 0; i < x.getMouseWheelListeners().length; i++)
        {
            this.addMouseWheelListener(x.getMouseWheelListeners()[i]);
        }
        for(i = 0; i < x.getMouseListeners().length; i++)
        {
            this.addMouseListener(x.getMouseListeners()[i]);
        }
        for(i = 0; i < x.getMouseMotionListeners().length; i++)
        {
            this.addMouseMotionListener(x.getMouseMotionListeners()[i]);
        }
        for(i = 0; i < x.getPropertyChangeListeners().length; i++)
        {
            this.addPropertyChangeListener(x.getPropertyChangeListeners()[i]);
        }
        for(i = 0; i < x.getVetoableChangeListeners().length; i++)
        {
            this.addVetoableChangeListener(x.getVetoableChangeListeners()[i]);
        }
    }
    public NodoGrafico()
    {
    }
    public NodoGrafico(Object dato)
    {
        this.dato = dato;
    }
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        int x = 0, y = 0, width = 0, height = 0;
        g.setFont(this.getFont());
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
                g.drawString(dato != null? dato.toString() : "", x+width/4, y+3*height/4);
            }
            else
            {
                g.setColor(this.getBackground());
                g.fillRect(x, y, width, height);
            }
        }
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
        if(dato instanceof String & o instanceof NodoGrafico)
        {
            NodoGrafico otro = (NodoGrafico)o;
            if(otro.getDato() instanceof String)
            {
                String x = (String)dato;
                String y = (String)otro.getDato();
                return x.compareTo(y);
            }
        }
        if(dato instanceof Integer & o instanceof Integer)
        {
            Integer x = (Integer)dato;
            Integer y = (Integer)o;
            return x.compareTo(y);
        }
        if(dato instanceof Integer & o instanceof NodoGrafico)
        {
            NodoGrafico otro = (NodoGrafico)o;
            if(otro.getDato() instanceof Integer)
            {
                Integer x = (Integer)dato;
                Integer y = (Integer)otro.getDato();
                return x.compareTo(y);
            }
        }
        return -1;
    }
    public Object clonar()
    {
        return new NodoGrafico(this);
    }
}
