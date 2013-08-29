/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class VentanaArbol extends JFrame implements AdjustmentListener, MouseListener, MouseMotionListener, KeyListener
{
    private boolean binario = true;
    private NodoGrafico invisible = new NodoGrafico("");
    private Stack pilaArboles = new Stack();
    private JScroll scroll = null;
    private JPopupMenu menu = null;
    private JMenuItem itemBorrar = null;
    private JComponent focused = null;
    private JMenu menuOrden = null;
    private JMenuItem itemPre = null, itemPos = null, itemIn = null, itemNull = null, itemInsertar = null;
    private PanelArbol cuadro = null;
    private ArbolLista arbol = null;
    private ArbolLg arbolg = null;
    private int size = 0;
    private JScrollBar hScrollBar, vScrollBar;

    public void setArbolg(ArbolLg arbolg) {
        this.arbolg = arbolg;
    }


    public void setBinario(boolean binario) {
        this.binario = binario;
        this.cuadro.setBinario(binario);
    }


    public boolean isBinario() {
        return binario;
    }


    public ArbolLg getArbolg() {
        return arbolg;
    }

    public ArbolLista getArbol()
    {
        return arbol;
    }
    public void setArbol(ArbolLista arbol)
    {
        this.arbol = arbol;
    }
    public VentanaArbol()
    {
        this.initComponents();
    }
    private void initComponents()
    {
        arbol = new ArbolLista();
        size = 35;
        cuadro = new PanelArbol();
        cuadro.setArbol(arbol);
        scroll = new JScroll(cuadro);
        this.setLayout(null);
        hScrollBar = new JScrollBar();
        vScrollBar = new JScrollBar();
        hScrollBar.addAdjustmentListener(this);
        vScrollBar.addAdjustmentListener(this);
        vScrollBar.addMouseListener(this);
        vScrollBar.setBlockIncrement(20);
        vScrollBar.setUnitIncrement(20);
        hScrollBar.setBlockIncrement(20);
        hScrollBar.setUnitIncrement(20);
        hScrollBar.addMouseListener(this);
        hScrollBar.setOrientation(JScrollBar.HORIZONTAL);
        scroll.addMouseListener(this);
        scroll.setVerticalScrollBar(vScrollBar);
        scroll.setHorizontalScrollBar(hScrollBar);
        scroll.addMouseListener(this);
        scroll.setBounds(10, 10, 1300, 600);
        this.add(scroll);
        this.addKeyListener(this);
        cuadro.addKeyListener(this);
        scroll.addKeyListener(this);
        itemBorrar = new JMenuItem("Borrar Nodo");
        menu = new JPopupMenu();
        menuOrden = new JMenu("Enhebrado");
        itemIn = new JMenuItem("Inorden");
        itemPre = new JMenuItem("Preorden");
        itemPos = new JMenuItem("Posorden");
        itemNull = new JMenuItem("Ninguno");
        itemInsertar = new JMenuItem("Insertar Nodo");
        menuOrden.add(itemIn);
        menuOrden.add(itemPre);
        menuOrden.add(itemPos);
        menuOrden.add(itemNull);
        menu.add(menuOrden);
        menu.add(itemBorrar);
        menu.add(itemInsertar);
        itemBorrar.addMouseListener(this);
        itemInsertar.addMouseListener(this);
        itemIn.addMouseListener(this);
        itemPos.addMouseListener(this);
        itemPre.addMouseListener(this);
        itemNull.addMouseListener(this);
        menuOrden.addMouseListener(this);
        invisible.setBounds((int)cuadro.getMaximumSize().getWidth(), (int)cuadro.getMaximumSize().getHeight(),size, size);
    }
    public void crearArbol(ArbolLg arbol)
    {
        Stack pila = new Stack();
        arbolg = arbol.copia();
        NodoLg p = arbolg.getPrimero();
        while(p != null)
        {
            if(p.isSw())
            {
                if(p.getLiga() != null)
                {
                    pila.push(p.getLiga());
                }
                p = (NodoLg)p.getDato();
                NodoGrafico grafico = new NodoGrafico(p.getDato());
                grafico.addMouseMotionListener(cuadro);
                grafico.addMouseListener(cuadro);
                grafico.addMouseListener(this);
                grafico.addKeyListener(this);
                grafico.setBackground(Color.orange);
                grafico.setForeground(Color.yellow);
                grafico.setComponentPopupMenu(menu);
                p.setDato(grafico);
                p = p.getLiga();
            }
            else
            {
                NodoGrafico grafico = new NodoGrafico(p.getDato());
                grafico.addMouseMotionListener(cuadro);
                grafico.addMouseListener(cuadro);
                grafico.addMouseListener(this);
                grafico.addKeyListener(this);
                grafico.setBackground(Color.orange);
                grafico.setForeground(Color.yellow);
                grafico.setComponentPopupMenu(menu);
                p.setDato(grafico);
                p = p.getLiga();
            }
            if(p == null & !pila.isEmpty())
            {
                p = (NodoLg)pila.pop();
            }
        }
        this.arbol = this.arbolg.convertir();
        this.arbolg.setArbol(this.arbol);
    }
    public void reconstruirArbol()
    {
        int tamaño = 0, ancho = 0;
        if(arbol.getRaiz() != null && arbol.getRaiz().isBi())
        {
            ancho = arbol.anchoIzquierda(arbol.getRaiz()) + arbol.anchoDerecha(arbol.getRaiz());
            tamaño = arbol.sizeP(arbol.getRaiz());
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
        cuadro.setLayout(null);
        cuadro.setArbol(arbol);
        cuadro.setPreferredSize(new Dimension(x, y));
        Stack pila = new Stack();
        NodoGrafico actual = null;
        NodoDoble p = arbol.getRaiz(), pp = arbol.getR();
        ancho = 0;
        if(p != null && p.isBi())
        {
            ancho = arbol.anchoIzquierda(p.getLi());
            tamaño = arbol.sizeP(p.getLi());
        }
        else
        {
            tamaño = 1;
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
            actual.setBounds(xa, ya, size, size);
            if(!p.isBd())
            {
                if(p.isBi())
                {
                    ancho = 0;
                    if(p.getLi().isBd())
                    {
                        ancho = arbol.anchoDerecha(p.getLi()) - 1;
                        tamaño = arbol.sizeP(p.getLi().getLd())+1;
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
                            tamaño = arbol.sizeP(pp.getLd().getLi()) + 1;
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
                        tamaño = arbol.sizeP(p.getLi().getLd())+1;
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
                        tamaño = arbol.sizeP(p.getLd().getLi())+1;
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
            actual.setBounds(xa, ya, size, size);
        }
    }
    public void construirArbol()
    {
        int tamaño = 0, ancho = 0;
        if(arbol.getRaiz() != null && arbol.getRaiz().isBi())
        {
            ancho = arbol.anchoIzquierda(arbol.getRaiz()) + arbol.anchoDerecha(arbol.getRaiz());
            tamaño = arbol.sizeP(arbol.getRaiz());
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
        cuadro.setLayout(null);
        cuadro.setArbol(arbol);
        cuadro.removeAll();
        cuadro.addKeyListener(this);
        scroll.addKeyListener(this);
        cuadro.setPreferredSize(new Dimension(x, y));
        Stack pila = new Stack();
        NodoGrafico actual = null;
        NodoDoble p = arbol.getRaiz(), pp = arbol.getR();
        ancho = 0;
        if(p != null && p.isBi())
        {
            ancho = arbol.anchoIzquierda(p.getLi());
            tamaño = arbol.sizeP(p.getLi());
        }
        else
        {
            tamaño = 1;
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
            actual.setBounds(xa, ya, size, size);
            cuadro.add(actual);
            if(!p.isBd())
            {
                if(p.isBi())
                {
                    ancho = 0;
                    if(p.getLi().isBd())
                    {
                        ancho = arbol.anchoDerecha(p.getLi()) - 1;
                        tamaño = arbol.sizeP(p.getLi().getLd())+1;
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
                            tamaño = arbol.sizeP(pp.getLd().getLi()) + 1;
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
                        tamaño = arbol.sizeP(p.getLi().getLd())+1;
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
                        tamaño = arbol.sizeP(p.getLd().getLi())+1;
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
            actual.setBounds(xa, ya, size, size);
            cuadro.add(actual);
        }
        invisible.setBounds((int)cuadro.getMaximumSize().getWidth(), (int)cuadro.getMinimumSize().getHeight(), size, size);
        invisible.setVisible(false);
        invisible.setBackground(this.getBackground());
        cuadro.add(invisible);
    }
    public void crearArbol(ArbolLista arbol)
    {
        Object pre[] = arbol.preordenMejorado(arbol.getR());
        Object in[] = arbol.inordenMejorado(arbol.getR());
        this.crearArbol(pre, in);
        if(arbol.getOrden() == ArbolLista.INORDEN)
        {
            this.arbol.inorden();
        }
        if(arbol.getOrden() == ArbolLista.PREORDEN)
        {
            this.arbol.preorden();
        }
        if(arbol.getOrden() == ArbolLista.POSORDEN)
        {
            this.arbol.posorden();
        }
        this.arbol.setAVL(arbol.isAVL());
        Stack pila = new Stack(), pila1 = new Stack();
        NodoDoble pp = arbol.getR();
        NodoDoble p = arbol.getRaiz();
        NodoDoble x = this.arbol.getRaiz(), ax = this.arbol.getR();
        while(x != null && p != null && (p.isBd() | p.isBi() | !pila.isEmpty()))
        {
            x.setFb(p.getFb());
            if(!p.isBd())
            {
                if(p.isBi())
                {
                    x = x.getLi();
                    p = p.getLi();
                }
                else
                {
                    if(!pila.isEmpty())
                    {
                        pp = (NodoDoble)pila.pop();
                        p = pp.getLd();
                    }
                    if(!pila1.isEmpty())
                    {
                        ax = (NodoDoble)pila1.pop();
                        x = ax.getLd();
                    }
                }
            }
            else
            {
                if(p.isBi())
                {
                    pila1.push(x);
                    x = x.getLi();
                    pila.push(p);
                    p = p.getLi();
                }
                else
                {
                    x = x.getLd();
                    p = p.getLd();
                }
            }
        }
    }
    public void mouseDragged(MouseEvent e)
    {
    }
    public void mouseMoved(MouseEvent e)
    {
    }
    public void keyTyped(KeyEvent e)
    {
    }
    public void keyPressed(KeyEvent e)
    {
        if(e.getSource() instanceof NodoGrafico)
        {
            NodoGrafico x = (NodoGrafico)e.getSource();

            if(e.getKeyCode() == KeyEvent.VK_C)
            {
                this.arbolg.borrar(x.getNodoDoble().getLp(), x.getNodoDoble());
                cuadro.remove(x);
                this.reconstruirArbol();
                Image img = this.createImage(this.getWidth(), this. getHeight());
                Graphics graficador = img.getGraphics();
                this.paintAll(graficador);
                this.getGraphics().drawImage(img, 0, 0, null);
                this.repaint();
            }

            if(e.getKeyCode() == KeyEvent.VK_Q)
            {
                String dato = JOptionPane.showInputDialog("Jodete");
                NodoGrafico grafico = new NodoGrafico(dato);
                grafico.addMouseMotionListener(cuadro);
                grafico.addMouseListener(cuadro);
                grafico.addMouseListener(this);
                grafico.addKeyListener(this);
                grafico.setBackground(Color.orange);
                grafico.setForeground(Color.yellow);
                grafico.setComponentPopupMenu(menu);
                NodoDoble nuevo = new NodoDoble(grafico);
                this.arbolg.insertar(x.getNodoDoble(), nuevo);
                grafico.setBounds(x.getX() + size, x.getY() + size, size, size);
                cuadro.add(grafico);
                this.construirArbol();
                Image img = this.createImage(this.getWidth(), this. getHeight());
                Graphics graficador = img.getGraphics();
                this.paintAll(graficador);
                this.getGraphics().drawImage(img, 0, 0, null);
                this.repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_B)
            {
                ArbolLista copia = arbol.copia();
                NodoDoble ax = arbol.padre(x.getNodoDoble());
                NodoDoble nx = x.getNodoDoble();
                if(arbol.borrarAVL(nx, ax))
                {
                    pilaArboles.push(copia);
                }
                cuadro.remove(x);
                this.reconstruirArbol();
                Image img = this.createImage(this.getWidth(), this. getHeight());
                Graphics graficador = img.getGraphics();
                this.paintAll(graficador);
                this.getGraphics().drawImage(img, 0, 0, null);
                if(arbol.getRaiz() != null)
                {
                    if(ax != arbol.getR())
                    {
                        focused = (JComponent)ax.getValue();
                        focused.requestFocus();
                        focused.repaint();
                    }
                    else
                    {
                        focused = (NodoGrafico)arbol.getRaiz().getValue();
                        focused.requestFocus();
                        focused.repaint();
                    }
                }
                else
                {
                    this.requestFocus();
                }
                cuadro.repaint();
            }
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
        if(e.getKeyCode() == KeyEvent.VK_Z)
        {
            Random r = new Random();
            NodoGrafico grafico = new NodoGrafico(r.nextInt(1000));
            grafico.addMouseMotionListener(cuadro);
            grafico.addMouseListener(cuadro);
            grafico.addMouseListener(this);
            grafico.addKeyListener(this);
            grafico.setBackground(Color.orange);
            grafico.setForeground(Color.yellow);
            grafico.setComponentPopupMenu(menu);
            ArbolLista copia = arbol.copia();
            if(arbol.insertarAVL(grafico))
            {
                this.pilaArboles.push(copia);
                this.construirArbol();
                Image img = this.createImage(this.getWidth(), this. getHeight());
                Graphics graficador = img.getGraphics();
                this.paintAll(graficador);
                this.getGraphics().drawImage(img, 0, 0, null);
            }
            this.requestFocus();
            repaint();
        }
        if(e.getKeyCode() == KeyEvent.VK_R)
        {
            ArbolLista ar = new ArbolLista();
            ar.setAVL(arbol.isAVL());
            ar.setOrden(arbol.getOrden());
            Random r = new Random();
            for(int i = 0; i < 500; i++)
            {
                ar.insertarAVL(r.nextInt(1000));
            }
            this.crearArbol(ar);
            this.construirArbol();
                Image img = this.createImage(this.getWidth(), this. getHeight());
                Graphics graficador = img.getGraphics();
                this.paintAll(graficador);
                this.getGraphics().drawImage(img, 0, 0, null);
            repaint();
            this.requestFocus();
        }
        if(e.getKeyCode() == KeyEvent.VK_K)
        {
            if(!pilaArboles.isEmpty())
            {
                arbol = (ArbolLista)pilaArboles.pop();
                this.construirArbol();
                Image img = this.createImage(this.getWidth(), this. getHeight());
                Graphics graficador = img.getGraphics();
                this.paintAll(graficador);
                this.getGraphics().drawImage(img, 0, 0, null);
                this.requestFocus();
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
        if(e.getKeyCode() == KeyEvent.VK_A)
        {
            if(arbol != null)
            {
                arbol.setAVL(false);
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
        if(e.getKeyCode() == KeyEvent.VK_M)
        {
            Integer dato = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo dato"));
            NodoGrafico grafico = new NodoGrafico(dato);
            grafico.addMouseMotionListener(cuadro);
            grafico.addMouseListener(cuadro);
            grafico.addMouseListener(this);
            grafico.addKeyListener(this);
            grafico.setBackground(Color.orange);
            grafico.setForeground(Color.yellow);
            grafico.setComponentPopupMenu(menu);
            ArbolLista copia = arbol.copia();
            if(arbol.insertarAVL(grafico))
            {
                pilaArboles.push(copia);
            }
            this.construirArbol();
            Image img = this.createImage(this.getWidth(), this. getHeight());
            Graphics graficador = img.getGraphics();
            this.paintAll(graficador);
            this.getGraphics().drawImage(img, 0, 0, null);
            arbol.preordenMejorado(arbol.getR());
            cuadro.repaint();
        }
    }
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_B)
        {
            focused.requestFocus();
            cuadro.repaint();
        }
    }
    public class JScroll extends JScrollPane implements MouseMotionListener
    {
        public JScroll()
        {
            super();
        }
        public JScroll(JComponent c)
        {
            super(c);
        }

        public void mouseDragged(MouseEvent e)
        {
        }

        @Override
        protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed)
        {
            return true;
        }
        public void mouseMoved(MouseEvent e) {
        }
    }
    public void crearArbol(Object pre[], Object in[])
    {
        Object preN[] = new Object[pre.length];
        Object inN[] = new Object[pre.length];
        boolean existencias[] = new boolean[pre.length+1];
        NodoGrafico grafico = null;
        int k = 0;
        int i = 0;
        for(i = 0; i < existencias.length; i++)
        {
            existencias[i] = false;
        }
        for(i = 0; i < pre.length; i++)
        {
            grafico = new NodoGrafico(pre[i]);
            grafico.addMouseMotionListener(cuadro);
            grafico.addMouseListener(cuadro);
            grafico.addMouseListener(this);
            grafico.addKeyListener(this);
            grafico.setBackground(Color.orange);
            grafico.setForeground(Color.yellow);
            grafico.setComponentPopupMenu(menu);
            k = 0;
            while(k < pre.length && (pre[i] != in[k] | existencias[k]))
            {
                k = k + 1;
            }
            existencias[k] = true;
            preN[i] = grafico;
            inN[k] = grafico;
        }
        arbol = ArbolLista.crearArbolPre(preN, inN);
    }
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        if(e.getSource() instanceof JScrollBar)
        {
            cuadro.repaint();
        }
    }
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
    }
    public void mouseClicked(MouseEvent e)
    {
    }
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1 & e.getSource() instanceof JMenuItem)
        {
            JMenuItem y = (JMenuItem)e.getSource();
            if(y == itemInsertar)
            {
                if(menu.isVisible())
                {
                    JPopupMenu p = menu;
                    if(p.getInvoker() instanceof VentanaArbol | p.getInvoker() instanceof PanelArbol | p.getInvoker() instanceof NodoGrafico)
                    {
                        String dato = JOptionPane.showInputDialog("Ingrese el nuevo dato");
                        NodoGrafico grafico = new NodoGrafico(dato);
                        grafico.addMouseMotionListener(cuadro);
                        grafico.addMouseListener(cuadro);
                        grafico.addMouseListener(this);
                        grafico.addKeyListener(this);
                        grafico.setBackground(Color.orange);
                        grafico.setForeground(Color.yellow);
                        grafico.setComponentPopupMenu(menu);
                        ArbolLista copia = arbol.copia();
                        pilaArboles.push(copia);
                        arbol.insertarAVL(grafico);
                        this.construirArbol();
                        p.setVisible(false);
                        this.paintAll(this.getGraphics());
                        cuadro.repaint();
                    }
                }
            }
            if(y == itemPre)
            {
                if(menu.isVisible())
                {
                    JPopupMenu p = menu;
                    if(p.getInvoker() instanceof VentanaArbol | p.getInvoker() instanceof PanelArbol | p.getInvoker() instanceof NodoGrafico)
                    {
                        arbol.preorden();
                        p.setVisible(false);
                        repaint();
                    }
                }
            }
            if(y == itemPos)
            {
                if(menu.isVisible())
                {
                    JPopupMenu p = menu;
                    if(p.getInvoker() instanceof VentanaArbol | p.getInvoker() instanceof PanelArbol | p.getInvoker() instanceof NodoGrafico)
                    {
                        arbol.posorden();
                        p.setVisible(false);
                        repaint();
                    }
                }
            }
            if(y == itemIn)
            {
                if(menu.isVisible())
                {
                    JPopupMenu p = menu;
                    if(p.getInvoker() instanceof VentanaArbol | p.getInvoker() instanceof PanelArbol | p.getInvoker() instanceof NodoGrafico)
                    {
                        arbol.inorden();
                        p.setVisible(false);
                        repaint();
                    }
                }
            }
            if(y == itemBorrar)
            {
                if(menu.isVisible())
                {
                    JPopupMenu p = menu;
                    if(p.getInvoker() instanceof NodoGrafico)
                    {
                        NodoGrafico x = (NodoGrafico)p.getInvoker();
                        ArbolLista copia = arbol.copia();
                        NodoDoble ax = arbol.padre(x.getNodoDoble());
                        NodoDoble nx = x.getNodoDoble();
                        if(arbol.borrarAVL(nx, ax))
                        {
                            pilaArboles.push(copia);
                        }
                        cuadro.remove(x);
                        p.setVisible(false);
                        this.reconstruirArbol();
                        if(arbol.getRaiz() != null)
                        {
                            if(ax != arbol.getR())
                            {
                                focused = (JComponent)ax.getValue();
                                focused.requestFocus();
                                focused.repaint();
                            }
                            else
                            {
                                focused = (NodoGrafico)arbol.getRaiz().getValue();
                                focused.requestFocus();
                                focused.repaint();
                            }
                        }
                        else
                        {
                            this.requestFocus();
                        }
                        cuadro.repaint();
                    }
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e) 
    {
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
}
