/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Stack;
import javax.swing.JPanel;
public class PanelArbol extends JPanel implements MouseMotionListener, MouseListener, KeyListener, Serializable
{
    private ArbolLista arbol = null;
    private Color colorFondoNodos = Color.ORANGE, colorFuenteNodos = Color.YELLOW;
    private Font fuenteNodos = new Font("Arial", Font.BOLD, 12);
    private NodoGrafico invisible = new NodoGrafico();
    private int size = 20;
    public void setSize(int size)
    {
        this.size = size;
    }
    public int getSize1()
    {
        return size;
    }
    public void setColorFuenteNodos(Color colorFuenteNodos)
    {
        this.colorFuenteNodos = colorFuenteNodos;
    }
    public void setColorFondoNodos(Color colorFondoNodos)
    {
        this.colorFondoNodos = colorFondoNodos;
    }
    public Color getColorFuenteNodos()
    {
        return colorFuenteNodos;
    }
    public Color getColorFondoNodos()
    {
        return colorFondoNodos;
    }
    public void setArbol(ArbolLista arbol)
    {
        this.arbol = arbol;
    }
    public void setFuenteNodos(Font fuenteNodos)
    {
        this.fuenteNodos = fuenteNodos;
    }
    public Font getFuenteNodos()
    {
        return fuenteNodos;
    }
    public ArbolLista getArbol()
    {
        return arbol;
    }
    public PanelArbol()
    {
    }
    public void reconstruirArbol()
    {
        int tamaño = 0, ancho = 0;
        if(arbol.getRaiz() != null && arbol.getRaiz().isBi())
        {
            ancho = arbol.anchoIzquierda(arbol.getRaiz()) + arbol.anchoDerecha(arbol.getRaiz());
            tamaño = arbol.size(arbol.getRaiz());
        }
        else
        {
            tamaño = 1;
        }
        if(tamaño < ancho)
        {
            tamaño = ancho;
        }
        int h = arbol.altura(arbol.getRaiz());
        int x = size*tamaño;
        int y = (size/2)*(3*h - 1);
        int ya = 0;
        this.setLayout(null);
        this.setArbol(arbol);
        this.setPreferredSize(new Dimension(x, y));
        Stack pila = new Stack();
        NodoGrafico actual = null;
        NodoDoble p = arbol.getRaiz(), pp = arbol.getR();
        ancho = 0;
        if(p != null && p.isBi())
        {
            ancho = arbol.anchoIzquierda(p.getLi());
            tamaño = arbol.size(p.getLi());
        }
        else
        {
            tamaño = 0;
        }
        if(ancho > tamaño)
        {
            tamaño = ancho;
        }
        int xa = size*tamaño;
        if(p == pp)
        {
            return;
        }
        while(p != null && (p.isBi() | p.isBd() | !pila.isEmpty()))
        {
            actual = (NodoGrafico)p.getValue();
            actual.setBackground(colorFondoNodos);
            actual.setForeground(colorFuenteNodos);
            actual.setFont(fuenteNodos);
            actual.setBounds(xa, ya, size, size);
            if(!p.isBd())
            {
                if(p.isBi())
                {
                    ancho = 0;
                    if(p.getLi().isBd())
                    {
                        ancho = arbol.anchoDerecha(p.getLi()) - 1;
                        tamaño = arbol.size(p.getLi().getLd())+1;
                    }
                    else
                    {
                        tamaño = 1;
                    }
                    if(ancho > tamaño)
                    {
                        tamaño = ancho;
                    }
                    xa = xa - size*tamaño;
                    ya = ya + 3*size/2;
                    p = p.getLi();
                }
                else
                {
                    if(!pila.isEmpty())
                    {
                        pp = (NodoDoble)pila.pop();
                        actual = (NodoGrafico)pp.getValue();
                        ancho = 0;
                        if(pp.getLd().isBi())
                        {
                            ancho = arbol.anchoIzquierda(pp.getLd().getLi());
                            tamaño = arbol.size(pp.getLd().getLi()) + 1;
                        }
                        else
                        {
                            tamaño = 1;
                        }
                        if(ancho > tamaño)
                        {
                            tamaño = ancho;
                        }
                        xa = actual.getX() + size*tamaño;
                        ya = actual.getY() + 3*size/2;
                        p = pp.getLd();
                    }
                }
            }
            else
            {
                if(p.isBi())
                {
                    pila.push(p);
                    ancho = 0;
                    if(p.getLi().isBd())
                    {
                        ancho = arbol.anchoDerecha(p.getLi().getLd())-1;
                        tamaño = arbol.size(p.getLi().getLd())+1;
                    }
                    else
                    {
                        tamaño = 1;
                    }
                    if(ancho > tamaño)
                    {
                        tamaño = ancho;
                    }
                    xa = xa - size*tamaño;
                    ya = ya + 3*size/2;
                    p = p.getLi();
                }
                else
                {
                    ancho = 0;
                    if(p.getLd().isBi())
                    {
                        ancho = arbol.anchoIzquierda(p.getLd())-1;
                        tamaño = arbol.size(p.getLd().getLi())+1;
                    }
                    else
                    {
                        tamaño = 1;
                    }
                    if(tamaño < ancho)
                    {
                        tamaño = ancho;
                    }
                    xa = xa + size*tamaño;
                    ya = ya + 3*size/2;
                    p = p.getLd();
                }
            }
        }
        if(p != null && !p.isBd() && !p.isBi() && pila.isEmpty())
        {
            actual = (NodoGrafico)p.getValue();
            actual.setBackground(colorFondoNodos);
            actual.setForeground(colorFuenteNodos);
            actual.setFont(fuenteNodos);
            actual.setBounds(xa, ya, size, size);
        }
    }
    public void construirArbol()
    {
        int tamaño = 0, ancho = 0;
        if(arbol.getRaiz() != null && arbol.getRaiz().isBi())
        {
            ancho = arbol.anchoIzquierda(arbol.getRaiz()) + arbol.anchoDerecha(arbol.getRaiz());
        }
        if(arbol.getRaiz() != null)
        {
            tamaño = arbol.size(arbol.getRaiz());
        }
        else
        {
            tamaño = 1;
        }
        if(tamaño < ancho)
        {
            tamaño = ancho;
        }
        int h = arbol.altura(arbol.getRaiz());
        int x = size*tamaño;
        int y = (size/2)*(3*h);
        int ya = 0;
        this.setLayout(null);
        this.setArbol(arbol);
        this.removeAll();
        this.addKeyListener(this);
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(x, y));
        Stack pila = new Stack();
        NodoGrafico actual = null;
        NodoDoble p = arbol.getRaiz(), pp = arbol.getR();
        ancho = 0;
        if(p != null && p.isBi())
        {
            ancho = arbol.anchoIzquierda(p.getLi());
            tamaño = arbol.size(p.getLi());
        }
        else
        {
            tamaño = 0;
        }
        if(ancho > tamaño)
        {
            tamaño = ancho;
        }
        int xa = size*tamaño;
        if(p == pp)
        {
            return;
        }
        while(p != null && (p.isBi() | p.isBd() | !pila.isEmpty()))
        {
            actual = (NodoGrafico)p.getValue();
            actual.setBackground(colorFondoNodos);
            actual.setForeground(colorFuenteNodos);
            actual.setFont(fuenteNodos);
            actual.setBounds(xa, ya, size, size);
            this.add(actual);
            if(!p.isBd())
            {
                if(p.isBi())
                {
                    ancho = 0;
                    if(p.getLi().isBd())
                    {
                        ancho = arbol.anchoDerecha(p.getLi()) - 1;
                        tamaño = arbol.size(p.getLi().getLd())+1;
                    }
                    else
                    {
                        tamaño = 1;
                    }
                    if(ancho > tamaño)
                    {
                        tamaño = ancho;
                    }
                    xa = xa - size*tamaño;
                    ya = ya + 3*size/2;
                    p = p.getLi();
                }
                else
                {
                    if(!pila.isEmpty())
                    {
                        pp = (NodoDoble)pila.pop();
                        actual = (NodoGrafico)pp.getValue();
                        ancho = 0;
                        if(pp.getLd().isBi())
                        {
                            ancho = arbol.anchoIzquierda(pp.getLd().getLi());
                            tamaño = arbol.size(pp.getLd().getLi()) + 1;
                        }
                        else
                        {
                            tamaño = 1;
                        }
                        if(ancho > tamaño)
                        {
                            tamaño = ancho;
                        }
                        xa = actual.getX() + size*tamaño;
                        ya = actual.getY() + 3*size/2;
                        p = pp.getLd();
                    }
                }
            }
            else
            {
                if(p.isBi())
                {
                    pila.push(p);
                    ancho = 0;
                    if(p.getLi().isBd())
                    {
                        ancho = arbol.anchoDerecha(p.getLi().getLd())-1;
                        tamaño = arbol.size(p.getLi().getLd())+1;
                    }
                    else
                    {
                        tamaño = 1;
                    }
                    if(ancho > tamaño)
                    {
                        tamaño = ancho;
                    }
                    xa = xa - size*tamaño;
                    ya = ya + 3*size/2;
                    p = p.getLi();
                }
                else
                {
                    ancho = 0;
                    if(p.getLd().isBi())
                    {
                        ancho = arbol.anchoIzquierda(p.getLd())-1;
                        tamaño = arbol.size(p.getLd().getLi())+1;
                    }
                    else
                    {
                        tamaño = 1;
                    }
                    if(tamaño < ancho)
                    {
                        tamaño = ancho;
                    }
                    xa = xa + size*tamaño;
                    ya = ya + 3*size/2;
                    p = p.getLd();
                }
            }
        }
        if(p != null && !p.isBd() && !p.isBi() && pila.isEmpty())
        {
            actual = (NodoGrafico)p.getValue();
            actual.setBackground(colorFondoNodos);
            actual.setForeground(colorFuenteNodos);
            actual.setFont(fuenteNodos);
            actual.setBounds(xa, ya, size, size);
            this.add(actual);
        }
        invisible.setBounds((int)this.getMaximumSize().getWidth(), (int)this.getMinimumSize().getHeight(), size, size);
        invisible.setVisible(false);
        invisible.setBackground(this.getBackground());
        this.add(invisible);
    }
    public void keyTyped(KeyEvent e)
    {
    }
    public void keyPressed(KeyEvent e)
    {
        if(e.getSource() instanceof NodoGrafico)
        {
            NodoGrafico x = (NodoGrafico)e.getSource();
            if(e.getKeyCode() == KeyEvent.VK_LEFT)
            {
                    NodoDoble y = x.getNodoDoble();
                    if(y.getLi() != null & y.getLi() != arbol.getR())
                    {
                        x = (NodoGrafico)y.getLi().getValue();
                        x.requestFocus();
                    }
                repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                NodoDoble y = x.getNodoDoble();
                    if(y.getLi() != null & y.getLi() != arbol.getR())
                    {
                        x = (NodoGrafico)y.getLi().getValue();
                        x.requestFocus();
                    }
                repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                    NodoDoble y = x.getNodoDoble();
                    if(y.getLd() != null & y.getLd() != arbol.getR())
                    {
                        x = (NodoGrafico)y.getLd().getValue();
                        x.requestFocus();
                    }
                repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_UP)
            {
                    NodoDoble y = x.getNodoDoble();
                    y = arbol.padre(y);
                    if(y != arbol.getR() & y != null)
                    {
                        x = (NodoGrafico)y.getValue();
                        x.requestFocus();
                    }
                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_I)
        {
            if(arbol != null)
            {
                arbol.inorden();
                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_O)
        {
            if(arbol != null)
            {
                arbol.posorden();
                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_E)
        {
            if(arbol != null)
            {
                arbol.preorden();
                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_N)
        {
            if(arbol != null)
            {
                arbol.desenhebrar();
                repaint();
            }
        }
    }
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_B)
        {
            this.repaint();
        }
    }
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        int i = 0;
        NodoGrafico x = null;
        for(i = 0; i < this.getComponentCount(); i++)
        {
            if(this.getComponent(i) instanceof NodoGrafico)
            {
                x = (NodoGrafico)this.getComponent(i);
                g.setColor(Color.red);
                NodoDoble nx = x.getNodoDoble();
                    if(nx != null)
                    {
                        NodoDoble li = nx.getLi(), ld = nx.getLd();
                        if(li != null)
                        {
                            NodoGrafico xi = (NodoGrafico)li.getValue();
                            if(xi != null)
                            {
                                if(nx.isBi())
                                {
                                    g.drawLine(x.getX(), x.getY()+x.getHeight(), xi.getX() +xi.getWidth()/2, xi.getY());
                                }
                                else
                                {
                                    g.setColor(Color.green.darker());
                                    g.drawLine(x.getX(), x.getY()+x.getHeight(), xi.getX() +xi.getWidth()/2, xi.getY());
                                    g.setColor(Color.red);
                                }
                            }
                        }
                        if(ld != null)
                        {
                            NodoGrafico xd = (NodoGrafico)ld.getValue();
                            if(xd != null)
                            {
                                if(nx.isBd())
                                {
                                    g.drawLine(x.getX() + x.getWidth(), x.getY()+x.getHeight(), xd.getX() +xd.getWidth()/2, xd.getY());
                                }
                                else
                                {
                                    g.setColor(Color.green.darker());
                                    g.drawLine(x.getX() + x.getWidth(), x.getY()+x.getHeight(), xd.getX() +xd.getWidth()/2, xd.getY());
                                    g.setColor(Color.red);
                                }
                            }
                        }
                    }
                
        }
    }
    }
    public void mouseDragged(MouseEvent e)
    {
            if(e.getSource() instanceof NodoGrafico)
            {
                NodoGrafico x = (NodoGrafico)e.getSource();
                int xe = (int)e.getLocationOnScreen().getX();
                int ye = (int)e.getLocationOnScreen().getY();
                int xc = (int)x.getLocationOnScreen().getX();
                int yc = (int)x.getLocationOnScreen().getY();
                int dx = xe-xc;
                int dy = ye-yc;
                int xn = x.getX() + dx;
                int yn = x.getY() + dy;
                if(xn > this.getX() & yn > this.getY())
                {
                    x.setBounds(xn, yn, x.getWidth(), x.getHeight());
                    if(xn > this.getWidth() | yn > this.getHeight())
                    {
                        Dimension dim = new Dimension();
                        if(xn > this.getWidth())
                        {
                            dim.width = xn+2*x.getSize().width;
                        }
                        else
                        {
                            dim.width = this.getWidth();
                        }
                        if(yn > this.getHeight())
                        {
                            dim.height = yn+2*x.getSize().height;
                        }
                        else
                        {
                            dim.height = this.getHeight();
                        }
                        this.setPreferredSize(dim);
                        this.getParent().paintAll(this.getGraphics());
                    }
                    repaint();
                }
            }
    }
    public void mouseMoved(MouseEvent e)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {
        if(e.getSource() instanceof NodoGrafico)
        {
            NodoGrafico grafico = (NodoGrafico)e.getSource();
            grafico.requestFocus();
            repaint();
        }
    }
    public void mouseExited(MouseEvent e) 
    {
    }
}
