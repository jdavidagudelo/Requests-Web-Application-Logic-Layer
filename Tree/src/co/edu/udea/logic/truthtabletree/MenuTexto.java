/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;
public class MenuTexto extends JPopupMenu implements MouseListener, KeyListener, PopupMenuListener, Serializable
{
    private FontDialog ventanaFuentes = null;
    /**
     * Al usuario hacer click en este item se le permite modificar el color de la fuente del componente en el que
     * el usuario hizo click derecho para mostrar el menu que lo contiene
     */
    private JMenuItem itemColorFuente;
    /**
     * Al usuario hacer click en este item se le permite modificar el color del fondo del componente en el que el usuario
     * hizo click derecho para mostrar el menu que lo contiene
     */
    private JMenuItem itemColorFondo;
    /**
     * Al usuario hacer click en este item se le permite copiar el texto que se encuentra seleccionado en el componente en
     * el que se hizo click para mostrar el menu que lo contiene
     */
    private JMenuItem itemCopiar;
    /**
     * Al usuario hacer click en este item se realiza el pegado en el componente en el que se hizo click para mostrar el
     * menu que lo contiene, considerando el texto que se encuentra copiado en el sistema operativo
     */
    private JMenuItem itemPegar;
    /**
     * Al usuario hacer click en este item se corta el texto que se encuentre seleccionado en el componente en el
     * cual se hizo click derecho para mostrar el menu.
     */
    private JMenuItem itemCortar;
    /**
     * Al usuario hacer click en este item se selecciona todo el texto del componente en el cual se hizo click derecho para
     * mostrar el menu
     */
    private JMenuItem itemSeleccionarTodo;
    private JMenuItem itemNodesSize;
    private JMenuItem itemTipoFuente;
    private JMenuItem itemInorden;
    private JMenuItem itemPreorden;
    private JMenuItem itemPosorden;
    private JMenuItem itemFondoPanel;
    private JMenuItem itemNull;
    public MenuTexto()
    {
        super();
        this.initComponents();
    }
    private void initComponents()
    {
        itemNodesSize = new JMenuItem("Tamaño nodos");
        itemTipoFuente = new JMenuItem("Fuente");
        itemFondoPanel = new JMenuItem("Color Panel");
        itemPreorden = new JMenuItem("Preorden");
        itemInorden = new JMenuItem("Inorden");
        itemPosorden = new JMenuItem("Posorden");
        itemNull = new JMenuItem("Ninguno");
        //se crea y se inicializa el texto del item para modificar el color de la fuente de algun componente
        itemColorFuente = new JMenuItem("Color fuente");
        //se crea y se inicializa el texto del item para modificar el color del fondo de algun componente  de la aplicacion
        itemColorFondo = new JMenuItem("Color fondo");
        //se crea y se incializa el texto del item para seleccionar el texto de algun componente de la aplicacion
        itemSeleccionarTodo = new JMenuItem("Seleccionar todo");
        //se crea y se incializa el texto del item para copiar el texto de algun componente de la aplicacion
        itemCopiar = new JMenuItem("Copiar");
        //se crea y se incializa el texto del item para pegar el texto de algun componente de la aplicacion
        itemPegar = new JMenuItem("Pegar");
        //se crea y se incializa el texto del item para cortar el texto de algun componente de la aplicacion
        itemCortar = new JMenuItem("Cortar");
        this.addMenuKeyListener(this.itemColorFondo);
        this.addMenuKeyListener(this.itemColorFuente);
        this.addMenuKeyListener(this.itemCopiar);
        this.addMenuKeyListener(this.itemCortar);
        this.addMenuKeyListener(this.itemFondoPanel);
        this.addMenuKeyListener(this.itemInorden);
        this.addMenuKeyListener(this.itemNodesSize);
        this.addMenuKeyListener(this.itemPegar);
        this.addMenuKeyListener(this.itemPosorden);
        this.addMenuKeyListener(this.itemPreorden);
        this.addMenuKeyListener(this.itemSeleccionarTodo);
        this.addMenuKeyListener(this.itemTipoFuente);
        this.addMenuKeyListener(this.itemNull);
        this.addPopupMenuListener(this);
        //se establece esta ventana como el escuchador de eventos del mouse para los items del menu
        itemColorFuente.addMouseListener(this);
        itemColorFondo.addMouseListener(this);
        itemTipoFuente.addMouseListener(this);
        itemNodesSize.addMouseListener(this);
        itemPegar.addMouseListener(this);
        itemCopiar.addMouseListener(this);
        itemCortar.addMouseListener(this);
        itemSeleccionarTodo.addMouseListener(this);
        itemFondoPanel.addMouseListener(this);
        itemPreorden.addMouseListener(this);
        itemPosorden.addMouseListener(this);
        itemInorden.addMouseListener(this);
        itemNull.addMouseListener(this);
        ventanaFuentes = new FontDialog();
    }
    private void addMenuKeyListener(final JMenuItem item)
    {
        item.addMenuKeyListener(new MenuKeyListener()
        {
            //sin implementar
            public void menuKeyTyped(MenuKeyEvent e)
            {
            }
            //este es el evento que se desea implementar
            public void menuKeyPressed(MenuKeyEvent e) {
                //se establece que este item sera la fuente donde se produjo el evento
                e.setSource(item);
                //en este método se procesa el evento considerando la fuente que lo generó
                menuItemPressed(e);
            }
            //sin implementar
            public void menuKeyReleased(MenuKeyEvent e) {
            }
        });
    }
    /**
     * Metodo que es llamado cada vez que el usuario presiona una tecla mientras recorre el menu que se muestra cada vez que se hace click derecho
     * sobre algun componente de la aplicacion
     */
    private void menuItemPressed(MenuKeyEvent e)
    {
        //se obtiene la fuente del evento
        Object obj = e.getSource();
        //se evalua si la fuente del evento es un item del menu
        if(obj instanceof JMenuItem)
        {
            //se obtiene el item en el cual se produjo el evento
            JMenuItem actual = (JMenuItem)obj;
            //se evalua si el item esta siendo seleccionado
            if(actual.isArmed())
            {
                //se evalua si la tecla presionada es ENTER
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    //en caso de que se presione la tecla enter se produce un evento de mousePressed que hace lo mismo que si se hubiera
                    //presionado el mouse en el item
                    mousePressed(new MouseEvent(actual, e.getID(), e.getWhen(), MenuKeyEvent.ALT_MASK, (int)actual.getBounds().getX(),
                            (int)actual.getBounds().getY(), 1, true));
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        Object obj = e.getSource();
        //se evalua si la fuente del evento es un item del menu
        if(obj instanceof JMenuItem)
        {
            JMenuItem actual = (JMenuItem)obj;
            //se evalua si la fuente del evento fue el item para seleccionar todo el texto de un componente
            if(actual == itemSeleccionarTodo)
            {
                //se evalua si el componente pertenece al menu
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    //se evalua si el componente en el que se hizo click para mostrar el menu es un campo de texto
                    if(pActual.getInvoker() instanceof JTextArea)
                    {
                        //se selecciona todo el texto del campo
                        JTextArea tActual = (JTextArea)pActual.getInvoker();
                        tActual.selectAll();
                    }
                }
            }
            //se evalua si la fuente del evento fue el item para cortar el texto de un componente
            if(itemCortar == actual)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JTextArea)
                    {
                        JTextArea tActual = (JTextArea)pActual.getInvoker();
                        tActual.cut();
                    }
                }
            }
            if(actual == itemPreorden)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JComponent)
                    {
                        PanelArbol panel = (PanelArbol)pActual.getInvoker();
                        panel.getArbol().preorden();
                        panel.repaint();
                    }
                }
            }
            if(actual == itemPosorden)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JComponent)
                    {
                        PanelArbol panel = (PanelArbol)pActual.getInvoker();
                        panel.getArbol().posorden();
                        panel.repaint();
                    }
                }
            }
            if(actual == itemNull)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JComponent)
                    {
                        PanelArbol panel = (PanelArbol)pActual.getInvoker();
                        panel.getArbol().desenhebrar();
                        panel.repaint();
                    }
                }
            }
            if(actual == itemInorden)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JComponent)
                    {
                        PanelArbol panel = (PanelArbol)pActual.getInvoker();
                        panel.getArbol().inorden();
                        panel.repaint();
                    }
                }
            }
            //se evalua si la fuente del evento fue el item para pegar el texto de un componente
            if(actual == itemPegar)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JTextArea)
                    {
                        JTextArea tActual = (JTextArea)pActual.getInvoker();
                        if(tActual.isEditable())
                        {
                            try
                            {
                                tActual.paste();
                            }
                            catch(Exception r)
                            {

                            }
                        }
                    }
                }
            }
            //se evalua si la fuente del evento fue el item para copiar el texto de un componente
            if(actual == itemNodesSize)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof PanelArbol)
                    {
                        PanelArbol tActual = (PanelArbol)pActual.getInvoker();
                        int size = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño de los nodos del árbol"));
                        tActual.setSize(size);
                        tActual.reconstruirArbol();
                    }
                }
            }
            //se evalua si la fuente del evento fue el item para copiar el texto de un componente
            if(actual == itemCopiar)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JTextComponent)
                    {
                        JTextComponent tActual = (JTextComponent)pActual.getInvoker();
                        try
                        {
                            tActual.copy();
                        }
                        catch(Exception r)
                        {
                        }
                    }
                }
            }
            if(actual == itemTipoFuente)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JComponent)
                    {
                        final JComponent jActual = (JComponent)pActual.getInvoker();
                        ventanaFuentes.setFuente(jActual.getFont());
                        ventanaFuentes.setFontSize(jActual.getFont().getSize());
                        ventanaFuentes.setVisible(true);
                        ventanaFuentes.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
                        Thread t = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                while(ventanaFuentes.isVisible())
                                {
                                }
                                jActual.setFont(ventanaFuentes.getFuente());
                                if(jActual instanceof JTextComponent)
                                {
                                    JTextComponent ta = (JTextComponent)jActual;
                                    ta.setText(ta.getText());
                                }
                                if(jActual instanceof PanelArbol)
                                {
                                    PanelArbol pa = (PanelArbol)jActual;
                                    pa.setFuenteNodos(ventanaFuentes.getFuente());
                                    pa.reconstruirArbol();
                                }
                            }
                        };
                        t.start();
                    }
                }
            }
            //se evalua si la fuente del evento fue el item para modificar el color de la fuente de algun componente
            if(actual == itemColorFuente)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JComponent)
                    {
                        JComponent tActual = (JComponent)pActual.getInvoker();
                        Color colorActual = JColorChooser.showDialog(itemColorFuente, "Color fuente", tActual.getForeground());
                        if(tActual instanceof PanelArbol)
                        {
                            PanelArbol pa = (PanelArbol)tActual;
                            pa.setColorFuenteNodos(colorActual);
                            pa.reconstruirArbol();
                        }
                        else
                        {
                            tActual.setForeground(colorActual);
                        }

                    }
                }
            }
            if(actual == itemFondoPanel)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JComponent)
                    {
                        JComponent tActual = (JComponent)pActual.getInvoker();
                        if(tActual instanceof JPanel)
                        {
                            Color colorActual = JColorChooser.showDialog(itemColorFuente, "Color fuente", tActual.getForeground());
                            tActual.setBackground(colorActual);
                        }
                    }
                }
            }
            //se evalua si la fuente del evento fue el item para modificar el color del fondo de algun componente
            if(actual == itemColorFondo)
            {
                if(actual.getParent() instanceof JPopupMenu)
                {
                    JPopupMenu pActual = (JPopupMenu)actual.getParent();
                    if(pActual.getInvoker() instanceof JComponent)
                    {
                        JComponent tActual = (JComponent)pActual.getInvoker();
                        Color colorActual = JColorChooser.showDialog(itemColorFuente, "Color fondo", tActual.getBackground());
                        if(tActual instanceof PanelArbol)
                        {
                            PanelArbol pa = (PanelArbol)tActual;
                            pa.setColorFondoNodos(colorActual);
                            pa.reconstruirArbol();
                        }
                        else
                        {
                            tActual.setBackground(colorActual);
                        }
                    }
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e)
    {
        if(e.getSource() instanceof MenuTexto)
        {
            MenuTexto jpm = (MenuTexto)e.getSource();
            if(jpm.getInvoker() instanceof JTextComponent)
            {
                jpm.add(this.itemTipoFuente);
                jpm.add(this.itemColorFondo);
                jpm.add(this.itemCopiar);
                JTextComponent jtc = (JTextComponent)jpm.getInvoker();
                if(jtc.isEditable())
                {
                    jpm.add(this.itemPegar);
                    jpm.add(this.itemCortar);
                }
                jpm.add(this.itemSeleccionarTodo);
                jpm.add(this.itemColorFuente);
            }
            if(jpm.getInvoker() instanceof NodoGrafico | jpm.getInvoker() instanceof JLabel | jpm.getInvoker() instanceof JButton)
            {
                jpm.add(this.itemColorFondo);
                jpm.add(this.itemColorFuente);
                jpm.add(this.itemTipoFuente);
                jpm.add(this.itemInorden);
                jpm.add(this.itemPosorden);
                jpm.add(this.itemNull);
                jpm.add(this.itemPreorden);
            }
            if(jpm.getInvoker() instanceof PanelArbol)
            {
                jpm.add(this.itemColorFondo);
                jpm.add(this.itemColorFuente);
                jpm.add(this.itemTipoFuente);
                jpm.add(this.itemNodesSize);
                jpm.add(this.itemInorden);
                jpm.add(this.itemPosorden);
                jpm.add(this.itemNull);
                jpm.add(this.itemPreorden);
                jpm.add(this.itemFondoPanel);
            }
        }
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) 
    {
        if(e.getSource() instanceof MenuTexto)
        {
            MenuTexto jpm = (MenuTexto)e.getSource();
            jpm.removeAll();
        }
    }

    public void popupMenuCanceled(PopupMenuEvent e) {
    }
}
