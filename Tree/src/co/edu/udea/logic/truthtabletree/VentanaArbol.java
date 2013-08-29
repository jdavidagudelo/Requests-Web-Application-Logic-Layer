/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Stack;
import javax.swing.*;
public class VentanaArbol extends JFrame implements AdjustmentListener, MouseListener, MouseMotionListener, KeyListener, ActionListener, ComponentListener,
        Serializable
{
    private int gap = 0;
    private JLabel label = null;
    private JScrollPane scroll = null;
    private JScrollPane principal = null;
    private JPanel cPrincipal = null;
    private JTextArea texto = new JTextArea();
    private JButton boton = new JButton();
    private JScrollPane scroll2 = null;
    private MenuTexto menuTexto = new MenuTexto();
    private Stack pilaTablas = new Stack();
    /**
     * Menu principal de la ventana
     */
    private JMenuBar barra = new JMenuBar();
    /**
     * Menú que contiene los items utilizados para modificar el juego, almacenar y abrir un juego, ademas de visualizar los records.
     */
    private JMenu archivo = new JMenu("Archivo");
    /**
     * Al hacer clic en este ítem se muestra el manuel técnico del programa
     */
    private JMenuItem manualTecnico = new JMenuItem("Manual Técnico");
    /**
     * Al hacer clic en este ítem se muestra el manuel de usuario del programa
     */
    private JMenuItem manualUsuario = new JMenuItem("Manual de usuario");
    /**
     * Al hacer clic en este ítem se permite guardar en memoria el juego actual
     */
    private JMenuItem guardarTabla = new JMenuItem("Guardar tabla");
    /**
     * Al hacer clic en este ítem se permite abrir desde memoria un juego
     */
    private JMenuItem abrirTabla = new JMenuItem("Abrir Tabla");
    /**
     * Menu que contiene los temas de ayuda del juego
     */
    private JMenu ayuda = new JMenu("Ayuda");
    /**
     * Al hacer clic en este ítem se cierra la aplicación
     */
    private JMenuItem salir = new JMenuItem("Salir");
    private PanelArbol cuadro = null;
    private ArbolLista arbol = null;
    private PanelTabla tabla = null;
    private JPanel panel = new JPanel();
    private JScrollPane text = null;
    public ArbolLista getArbol()
    {
        return arbol;
    }
    public void setArbol(ArbolLista arbol)
    {
        this.arbol = arbol;
    }
    public void setTexto(JTextArea texto)
    {
        this.texto = texto;
    }
    public JTextArea getTexto()
    {
        return texto;
    }
    public VentanaArbol()
    {
        this.initComponents();
    }
    private void initComponents()
    {
        //se establece que esta ventana se inicializará maximizada
        this.setExtendedState(MAXIMIZED_BOTH);
        texto.setFont(new Font("Arial", 12, 20));
        texto.setComponentPopupMenu(menuTexto);
        this.addComponentListener(this);
        cPrincipal = new JPanel();
        principal = new JScrollPane(cPrincipal);
        principal.setBounds(0, 0, this.getWidth(), this.getHeight());
        cPrincipal.setLayout(null);
        this.add(principal);
        principal.getHorizontalScrollBar().addAdjustmentListener(this);
        principal.getVerticalScrollBar().addAdjustmentListener(this);
        this.setJMenuBar(barra);
        gap = 5;
        barra.add(archivo);
        barra.add(ayuda);
        ayuda.add(manualUsuario);
        ayuda.add(manualTecnico);
        barra.addMouseListener(this);
        archivo.add(abrirTabla);
        archivo.add(guardarTabla);
        archivo.add(salir);
        abrirTabla.addMouseListener(this);
        guardarTabla.addMouseListener(this);
        salir.addMouseListener(this);
        boton.addMouseMotionListener(this);
        texto.addMouseMotionListener(this);
        arbol = new ArbolLista();
        cuadro = new PanelArbol();
        tabla = new PanelTabla();
        cuadro.setArbol(arbol);
        texto.setBounds(10, 10, 100, 40);
        boton.setText("Nueva tabla");
        boton.addActionListener(this);
        boton.setBounds(600, 10, 100, 40);
        cPrincipal.add(boton);
        this.setLayout(null);
        scroll = new JScrollPane(cuadro);
        scroll2 = new JScrollPane(tabla);
        scroll.addMouseListener(this);
        scroll.setBounds(600, 150, 740, 500);
        scroll2.setBounds(10, 150, 550, 500);
        cPrincipal.add(scroll);
        cPrincipal.add(scroll2);
        scroll2.addMouseListener(this);
        scroll.addMouseMotionListener(this);
        scroll2.addMouseMotionListener(this);
        scroll.addComponentListener(this);
        scroll2.addComponentListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        cuadro.addKeyListener(this);
        scroll.addKeyListener(this);
        BorderLayout bl = new BorderLayout();
        bl.setHgap(gap);
        bl.setVgap(gap);
        panel.setLayout(bl);
        label = new JLabel("Ingrese una expresión booleana válida.");
        label.setFont(new Font("Arial", 20, 20));
        panel.add(label, BorderLayout.NORTH);
        panel.add(texto, BorderLayout.CENTER);
        text = new JScrollPane(panel);
        text.setBounds(10, 10, 550, 120);
        cPrincipal.add(text);
        scroll.getHorizontalScrollBar().addAdjustmentListener(this);
        scroll.getVerticalScrollBar().addAdjustmentListener(this);
        scroll2.getHorizontalScrollBar().addAdjustmentListener(this);
        scroll2.getVerticalScrollBar().addAdjustmentListener(this);
        label.setOpaque(true);
        label.setComponentPopupMenu(menuTexto);
        boton.setComponentPopupMenu(menuTexto);
        cuadro.setComponentPopupMenu(menuTexto);
        this.addKeyListener(this);
        boton.addKeyListener(this);
        texto.addKeyListener(this);
        tabla.getTexto().addKeyListener(this);
        label.addKeyListener(this);
        cuadro.addKeyListener(this);
    }
    public void abrirTabla()
    {
        //cuadro de dialogo utilizado para abrir un juego
        JFileChooser x = new JFileChooser();
        //se muestra el cuadro de dialogo
        int y = x.showOpenDialog(this);
        //se evalua se aprobo abrir un archivo de memoria
        if(y == JFileChooser.APPROVE_OPTION)
        {
            //excepcion utilizada para evaluar si el archivo es correcto o no
            try
            {
                //se obtiene un archivo de lectura del archivo seleccionado por el usuario
                FileInputStream fis = new FileInputStream(x.getSelectedFile());
                //se obtiene un archivo que almacena objetos del archivo de lectura
                ObjectInputStream ois = new ObjectInputStream(fis);
                //De acuerdo con la forma que se han almacenado los juegos, el archivo contendra un objeto de la clase Juego
                tabla.setTabla((Tabla)ois.readObject());
                label.setText("Ingrese una expresión booleana válida.");
                this.crearArbol(tabla.getExpresion().getOperaciones());
                this.construirArbol();
                repaint();
                //se cierra el archivo con objetos
                ois.close();
                //se cierra el archivo de lectura
                fis.close();
            }
            catch(Exception ex)
            {
                //si el archivo no es correcto se le muestra el mensaje al usuario
                JOptionPane.showMessageDialog(this, "El archivo seleccionado no es correcto\n"+ex.getMessage());
            }
        }
    }
    public void guardarTabla()
    {
        JFileChooser x = new JFileChooser();
        boolean continuar = true;
        //se realiza un ciclo para permitir al usuario ingresar nombre de archivo apropiado
        while(continuar)
        {
            int y = x.showSaveDialog(this);
            //si se canceló la operacion de guardado se termina el ciclo
            if(y == JFileChooser.CANCEL_OPTION)
            {
                continuar = false;
            }
            //se evalua si el usuario hizo clic en guardar
            if(y == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    //se obtiene el archivo que el usuario desea guardar
                    File f = x.getSelectedFile();
                    //se evalua si un archivo con el mismo nombre ya existe en la memoria
                    if(f.exists())
                    {
                        //Se le pregunta al usuario si desea reemplazar un archivo existente en memoria con el nuevo archivo
                        //quye contendra el juego
                        int z = JOptionPane.showConfirmDialog(this, "El nombre de archivo seleccionado ya existe, ¿Desea reemplazarlo?");
                        //se evalua si el usuario acepto reemplazar un archivo existente
                        if(z == JOptionPane.OK_OPTION)
                        {
                            //se lee el archivo
                            FileOutputStream fos = new FileOutputStream(f);
                            //se obtiene el objeto para escribir objetos
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            //se almacena el juego actual en memoria
                            oos.writeObject(tabla.getTabla());
                            //se cierran los archivos
                            oos.close();
                            fos.close();
                            //se termina el ciclo
                            continuar = false;
                        }
                        //si se cancela se termina el ciclo
                        if(z == JOptionPane.CANCEL_OPTION)
                        {
                            continuar = false;
                        }
                    }
                    else
                    {
                        //si el archivo no existe se termina el ciclo
                        continuar = false;
                        FileOutputStream fos = new FileOutputStream(x.getSelectedFile());
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(tabla.getTabla());
                        oos.close();
                        fos.close();
                    }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this, "Error al guardar el Archivo\n"+ex.getMessage());
                }
            }
        }
    }
    public void setTabla(PanelTabla tabla)
    {
        this.tabla = tabla;
    }
    public PanelTabla getTabla()
    {
        return tabla;
    }
    public void reconstruirArbol()
    {
        cuadro.setArbol(arbol);
        cuadro.reconstruirArbol();
    }
    public void construirTabla()
    {
        final Tabla t = tabla.getTabla();
        new Thread()
        {
            @Override
            public void run()
            {
               String s = texto.getText();
               Expresion ex = null;
               try
               {
                   ex = Expresion.arbolExpresiones(s);
                   label.setText("Construyendo tabla...");
                   tabla.setExpresion(ex);
               }
               catch(Exception exp)
               {
                   JOptionPane.showMessageDialog(null, exp.getMessage());
                   tabla.setTabla(t);
                   label.setText("Ingrese una expresión booleana válida.");
                   return;
               }
               tabla.crearTabla(label);
               label.setText("Ingrese una expresión booleana válida.");
               crearArbol(ex.getOperaciones());
               construirArbol();
               repaint();
            }
        }.start();
    }
    public void construirArbol()
    {
        cuadro.setArbol(arbol);
        cuadro.construirArbol();
    }
    public void crearArbol(ArbolLista arbol)
    {
        Object pre[] = arbol.preordenMejorado();
        Object in[] = arbol.inordenMejorado();
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
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z)
        {
            if(!pilaTablas.isEmpty())
            {
                tabla.setTabla((Tabla)pilaTablas.pop());
                label.setText("Ingrese una expresión booleana válida.");
                this.crearArbol(tabla.getExpresion().getOperaciones());
                this.construirArbol();
                repaint();
            }
        }
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
            cuadro.repaint();
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof JButton)
        {
           JButton b = (JButton)e.getSource();
           if(b == boton)
           {
               if(tabla.getTabla() != null && tabla.getTabla().getExpresion() != null)
               {
                   pilaTablas.push(tabla.getTabla().clonar());
               }
               this.construirTabla();
           }
        }
    }
    public void componentResized(ComponentEvent e)
    {
        if(e.getSource() instanceof VentanaArbol)
        {
            VentanaArbol v = (VentanaArbol)e.getSource();
            v.principal.setBounds(0, 0, v.getWidth()-2*gap, v.getHeight()-60);
            Image img = v.createImage(v.getWidth(), v.getHeight());
            Graphics g = img.getGraphics();
            v.paintAll(g);
            v.getGraphics().drawImage(img, 0, 0, null);
        }
    }
    public void componentMoved(ComponentEvent e) 
    {
    }
    public void componentShown(ComponentEvent e)
    {
        if(e.getSource() instanceof VentanaArbol)
        {
            VentanaArbol v = (VentanaArbol)e.getSource();
            v.cPrincipal.setPreferredSize(this.getBounds().getSize());
        }
    }
    public void componentHidden(ComponentEvent e)
    {
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
            grafico.setComponentPopupMenu(menuTexto);
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
            if(this.getWidth() > 0 && this.getHeight() > 0)
            {
                Image img = this.createImage(this.getWidth(), this.getHeight());
                Graphics g = img.getGraphics();
                this.paintAll(g);
                this.getGraphics().drawImage(img, 0, 0, null);
                this.repaint();
            }
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
            if(y == guardarTabla)
            {
                this.guardarTabla();
            }
            if(y == abrirTabla)
            {
                int x = JOptionPane.showConfirmDialog(barra, "¿Desea Guardar el juego actual?");
                if(x == JOptionPane.OK_OPTION)
                {
                    this.guardarTabla();
                }
                if(x == JOptionPane.CANCEL_OPTION)
                {
                    return;
                }
                this.abrirTabla();
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