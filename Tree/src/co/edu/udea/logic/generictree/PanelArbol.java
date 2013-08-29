/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class PanelArbol extends JPanel implements MouseMotionListener, MouseListener
{
    private ArbolLista arbol = null;
    private ArbolLg arbolg = null;
    private boolean binario = true;
    public void setArbol(ArbolLista arbol)
    {
        this.arbol = arbol;
    }
    public ArbolLista getArbol()
    {
        return arbol;
    }
    public boolean isBinario()
    {
        return binario;
    }
    public void setBinario(boolean binario)
    {
        this.binario = binario;
    }
    public ArbolLg getArbolg()
    {
        return arbolg;
    }
    public void setArbolLg(ArbolLg arbolg)
    {
        this.arbolg = arbolg;
    }
    public PanelArbol()
    {
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
                if(this.isBinario())
                {
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
                else
                {
                    NodoDoble nx = x.getNodoDoble();
                    if(nx != null)
                    {
                        NodoDoble ns = nx.getLi();
                        if(nx.isBi())
                        {
                            NodoGrafico xs = (NodoGrafico)ns.getValue();
                            g.drawLine(x.getX() + x.getWidth()/2, x.getY() + x.getHeight(), xs.getX()+xs.getWidth()/2, xs.getY());
                            ns = ns.getLd();
                            if(ns != null)
                            {
                                while(ns != null)
                                {
                                    xs = (NodoGrafico)ns.getValue();
                                    g.drawLine(x.getX() + x.getWidth()/2, x.getY() + x.getHeight(), xs.getX()+xs.getWidth()/2, xs.getY());
                                    ns = ns.getLd();
                                }
                        }
                        }
                    }
                }
                x.paint(g);
            }
        }
        if(!this.isBinario())
        {
        }
    }
    public void mouseDragged(MouseEvent e)
    {
            if(e.getSource() instanceof NodoGrafico)
            {
                NodoGrafico x = (NodoGrafico)e.getSource();
                int dx = (int)e.getLocationOnScreen().getX()-(int)x.getLocationOnScreen().getX();
                int dy = (int)e.getLocationOnScreen().getY()-(int)x.getLocationOnScreen().getY();
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

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e) {
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
