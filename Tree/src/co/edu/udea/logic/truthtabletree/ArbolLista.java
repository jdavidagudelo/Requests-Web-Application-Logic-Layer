package co.edu.udea.logic.truthtabletree;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
/**
 * Clase que permite representar un árbol binario como una lista ligada.
 */
public class ArbolLista<K, V> implements Serializable, Clonable, Map<K, V>
{
    public static final int TYPE_NODE = 0;
    public static final int TYPE_KEY = 1;
    public static final int TYPE_VALUE = 2;
    /**
     * Nodo utilizado para acceder al principio del árbol, es utilizado como la cabeza
     * de una lista ligada circular para enhebrar el árbol en cualquiera de los ordenes posibles.
     */
    private NodoDoble R = null;
    /**
     * Variable booleana que indica si el árbol es un árbol binario de búsqueda o no.
     * Por defecto se inicializa en true.
     */
    private boolean search = true;
    /**
     * Variable booleana que indica si el árbol es AVL. Por defecto se inicializa en true.
     */
    private boolean AVL = true;
    /**
     * Constante que indica que un árbol binario esta siendo enhebrado en preorden, esto implica que para realizar el recorrido
     * preorden sobre el árbol no será necesario utilizar pilas o recursión.
     */
    public static final int PREORDEN = 0;
    /**
     * Constante que indica que un árbol binario esta siendo enhebrado en inorden, esto implica que para realizar el recorrido
     * inorden sobre el árbol no será necesario utilizar pilas o recursión.
     */
    public static final int INORDEN = 1;
    /**
     * Constante que indica que un árbol binario esta siendo enhebrado en preorden, esto implica que para realizar el recorrido posorden
     * sobre el árbol no será necesario utilizar pilas o recursión.
     */
    public static final int POSORDEN = 2;
    /**
     * Constante que indica que el árbol no esta siendo enhebrado, lo cual implica que para realizar todos los recorridos sobre el
     * árbol es necesario utilizar pilas o recursión.
     */
    public static final int NULL = 3;
    /**
     * Variable que indica la forma como esta siendo enhebrado este árbol binario, se incializa sin enhebrado alguno.
     */
    private Integer orden = NULL;
    /**
     * Variable que indica el número de elementos que continene este árbol binario.
     */
    private Integer length = 0;
    /**
     * Permite establecer si este árbol es de búsqueda o no.
     * @param search true si el árbol es de busqueda false en otro caso.
     */
    public void setSearch(boolean search)
    {
        this.search = search;
    }
    /**
     * Retorna un valor que indica si el árbol es de búsqueda o no.
     * @return true si este árbol binario es de busqueda, false en otro caso.
     */
    public boolean isSearch()
    {
        return search;
    }
    /**
     * Establece el nodo cabeza de este árbol binario.
     * @param R el nuevo nodo cabeza de este árbol.
     */
    public void setR(NodoDoble R)
    {
        this.R = R;
    }
    /**
     * Constructor que crea un nuevo arbol binario con las mismas propiedades del 
     * arbolLista especificado.
     * @param orig árbol lista que será copiado para crear el nuevo árbol
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ArbolLista(ArbolLista orig)
    {
        this();
        this.setRaiz(new NodoDoble(orig.getRaiz()));
        //se enhebra el nuevo árbol de acuerdo con el orden del árbol especificado
        if(orig.getOrden().equals(ArbolLista.INORDEN))
        {
            this.inorden();
        }
        else if(orig.getOrden().equals(ArbolLista.PREORDEN))
        {
            this.preorden();
        }
        else if(orig.getOrden().equals(ArbolLista.POSORDEN))
        {
            this.posorden();
        }
        this.length = orig.length;
        this.AVL = orig.AVL;
        this.search = orig.search;
    }
    /**
     * Método que permite obtener el subárbol que tiene como raiz el nodo especificado como
     * argumento. El subarbol obtenido tiene las mismas propiedades de este árbol.
     * @param El nodo doble que será la raiz del subárbol que se desea obtener. Este nodo
     * debe ser un nodo que contenga un dato, el nodo cabeza no es válido como argumento y si
     * se ingresa un nodo que no pertenece a este árbol los resultados pueden ser incorrectos.
     * @return El subárbol que tiene como raiz el nodo especificado como argumento
     */
    public ArbolLista subarbol(NodoDoble x)
    {
        ArbolLista subarbol = new ArbolLista();
        subarbol.AVL = AVL;
        subarbol.length = this.size(x);
        subarbol.search = search;
        subarbol.setRaiz(new NodoDoble(x));
        if(orden.equals(ArbolLista.INORDEN))
        {
            subarbol.inorden();
        }
        else if(orden.equals(ArbolLista.PREORDEN))
        {
            subarbol.preorden();
        }
        else if(orden.equals(ArbolLista.POSORDEN))
        {
            subarbol.posorden();
        }
        return subarbol;
    }
    /**
     * Metodo que permite eliminar las hebras construidas para este árbol, teniendo en cuenta
     * el tipo de enhebrado que se halla utilizado
     */
    public void desenhebrar()
    {
        if(orden.equals(INORDEN))
        {
            this.desenhebrarIn();
        }
        else if(orden.equals(POSORDEN))
        {
            this.desenhebrarPos();
        }
        else if(orden.equals(PREORDEN))
        {
            this.desenhebrarPre();
        }
    }
    /**
     * Método utilizado para establecer en null las hebras de este árbol si esta enhebrado en preorden
     */
    public void desenhebrarPre()
    {
        NodoDoble x = this.siguientePre(this.getR());
        NodoDoble y = x;
        //se recorren todos los nodos del arbol y se evalua cuales tienen hebra y se establece dicho campo en null
        while(x != R && x != null)
        {
            x = this.siguientePre(x);
            if(!y.isBd())
            {
                y.setLd(null);
                y.setBd(true);
            }
            if(!y.isBi())
            {
                y.setLi(null);
                y.setBi(true);
            }
            y = x;
        }
        orden = NULL;
    }
    /**
     * Método utilizado para establecer en null las hebras de este árbol si esta enhebrado en inorden
     */
    public void desenhebrarPos()
    {
        NodoDoble x = this.siguientePos(R);
        NodoDoble y = x;
        while(x != R && x != null)
        {
            x = this.siguientePos(x);
            if(!y.isBd())
            {
                y.setLd(null);
                y.setBd(true);
            }
            if(!y.isBi())
            {
                y.setLi(null);
                y.setBi(true);
            }
            y = x;
        }
        orden = NULL;
    }
    /**
     * Método utilizado para establecer en null las hebras de este árbol si esta enhebrado en preorden
     */
    public void desenhebrarIn()
    {
        NodoDoble x = this.siguienteIn(this.getR());
        NodoDoble y = x;
        while(x != this.getR() && x != null)
        {
            x = this.siguienteIn(x);
            if(!y.isBd())
            {
                y.setLd(null);
                y.setBd(true);
            }
            if(!y.isBi())
            {
                y.setLi(null);
                y.setBi(true);
            }
            y = x;
        }
        orden = NULL;
    }
    /**
     * Método que permite establecer el orden para el que esta siendo enhebrado este árbol binario.
     * El valor estandar es ArbolLista.NULL, este valor debe ser establecido preferiblemente antes de
     * insertar cualquier elemento en el árbol. Segun el orden que se establezca el árbol se enhebrara
     * y esto hara el recorrido seleccionado mucho más eficiente.
     * @param orden el nuevo orden de este arbol binario el cual puede ser cualquiera de las constantes:
     *  - ArbolLista.INORDEN: El árbol estara enhebrado en inorden
     *  - ArbolLista.PREORDEN: El árbol estara enhebrado en preorden
     *  - ArbolLista.POSORDEN: El árbol estara enhebrado en posorden
     *  - ArbolLista.NULL: El árbol no tendrá hebra alguna.
     */
    public void setOrden(Integer orden)
    {
        this.orden = orden;
    }
    /**
     * Obtiene el tipo de enhebrado de este árbol binario.
     * @return el tipo de enhebrado de este árbol binario
     */
    public Integer getOrden()
    {
        return orden;
    }
    public void setAVL(boolean AVL)
    {
        this.AVL = AVL;
    }
    public boolean isAVL()
    {
        return AVL;
    }
    /**
     * Constructor que crea un nuevo arbol vacio, la cabeza del árbol tiene sus dos campos de liga
     * apuntando hacia el mismo y true el campo de bd y false el campo de bi
     */
    public ArbolLista()
    {
        R = new NodoDoble();
        R.setBd(true);
        R.setBi(false);
        R.setLd(R);
        R.setLi(R);
    }
    /**
     * Retorna eñ número de elementos contenidos en este árbol binario
     * @return El número de elementos contenidos en este árbol binario
     */
    public Integer getLength()
    {
        return length;
    }
    /**
     * Establece el número de elementos contenidos por este arbol binario
     * @param length el nuevo número de elementos contenido por este árbol binario
     */
    public void setLength(Integer length)
    {
        this.length = length;
    }
    /**
     * Método que permite obtener el nodo cabeza del árbol binario, el cual representa el nodo inicial de los recorridos del
     * árbol binario según el tipo de hebras que este tenga establecidas
     * @return el nodo cabeza de este arbol binario
     */
    public NodoDoble getR()
    {
        return R;
    }
    /**
     * Método que permite obtener el primer elemento del árbol binario o null en caso de que el árbol este vacio
     * @return la raiz del árbol binario o null si esta vacio
     */
    public NodoDoble getRaiz()
    {
        return R.isBi()? R.getLi() : null;
    }
    /**
     * Método que permite obtener el número de hojas del subarbol que tiene como raiz el nodo ingresado como argumento
     * @param x la raiz del subarbol al cual se le desea contar el número de hojas
     * @return el número de hojas del subarbol que tiene como raiz el nodo ingresado como argumento
     */
    public int hojas(NodoDoble x)
    {
        //si el nodo es null se retorna 0
        if(x == null)
        {
            return 0;
        }
        int hojas = 0;
        //si el bit derecho es false, significa que el nodo no tiene hijo derecha
        if(x.isBd())
        {
            if(x.isBi())
            {
                //si el nodo tiene hijo derecho e izquierdo se continua el recorrido en los subarboles correspondientes
                hojas = hojas + hojas(x.getLi()) + hojas(x.getLd());
            }
            else
            {
                //si solo tiene hijo derecho se realiza el llamado recursivo para el hijo derecho
                hojas = hojas + hojas(x.getLd());
            }
        }
        else
        {
            if(x.isBi())
            {
                //si solo tiene hijo izquierdo se realiza llamado recursivo para el hijo izquierdo
                hojas = hojas + hojas(x.getLi());
            }
            else
            {
                //el nodo no tiene hijo, por lo tanto es una hoja y se debe aumentar el contador en 1
                hojas = hojas + 1;
            }
        }
        return hojas;
    }
    /**
     * Método que permite obtener la altura del subarbol cuya raiz es el nodo ingresado como argumento
     * @param x la raiz del subarbol al cual se le desea calcular la altura
     * @return la altura del subarbol cuya raiz es el nodo ingresado como parametro
     */
    public int altura(NodoDoble x)
    {
        if(x == null)
        {
            return 0;
        }
        //si el nodo no es null la mínima altura del subarbol es 1
        int h = 1;
        int hMax = 1;
        //se halla la altura del subarbol cuya raiz es el hijo derecho del nodo ingresado como parametro
        if(x.isBd())
        {
            h = 1 + altura(x.getLd());
        }
        else
        {
            h = 1;
        }
        //la altura es la máxima de todos los subarboles
        if(h > hMax)
        {
            hMax = h;
        }
        //se halla la altura del subarbol cuya raiz es el hijo izquierdo del nodo ingresado como parametro
        if(x.isBi())
        {
            h = 1 + altura(x.getLi());
        }
        else
        {
            h = 1;
        }
        //la altura es la máxima de todos los subarboles
        if(h > hMax)
        {
            hMax = h;
        }
        return hMax;
    }
    /**
     * Método utilizado para eliminar un nodo de un arbol AVL que tiene dos hijos, para esto
     * recibe como parametro el nodo que se desea eliminar del árbol, intercambia su valor con
     * el nodo que tiene el menor valor de su subarbol derecho y luego elimina ese valor como si fuera
     * un nodo hoja o un nodo con solo un hijo.
     * @param x El nodo que se desea eliminar del árbol AVL
     */
    public void menor(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        //se evalua si el árbol esta enhebrado o no
        if(orden.equals(NULL))
        {
            //una vez finaliza el ciclo p corresponde al nodo con el menor valor del subárbol derecho de p
            while(p.getLi() != null)
            {
                p = p.getLi();
            }
            //se obtiene el padre del nodo p
            NodoDoble pp = this.padre(p);
            //se modifica el valor del nodo x
            x.setValue(p.getValue());
            //se modifica la clave del nodo x
            x.setKey(p.getKey());
            //se elimina el nodo p del arbol
            this.eliminarNodo(p, pp);
        }
        else
        {
            //una vez finaliza el ciclo p corresponde al nodo con el menor valor del subárbol derecho de p
            while(p.isBi())
            {
                p = p.getLi();
            }
            //se obtiene el padre del nodo p
            NodoDoble pp = padre(p);
            //se modifica el valor del nodo x
            x.setValue(p.getValue());
            //se modifica la clave del nodo x
            x.setKey(p.getKey());
            //se elimina el nodo p del arbol
            this.eliminarNodo(p, pp);
        }
    }
    /**
     * Método utilizado para balancear el arbol AVL cuando un nodo ha sido eliminado, para esto recibe como
     * argumento el padre del nodo que ha sido eliminado del árbol y con base en su factor de balance y el
     * factor de balance del padre de este nodo se decide que modificaciones se deben hacer en el  árbol con
     * el fin de que continue siendo AVL.
     * @param q el nodo padre del nodo que se ha eliminado del árbol
     */
    public void rebalancearArbol(NodoDoble q)
    {
        NodoDoble pq = null;
        if(q.getFb().equals(0))
        {
            pq = padre(q);
            if(pq == null)
            {
                return;
            }
            if(pq.getFb().equals(0))
            {
                //si el factor de balance del padre del nodo eliminado y su abuelo son cero, solo es necesario
                //modificar el factor de balance a 1 o -1 si q es la liga derecha o la liga izquierda de su padre respectivamente
                if(orden.equals(NULL))
                {
                    if(q == pq.getLd())
                    {
                        pq.setFb(1);
                    }
                    else
                    {
                        pq.setFb(-1);
                    }
                }
                else
                {
                    if(q == pq.getLd() & pq.isBd())
                    {
                        pq.setFb(1);
                    }
                    else
                    {
                        pq.setFb(-1);
                    }
                }
            }
            else
            {
                //el factor de balance de pq es 1 o -1, luego se suma o se resta 1 al factor de balance de pq
                if(orden.equals(NULL))
                {
                    if(q == pq.getLd())
                    {
                        pq.setFb(pq.getFb() + 1);
                    }
                    else
                    {
                        pq.setFb(pq.getFb() - 1);
                    }
                }
                else
                {
                    if(q == pq.getLd() & pq.isBd())
                    {
                        pq.setFb(pq.getFb() + 1);
                    }
                    else
                    {
                        pq.setFb(pq.getFb() - 1);
                    }
                }
                //se debe rotar el arbol a partir del padre de q hasta llegar a la raiz del árbol
                this.rotarMenosAltura(pq);
            }
        }
        else
        {
            //si el factor de balance de q es 2 o -2 se debe rotar el arbol a partir del nodo q
            //si el factor de balance de q es 1 o -1 no es necesario realizar rotaciones o modificaciones de factores de balance
            if(q.getFb().equals(2) | q.getFb().equals(-2))
            {
                rotarMenosAltura(q);
            }
        }
    }
    /**
     * Método que permite eliminar un nodo de un árbol AVL, para esto recibe como parametros el nodo que se desea
     * eliminar del árbol y su padre. En el método se eliminan las hojas o los nodos con un unico hijo, para nodo con
     * dos hijos llama el método menor que luego lo llamara nuevamente con el parametro adecuado. Además en este metodo se
     * mantienen las hebras del árbol en caso de que este enhebrado, para lo cual se llaman los métodos apropiados para cada
     * tipo de enhebrado. En este método se modifica el factor de balance del padre del nodo que se desea eliminar y
     * se desconecta del árbol teniendo en cuenta el tipo de enhebrado.
     * @param p El nodo que se desea eliminar del árbol AVL
     * @param q El padre del nodo que se desea eliminar del árbol AVL
     */
    public void eliminarNodo(NodoDoble p, NodoDoble q)
    {
        if(orden.equals(NULL))
        {
            if(p.getLd() == null & p.getLi() == null)
            {
                if(q == R)
                {
                    R.setLi(R);
                    R.setBi(false);
                    return;
                }
                if(p == q.getLd())
                {
                    q.setFb(q.getFb() + 1);
                    this.eliminarNormal(p, q);
                    if(q.getFb().equals(1))
                    {
                        return;
                    }
                    this.rebalancearArbol(q);
                }
                else
                {
                    if(p == q.getLi())
                    {
                        q.setFb(q.getFb() - 1);
                        this.eliminarNormal(p, q);
                        if(q.getFb().equals(-1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                }
            }
            else
            {
                if(p.getLd() == null)
                {
                    if(p == q.getLd())
                    {
                        q.setFb(q.getFb() + 1);
                        this.eliminarNormal(p, q);
                        if(q.getFb().equals(1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                    else
                    {
                        if(p == q.getLi())
                        {
                            q.setFb(q.getFb() - 1);
                            this.eliminarNormal(p, q);
                            if(q.getFb().equals(-1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                    }
                }
                else
                {
                    if(p.getLi() == null)
                    {
                        if(p == q.getLd())
                        {
                            q.setFb(q.getFb() + 1);
                            this.eliminarNormal(p, q);
                            if(q.getFb().equals(1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                        else
                        {
                            if(p == q.getLi())
                            {
                                q.setFb(q.getFb() - 1);
                                this.eliminarNormal(p, q);
                                if(q.getFb().equals(-1))
                                {
                                    return;
                                }
                                this.rebalancearArbol(q);
                            }
                        }
                    }
                    else
                    {
                        this.menor(p);
                    }
                }
            }
        }
        else if(orden.equals(PREORDEN))
        {
            if(!p.isBd() & !p.isBi())
            {
                if(q == R)
                {
                    R.setLi(R);
                    R.setBi(false);
                    return;
                }
                if(p == q.getLd() & q.isBd())
                {
                    q.setFb(q.getFb() + 1);
                    this.eliminarPre(p, q);
                    if(q.getFb().equals(1))
                    {
                        return;
                    }
                    this.rebalancearArbol(q);
                }
                else
                {
                    if(p == q.getLi() & q.isBi())
                    {
                        q.setFb(q.getFb() - 1);
                        this.eliminarPre(p, q);
                        if(q.getFb().equals(-1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                }
            }
            else
            {
                if(!p.isBd())
                {
                    if(p == q.getLd() & q.isBd())
                    {
                        q.setFb(q.getFb() + 1);
                        this.eliminarPre(p, q);
                        if(q.getFb().equals(1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                    else
                    {
                        if(p == q.getLi() & q.isBi())
                        {
                            q.setFb(q.getFb() - 1);
                            this.eliminarPre(p, q);
                            if(q.getFb().equals(-1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                    }
                }
                else
                {
                    if(!p.isBi())
                    {
                        if(p == q.getLd() & q.isBd())
                        {
                            q.setFb(q.getFb() + 1);
                            this.eliminarPre(p, q);
                            if(q.getFb().equals(1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                        else
                        {
                            if(p == q.getLi() & q.isBi())
                            {
                                q.setFb(q.getFb() - 1);
                                this.eliminarPre(p, q);
                                if(q.getFb().equals(-1))
                                {
                                    return;
                                }
                                this.rebalancearArbol(q);
                            }
                        }
                    }
                    else
                    {
                       this.menor(p);
                    }
                }
            }
        }
        else if(orden.equals(POSORDEN))
        {
            if(!p.isBd() & !p.isBi())
            {
                if(q == R)
                {
                    R.setLi(R);
                    R.setBi(false);
                    return;
                }
                if(p == q.getLd() & q.isBd())
                {
                    q.setFb(q.getFb() + 1);
                    this.eliminarPos(p, q);
                    if(q.getFb().equals(1))
                    {
                        return;
                    }
                    this.rebalancearArbol(q);
                }
                else
                {
                    if(p == q.getLi() & q.isBi())
                    {
                        q.setFb(q.getFb() - 1);
                        this.eliminarPos(p, q);
                        if(q.getFb().equals(-1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                }
            }
            else
            {
                if(!p.isBd())
                {
                    if(p == q.getLd() & q.isBd())
                    {
                        q.setFb(q.getFb() + 1);
                        this.eliminarPos(p, q);
                        if(q.getFb().equals(1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                    else
                    {
                        if(p == q.getLi() & q.isBi())
                        {
                            q.setFb(q.getFb() - 1);
                            this.eliminarPos(p, q);
                            if(q.getFb().equals(-1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                    }
                }
                else
                {
                    if(!p.isBi())
                    {
                        if(p == q.getLd() & q.isBd())
                        {
                            q.setFb(q.getFb() + 1);
                            this.eliminarPos(p, q);
                            if(q.getFb().equals(1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                        else
                        {
                            if(p == q.getLi() & q.isBi())
                            {
                                q.setFb(q.getFb() - 1);
                                this.eliminarPos(p, q);
                                if(q.getFb().equals(-1))
                                {
                                    return;
                                }
                                this.rebalancearArbol(q);
                            }
                        }
                    }
                    else
                    {
                        this.menor(p);
                    }
                }
            }
        }
        else if(orden.equals(INORDEN))
        {
            if(!p.isBd() & !p.isBi())
            {
                if(q == R)
                {
                    R.setLi(R);
                    R.setBi(false);
                    return;
                }
                if(p == q.getLd() & q.isBd())
                {
                    q.setFb(q.getFb() + 1);
                    this.eliminarIn(p, q);
                    if(q.getFb().equals(1))
                    {
                        return;
                    }
                    this.rebalancearArbol(q);
                }
                else
                {
                    if(p == q.getLi() & q.isBi())
                    {
                        q.setFb(q.getFb() - 1);
                        this.eliminarIn(p, q);
                        if(q.getFb().equals(-1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                }
            }
            else
            {
                if(!p.isBd())
                {
                    if(p == q.getLd() & q.isBd())
                    {
                        q.setFb(q.getFb() + 1);
                        this.eliminarIn(p, q);
                        if(q.getFb().equals(1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                    else
                    {
                        if(p == q.getLi() & q.isBi())
                        {
                            q.setFb(q.getFb() - 1);
                            this.eliminarIn(p, q);
                            if(q.getFb().equals(-1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                    }
                }
                else
                {
                    if(!p.isBi())
                    {
                        if(p == q.getLd() & q.isBd())
                        {
                            q.setFb(q.getFb() + 1);
                            this.eliminarIn(p, q);
                            if(q.getFb().equals(1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                        else
                        {
                            if(p == q.getLi() & q.isBi())
                            {
                                q.setFb(q.getFb() - 1);
                                this.eliminarIn(p, q);
                                if(q.getFb().equals(-1))
                                {
                                    return;
                                }
                                this.rebalancearArbol(q);
                            }
                        }
                    }
                    else
                    {
                        this.menor(p);
                    }
                }
            }
        }
    }
    /**
     * Método que permite eliminar un nodo del árbol considerando si es o no AVL y si tiene algun tipo de enhebrado.
     * @param ax el padre del nodo que se desea eliminar del arbol
     * @param x el nodo que se desea eliminar del árbol
     */
    public boolean borrarAVL(NodoDoble x, NodoDoble ax)
    {
        //el tamaño siminuye 1
        length = length - 1;
        if(this.isAVL())
        {
            //se elimina el nodo x considerando que es AVL
            this.eliminarNodo(x, ax);
        }
        else
        {
            if(orden.equals(NULL))
            {
                //se elimina el nodo x considerado que el árbol no esta siendo enehebrado y no es AVL
                this.eliminarNormal(x, ax);
            }
            if(orden.equals(INORDEN))
            {
                //se elimina el nodo x considerando que el árbol esta siendo enhebrado en INORDEN y no es AVL
                this.eliminarIn(x, ax);
            }
            if(orden.equals(POSORDEN))
            {
                //se elimna el nodo x considerando que el árbol esta siendo enhebrado en POSORDEN y no es AVL
                this.eliminarPos(x, ax);
            }
            if(orden.equals(PREORDEN))
            {
                //se elimina el nodo x considerando que el árbol esta siendo enhebrado en PREORDEN y no es AVL
                this.eliminarPre(x, ax);
            }
        }
        return true;
    }
    /**
     * Método que permite eliminar el nodo del árbol cuya campo key es igual al objeto ingresado como argumento.
     * @param d la clave del nodo que se desea eliminar del árbol
     * @return true si el nodo fue hallado y eliminado exitosamente, false en caso contrario
     */
    public boolean borrarAVL(K d)
    {
        if(this.isAVL() & this.isSearch())
        {
            NodoDoble p = this.getRaiz();
            NodoDoble q = R;
            {
                if(this.getRaiz() == null)
                {
                    return false;
                }
                while(p != null & p != R)
                {
                    //la clave debe ser una instancia de la interfaz Comparable
                    if(d instanceof Comparable & p.getKey() instanceof Comparable & p.getKey().getClass() == d.getClass())
                    {
                        Comparable w = (Comparable)d;
                        Comparable z = (Comparable)p.getKey();
                        //si la comparacion es menor que cero se avanza por la izquierda, mayor por la derecha, igual a cero se hallo el nodo deseado
                        if(w.compareTo(z) < 0)
                        {
                            if(!p.isBi())
                            {
                                return false;
                            }
                            q = p;
                            p = p.getLi();
                        }
                        else
                        {
                            if(w.compareTo(z) > 0)
                            {
                                if(!p.isBd())
                                {
                                    return false;
                                }
                                else
                                {
                                    q = p;
                                    p = p.getLd();
                                }
                            }
                            else
                            {
                                //se reduce en 1 la longitud del árbol
                                length = length - 1;
                                //se elimina el nodo
                                this.eliminarNodo(p, q);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        else
        {
            return this.borrar(d);
        }
        return false;
    }
    /**
     * Método utilizado para rotar el árbol una vez ha sido desbalanceado luego de la elimnación de un nodo.
     * Recibe como parametro el nodo a partir del cual se debe realizar el balanceo y realiza las rotaciones segun los
     * factores de balance que se vayan estableciendo, este recorrido se hace recorriendo todos los nodos hasta llegar a
     * la raiz del árbol.
     * @param z El nodo a partir del cual se debe realizar la rotaciones en el árbol
     */
    public void rotarMenosAltura(NodoDoble z)
    {
        NodoDoble x = z;
        NodoDoble px = null;
        NodoDoble aux = null;
        //según el tipo de enhebrado que tenga el árbol se llevara a cabo una serie de rotaciones
        if(orden.equals(NULL))
        {
            //se recorre el árbol hasta llegar a la raiz
            while(x != R)
            {
                //padre del nodo actual
                px = this.padre(x);
                aux = null;
                if(x.getFb().equals(2))
                {
                    if(x.getLi().getFb().equals(1))
                    {
                        //se realiza una rotación a la derecha
                        aux = this.unaRotDer(x, x.getLi());
                        //se establece el factor de balance del padre de x, teniendo en cuenta que la
                        //altura de uno de sus subarboles fue modificada y se modifica uno de sus hijos
                        if(px.getLd() == x)
                        {
                            px.setFb(px.getFb() + 1);
                            px.setLd(aux);
                        }
                        else
                        {
                            px.setFb(px.getFb() - 1);
                            px.setLi(aux);
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(-1))
                        {
                            //se realiza una doble rotación a la derecha
                            aux = this.dobleRotDer(x, x.getLi());
                            if(x == px.getLd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLi().getFb().equals(0))
                            {
                                //se realiza una rotacón especial a la derecha, la cual no modifica la altura del árbol
                                aux = this.unaRotDerEs(x, x.getLi());
                                //se modifica uno de los hijos de px
                                if(x == px.getLd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                                //el árbol ya esta balanceado retorne
                                return;
                            }
                        }
                    }
                }
                else
                {
                    if(x.getFb().equals(-2))
                    {
                        if(x.getLd().getFb().equals(-1))
                        {
                            //una rotacion a la izquierda
                            aux = unaRotIzq(x, x.getLd());
                            if(px.getLd() == x)
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(1))
                            {
                                //doble rotación a la izquierda
                                aux = this.dobleRotIzq(x, x.getLd());
                                if(x == px.getLd())
                                {
                                    px.setFb(px.getFb() + 1);
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setFb(px.getFb() - 1);
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                if(x.getLd().getFb().equals(0))
                                {
                                    //rotacion especial a la izquierda que no modifica la altura del árbol
                                    aux = this.unaRotIzqEs(x, x.getLd());
                                    if(x == px.getLd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(px != null)
                        {
                            if(x == px.getLd() & x.getFb().equals(0))
                            {
                                px.setFb(px.getFb() + 1);
                            }
                            else
                            {
                                if(x.getFb().equals(0))
                                {
                                    px.setFb(px.getFb() - 1);
                                }
                                else
                                {
                                    //si el factor de balance de x es 1 o -1 el árbol esta balanceado
                                    return;
                                }
                            }
                        }
                    }
                }
                //se avanza hacia el padre de x
                x = px;
            }
        }
        if(orden.equals(POSORDEN))
        {
            while(x != R)
            {
                px = padre(x);
                aux = null;
                if(x.getFb().equals(2))
                {
                    if(x.getLi().getFb().equals(1))
                    {
                        aux = this.unaRotDerPos(x, x.getLi());
                        if(px.getLd() == x & px.isBd())
                        {
                            px.setFb(px.getFb() + 1);
                            px.setLd(aux);
                        }
                        else
                        {
                            px.setFb(px.getFb() - 1);
                            px.setLi(aux);
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(-1))
                        {
                            aux = this.dobleRotDerPos(x, x.getLi());
                            if(x == px.getLd() & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLi().getFb().equals(0))
                            {
                                aux = this.unaRotDerEsPos(x, x.getLi());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                                return;
                            }
                        }
                    }
                }
                else
                {
                    if(x.getFb().equals(-2))
                    {
                        if(x.getLd().getFb().equals(-1))
                        {
                            aux = unaRotIzqPos(x, x.getLd());
                            if(px.getLd() == x & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(1))
                            {
                                aux = this.dobleRotIzqPos(x, x.getLd());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setFb(px.getFb() + 1);
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setFb(px.getFb() - 1);
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                if(x.getLd().getFb().equals(0))
                                {
                                    aux = this.unaRotIzqEsPos(x, x.getLd());
                                    if(x == px.getLd() & px.isBd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(px != null)
                        {
                            if(x == px.getLd() & x.getFb().equals(0))
                            {
                                px.setFb(px.getFb() + 1);
                            }
                            else
                            {
                                if(x.getFb().equals(0))
                                {
                                    px.setFb(px.getFb() - 1);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                x = px;
            }
        }
        if(orden.equals(PREORDEN))
        {
            while(x != R)
            {
                px = padre(x);
                aux = null;
                if(x.getFb().equals(2))
                {
                    if(x.getLi().getFb().equals(1))
                    {
                        aux = this.unaRotDerPre(x, x.getLi());
                        if(px.getLd() == x & px.isBd())
                        {
                            px.setFb(px.getFb() + 1);
                            px.setLd(aux);
                        }
                        else
                        {
                            px.setFb(px.getFb() - 1);
                            px.setLi(aux);
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(-1))
                        {
                            aux = this.dobleRotDerPre(x, x.getLi());
                            if(x == px.getLd() & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLi().getFb().equals(0))
                            {
                                aux = this.unaRotDerEsPre(x, x.getLi());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                                return;
                            }
                        }
                    }
                }
                else
                {
                    if(x.getFb().equals(-2))
                    {
                        if(x.getLd().getFb().equals(-1))
                        {
                            aux = unaRotIzqPre(x, x.getLd());
                            if(px.getLd() == x & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(1))
                            {
                                aux = this.dobleRotIzqPre(x, x.getLd());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setFb(px.getFb() + 1);
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setFb(px.getFb() - 1);
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                if(x.getLd().getFb().equals(0))
                                {
                                    aux = this.unaRotIzqEsPre(x, x.getLd());
                                    if(x == px.getLd() & px.isBd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(px != null)
                        {
                            if(x == px.getLd() & x.getFb().equals(0))
                            {
                                px.setFb(px.getFb() + 1);
                            }
                            else
                            {
                                if(x.getFb().equals(0))
                                {
                                    px.setFb(px.getFb() - 1);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                x = px;
            }
        }
        if(orden.equals(INORDEN))
        {
            while(x != R)
            {
                px = padre(x);
                aux = null;
                if(x.getFb().equals(2))
                {
                    if(x.getLi().getFb().equals(1))
                    {
                        aux = this.unaRotDerIn(x, x.getLi());
                        if(px.getLd() == x & px.isBd())
                        {
                            px.setFb(px.getFb() + 1);
                            px.setLd(aux);
                        }
                        else
                        {
                            px.setFb(px.getFb() - 1);
                            px.setLi(aux);
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(-1))
                        {
                            aux = this.dobleRotDerIn(x, x.getLi());
                            if(x == px.getLd() & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLi().getFb().equals(0))
                            {
                                aux = this.unaRotDerEsIn(x, x.getLi());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                                return;
                            }
                        }
                    }
                }
                else
                {
                    if(x.getFb().equals(-2))
                    {
                        if(x.getLd().getFb().equals(-1))
                        {
                            aux = unaRotIzqIn(x, x.getLd());
                            if(px.getLd() == x & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(1))
                            {
                                aux = this.dobleRotIzqIn(x, x.getLd());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setFb(px.getFb() + 1);
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setFb(px.getFb() - 1);
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                if(x.getLd().getFb().equals(0))
                                {
                                    aux = this.unaRotIzqEsIn(x, x.getLd());
                                    if(x == px.getLd() & px.isBd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(px != null)
                        {
                            if(x == px.getLd() & x.getFb().equals(0))
                            {
                                px.setFb(px.getFb() + 1);
                            }
                            else
                            {
                                if(x.getFb().equals(0))
                                {
                                    px.setFb(px.getFb() - 1);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                x = px;
            }
        }
    }
    /**
     * Método que efectua una rotación a la derecha en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL no esta enhebrado.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo izquierdo de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotDer(NodoDoble p, NodoDoble q)
    {
        p.setLi(q.getLd());
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    /**
     * Método que efectua una rotación a la izquierda en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL no esta enhebrado.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser -1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotIzq(NodoDoble p, NodoDoble q)
    {
        p.setLd(q.getLi());
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    /**
     * Método que efectua una doble rotación a la derecha en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL no esta enhebrado.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo izquierdo de p y cuyo factor de balance debe ser -1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al hijo derecho de q
     */
    public NodoDoble dobleRotDer(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLd();
        q.setLd(r.getLi());
        r.setLi(q);
        p.setLi(r.getLd());
        r.setLd(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(-1);
                q.setFb(0);
            }
            else
            {
                p.setFb(0);
                q.setFb(1);
            }
        }
        r.setFb(0);
        return r;
    }
    /**
     * Método que efectua una doble rotación a la izquierda en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL no esta enhebrado.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al hijo izquierdo de q
     */
    public NodoDoble dobleRotIzq(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLi();
        q.setLi(r.getLd());
        r.setLd(q);
        p.setLd(r.getLi());
        r.setLi(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(0);
                q.setFb(-1);
            }
            else
            {
                p.setFb(1);
                q.setFb(0);
            }
        }
        r.setFb(0);
        return r;
    }
    /**
     * Método que efectua una rotación a la derecha en el árbol AVL sin modificar la altura del subarbol rotado.
     * Solo válido si el árbol AVL no esta enhebrado.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser 0
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotDerEs(NodoDoble p, NodoDoble q)
    {
        p.setLi(q.getLd());
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    /**
     * Método que efectua una rotación a la izquierda en el árbol AVL sin modificar la altura del subarbol rotado.
     * Solo válido si el árbol AVL no esta enhebrado.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser 0
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotIzqEs(NodoDoble p, NodoDoble q)
    {
        p.setLd(q.getLi());
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    /**
     * Método que efectua una rotación a la derecha en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en PREORDEN.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo izquierdo de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotDerPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble ap = this.anteriorPre(p);
        if(!ap.isBd())
        {
            ap.setLd(q);
        }
        if(q.isBd())
        {
            NodoDoble aux = this.anteriorPre(q.getLd());
            p.setBi(true);
            p.setLi(q.getLd());
            if(!aux.isBd())
            {
                aux.setLd(p);
            }
            if(!q.getLd().isBi())
            {
                q.getLd().setLi(p);
            }
        }
        else
        {
            p.setBi(false);
            p.setLi(q.getLi());
            if(!p.isBd())
            {
                p.setLd(q.getLi().getLd());
            }
            if(!q.getLi().getLd().isBi())
            {
                q.getLi().getLd().setLi(p);
            }
            q.getLi().setLd(p);
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    /**
     * Método que efectua una rotación a la izquierda en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en PREORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser -1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotIzqPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble ap = this.anteriorPre(p);
        NodoDoble aq = this.anteriorPre(q);
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
            if(p.isBi())
            {
                if(!q.getLi().isBi())
                {
                    q.getLi().setLi(aq);
                }
            }
            if(!aq.isBd())
            {
                aq.setLd(q.getLi());
            }
        }
        else
        {
           p.setBd(false);
           p.setLd(q.getLd());
           if(!q.getLd().isBi())
           {
               q.getLd().setLi(p);
           }
        }
        if(!p.isBi())
        {
            p.setLi(q);
        }
        if(!ap.isBd())
        {
            ap.setLd(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    /**
     * Método que efectua una doble rotación a la derecha en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en PREORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al hijo izquierdo de q
     */
    public NodoDoble dobleRotDerPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLd();
        NodoDoble ap = anteriorPre(p);
        NodoDoble arld = null;
        if(r.isBd())
        {
          arld = anteriorPre(r.getLd());
        }
        NodoDoble ar = anteriorPre(r);
        NodoDoble x = null;
        if(r.isBi())
        {
            if(q.isBi())
            {
                if(!ar.isBd())
                {
                    ar.setLd(r.getLi());
                }
            }
            if(!r.getLi().isBi())
            {
                r.getLi().setLi(ar);
            }
            q.setBd(true);
            q.setLd(r.getLi());
        }
        else
        {
            if(!ar.isBd())
            {
                ar.setLd(p);
            }
            q.setBd(false);
            if(!q.isBi())
            {
                q.setLd(p);
                q.setLi(r);
            }
            else
            {
                q.setLd(q.getLi());
                q.getLi().setLi(q);
                q.setLd(q.getLi());
            }
        }
        if(r.isBd())
        {
            if(!arld.isBd())
            {
                arld.setLd(p);
            }
            if(!r.getLd().isBi())
            {
                r.getLd().setLi(p);
            }
            p.setBi(true);
            p.setLi(r.getLd());
        }
        else
        {
            if(!p.isBd())
            {
                p.setLd(r.getLd());
                if(!r.getLd().isBi())
                {
                    r.getLd().setLi(p);
                }
            }
            if(r.isBi())
            {
                if(p.isBd())
                {
                    x = this.anteriorPre(p.getLd());
                    p.setBi(false);
                    p.setLi(x);
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(p);
                    }
                    x.setLd(p);
                    if(!ar.isBd())
                    {
                        ar.setLd(x);
                    }
                    x.setLi(ar);
                }
            }
            else
            {
                p.setBi(false);
                p.setLi(q);
            }
        }
        if(!ap.isBd())
        {
            ap.setLd(r);
        }
        r.setBi(true);
        r.setLi(q);
        r.setBd(true);
        r.setLd(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(-1);
                q.setFb(0);
            }
            else
            {
                p.setFb(0);
                q.setFb(1);
            }
        }
        r.setFb(0);
        return r;
    }
    /**
     * Método que efectua una doble rotación a la izquierda en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en PREORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al hijo izquierdo de q
     */
    public NodoDoble dobleRotIzqPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLi();
        NodoDoble arld = null;
        if(r.isBd())
        {
            arld = this.anteriorPre(r.getLd());
        }
        NodoDoble aq = this.anteriorPre(q);
        NodoDoble ap = this.anteriorPre(p);
        NodoDoble x = null;
        if(r.isBd())
        {
            if(!arld.isBd())
            {
                arld.setLd(q);
            }
            q.setBi(true);
            q.setLi(r.getLd());
            if(!r.getLd().isBi())
            {
                r.getLd().setLi(q);
            }
            if(!aq.isBd())
            {
                aq.setLd(r.getLi());
            }
            if(!r.getLi().isBi())
            {
                r.getLi().setLi(aq);
            }
        }
        else
        {
            q.setBi(false);
            if(r.isBi())
            {
                x = r.getLi();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBd())
                    {
                        x = x.getLd();
                    }
                    else
                    {
                        x = x.getLi();
                    }
                }
                q.setLi(x);
                x.setLd(q);
            }
            else
            {
                q.setLi(p);
            }
            if(!q.isBd())
            {
                q.setLd(r.getLd());
            }
            else
            {
                if(!q.getLd().isBi())
                {
                    q.getLd().setLi(q);
                }
            }
            if(!r.getLd().isBi())
            {
                if(aq != p)
                {
                    r.getLd().setLi(aq);
                }
                else
                {
                    r.getLd().setLi(q);
                }
            }
        }
        if(r.isBi())
        {
            p.setBd(true);
            p.setLd(r.getLi());
            if(!r.getLi().isBi())
            {
                r.getLi().setLi(aq);
            }
        }
        else
        {
            if(!p.isBi())
            {
                if(!p.getLi().isBd())
                {
                    p.getLi().setLd(r);
                }
                p.setLi(r);
                p.setBd(false);
                p.setLd(q);
            }
            else
            {
                p.setBd(false);
                p.setLd(p.getLi());
            }
        }
        if(!ap.isBd())
        {
            ap.setLd(r);
        }
        if(!aq.isBd())
        {
            if(r.isBi())
            {
                aq.setLd(r.getLi());
            }
            else
            {
                aq.setLd(q);
            }
        }
        r.setBd(true);
        r.setLd(q);
        r.setBi(true);
        r.setLi(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(0);
                q.setFb(-1);
            }
            else
            {
                p.setFb(1);
                q.setFb(0);
            }
        }
        r.setFb(0);
        return r;
    }
    /**
     * Método que efectua una rotación a la derecha en el árbol AVL sin modificar la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en PREORDEN.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser 0
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotDerEsPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble ap = anteriorPre(p);
        NodoDoble x = null;
        if(!ap.isBd())
        {
            ap.setLd(q);
        }
        if(q.isBd())
        {
            NodoDoble aux = this.anteriorPre(q.getLd());
            if(!p.isBd())
            {
                p.setLd(q.getLd());
            }
            p.setBi(true);
            p.setLi(q.getLd());
            if(!aux.isBd())
            {
                aux.setLd(p);
            }
            if(!q.getLd().isBi())
            {
                q.getLd().setLi(p);
            }
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    /**
     * Método que efectua una rotación a la izquierda en el árbol AVL sin modificar la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en PREORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser 0
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotIzqEsPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble ap = this.anteriorPre(p);
        NodoDoble aq = this.anteriorPre(q);
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
            if(p.isBi())
            {
                if(!q.getLi().isBi())
                {
                    q.getLi().setLi(aq);
                }
            }
            if(!aq.isBd())
            {
                aq.setLd(q.getLi());
            }
        }
        if(!p.isBi())
        {
            p.setLi(q);
        }
        if(!ap.isBd())
        {
            ap.setLd(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    /**
     * Método que efectua una rotación a la derecha en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en POSORDEN.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo izquierdo de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotDerPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble x = null;
        NodoDoble sp = this.siguientePos(p);
        if(!p.isBd())
        {
            if(!p.getLd().isBi())
            {
                p.getLd().setLi(q);
            }
            p.setLd(q);
        }
        if(q.isBd())
        {
            p.setBi(true);
            p.setLi(q.getLd());
            if(p.isBd())
            {
                x = p.getLd();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(q.getLd());
                if(!q.getLd().isBd())
                {
                    q.getLd().setLd(x);
                }
            }
            else
            {
                if(!q.getLd().isBd())
                {
                    q.getLd().setLd(p);
                }
            }
        }
        else
        {
            if(!q.getLi().isBd())
            {
                q.getLi().setLd(p);
            }
            p.setBi(false);
            p.setLi(q.getLi());
        }
        if(!sp.isBi())
        {
            sp.setLi(q);
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    /**
     * Método que efectua una rotación a la izquierda en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en POSORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser -1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotIzqPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble sp = siguientePos(p);
        NodoDoble x = null;
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
            if(!q.getLi().isBd())
            {
                if(!q.getLi().getLd().isBi())
                {
                    q.getLi().getLd().setLi(p);
                }
                else
                {
                    x = q.getLd();
                    while(x.isBi() | x.isBd())
                    {
                        if(x.isBi())
                        {
                            x = x.getLi();
                        }
                        else
                        {
                            x = x.getLd();
                        }
                    }
                    x.setLi(p);
                }
                q.getLi().setLd(p);
            }
            else
            {
                x = q.getLd();
                while(x.isBi() | x.isBd())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(p);
            }
        }
        else
        {
           p.setBd(false);
           p.setLd(q.getLd());
           if(!q.getLd().isBi())
           {
               if(!q.getLd().getLi().isBd())
               {
                   q.getLd().getLi().setLd(p);
               }
               if(!p.isBi())
               {
                   p.setLi(q.getLd().getLi());
               }
               q.getLd().setLi(p);
           }
        }
        if(!sp.isBi())
        {
            sp.setLi(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    /**
     * Método que efectua una doble rotación a la derecha en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en POSORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al hijo izquierdo de q
     */
    public NodoDoble dobleRotDerPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble x = null;
        NodoDoble r = q.getLd();
        NodoDoble sp = siguientePos(p);
        if(r.isBi())
        {
            q.setBd(true);
            q.setLd(r.getLi());
            if(!r.getLi().isBd())
            {
                if(!r.getLi().getLd().isBi())
                {
                    r.getLi().getLd().setLi(q);
                }
                r.getLi().setLd(q);
            }
        }
        else
        {
            if(!r.getLi().isBd())
            {
                r.getLi().setLd(q);
            }
            if(!r.isBd())
            {
                q.setBd(false);
                q.setLd(p);
            }
            else
            {
                x = r.getLd();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                q.setBd(false);
                q.setLd(x);
                if(!x.isBi())
                {
                    x.setLi(q);
                }
            }
            if(!q.isBi())
            {
                q.setLi(r.getLi());
            }
            else
            {
                if(!q.getLi().isBd())
                {
                    q.getLi().setLd(q);
                }
            }
        }
        if(r.isBd())
        {
            if(!r.getLd().isBd())
            {
                x = p.getLd();
                while(x.isBi())
                {
                    x = x.getLi();
                }
                r.getLd().setLd(x);
                if(!x.isBi())
                {
                    x.setLi(r.getLd());
                }
            }
            else
            {
                x = p.getLd();
                while(x.isBi() | x.isBd())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                if(!x.isBi())
                {
                    x.setLi(r.getLd());
                }
            }
            if(p.isBd())
            {
                if(!p.getLd().isBi())
                {
                    p.getLd().setLi(r.getLd());
                }
            }
            if(!r.getLd().isBi() & !r.getLd().isBd())
            {
                r.getLd().setLi(q);
            }
            else
            {
                x = r.getLd();
                while(x.isBi() | x.isBd())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(q);
            }
            if(!sp.isBi())
            {
                sp.setLi(r);
            }
            p.setBi(true);
            p.setLi(r.getLd());
        }
        else
        {
            if(!sp.isBi())
            {
                sp.setLi(r);
            }
            if(!p.isBd())
            {
                p.setLd(r);
                p.setBi(false);
                p.setLi(q);
            }
            else
            {
                p.setBi(false);
                p.setLi(p.getLd());
            }
        }
        r.setBi(true);
        r.setLi(q);
        r.setBd(true);
        r.setLd(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(-1);
                q.setFb(0);
            }
            else
            {
                p.setFb(0);
                q.setFb(1);
            }
        }
        r.setFb(0);
        return r;
    }
    /**
     * Método que efectua una doble rotación a la izquierda en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en POSORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al hijo izquierdo de q
     */
    public NodoDoble dobleRotIzqPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble x = null;
        NodoDoble r = q.getLi();
        NodoDoble sp = this.siguientePos(p);
        NodoDoble sr = this.siguientePos(r);
        if(r.isBd())
        {
            if(!sr.isBi())
            {
                sr.setLi(r.getLd());
            }
            q.setBi(true);
            q.setLi(r.getLd());
            x = r.getLd();
            while(x.isBd() | x.isBi())
            {
                if(x.isBi())
                {
                    x = x.getLi();
                }
                else
                {
                    x = x.getLd();
                }
            }
            x.setLi(p);
            if(!r.getLd().isBd())
            {
                if(q.isBd())
                {
                    x = q.getLd();
                    while(x.isBi() | x.isBd())
                    {
                        if(x.isBi())
                        {
                            x = x.getLi();
                        }
                        else
                        {
                            x = x.getLd();
                        }
                    }
                    r.getLd().setLd(x);
                    if(!x.isBi())
                    {
                        x.setLi(r.getLd());
                    }
                }
                else
                {
                    r.getLd().setLd(q);
                }
            }
        }
        else
        {
            q.setBi(false);
            q.setLi(p);
            if(!q.isBd())
            {
                q.setLd(r);
            }
        }
        if(r.isBi())
        {
            p.setBd(true);
            p.setLd(r.getLi());
            if(!r.getLi().isBd())
            {
                r.getLi().setLd(p);
            }
        }
        else
        {
            p.setBd(false);
            if(!r.isBd())
            {
                p.setLd(q);
                if(!r.getLd().isBi())
                {
                    r.getLd().setLi(p);
                }
            }
            else
            {
                x = r.getLd();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(p);
                p.setLd(x);
            }
            if(!p.isBi())
            {
                p.setLi(r.getLi());
                if(!r.getLi().isBd())
                {
                    r.getLi().setLd(p);
                }
            }
            else
            {
                if(!p.getLi().isBd())
                {
                    p.getLi().setLd(p);
                }
            }
        }
        if(!sp.isBi())
        {
            sp.setLi(r);
        }
        r.setBd(true);
        r.setLd(q);
        r.setBi(true);
        r.setLi(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(0);
                q.setFb(-1);
            }
            else
            {
                p.setFb(1);
                q.setFb(0);
            }
        }
        r.setFb(0);
        return r;
    }
    /**
     * Método que efectua una rotación a la derecha en el árbol AVL sin modificar la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en POSORDEN.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser 0
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotDerEsPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble x = null;
        NodoDoble sp = this.siguientePos(p);
        if(!p.isBd())
        {
            if(!p.getLd().isBi())
            {
                p.getLd().setLi(q);
            }
            p.setLd(q);
        }
        if(q.isBd())
        {
            p.setBi(true);
            p.setLi(q.getLd());
            if(p.isBd())
            {
                x = p.getLd();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(q.getLd());
                if(!q.getLd().isBd())
                {
                    q.getLd().setLd(x);
                }
            }
            else
            {
                if(!q.getLd().isBd())
                {
                    q.getLd().setLd(p);
                }
            }
        }
        if(!sp.isBi())
        {
            sp.setLi(q);
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    /**
     * Método que efectua una rotación a la izquierda en el árbol AVL sin modificar la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en POSORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser 0
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotIzqEsPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble sp = this.siguientePos(p);
        NodoDoble x = null;
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
            if(!q.getLi().isBd())
            {
                if(!q.getLi().getLd().isBi())
                {
                    q.getLi().getLd().setLi(p);
                }
                else
                {
                    x = q.getLd();
                    while(x.isBi() | x.isBd())
                    {
                        if(x.isBi())
                        {
                            x = x.getLi();
                        }
                        else
                        {
                            x = x.getLd();
                        }
                    }
                    x.setLi(p);

                }
                q.getLi().setLd(p);
                if(!p.isBi())
                {
                    p.setLi(q.getLi());
                }
            }
            else
            {
                x = q.getLd();
                while(x.isBi() | x.isBd())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(p);
            }
        }
        if(!sp.isBi())
        {
            sp.setLi(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    /**
     * Método que efectua una rotación a la derecha en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en INORDEN.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo izquierdo de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotDerIn(NodoDoble p, NodoDoble q)
    {
        if(q.isBd())
        {
            p.setBi(true);
            p.setLi(q.getLd());
        }
        else
        {
            p.setBi(false);
            p.setLi(q);
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    /**
     * Método que efectua una rotación a la izquierda en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en INORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser -1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotIzqIn(NodoDoble p, NodoDoble q)
    {
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
        }
        else
        {
           p.setBd(false);
           p.setLd(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    /**
     * Método que efectua una doble rotación a la derecha en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en INORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al hijo izquierdo de q
     */
    public NodoDoble dobleRotDerIn(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLd();
        if(r.isBi())
        {
            q.setBd(true);
            q.setLd(r.getLi());
        }
        else
        {
            q.setBd(false);
            q.setLd(r);
        }
        r.setBi(true);
        r.setLi(q);
        if(r.isBd())
        {
            p.setBi(true);
            p.setLi(r.getLd());
        }
        else
        {
            p.setBi(false);
            p.setLi(r);
        }
        r.setBd(true);
        r.setLd(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(-1);
                q.setFb(0);
            }
            else
            {
                p.setFb(0);
                q.setFb(1);
            }
        }
        r.setFb(0);
        return r;
    }
    /**
     * Método que efectua una doble rotación a la izquierda en el árbol AVL disminuyendo en 1 la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en INORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser +1
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al hijo izquierdo de q
     */
    public NodoDoble dobleRotIzqIn(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLi();
        if(r.isBd())
        {
            q.setBi(true);
            q.setLi(r.getLd());
        }
        else
        {
            q.setBi(false);
            q.setLi(r);
        }
        r.setBd(true);
        r.setLd(q);
        if(r.isBi())
        {
            p.setBd(true);
            p.setLd(r.getLi());
        }
        else
        {
            p.setBd(false);
            p.setLd(r);
        }
        r.setBi(true);
        r.setLi(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(0);
                q.setFb(-1);
            }
            else
            {
                p.setFb(1);
                q.setFb(0);
            }
        }
        r.setFb(0);
        return r;
    }
    /**
     * Método que efectua una rotación a la derecha en el árbol AVL sin modificar la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en INORDEN.
     * @param p Nodo cuyo factor de balance debe ser +2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser 0
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotDerEsIn(NodoDoble p, NodoDoble q)
    {
        if(q.isBd())
        {
            p.setBi(true);
            p.setLi(q.getLd());
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    /**
     * Método que efectua una rotación a la izquierda en el árbol AVL sin modificar la altura del subarbol rotado.
     * Solo válido si el árbol AVL esta enhebrado en INORDEN.
     * @param p Nodo cuyo factor de balance debe ser -2
     * @param q Nodo que corresponde al hijo derecho de p y cuyo factor de balance debe ser 0
     * @return la nueva raiz del subárbol rotado, en este caso corresponde al nodo q
     */
    public NodoDoble unaRotIzqEsIn(NodoDoble p, NodoDoble q)
    {
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    /**
     * Método utilizado para balancear el árbol AVL despues de la inserción de un nuevo nodo.
     * Este método solo es válido si el árbol no esta enhebrado.
     * @param x el nuevo nodo que será insertado en el árbol
     * @param q el nodo despues del cual se debe insertar el nuevo nodo en el árbol
     * @param pivote el nodo que queda desbalanceado despues de la insercion del nuevo nodo
     * @param pp el nodo padre del nodo que queda desbalanceado despues de la insercion
     */
    public void balancearNormal(NodoDoble x, NodoDoble q, NodoDoble pivote, NodoDoble pp)
    {
        //la clave del nuevo nodo debe ser una instancia de la interfaz Comparable
        if(x.getKey() instanceof Comparable)
        {
            Comparable w = null, z = null;
            //se obtiene la clave del nuevo nodo
            z = (Comparable)x.getKey();
            Integer aux = pivote.getFb();
            if(pivote.getKey() instanceof Comparable)
            {
                w = (Comparable)pivote.getKey();
                if(z.compareTo(w) < 0)
                {
                    pivote.setFb(aux + 1);
                    q = pivote.getLi();
                }
                else
                {
                    pivote.setFb(aux - 1);
                    q = pivote.getLd();
                }
            }
            NodoDoble p = q;
            //se modifican lo factores de balance de todos los nodos en la ruta desde el pivote
            //hasta el nuevo nodo
            while(p != x & p.getKey() instanceof Comparable)
            {
                w = (Comparable)p.getKey();
                if(z.compareTo(w) < 0)
                {
                    p.setFb(1);
                    p = p.getLi();
                }
                else
                {
                    p.setFb(-1);
                    p = p.getLd();
                }
            }
        }
        //si el factor de balance del pivote no es 2 o -2 no hay que realizar rotación alguna
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        //de acuerdo con los factores de balance se realizan las rotaciones apropiadas
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                q = this.unaRotDer(pivote, q);
            }
            else
            {
                q = this.dobleRotDer(pivote, q);
            }
        }
        else
        {
            if(q.getFb().equals(-1))
            {
                q = this.unaRotIzq(pivote, q);
            }
            else
            {
                q = this.dobleRotIzq(pivote, q);
            }
        }
        if(pivote == this.getRaiz())
        {
            this.setRaiz(q);
            return;
        }
        if(pp.getLi() == pivote)
        {
            pp.setLi(q);
        }
        else
        {
            pp.setLd(q);
        }
    }
    /**
     * Método utilizado para balancear el árbol AVL despues de la inserción de un nuevo nodo.
     * Este método solo es válido si el árbol esta enhebrado en PREORDEN.
     * @param x el nuevo nodo que será insertado en el árbol
     * @param q el nodo despues del cual se debe insertar el nuevo nodo en el árbol
     * @param pivote el nodo que queda desbalanceado despues de la insercion del nuevo nodo
     * @param pp el nodo padre del nodo que queda desbalanceado despues de la insercion
     */
    public void balancearPre(NodoDoble x, NodoDoble q, NodoDoble pivote, NodoDoble pp)
    {
        if(x.getKey() instanceof Comparable)
        {
            Comparable w = null, z = null;
            z = (Comparable)x.getKey();
            Integer aux = pivote.getFb();
            if(pivote.getKey() instanceof Comparable)
            {
                w = (Comparable)pivote.getKey();
                if(z.compareTo(w) < 0)
                {
                    pivote.setFb(aux + 1);
                    q = pivote.getLi();
                }
                else
                {
                    pivote.setFb(aux - 1);
                    q = pivote.getLd();
                }
            }
            NodoDoble p = q;
            while(p != x & p.getKey() instanceof Comparable)
            {
                w = (Comparable)p.getKey();
                if(z.compareTo(w) < 0)
                {
                    p.setFb(1);
                    p = p.getLi();
                }
                else
                {
                    p.setFb(-1);
                    p = p.getLd();
                }
            }
        }
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        Integer fp = pivote.getFb(), fq = q.getFb();
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                q = this.unaRotDerPre(pivote, q);
            }
            else
            {
                q = this.dobleRotDerPre(pivote, q);
            }
        }
        else
        {
            if(q.getFb().equals(-1))
            {
                q = this.unaRotIzqPre(pivote, q);
            }
            else
            {
                q = this.dobleRotIzqPre(pivote, q);
            }
        }
        if(pivote == this.getRaiz())
        {
            this.setRaiz(q);
            return;
        }
        if(pp.getLi() == pivote)
        {
            pp.setLi(q);
        }
        else
        {
            pp.setLd(q);
        }
    }
    /**
     * Método utilizado para balancear el árbol AVL despues de la inserción de un nuevo nodo.
     * Este método solo es válido si el árbol esta enhebrado en POSORDEN.
     * @param x el nuevo nodo que será insertado en el árbol
     * @param q el nodo despues del cual se debe insertar el nuevo nodo en el árbol
     * @param pivote el nodo que queda desbalanceado despues de la insercion del nuevo nodo
     * @param pp el nodo padre del nodo que queda desbalanceado despues de la insercion
     */
    public void balancearPos(NodoDoble x, NodoDoble q, NodoDoble pivote, NodoDoble pp)
    {
        if(x.getKey() instanceof Comparable)
        {
            Comparable w = null, z = null;
            z = (Comparable)x.getKey();
            Integer aux = pivote.getFb();
            if(pivote.getKey() instanceof Comparable)
            {
                w = (Comparable)pivote.getKey();
                if(z.compareTo(w) < 0)
                {
                    pivote.setFb(aux + 1);
                    q = pivote.getLi();
                }
                else
                {
                    pivote.setFb(aux - 1);
                    q = pivote.getLd();
                }
            }
            NodoDoble p = q;
            while(p != x & p.getKey() instanceof Comparable)
            {
                w = (Comparable)p.getKey();
                if(z.compareTo(w) < 0)
                {
                    p.setFb(1);
                    p = p.getLi();
                }
                else
                {
                    p.setFb(-1);
                    p = p.getLd();
                }
            }
        }
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                q = this.unaRotDerPos(pivote, q);
            }
            else
            {
                q = this.dobleRotDerPos(pivote, q);
            }
        }
        else
        {
            if(q.getFb().equals(-1))
            {
                q = this.unaRotIzqPos(pivote, q);
            }
            else
            {
                q = this.dobleRotIzqPos(pivote, q);
            }
        }
        if(pivote == this.getRaiz())
        {
            this.setRaiz(q);
            return;
        }
        if(pp.getLi() == pivote & pp.isBi())
        {
            pp.setLi(q);
        }
        else
        {
            pp.setLd(q);
        }
    }
    /**
     * Método utilizado para balancear el árbol AVL despues de la inserción de un nuevo nodo.
     * Este método solo es válido si el árbol esta enhebrado en INORDEN.
     * @param x el nuevo nodo que será insertado en el árbol
     * @param q el nodo despues del cual se debe insertar el nuevo nodo en el árbol
     * @param pivote el nodo que queda desbalanceado despues de la insercion del nuevo nodo
     * @param pp el nodo padre del nodo que queda desbalanceado despues de la insercion
     */
    public void balancearIn(NodoDoble x, NodoDoble q, NodoDoble pivote, NodoDoble pp)
    {
        if(x.getKey() instanceof Comparable)
        {
            Comparable w = null, z = null;
            z = (Comparable)x.getKey();
            Integer aux = pivote.getFb();
            if(pivote.getKey() instanceof Comparable)
            {
                w = (Comparable)pivote.getKey();
                if(z.compareTo(w) < 0)
                {
                    pivote.setFb(aux + 1);
                    q = pivote.getLi();
                }
                else
                {
                    pivote.setFb(aux - 1);
                    q = pivote.getLd();
                }
            }
            NodoDoble p = q;
            while(p != x & p.getKey() instanceof Comparable)
            {
                w = (Comparable)p.getKey();
                if(z.compareTo(w) < 0)
                {
                    p.setFb(1);
                    p = p.getLi();
                }
                else
                {
                    p.setFb(-1);
                    p = p.getLd();
                }
            }
        }
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                q = this.unaRotDerIn(pivote, q);
            }
            else
            {
                q = this.dobleRotDerIn(pivote, q);
            }
        }
        else
        {
            if(q.getFb().equals(-1))
            {
                q = this.unaRotIzqIn(pivote, q);
            }
            else
            {
                q = this.dobleRotIzqIn(pivote, q);
            }
        }
        if(pivote == this.getRaiz())
        {
            this.setRaiz(q);
            return;
        }
        if(pp.getLi() == pivote)
        {
            pp.setLi(q);
        }
        else
        {
            pp.setLd(q);
        }
    }
    /**
     * Método que permite insertar un nuevo elemento en el árbol. Este método utiliza los metodos necesarios para
     * insertar un nuevo elemento en el árbol teniendo en cuenta el tipo de enhebrado del árbol y el hecho de si este
     * es o no un árbol AVL, pero siempre considerandolo como un árbol binario de búsqueda.
     * @param el valor del nuevo nodo que contiene el elemento y la clave del elemento que se desea insertar en el árbol
     * @return true si el nuevo nodo no existe en el arbol y pudo ser insertado exitosamente, false en caso contrario
     */
    public boolean insertarAVL(NodoDoble x)
    {
        if(this.isAVL() & this.isSearch())
        {
            NodoDoble p, q, pivote, pp;
            if(!R.isBi())
            {
                //se aumenta en 1 el tamaño del arbol binario
                length = length + 1;
                if(orden.equals(NULL))
                {
                    this.conectarNormal(R, x, true);
                }
                if(orden.equals(INORDEN))
                {
                    this.conectarIn(R, x, true);
                }
                if(orden.equals(POSORDEN))
                {
                    this.conectarPos(R, x, true);
                }
                if(orden.equals(PREORDEN))
                {
                    this.conectarPre(R, x, true);
                }
                return true;
            }
            p = this.getRaiz();
            q = null;
            pivote = this.getRaiz();
            pp = null;
            while(p != null & p != R)
            {
                if(p.getKey() instanceof Comparable & x.getKey() instanceof Comparable & p.getKey().getClass() == x.getKey().getClass())
                {
                    Comparable z = null, w = null;
                    z = (Comparable)x.getKey();
                    w = (Comparable)p.getKey();
                    //si el dato ya existe se retorna si modificar el árbol
                    if(z.compareTo(w) == 0)
                    {
                        return false;
                    }
                    //El pivote es el último nodo en la ruta hacia el punto de insercion cuyo factor de balance no es 0
                    if(!p.getFb().equals(0))
                    {
                        pivote = p;
                        pp = q;
                    }
                    q = p;
                    if(z.compareTo(w) < 0)
                    {
                        //el nuevo nodo se inserta a la izquierda del nodo actual
                        if(!p.isBi())
                        {
                            //incrementa el tamaño del arbol
                            length = length + 1;
                            if(orden.equals(NULL))
                            {
                                //conecta el nuevo nodo a la izquierda del actual considerando que el árbol no tiene hebras
                                this.conectarNormal(q, x, true);
                                //balancea el árbol considerando que no esta enhebrado
                                this.balancearNormal(x, q, pivote, pp);
                            }
                            else if(orden.equals(INORDEN))
                            {
                                //conecta el nuevo nodo  a la izquierda del actual considerando que esta enhebrado en INORDEN
                                this.conectarIn(q, x, true);
                                //balancea el árbol considerando que esta enhebrado en INORDEN
                                this.balancearIn(x, q, pivote, pp);
                            }
                            else if(orden.equals(PREORDEN))
                            {
                                //conecta el nuevo nodo a la izquierda del actual considerando que esta enhebrado en PREORDEN
                                this.conectarPre(q, x, true);
                                //balancea el árbol considerando que esta enhebrado en PREORDEN
                                this.balancearPre(x, q, pivote, pp);
                            }
                            else if(orden.equals(POSORDEN))
                            {
                                //conecta el nuevo nodo a la izquierda del actual considerando que esta enhebrado en POSORDEN
                                this.conectarPos(q, x, true);
                                //balancea el árbol considerando que esta enhebrado en POSORDEN
                                this.balancearPos(x, q, pivote, pp);
                            }
                            return true;
                        }
                        //avanza por la izquierda
                        p = p.getLi();
                    }
                    else
                    {
                        //el nuevo nodo se inserta a la derecha del nodo actual
                        if(!p.isBd())
                        {
                            //se incrementa en 1 el tamaño del árbol
                            length = length + 1;
                            if(orden.equals(NULL))
                            {
                                //conecta el nuevo nodo a la derecha del actual considerando que el árbol no tiene hebras
                                this.conectarNormal(q, x, false);
                                //balancea el árbol considerando que no esta enhebrado
                                this.balancearNormal(x, q, pivote, pp);
                            }
                            else if(orden.equals(INORDEN))
                            {
                                //conecta el nuevo nodo  a la derecha del actual considerando que esta enhebrado en INORDEN
                                this.conectarIn(q, x, false);
                                //balancea el árbol considerando que esta enhebrado en INORDEN
                                this.balancearIn(x, q, pivote, pp);
                            }
                            else if(orden.equals(PREORDEN))
                            {
                                //conecta el nuevo nodo a la derecha del actual considerando que esta enhebrado en PREORDEN
                                this.conectarPre(q, x, false);
                                //balancea el árbol considerando que esta enhebrado en PREORDEN
                                this.balancearPre(x, q, pivote, pp);
                            }
                            else if(orden.equals(POSORDEN))
                            {
                                //conecta el nuevo nodo a la izquierda del actual considerando que esta enhebrado en POSORDEN
                                this.conectarPos(q, x, false);
                                //balancea el árbol considerando que esta enhebrado en POSORDEN
                                this.balancearPos(x, q, pivote, pp);
                            }
                            return true;
                        }
                        p = p.getLd();
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            //se elimina el elemento considerando que el árbol no es AVL
            return this.insertar(x);
        }
        return false;
    }
    /**
     * Metodo que permite insertar un nuevo elemento elemento en el árbol que tiene el valor ingresado como argumento
     * @param d el valor del nuevo nodo que se desea insertar en el árbol
     */
    public boolean insertarAVL(V d)
    {
        NodoDoble<K, V> x = new NodoDoble<K, V>(d);
        return this.insertarAVL(x);
    }
    /**
     * Método que permite insertar en el árbol un nuevo nodo con el valor y la clave ingresadas como argumento.
     * @param key la clave del nuevo nodo que se desea insertar en el árbol
     * @param value el valor del nuevo elemento que se desea insertar en el árbol
     * @retun true si el nuevo nodo pudo ser insertado exitosamente en el árbol, false en caso contrario
     */
    public boolean insertarAVL(K key, V value)
    {
        NodoDoble<K, V> x = new NodoDoble<K, V>(key, value);
        return this.insertarAVL(x);
    }
    /**
     * Método utilizado para eliminar un nodo que tiene dos hijos en el arbol cuando este no es AVL y esta
     * enhebrado en POSORDEN.
     * @param x  el nodo que tiene dos hijos y se desea eliminar del árbol
     */
    public void menorPos(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        while(p.isBi())
        {
            p = p.getLi();
        }
        NodoDoble pp = padre(p);
        x.setValue(p.getValue());
        x.setKey(p.getKey());
        this.eliminarPos(p, pp);
    }
    /**
     * Método utilizado para desconectar un nodo del árbol considerando que este esta siendo enhebrado en
     * POSORDEN, para eso modifica las hebras del árbol garantizando que este seguira estando enhebrado en
     * POSORDEN.
     * @param ax el padre del nodo que se desea eliminar del árbol
     * @param x el nodo que se desea eliminar del árbol
     */
    public void eliminarPos(NodoDoble x, NodoDoble ax)
    {
        NodoDoble aux = null;
        if(x == ax.getLi() & ax.isBi())
        {
            if(!x.isBi())
            {
                if(!x.isBd())
                {
                    if(!x.getLi().isBd())
                    {
                        x.getLi().setLd(x.getLd());
                    }
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(x.getLi());
                    }
                    ax.setBi(false);
                    if(ax.isBd())
                    {
                        ax.setLi(ax.getLd());
                    }
                    else
                    {
                        ax.setLi(x.getLi());
                    }
                }
                else
                {
                    aux = this.siguientePos(x);
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLd());
                    }
                    if(!x.getLd().isBd())
                    {
                        x.getLd().setLd(aux);
                    }
                    ax.setLi(x.getLd());
                }
            }
            else
            {
                if(!x.isBd())
                {
                    aux = this.siguientePos(x);
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLi());
                    }
                    if(!x.getLi().isBd())
                    {
                        x.getLi().setLd(aux);
                    }
                    ax.setLi(x.getLi());
                }
                else
                {
                    this.menorPos(x);
                }
            }
        }
        else
        {
            if(!x.isBi())
            {
                if(!x.isBd())
                {
                    aux = this.siguientePos(ax);
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(x.getLi());
                    }
                    if(!x.getLi().isBd())
                    {
                        x.getLi().setLd(x.getLd());
                    }
                    ax.setBd(false);
                    ax.setLd(aux);
                }
                else
                {
                    aux = this.siguientePos(x);
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLd());
                    }
                    if(!x.getLd().isBd())
                    {
                        x.getLd().setLd(aux);
                    }
                    ax.setLd(x.getLd());
                }
            }
            else
            {
                if(!x.isBd())
                {
                    aux = this.siguientePos(x);
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLi());
                    }
                    if(!x.getLi().isBd())
                    {
                        x.getLi().setLd(aux);
                    }
                    ax.setLd(x.getLi());
                }
                else
                {
                    this.menorPos(x);
                }
            }
        }
    }
    /**
     * Método que permite establecer la raiz de este árbol binario, dad la representación esta
     * correspondera a la liga izquierda del nodo cabeza y para saber que el árbol tiene cabeza se
     * establece en true el campo de bi de la cabeza.
     * @param x la nueva cabeza del árbol binario
     *
     */
    public void setRaiz(NodoDoble x)
    {
        if(x != null)
        {
            R.setBi(true);
            R.setLi(x);
        }
    }
    /**
     * Método utilizado para eliminar un nodo que tiene dos hijos en el arbol cuando este no es AVL y esta
     * enhebrado en INORDEN.
     * @param x  el nodo que tiene dos hijos y se desea eliminar del árbol
     */
    public void menorIn(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        while(p.isBi())
        {
            p = p.getLi();
        }
        NodoDoble pp = padre(p);
        x.setValue(p.getValue());
        x.setKey(p.getKey());
        this.eliminarIn(p, pp);
    }
    /**
     * Método utilizado para desconectar un nodo del árbol considerando que este esta siendo enhebrado en
     * INORDEN, para eso modifica las hebras del árbol garantizando que este seguira estando enhebrado en
     * INORDEN.
     * @param ax el padre del nodo que se desea eliminar del árbol
     * @param x el nodo que se desea eliminar del árbol
     */
    public void eliminarIn(NodoDoble x, NodoDoble ax)
    {
        NodoDoble aux = null;
        if(ax.getLd() == x)
        {
            if(!x.isBd())
            {
                if(x.isBi())
                {
                    ax.setBd(true);
                    ax.setLd(x.getLi());
                    aux = this.anteriorIn(x);
                    aux.setBd(false);
                    aux.setLd(this.siguienteIn(x));
                }
                else
                {
                    ax.setBd(false);
                    ax.setLd(this.siguienteIn(x));
                }
            }
            else
            {
                if(!x.isBi())
                {
                    ax.setBd(true);
                    ax.setLd(x.getLd());
                    aux = this.siguienteIn(x);
                    aux.setBi(false);
                    aux.setLi(this.anteriorIn(x));
                }
                else
                {
                    this.menorIn(x);
                }
            }
        }
        else
        {
            if(!x.isBd())
            {
                if(x.isBi())
                {
                    ax.setBi(true);
                    ax.setLi(x.getLi());
                    aux = anteriorIn(x);
                    aux.setBd(false);
                    aux.setLd(siguienteIn(x));
                }
                else
                {
                    ax.setBi(false);
                    ax.setLi(x.getLi());
                }
            }
            else
            {
                if(!x.isBi())
                {
                    ax.setBi(true);
                    ax.setLi(x.getLd());
                    aux = siguienteIn(x);
                    aux.setBi(false);
                    aux.setLi(this.anteriorIn(x));
                }
                else
                {
                    this.menorIn(x);
                }
            }
        }
    }
    /**
     * Método que permite eliminar del árbol el nodo correspondiente a la clave ingresada como argumento,
     * este método considera que el árbol binario no es AVL, pero si de busqueda.
     * @param dato la clave del nodo que se desea eliminar del árbol binario
     * @return true si la ingresada como argumento corresponde a algún elemento del arbol o false en caso contrario
     */
    public boolean borrar(K dato)
    {
        if(this.isSearch())
        {
            //se obtiene el nodo que se desea eliminar
            NodoDoble x = this.buscar(dato, false);
            //se obtiene el padre del nodo que se desea eliminar
            NodoDoble ax =  null;
            if(x != null)
            {
                ax = this.padre(x);
            }
            //si no es la cabeza o null el nodo si existe en el árbol
            if(x != R & x != null)
            {
                if(x.getKey() instanceof Comparable & dato instanceof Comparable & x.getKey().getClass() == dato.getClass())
                {
                    Comparable z = (Comparable)dato;
                    Comparable w = (Comparable)x.getKey();
                    if(z.equals(w))
                    {
                        AVL = false;
                        //disminuye en 1 el tamaño del árbol
                        length = length - 1;
                        if(orden.equals(INORDEN))
                        {
                            //elimina el nodo considerando que el árbol esta siendo enhebrado en INORDEN
                            this.eliminarIn(x, ax);
                        }
                        if(orden.equals(NULL))
                        {
                            //elimina el nodo considerando que el árbol no esta siendo enhebrado
                            this.eliminarNormal(x, ax);
                        }
                        if(orden.equals(POSORDEN))
                        {
                            //elimina el nodo considerando que el árbol esta siendo enhebrado en POSORDEN
                            this.eliminarPos(x, ax);
                        }
                        if(orden.equals(PREORDEN))
                        {
                            //elimina el nodo considerando que el árbol esta siendo enhebrado en PREORDEN
                            this.eliminarPre(x, ax);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Método utilizado para eliminar un nodo que tiene dos hijos en el arbol cuando este no es AVL y esta
     * enhebrado en PREORDEN.
     * @param x  el nodo que tiene dos hijos y se desea eliminar del árbol
     */
    public void menorPre(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        while(p.isBi())
        {
            p = p.getLi();
        }
        NodoDoble pp = padre(p);
        x.setValue(p.getValue());
        x.setKey(p.getKey());
        this.eliminarPre(p, pp);
    }
    /**
     * Método utilizado para desconectar un nodo del árbol considerando que este esta siendo enhebrado en
     * PREORDEN, para eso modifica las hebras del árbol garantizando que este seguira estando enhebrado en
     * PREORDEN.
     * @param ax el padre del nodo que se desea eliminar del árbol
     * @param x el nodo que se desea eliminar del árbol
     */
    public void eliminarPre(NodoDoble x, NodoDoble ax)
    {
        NodoDoble aux = null;
        if(x == ax.getLd() & ax.isBd())
        {
            if(!x.isBi())
            {
                if(!x.isBd())
                {
                    aux = x.getLi();
                    if(!aux.isBd())
                    {
                        aux.setLd(x.getLd());
                    }
                    aux = x.getLd();
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLi());
                    }
                    ax.setBd(false);
                    if(ax.isBi())
                    {
                        ax.setLd(ax.getLi());
                    }
                    else
                    {
                        ax.setLd(x.getLd());
                    }
                }
                else
                {
                    aux = this.anteriorPre(x);
                    if(!aux.isBd())
                    {
                        aux.setLd(x.getLd());
                    }
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(aux);
                    }
                    ax.setLd(x.getLd());
                }
            }
            else
            {
                if(!x.isBd())
                {
                    aux = this.anteriorPre(x);
                    ax.setLd(x.getLi());
                    if(!aux.isBd())
                    {
                        aux.setLd(x.getLi());
                    }
                    if(!x.getLi().isBi())
                    {
                        x.getLi().setLi(aux);
                    }
                }
                else
                {
                    this.menorPre(x);
                }
            }
        }
        else
        {
            if(!x.isBi())
            {
                if(!x.isBd())
                {
                    aux = this.anteriorPre(ax);
                    ax.setBi(false);
                    ax.setLi(aux);
                    if(ax.isBd())
                    {
                        aux = ax.getLd();
                        if(!aux.isBi())
                        {
                            aux.setLi(ax);
                        }
                    }
                    else
                    {
                        ax.setLd(x.getLd());
                    }
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(ax);
                    }
                }
                else
                {
                    if(!ax.isBd())
                    {
                        ax.setLd(x.getLd());
                    }
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(ax);
                    }
                    ax.setLi(x.getLd());
                }
            }
            else
            {
                if(!x.isBd())
                {
                    if(!ax.isBd())
                    {
                        ax.setLd(x.getLi());
                    }
                    if(!x.getLi().isBi())
                    {
                        x.getLi().setLi(ax);
                    }
                    ax.setLi(x.getLi());
                }
                else
                {
                    this.menorPre(x);
                }
            }
        }
    }
    /**
     * Método utilizado para insertar un nuevo nodo en el árbol teniendo en cuenta que este se encuentra
     * enhebrado en INORDEN, este método garantiza que luego de la inserción el árbol binario seguira
     * estando enhebrado en INORDEN.
     * @param x El nodo despues del cual se desea insertar el nuevo nodo
     * @param izquierda si es true significa que el nuevo nodo será la liga izquierda de x, false que sera la liga derecha
     * @param nuevo el nuevo nodo que se desea insertar en el árbol
     */
    public void conectarIn(NodoDoble x, NodoDoble nuevo, boolean izquierda)
    {
        if(izquierda)
        {
            nuevo.setBd(false);
            nuevo.setBi(false);
            x.setBi(true);
            nuevo.setLd(x);
            nuevo.setLi(x.getLi());
            x.setLi(nuevo);
        }
        else
        {
            nuevo.setBd(false);
            nuevo.setBi(false);
            x.setBd(true);
            nuevo.setLi(x);
            nuevo.setLd(x.getLd());
            x.setLd(nuevo);
        }
    }
    /**
     * Método que permite insertar un nuevo nodo en el árbol binario considerando que este no es AVL pero si de
     * busqueda.
     * @param nuevo el nuevo nodo que se desea insertar en el árbol binario
     * @return true si el nuevo nodo no existia en el árbol y pudo ser insertado exitosamente, false en otro caso
     */
    public boolean insertar(NodoDoble nuevo)
    {
        if(this.isSearch())
        {
            //x contiene el nodo despues del cual se debe insertar el nuevo nodo
            NodoDoble x = this.buscar((K)nuevo.getKey(), true);
            if(x == R)
            {
                length = length + 1;
                if(orden.equals(NULL))
                {
                    this.conectarNormal(R, nuevo, true);
                }
                else if(orden.equals(INORDEN))
                {
                    this.conectarIn(R, nuevo, true);
                }
                else if(orden.equals(POSORDEN))
                {
                    this.conectarPos(R, nuevo, true);
                }
                else if(orden.equals(PREORDEN))
                {
                    this.conectarPre(R, nuevo, true);
                }
                return true;
            }
            if(x != null && nuevo.getKey() instanceof Comparable && x.getKey() instanceof Comparable)
            {
                Comparable w = null, z = null;
                w = (Comparable)x.getKey();
                z = (Comparable)nuevo.getKey();
                if(w.compareTo(z) > 0)
                {
                    length = length + 1;
                    AVL = false;
                    if(orden.equals(INORDEN))
                    {
                        this.conectarIn(x, nuevo, true);
                    }
                    else if(orden.equals(NULL))
                    {
                        this.conectarNormal(x, nuevo, true);
                    }
                    else if(orden.equals(POSORDEN))
                    {
                        this.conectarPos(x, nuevo, true);
                    }
                    else if(orden.equals(PREORDEN))
                    {
                        this.conectarPre(x, nuevo, true);
                    }
                    return true;
                }
                else
                {
                    if(w.compareTo(z) < 0)
                    {
                        length = length + 1;
                        AVL = false;
                        if(orden.equals(INORDEN))
                        {
                            this.conectarIn(x, nuevo, false);
                        }
                        else if(orden.equals(NULL))
                        {
                            this.conectarNormal(x, nuevo, false);
                        }
                        else if(orden.equals(POSORDEN))
                        {
                            this.conectarPos(x, nuevo, false);
                        }
                        else if(orden.equals(PREORDEN))
                        {
                            this.conectarPre(x, nuevo, false);
                        }
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        return false;
    }
    public boolean insertar(V dato)
    {
        NodoDoble<K, V> nuevo = new NodoDoble<K, V>(dato);
        return this.insertar(nuevo);
    }
    public boolean insertar(K key, V value)
    {
        NodoDoble<K, V> nuevo = new NodoDoble<K, V>(key, value);
        return this.insertar(nuevo);
    }
    /**
     * Método utilizado para insertar un nuevo nodo en el árbol teniendo en cuenta que este no se encuentra
     * enhebrado, este método garantiza que luego de la inserción el árbol binario seguira
     * no estando enhebrado.
     * @param x El nodo despues del cual se desea insertar el nuevo nodo
     * @param izquierda si es true significa que el nuevo nodo será la liga izquierda de x, false que sera la liga derecha
     * @param nuevo el nuevo nodo que se desea insertar en el árbol
     */
    public void conectarNormal(NodoDoble x, NodoDoble nuevo, boolean izquierda)
    {
        if(izquierda)
        {
            x.setLi(nuevo);
            x.setBi(true);
        }
        else
        {
            x.setLd(nuevo);
            x.setBd(true);
        }
    }
    /**
     * Método utilizado para buscar un nodo en el árbol.
     * @param key la clave del nodo que se desea buscar en el árbol
     * @param insert true si se desea buscar el nodo que deberia estar antes de un nodo que tuviera la clave ingresada
     * como argumento, false si se desea buscar el nodo que corresponde a la clave ingresada como argumento.
     * @return si insert es true el Nodo despues del cual se podria insertar el nodo que corresponde a la clave ingresada como
     * argumento o null en caso de que dicho nodo ya exista. si insertar es false retorna el nodo correspondiente
     * a la clave ingresada como argumento.
     */
    public NodoDoble buscar(K key, boolean insert)
    {
        if(this.isSearch())
        {
            NodoDoble x = R.getLi();
            if(x == R)
            {
                return x;
            }
            while(x != null && x != R)
            {
                if(x.getKey() instanceof Comparable & key instanceof Comparable & x.getKey().getClass() == key.getClass())
                {
                    Comparable w = null, z = null;
                    w = (Comparable)x.getKey();
                    z = (Comparable)key;
                    //si la clave actual es mayor que la clave del argumento se avanza por la izquierda
                    if(w.compareTo(z) > 0)
                    {
                        if(!x.isBi())
                        {
                            if(insert)
                            {
                                //hallo donde insertar el nodo correspondiente a la clave ingresada como argumento
                                return x;
                            }
                            //no hallo el nodo correspondiente a la clave ingresada como argumento
                            return null;
                        }
                        else
                        {
                            x = x.getLi();
                        }
                    }
                    else
                    {
                        //si la clave es mayor que el argumento se avanza por la derecha
                        if(w.compareTo(z) < 0)
                        {
                            if(!x.isBd())
                            {
                                if(insert)
                                {
                                    //hallo donde insertar el nodo correspondiente a la clave ingresada como argumento
                                    return x;
                                }
                                //no hallo el nodo correspondiente a la clave ingresada como argumento
                                return null;
                            }
                            else
                            {
                                x = x.getLd();
                            }
                        }
                        else
                        {
                            if(insert)
                            {
                                //no hallo donde insertar el nodo correspondiente a la clave ingresada como argumento
                                return null;
                            }
                            //halló el nodo correspondiente a la clave ingresada como argumento
                            return x;
                        }
                    }
                }
                else
                {
                    return null;
                }
            }
        }
        return null;
    }
    /**
     * Método que permite hallar el nodo que corresponde a la clave ingresada como argumento en
     * el árbol binario teniendo en cuenta que todas las claves deben ser unicas dentro del árbol.
     * Este método solo encuentra el valor correspondiente a la clave si el árbol es de búqueda, en caso
     * contrario retorna null.
     * @param key la clave del nodo cuyo valor desea ser hallado
     * @return el valor del nodo que corresponde a la clave ingresada como argumento o null en caso de que esta no exista en
     * el árbol.
     */
    public V buscar(K key)
    {
        if(this.isSearch())
        {
            NodoDoble<K, V> x = R.getLi();
            while(x != null && x != R)
            {
                if(x.getKey() instanceof Comparable & key instanceof Comparable & x.getKey().getClass() == key.getClass())
                {
                    Comparable w = null, z = null;
                    w = (Comparable)x.getKey();
                    z = (Comparable)key;
                    //si la clave actual es mayor que la clave del argumento se avanza por la izquierda
                    if(w.compareTo(z) > 0)
                    {
                        //clave no hallada retorna null
                        if(!x.isBi())
                        {
                            return null;
                        }
                        else
                        {
                            x = x.getLi();
                        }
                    }
                    else
                    {
                        //si la clave es mayor que el argumento se avanza por la derecha
                        if(w.compareTo(z) < 0)
                        {
                            //clave no hallada retorna null
                            if(!x.isBd())
                            {
                                return null;
                            }
                            else
                            {
                                x = x.getLd();
                            }
                        }
                        else
                        {
                            //clave hallada retorna el valor correspondiente
                            return x.getValue();
                        }
                    }
                }
                else
                {
                    return null;
                }
            }
        }
        return null;
    }
    /**
     * Método que permite hallar el nodo anterior al nodo ingresado como argumento en el recorrido PREORDEN, este
     * método SOLO ES VÄLIDO si el árbol binario se encuentra enhebrado en PREORDEN, en otro caso retornará valores
     * incorrectos.
     * @param x el nodo para el cual se desea conocer el anterior nodo en el recorrido PREORDEN
     * @return nodo anterior en el recorrido PREORDEN al nodo ingresado como argumento
     */
    public NodoDoble anteriorPre(NodoDoble x)
    {
        NodoDoble p = x.getLi();
        if(x.isBi())
        {
            p = this.padre(x);
            if(x == p.getLd() & p.isBd())
            {
                if(p.isBi())
                {
                    p = p.getLi();
                    while(p.isBi() | p.isBd())
                    {
                        if(p.isBd())
                        {
                            p = p.getLd();
                        }
                        else
                        {
                            p = p.getLi();
                        }
                    }
                }
            }
        }
        return p;
    }
    /**
     * Método utilizado para insertar un nuevo nodo en el árbol teniendo en cuenta que este se encuentra
     * enhebrado en PREORDEN, este método garantiza que luego de la inserción el árbol binario seguira
     * estando enhebrado en PREORDEN.
     * @param x El nodo despues del cual se desea insertar el nuevo nodo
     * @param izquierda si es true significa que el nuevo nodo será la liga izquierda de x, false que sera la liga derecha
     * @param nuevo el nuevo nodo que se desea insertar en el árbol
     */
    public void conectarPre(NodoDoble x, NodoDoble nuevo, boolean izquierda)
    {
        if(izquierda)
        {
            x.setBi(true);
            nuevo.setBd(false);
            nuevo.setBi(false);
            if(x.isBd())
            {
                nuevo.setLd(x.getLd());
                if(!x.getLd().isBi())
                {
                    x.getLd().setLi(nuevo);
                }
                nuevo.setLi(x);
            }
            else
            {
                nuevo.setLd(x.getLd());
                if(!x.getLd().isBi())
                {
                    x.getLd().setLi(nuevo);
                }
                nuevo.setLi(x);
                x.setLd(nuevo);
            }
            x.setLi(nuevo);
        }
        else
        {
            nuevo.setBi(false);
            nuevo.setBd(false);
            x.setBd(true);
            if(x.isBi())
            {
                NodoDoble p = x.getLi();
                if(p.isBi())
                {
                    while(!p.isBd() && p.isBi())
                    {
                        p = p.getLi();
                    }
                }
                while((p.isBd() | p.isBi()) & p != this.R)
                {
                    if(p.isBd())
                    {
                        p = p.getLd();
                    }
                    else
                    {
                        p = p.getLi();
                    }
                }
                if(p != this.R)
                {
                    p.setLd(nuevo);
                    nuevo.setLi(p);
                }
                NodoDoble aux = x;
                NodoDoble paux = this.padre(aux);
                while(aux == paux.getLd() | !paux.isBd())
                {
                    aux = paux;
                    paux = this.padre(aux);
                }
                aux = paux;
                if(aux != null)
                {
                    p = aux.getLd();
                }
                else
                {
                    p = this.R;
                }
                if(!p.isBi())
                {
                    p.setLi(nuevo);
                }
                nuevo.setLd(p);
            }
            else
            {
                nuevo.setLd(x.getLd());
                nuevo.setLi(x);
                if(!x.getLd().isBi())
                {
                    x.getLd().setLi(nuevo);
                }
            }
            x.setLd(nuevo);
        }
    }
    /**
     * Método utilizado para conocer el número de nodo que se pueden recorrer desde el nodo ingresado como
     * argumento hasta un nodo que no tenga hijos utilizando solo la liga derecha.
     * @param x el nodo para el cual se desea calcular el número de nodos que se pueden recorrer solo con la liga
     * derecha.
     * @return el número de nodos que se pueden recorrer partiendo desde el nodo ingresado como arguemnto solo
     * utilizando el campo de liga derecha
     */
    public int anchoDerecha(NodoDoble x)
    {
        int ancho = 0;
        if(x != null)
        {
            if(x.isBd())
            {
                ancho = anchoDerecha(x.getLd()) + 1;
            }
            else
            {
                ancho = 1;
            }
        }
        return ancho;
    }
    /**
     * Método utilizado para conocer el número de nodos que se pueden recorrer desde el nodo ingresado como
     * argumento hasta un nodo que no tenga hijos utilizando solo la liga izquierda.
     * @param x el nodo para el cual se desea calcular el número de nodos que se pueden recorrer solo con la liga
     * izquierda.
     * @return el número de nodos que se pueden recorrer partiendo desde el nodo ingresado como arguemnto solo
     * utilizando el campo de liga izquierda.
     */
    public int anchoIzquierda(NodoDoble x)
    {
        int ancho = 0;
        if(x != null)
        {
            if(x.isBi())
            {
                ancho = anchoIzquierda(x.getLi()) + 1;
            }
            else
            {
                ancho = 1;
            }
        }
        return ancho;
    }
    /**
     * Método que permite hallar el nodo siguiente al nodo ingresado como argumento en el recorrido PREORDEN, este
     * método SOLO ES VÄLIDO si el árbol binario se encuentra enhebrado en PREORDEN, en otro caso retornará valores
     * incorrectos.
     * @param x el nodo para el cual se desea conocer el siguiente nodo en el recorrido PREORDEN
     * @return nodo siguiente en el recorrido PREORDEN al nodo ingresado como argumento
     */
    public NodoDoble siguientePre(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        if(x.isBd())
        {
            if(x.isBi())
            {
                p = x.getLi();
            }
        }
        return p;
    }
    /**
     * Método que permite hallar el nodo siguiente al nodo ingresado como argumento en el recorrido POSORDEN, este
     * método SOLO ES VÄLIDO si el árbol binario se encuentra enhebrado en POSORDEN, en otro caso retornará valores
     * incorrectos.
     * @param x el nodo para el cual se desea conocer el siguiente nodo en el recorrido POSORDEN
     * @return nodo siguiente en el recorrido PREORDEN al nodo ingresado como argumento
     */
    public NodoDoble siguientePos(NodoDoble x)
    {
        NodoDoble p = null;
        if(x == this.R)
        {
            p = x.getLi();
            if(p == x)
            {
                return this.R;
            }
            while(p.isBi() | p.isBd())
            {
                if(p.isBi())
                {
                    p = p.getLi();
                }
                else
                {
                    p = p.getLd();
                }
            }
            return p;
        }
        p = x.getLd();
        if(x.isBd())
        {
            NodoDoble pp = padre(x);
            p = pp;
            if(p == this.R)
            {
                return p;
            }
            if(x == pp.getLi())
            {
                if(p.isBd())
                {
                    p = p.getLd();
                    if(p == x)
                    {
                        return pp;
                    }
                    while(p.isBd() | p.isBi())
                    {
                        if(p.isBi())
                        {
                            p = p.getLi();
                        }
                        else
                        {
                            p = p.getLd();
                        }
                    }
                }
            }
        }
        return p;
    }
    /**
     * Método utilizado para insertar un nuevo nodo en el árbol teniendo en cuenta que este se encuentra
     * enhebrado en POSORDEN, este método garantiza que luego de la inserción el árbol binario seguira
     * estando enhebrado en POSORDEN.
     * @param x El nodo despues del cual se desea insertar el nuevo nodo
     * @param izquierda si es true significa que el nuevo nodo será la liga izquierda de x, false que sera la liga derecha
     * @param nuevo el nuevo nodo que se desea insertar en el árbol
     */
    public void conectarPos(NodoDoble x, NodoDoble nuevo, boolean izquierda)
    {
        if(izquierda)
        {
            nuevo.setBd(false);
            nuevo.setBi(false);
            NodoDoble p = x;
            if(p.isBd() & p != this.R)
            {
                p = p.getLd();
                while(p.isBd() | p.isBi())
                {
                    if(p.isBi())
                    {
                        p = p.getLi();
                    }
                    else
                    {
                        p = p.getLd();
                    }
                }
            }
            nuevo.setLd(p);
            NodoDoble aux = x.getLi();
            if(p != x)
            {
                nuevo.setLi(p.getLi());
                if(!p.getLi().isBd() )
                {
                    p.getLi().setLd(nuevo);
                }
                p.setLi(nuevo);
            }
            else
            {
                nuevo.setLi(aux);
                if(!aux.isBd())
                {
                    aux.setLd(nuevo);
                }
            }
            x.setBi(true);
            x.setLi(nuevo);
        }
        else
        {
            nuevo.setBd(false);
            nuevo.setBi(false);
            if(x.isBi())
            {
                nuevo.setLi(x.getLi());
                if(!x.getLi().isBd())
                {
                    x.getLi().setLd(nuevo);
                }
                nuevo.setLd(x);
            }
            else
            {
                if(!x.getLi().isBd())
                {
                    x.getLi().setLd(nuevo);
                }
                nuevo.setLi(x.getLi());
                nuevo.setLd(x);
                x.setBi(false);
                x.setLi(nuevo);
            }
            x.setBd(true);
            x.setLd(nuevo);
        }
    }
    /**
     * Método utilizado para obtener el padre de un nodo del árbol.
     * @param x el nodo para el cual se desea conocer su padre
     * @return el nodo padre del nodo ingresado como argumento
     */
    public NodoDoble padre(NodoDoble x)
    {
        return x != null? x.getLp() : null;
    }
    /**
     * Método utilizado para eliminar un nodo que tiene dos hijos en el arbol cuando este no es AVL y no esta
     * enhebrado.
     * @param x  el nodo que tiene dos hijos y se desea eliminar del árbol
     */
    public void menorNormal(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        while(p.getLi() != null)
        {
            p = p.getLi();
        }
        NodoDoble pp = padre(p);
        x.setValue(p.getValue());
        x.setKey(p.getKey());
        this.eliminarNormal(p, pp);
    }
    /**
     * Método utilizado para desconectar un nodo del árbol considerando que este no esta siendo enhebrado,
     * para eso modifica las hebras del árbol garantizando que este seguira no estando enhebrado.
     * @param ax el padre del nodo que se desea eliminar del árbol
     * @param x el nodo que se desea eliminar del árbol
     */
    public void eliminarNormal(NodoDoble x, NodoDoble ax)
    {
        if(ax.getLd() == x)
        {
            if(x.getLd() == null)
            {
                ax.setLd(x.getLi());
            }
            else
            {
                if(x.getLi() == null)
                {
                    ax.setLd(x.getLd());
                }
                else
                {
                    this.menorNormal(x);
                }
            }
        }
        else
        {
            if(x.getLd() == null)
            {
                ax.setLi(x.getLi());
                if(ax == this.R && x.getLi() == null)
                {
                    this.R.setBi(false);
                    this.R.setLi(this.R);
                }
            }
            else
            {
                if(x.getLi() == null)
                {
                    ax.setLi(x.getLd());
                }
                else
                {
                    this.menorNormal(x);
                }
            }
        }
    }
    /**
     * Método que permite obtener el número de nodo existentes en el subárbol que tiene como raiz el nodo
     * ingresado como argumento.
     * @param x el nodo raiz del subarbol al cual se le desea contar el número de elementos
     * @return el número de nodos contenidos en el subárbol que tiene como raiz el nodo ingresado como argumento
     */
    public int size(NodoDoble x)
    {
        //árbol vacio que tiene 0 elementos
        if(x == null)
        {
            return 0;
        }
        Stack pila = new Stack();
        NodoDoble p = x;
        int size = 0;
        if(p == p.getLi())
        {
            return size;
        }
        size = 1;
        while(p != this.R && (p.isBd() | p.isBi() | !pila.isEmpty()))
        {
            size = size + 1;
            if(!p.isBd())
            {
                if(p.isBi())
                {
                    //se avanza a la liga izquierda del nodo actual
                    p = p.getLi();
                }
                else
                {
                    //nodo actual es una hoja si la pila contiene elementos se desapila para continuar el recorrido
                    if(!pila.isEmpty())
                    {
                        //se desapila el nodo actual en la cima de la pila
                        p = (NodoDoble)pila.pop();
                        //el recorrido debe continuar en la liga derecha del nodo que se habia
                        //almacenado preciamente en la pila
                        p = p.getLd();
                    }
                }
            }
            else
            {
                if(p.isBi())
                {
                    //el nodo tiene liga derecha e izquierda por lo tanto se apila en la pila,
                    //el recorrido continuara por su liga izquierda, pero una vez se termine este recorrido
                    //se recorrera por la liga derecha de este nodo
                    pila.push(p);
                    //se avanza por la liga izquierda
                    p = p.getLi();
                }
                else
                {
                    //se avanza por la liga derecha
                    p = p.getLd();
                }
            }
        }
        return size;
    }
    /**
     * Método que retorna el árbol binario correspondiente a los recorridos preorden e inorden ingresados como argumento.
     * Es necesario indicar que ambos recorridos deben contener objetos de la misma clase y los objetos son comparados de
     * acuerdo con la ubicación de memoria, no con el método equals de la clase Object, lo cual hace que los objetos que
     * contendrá el nuevo árbol deben estar en ambos arreglos. Lo anterior implica que no son válidos argumentos como:
     * pre = {"b", "a", "c"};
     * in = {"a", "b", "c"};
     * dado que aunque si se comparan con equals los elementos pre[1] y in[0] son iguales, ambos apuntan a dos direcciones
     * de memoria diferente, lo cual daria un resultado incorrecto al realizar el algoritmo. Una forma apropiada de crear el
     * arbol indicado anteriormente sería:
     * String a = "a", b = "b", c = "c";
     * pre = {b, c, a};
     * in = {a, b, c};
     * en este caso pre[1] y in[0] si corresponderían al mismo elemento en memoria y sería válido el árbol creado por este algoritmo.
     * @param pre el recorrido preorden del árbol que se desea crear
     * @param in el recoorrido inorden del árbol que se desea crear
     * @return el árbol binario correpondiente a los recorridos preorden e inorden ingresados como arguemento
     */
    public static ArbolLista crearArbolPre(Object pre[], Object in[])
    {
        //se evalua si ambos recorridos tienen el mismo tamaño y no son vacios
        if(pre.length == in.length && pre.length > 0)
        {
            //llama el método consabPre que retorna el arbol binario correspondiente a los recorridos preorden e inorden
            return ArbolLista.consabPre(0, pre.length - 1, 0, pre, in);
        }
        return new ArbolLista();
    }
    /**
     * Método que retorna el árbol binario correspondiente a los recorridos posorden e inorden ingresados como argumento.
     * Es necesario indicar que ambos recorridos deben contener objetos de la misma clase y los objetos son comparados de
     * acuerdo con la ubicación de memoria, no con el método equals de la clase Object, lo cual hace que los objetos que
     * contendrá el nuevo árbol deben estar en ambos arreglos. Lo anterior implica que no son válidos argumentos como:
     * pos = {"a", "c", "b"};
     * in = {"a", "b", "c"};
     * dado que aunque si se comparan con equals los elementos pos[0] y in[0] son iguales, ambos apuntan a dos direcciones
     * de memoria diferente, lo cual daria un resultado incorrecto al realizar el algoritmo. Una forma apropiada de crear el
     * arbol indicado anteriormente sería:
     * String a = "a", b = "b", c = "c";
     * pos = {a, c, b};
     * in = {a, b, c};
     * en este caso pos[0] y in[0] si corresponderían al mismo elemento en memoria y sería válido el árbol creado por este algoritmo.
     * @param pos el recorrido posorden del árbol que se desea crear
     * @param in el recoorrido inorden del árbol que se desea crear
     * @return el árbol binario correpondiente a los recorridos posorden e inorden ingresados como arguemento
     */
    public static ArbolLista crearArbolPos(Object pos[], Object in[])
    {
        if(pos.length == in.length && pos.length > 0)
        {
            return ArbolLista.consabPos(0, pos.length - 1, pos.length - 1, pos, in);
        }
        return new ArbolLista();
    }
    /**
     * Método que permite obtener el arbol binario correspondiente a los recorridos posorden e inorden ingresados como argumento.
     * @param i indice utilizado para recorrer el arreglo del recorrido preorden
     * @param lin indice que indica cual es el menor indice a partir del cual se debe buscar el dato ubicado en la posicion i del arreglo
     * preorden en el arreglo que contiene el recorrido inorden
     * @param lis indice que indica cual es el mayor indice hasta el cual se debe buscar el dato ubicado en la posicion i del arreglo
     * preorden en el arreglo que contiene el recorrido inorden.
     * @param pos arreglo que contiene el recorrido posorden del árbol que se desea crear
     * @param in arreglo que contiene el recorrido inorden del árbol que se desea crear.
     * @return Subárbol que contiene todos los elementos del arreglo inorden que se encuentren entre el indice lin y el indice lis, lo cual para
     * el caso inicial representa todo el arbol, pero luego a base de llamadas recursivas se reduce hasta llegar a producir solo un nodo.
     */
    public static ArbolLista consabPos(int lin, int lis, int i, Object pos[], Object in[])
    {
        ArbolLista hi = new ArbolLista();
        ArbolLista hd = new ArbolLista();
        int k = lin;
        if(lis > lin)
        {
            while(in[k] != pos[i] & k < lis)
            {
                k = k + 1;
            }
            if(k > lin)
            {
                if(k < lis)
                {
                    hd = ArbolLista.consabPos(k + 1, lis, i - 1, pos, in);
                }
                i = i - hd.getLength();
                hi = ArbolLista.consabPos(lin, k - 1, i - 1, pos, in);
            }
            else
            {
                if(k < lis)
                {
                    hd = ArbolLista.consabPos(k + 1, lis, i - 1, pos, in);
                }
            }
        }
        NodoDoble x = null;
        x = new NodoDoble(in[k]);
        x.setLi(hi.getRaiz());
        x.setLd(hd.getRaiz());
        ArbolLista ax = new ArbolLista();
        ax.setLength(1 + hd.getLength() + hi.getLength());
        ax.setRaiz(x);
        return ax;
    }
    /**
     * Método que permite obtener el arbol binario correspondiente a los recorridos preorden e inorden ingresados como argumento.
     * @param i indice utilizado para recorrer el arreglo del recorrido preorden
     * @param lin indice que indica cual es el menor indice a partir del cual se debe buscar el dato ubicado en la posicion i del arreglo
     * preorden en el arreglo que contiene el recorrido inorden
     * @param lis indice que indica cual es el mayor indice hasta el cual se debe buscar el dato ubicado en la posicion i del arreglo
     * preorden en el arreglo que contiene el recorrido inorden.
     * @param pre arreglo que contiene el recorrido preorden del árbol que se desea crear
     * @param in arreglo que contiene el recorrido inorden del árbol que se desea crear.
     * @return Subárbol que contiene todos los elementos del arreglo inorden que se encuentren entre el indice lin y el indice lis, lo cual para
     * el caso inicial representa todo el arbol, pero luego a base de llamadas recursivas se reduce hasta llegar a producir solo un nodo.
     */
    public static ArbolLista consabPre(int lin, int lis, int i, Object pre[], Object in[])
    {
        //subárbol izquierdo del arbol que se va a crear
        ArbolLista hi = new ArbolLista();
        //subárbol derecho del árbol que se va a crear
        ArbolLista hd = new ArbolLista();
        //se inicia el recorrido en el arreglo inorden a partir del limite inferior ingresado como argumento
        int k = lin;
        if(lis > lin)
        {
            //Se recorre el arreglo inorden hasta encontrar el elemento hallado en la posición i del arreglo preorden
            //o hasta que k sea igual al limite superior
            while( k < lis && in[k] != pre[i])
            {
                k = k + 1;
            }
            if(k > lin)
            {
                //si k es mayor que el limite inferior significa que este árbol debe tener subarbol izquierdo no vacio
                //este recibira como argumentos el mismo limite inferior, su limite superior sera k - 1 y avanzara una unidad en
                //el arreglo preorden
                hi = ArbolLista.consabPre(lin, k - 1, i + 1, pre, in);
                if(k < lis)
                {
                    //al llamar el método recursivamente para crear el subárbol izquierdo se agregaron nuevos nodos en el subárbol y ademas
                    //se se avanzo en el recorrido preorden, lo cual implica que es necesario conocer el número de nodos creados al
                    //llamar dicho método, para esto se utiliza la propiedad length del subarbol izquierdo de este árbol
                    i = i + hi.getLength();
                    //dado que k es menor que lis este árbol debe tener subárbol derecho no vacio, su limite inferior es k + 1 y su limite
                    //superior es el mismo y el indice en el recorrido preorden es el que se menciono previamente
                    hd = ArbolLista.consabPre(k + 1, lis, i + 1, pre, in);
                }
            }
            else
            {
                if(k < lis)
                {
                    //dado que k es menor que lis este árbol debe tener subárbol derecho no vacio, su limite inferior es k + 1 y su limite
                    //superior es el mismo y el indice en el recorrido preorden es el que se menciono previamente
                    hd = ArbolLista.consabPre(k + 1, lis, i + 1, pre, in);
                }
            }
        }
        NodoDoble x = null;
        //se crea un nuevo nodo cuyo dato es el valor existente en el indice k del recorrido inorden
        x = new NodoDoble(in[k]);
        //se establece como liga izquierda de este nodo la raiz del subarbol izquierdo generado recursivamente
        x.setLi(hi.getRaiz());
        //se establece como liga derecho de este nodo la raiz del subarbol derecho generado recursivamente
        x.setLd(hd.getRaiz());
        //se crea un nuevo árbol binario
        ArbolLista ax = new ArbolLista();
        //se establece el número de nodos de nuevo arbol
        ax.setLength(1+hd.getLength()+hi.getLength());
        //se establece el nuevo nodo creado como la raiz del nuevo árbol
        ax.setRaiz(x);
        return ax;
    }
    /**
     * Método que permite hallar el nodo anterior al nodo ingresado como argumento en el recorrido INORDEN, este
     * método SOLO ES VÄLIDO si el árbol binario se encuentra enhebrado en INORDEN, en otro caso retornará valores
     * incorrectos.
     * @param x el nodo para el cual se desea conocer el anterior nodo en el recorrido INORDEN
     * @return nodo anterior en el recorrido PREORDEN al nodo ingresado como argumento
     */
    public NodoDoble anteriorIn(NodoDoble x)
    {
        NodoDoble p = null;
        p = x.getLi();
        if(x.isBi())
        {
            while(p != null && p.isBd())
            {
                p = p.getLd();
            }
        }
        return p;
    }
    /**
     * Método que permite hallar el nodo siguiente al nodo ingresado como argumento en el recorrido INORDEN, este
     * método SOLO ES VÄLIDO si el árbol binario se encuentra enhebrado en INORDEN, en otro caso retornará valores
     * incorrectos.
     * @param x el nodo para el cual se desea conocer el siguiente nodo en el recorrido INORDEN
     * @return nodo siguiente en el recorrido PREORDEN al nodo ingresado como argumento
     */
    public NodoDoble siguienteIn(NodoDoble x)
    {
        NodoDoble p = null;
        p = x.getLd();
        if(x.isBd())
        {
            while(p != null && p.isBi())
            {
                p = p.getLi();
            }
        }
        return p;
    }
    /**
     * Método que permite obtener un arreglo que contiene todos los valores de los nodos del árbol ordenados
     * según el recorrido PREORDEN del árbol, Este método SOLO ES VÄLIDO si el árbol se encuentra enhebrado en
     * PREORDEN, en otro caso retornara valores incorrectos.
     * @return un arreglo de los valores de los nodos del árbol ordenados de acuerdo con el recorrido PREORDEN este
     * arreglo tiene el mismo tamaño que el árbol.
     */
    public V[] preordenPre()
    {
        return (V[])this.preordenPre(ArbolLista.TYPE_VALUE);
    }
    /**
     * Método que permite obtener un arreglo que contienen el recorrido INORDEN de este árbol.
     * Si el árbol se encuentra enhebrado en INORDEN efectua el método que permite realizar este recorrido sin necesitar
     * recursión o pilas con orden de magnitud lineal, en caso de que no este enhebrado en INORDEN será necesario
     * realizar el método mediante pilas y en el peor caso podrá ser un  algoritmo de orden de magnitud exponencial.
     * @return el recorrido INORDEN sobre este árbol binario
     */
    public V[] inordenMejorado()
    {
        Object inorden[] = null;
        if(orden.equals(ArbolLista.INORDEN))
        {
            //algoritmo de orden de magnitud lineal
            inorden = this.inordenIn();
        }
        else
        {
            //algoritmo de orden de magnitud exponencial
            inorden = this.inordenNormal();
        }
        return (V[])inorden;
    }
    /**
     * Metodo que permite obtener el arreglo que contiene el recorrido INORDEN de este árbol sin importar como esté enehebrado,
     * utiliza una pila y es de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido INORDEN de este árbol binario
     */
    public V[] inordenNormal()
    {
        return (V[])this.inordenNormal(ArbolLista.TYPE_VALUE);
    }
    /**
     * Método que permite obtener un arreglo que contiene todos los valores de los nodos del árbol ordenados
     * según el recorrido INORDEN del árbol, Este método SOLO ES VÄLIDO si el árbol se encuentra enhebrado en
     * INORDEN, en otro caso retornara valores incorrectos.
     * @return un arreglo de los valores de los nodos del árbol ordenados de acuerdo con el recorrido INORDEN, este
     * arreglo tiene el mismo tamaño que el árbol.
     */
    public V[] inordenIn()
    {
        return (V[])this.inordenIn(ArbolLista.TYPE_VALUE);
    }
    /**
     * Método que permite obtener un arreglo que contienen el recorrido PREORDEN de este árbol.
     * Si el árbol se encuentra enhebrado en PREORDEN efectua el método que permite realizar este recorrido sin necesitar
     * recursión o pilas con orden de magnitud lineal, en caso de que no este enhebrado en PREORDEN será necesario
     * realizar el método mediante pilas y en el peor caso podrá ser un  algoritmo de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido PREORDEN sobre este árbol binario
     */
    public V[] preordenMejorado()
    {
        Object preorden[] = null;
        if(orden.equals(PREORDEN))
        {
            preorden = this.preordenPre();
        }
        else
        {
            preorden = this.preordenNormal();
        }
        return (V[])preorden;
    }
    /**
     * Metodo que permite obtener el arreglo que contiene el recorrido PREORDEN de este árbol sin importar como esté enehebrado,
     * utiliza una pila y es de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido PREORDEN de este árbol
     */
    public V[] preordenNormal()
    {
        return (V[])this.preordenNormal(ArbolLista.TYPE_VALUE);
    }
    /**
     * Método que permite obtener un arreglo que contienen el recorrido POSORDEN de este árbol.
     * Si el árbol se encuentra enhebrado en POSORDEN efectua el método que permite realizar este recorrido sin necesitar
     * recursión o pilas con orden de magnitud lineal, en caso de que no este enhebrado en POSORDEN será necesario
     * realizar el método mediante pilas y en el peor caso podrá ser un  algoritmo de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido POSORDEN sobre este árbol binario
     */
    public V[] posordenMejorado()
    {
        Object posorden[] = null;
        if(orden.equals(POSORDEN))
        {
            posorden = this.posordenPos();
        }
        else
        {
            posorden = this.posordenNormal();
        }
        return (V[])posorden;
    }
    /**
     * Método que permite obtener un arreglo que contiene todos los valores de los nodos del árbol ordenados
     * según el recorrido POSORDEN del árbol, Este método SOLO ES VÄLIDO si el árbol se encuentra enhebrado en
     * POSORDEN, en otro caso retornara valores incorrectos.
     * @return un arreglo de los valores de los nodos del árbol ordenados de acuerdo con el recorrido POSORDEN este
     * arreglo tiene el mismo tamaño que el árbol.
     */
    public V[] posordenPos()
    {
        return (V[])this.posordenPos(ArbolLista.TYPE_VALUE);
    }
    /**
     * Metodo que permite obtener el arreglo que contiene el recorrido POSORDEN de este árbol sin importar como esté enehebrado,
     * utiliza una pila y es de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido POSORDEN de este árbol
     */
    public V[] posordenNormal()
    {
        return (V[])this.posordenNormal(ArbolLista.TYPE_VALUE);
    }
    /**
     * Método que permite obtener un arreglo que contiene todos los valores de los nodos del árbol ordenados
     * según el recorrido PREORDEN del árbol, Este método SOLO ES VÄLIDO si el árbol se encuentra enhebrado en
     * PREORDEN, en otro caso retornara valores incorrectos.
     * @return un arreglo de los valores de los nodos del árbol ordenados de acuerdo con el recorrido PREORDEN este
     * arreglo tiene el mismo tamaño que el árbol.
     */
    public Object[] preordenPre(int type)
    {
        //arreglo de objetos genericos del mismo tamaño que este árbol
        Object preorden[] = new Object[length];
        //se obtiene el nodo siguiente al nodo ingresado como argumento en el recorrido PREORDEN
        NodoDoble p = this.siguientePre(this.getR());
        int i = 0;
        while(p != this.getR())
        {
            if(type == ArbolLista.TYPE_KEY)
            {
                preorden[i] = p.getKey();
            }
            else if(type == ArbolLista.TYPE_NODE)
            {
                preorden[i] = p;
            }
            else if(type == ArbolLista.TYPE_VALUE)
            {
                preorden[i] = p.getValue();
            }
            i = i + 1;
            p = this.siguientePre(p);
        }
        return preorden;
    }
    /**
     * Método que permite obtener un arreglo que contienen el recorrido INORDEN de este árbol.
     * Si el árbol se encuentra enhebrado en INORDEN efectua el método que permite realizar este recorrido sin necesitar
     * recursión o pilas con orden de magnitud lineal, en caso de que no este enhebrado en INORDEN será necesario
     * realizar el método mediante pilas y en el peor caso podrá ser un  algoritmo de orden de magnitud exponencial.
     * @return el recorrido INORDEN sobre este árbol binario
     */
    public Object[] inordenMejorado(int type)
    {
        Object inorden[] = null;
        if(orden.equals(ArbolLista.INORDEN))
        {
            //algoritmo de orden de magnitud lineal
            inorden = this.inordenIn(type);
        }
        else
        {
            //algoritmo de orden de magnitud exponencial
            inorden = this.inordenNormal(type);
        }
        return inorden;
    }
    /**
     * Metodo que permite obtener el arreglo que contiene el recorrido INORDEN de este árbol sin importar como esté enehebrado,
     * utiliza una pila y es de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido INORDEN de este árbol binario
     */
    public Object[] inordenNormal(int type)
    {
        Object inorden[] = new Object[length];
        int i = 0;
        NodoDoble x = this.getR().getLi();
        if(x == this.getR())
        {
            return inorden;
        }
        Stack pila = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
                if(type == ArbolLista.TYPE_KEY)
                {
                    inorden[i] = x.getKey();
                }
                else if(type == ArbolLista.TYPE_NODE)
                {
                    inorden[i] = x;
                }
                else if(type == ArbolLista.TYPE_VALUE)
                {
                    inorden[i] = x.getValue();
                }
                i = i + 1;
                if(x.isBd())
                {
                    x = x.getLd();
                }
                else
                {
                    x = null;
                }
            }
            else
            {
                pila.push(x);
                if(x.isBi())
                {
                    x = x.getLi();
                }
                else
                {
                    x = null;
                }
            }
        }
        return inorden;
    }
    /**
     * Método que permite obtener un arreglo que contiene todos los valores de los nodos del árbol ordenados
     * según el recorrido INORDEN del árbol, Este método SOLO ES VÄLIDO si el árbol se encuentra enhebrado en
     * INORDEN, en otro caso retornara valores incorrectos.
     * @return un arreglo de los valores de los nodos del árbol ordenados de acuerdo con el recorrido INORDEN, este
     * arreglo tiene el mismo tamaño que el árbol.
     */
    public Object[] inordenIn(int type)
    {
        Object inorden[] = new Object[length];
        NodoDoble p = this.siguienteIn(this.getR());
        int i = 0;
        while(p != this.getR())
        {
            if(type == ArbolLista.TYPE_KEY)
            {
                inorden[i] = p.getKey();
            }
            else if(type == ArbolLista.TYPE_NODE)
            {
                inorden[i] = p;
            }
            else if(type == ArbolLista.TYPE_VALUE)
            {
                inorden[i] = p.getValue();
            }
            i = i + 1;
            //siguiente nodo en el recorrido inorden
            p = siguienteIn(p);
        }
        return inorden;
    }
    /**
     * Método que permite obtener un arreglo que contienen el recorrido PREORDEN de este árbol.
     * Si el árbol se encuentra enhebrado en PREORDEN efectua el método que permite realizar este recorrido sin necesitar
     * recursión o pilas con orden de magnitud lineal, en caso de que no este enhebrado en PREORDEN será necesario
     * realizar el método mediante pilas y en el peor caso podrá ser un  algoritmo de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido PREORDEN sobre este árbol binario
     */
    public Object[] preordenMejorado(int type)
    {
        Object preorden[] = null;
        if(orden.equals(PREORDEN))
        {
            preorden = this.preordenPre(type);
        }
        else
        {
            preorden = this.preordenNormal(type);
        }
        return preorden;
    }
    /**
     * Metodo que permite obtener el arreglo que contiene el recorrido PREORDEN de este árbol sin importar como esté enehebrado,
     * utiliza una pila y es de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido PREORDEN de este árbol
     */
    public Object[] preordenNormal(int type)
    {
        Object preorden[] = new Object[length];
        NodoDoble x = this.getRaiz();
        if(x == null)
        {
            return preorden;
        }
        int i = 0;
        Stack pila = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
            }
            else
            {
                if(type == ArbolLista.TYPE_KEY)
                {
                    preorden[i] = x.getKey();
                }
                else if(type == ArbolLista.TYPE_NODE)
                {
                    preorden[i] = x;
                }
                else if(type == ArbolLista.TYPE_VALUE)
                {
                    preorden[i] = x.getValue();
                }
                i = i + 1;
                if(x.isBd())
                {
                    pila.push(x.getLd());
                }
                else
                {
                    pila.push(null);
                }
                if(x.isBi())
                {
                    x = x.getLi();
                }
                else
                {
                    x = null;
                }
            }
        }
        return preorden;
    }
    /**
     * Método que permite obtener un arreglo que contienen el recorrido POSORDEN de este árbol.
     * Si el árbol se encuentra enhebrado en POSORDEN efectua el método que permite realizar este recorrido sin necesitar
     * recursión o pilas con orden de magnitud lineal, en caso de que no este enhebrado en POSORDEN será necesario
     * realizar el método mediante pilas y en el peor caso podrá ser un  algoritmo de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido POSORDEN sobre este árbol binario
     */
    public Object[] posordenMejorado(int type)
    {
        Object posorden[] = null;
        if(orden.equals(POSORDEN))
        {
            posorden = this.posordenPos(type);
        }
        else
        {
            posorden = this.posordenNormal(type);
        }
        return posorden;
    }
    /**
     * Método que permite obtener un arreglo que contiene todos los valores de los nodos del árbol ordenados
     * según el recorrido POSORDEN del árbol, Este método SOLO ES VÄLIDO si el árbol se encuentra enhebrado en
     * POSORDEN, en otro caso retornara valores incorrectos.
     * @return un arreglo de los valores de los nodos del árbol ordenados de acuerdo con el recorrido POSORDEN este
     * arreglo tiene el mismo tamaño que el árbol.
     */
    public Object[] posordenPos(int type)
    {
        Object posorden[] = new Object[length];
        int i = 0;
        NodoDoble p = siguientePos(this.getR());
        while(p != this.getR())
        {
            if(type == ArbolLista.TYPE_KEY)
            {
                posorden[i] = p.getKey();
            }
            else if(type == ArbolLista.TYPE_NODE)
            {
                posorden[i] = p;
            }
            else if(type == ArbolLista.TYPE_VALUE)
            {
                posorden[i] = p.getValue();
            }
            i = i + 1;
            //se obtiene el siguiente nodo en el recorrido POSORDEN
            p = siguientePos(p);
        }
        return posorden;
    }
    /**
     * Metodo que permite obtener el arreglo que contiene el recorrido POSORDEN de este árbol sin importar como esté enehebrado,
     * utiliza una pila y es de orden de magnitud exponencial.
     * @return arreglo que contiene el recorrido POSORDEN de este árbol
     */
    public Object[] posordenNormal(int type)
    {
        //el arreglo retornado tiene el mismo tamaño de este árbol
        Object posorden[] = new Object[length];
        int i = 0;
        //recorre el árbol a partir de la raiz
        NodoDoble x = this.getRaiz();
        if(x == null)
        {
            //si el árbol esta vacio se retorna un arreglo de tamaño 0
            return posorden;
        }
        //pila utilizada para recorrer el árbol
        Stack pila = new Stack();
        //pila que almacena el recorrido posorden en orden inverso
        Stack vuelta = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                //x es el tope de la pila
                x = (NodoDoble)pila.pop();
            }
            else
            {
                //apila el nodo actual en la pila en orden inverso
                vuelta.push(x);
                //apila la liga derecha en la pila
                if(x.isBi())
                {
                    pila.push(x.getLi());
                }
                else
                {
                    pila.push(null);
                }
                //avanza por la liga derecha
                if(x.isBd())
                {
                    x = x.getLd();
                }
                else
                {
                    x = null;
                }
            }
        }
        //desapila los elementos de la pila en orden inverso en el arreglo
        while(!vuelta.isEmpty())
        {
            x = (NodoDoble)vuelta.pop();
            if(type == ArbolLista.TYPE_KEY)
            {
                posorden[i] = x.getKey();
            }
            else if(type == ArbolLista.TYPE_NODE)
            {
                posorden[i] = x;
            }
            else if(type == ArbolLista.TYPE_VALUE)
            {
                posorden[i] = x.getValue();
            }
            i = i + 1;
        }
        return posorden;
    }
    /**
     * Método que permite obtener el recorrido INORDEN del subárbol cuya raiz corresponde al 
     * nodo ingresado como argumento.
     * @param x El nodo raiz del subarbol
     * @return arreglo que contiene el recorrido INORDEN del subárbol con raiz x
     */
    public Object[] inorden(NodoDoble x)
    {
        ArbolLista subarbol = this.subarbol(x);
        return subarbol.inordenMejorado();
    }
    /**
     * Método que permite obtener el recorrido INORDEN del subárbol cuya raiz corresponde al 
     * nodo ingresado como argumento.
     * @param x El nodo raiz del subarbol
     * @return arreglo que contiene el recorrido POSORDEN del subárbol con raiz x
     */
    public Object[] posorden(NodoDoble x)
    {
        ArbolLista subarbol = this.subarbol(x);
        return subarbol.posordenMejorado();
    }
    /**
     * Método que permite obtener el recorrido INORDEN del subárbol cuya raiz corresponde al 
     * nodo ingresado como argumento.
     * @param x El nodo raiz del subarbol
     * @return arreglo que contiene el recorrido PREORDEN del subárbol con raiz x
     */
    public Object[] preorden(NodoDoble x)
    {
        ArbolLista subarbol = this.subarbol(x);
        return subarbol.preordenMejorado();
    }
    /**
     * Método que permite enhebrar en PREORDEN el árbol binario sin importar el tipo de hebras que tenga previamente
     * a la llamada de este método.
     */
    public void preorden()
    {
        //se cambia el orden en el que esta siendo enhebrado el arbol
        orden = PREORDEN;
        //Nodo actual en el recorrido PREORDEN
        NodoDoble x = R.getLi();
        //Nodo anterior al nodo actual en el recorrido PREORDEN
        NodoDoble ax = this.getR();
        if(x == ax)
        {
            return;
        }
        Stack pila = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
            }
            else
            {
                boolean isBi = x.isBi();
                NodoDoble aux = x.getLi();
                if(!x.isBi())
                {
                    //la liga izquierda del nodo actual es null o una hebra, por lo tanto se debe establecer
                    //el valor de bi en false y la liga izquierda el nodo anterior en el recorrido PREORDEN
                    x.setBi(false);
                    x.setLi(ax);
                }
                if(!ax.isBd())
                {
                    //la liga derecha del nodo anterior al actual es null o una hebra, por lo tanto se debe establecer el
                    //valor bd en false y la liga derecha el nodo actual
                    ax.setBd(false);
                    ax.setLd(x);
                }
                ax = x;
                if(isBi)
                {
                    x = aux;
                }
                else
                {
                    x = null;
                }
                if(ax.isBd())
                {
                    pila.push(ax.getLd());
                }
                else
                {
                    pila.push(null);
                }
            }
        }
        if(!ax.isBd())
        {
            //se hace circular la lista que contiene el recorrido PREORDEN
            ax.setBd(false);
            ax.setLd(R);
        }
    }
    /**
     * Método que permite enhebrar en INORDEN el árbol binario sin importar el tipo de hebras que tenga previamente
     * a la llamada de este método.
     */
    public void inorden()
    {
        orden = INORDEN;
        //Nodo actual en el recorrido INORDEN
        NodoDoble x = R.getLi();
        //Nodo anterior al nodo actual en el recorrido INORDEN
        NodoDoble ax = R;
        if(x == ax)
        {
            return;
        }
        Stack pila = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
                if(!x.isBi())
                {
                    x.setBi(false);
                    x.setLi(ax);
                }
                if(!ax.isBd())
                {
                    ax.setBd(false);
                    ax.setLd(x);
                }
                ax = x;
                if(x.isBd())
                {
                    x = x.getLd();
                }
                else
                {
                    x = null;
                }
            }
            else
            {
                pila.push(x);
                if(x.isBi())
                {
                    x = x.getLi();
                }
                else
                {
                    x = null;
                }
            }
        }
        if(!ax.isBd())
        {
            ax.setBd(false);
            ax.setLd(R);
        }
    }    
    /**
     * Método que permite enhebrar en POSORDEN el árbol binario sin importar el tipo de hebras que tenga previamente
     * a la llamada de este método.
     */
    public void posorden()
    {
        orden = POSORDEN;
        NodoDoble x = R.getLi();
        NodoDoble ax = R;
        if(x == ax)
        {
            return;
        }
        Stack pila = new Stack(), vuelta = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
            }
            else
            {
                vuelta.push(x);
                if(x.isBi())
                {
                    pila.push(x.getLi());
                }
                else
                {
                    pila.push(null);
                }
                if(x.isBd())
                {
                    x = x.getLd();
                }
                else
                {
                    x = null;
                }
            }
        }
        while(!vuelta.isEmpty())
        {
            x = (NodoDoble)vuelta.pop();
            if(!ax.isBd() | ax.getLd() == null)
            {
                ax.setBd(false);
                ax.setLd(x);
            }
            if(!x.isBi() | x.getLi() == null)
            {
                x.setBi(false);
                x.setLi(ax);
            }
            ax = x;
        }
        if(!ax.isBd())
        {
            ax.setBd(false);
            ax.setLd(R);
        }
    }
    /**
     * Método que permite obtener una copia de este árbol
     * @return una copia de este árbol
     */
    public Object clonar() 
    {
        return new ArbolLista(this);
    }
    public int size()
    {
        return this.length;
    }
    public boolean isEmpty()
    {
        return this.length == 0;
    }
    public boolean containsKey(Object key)
    {
        return this.buscar((K)key) != null;
    }
    public boolean containsValue(Object value)
    {
        Object values[] = new Object[0];
        if(orden.equals(ArbolLista.INORDEN))
        {
            values = this.inordenMejorado();
        }
        else if(orden.equals(ArbolLista.POSORDEN))
        {
            values = this.posordenMejorado();
        }
        else if(orden.equals(ArbolLista.PREORDEN))
        {
            values = this.preordenMejorado();
        }
        else
        {
            values = this.inordenMejorado();
        }
        for(int i = 0; i < values.length; i++)
        {
            if(values[i] != null && values[i].equals(value))
            {
                return true;
            }
        }
        return false;
    }
    public V get(Object key)
    {
        return this.buscar((K)key);
    }
    public V put(K key, V value)
    {
        this.insertarAVL(key, value);
        return value;
    }
    public V remove(Object key)
    {
        V v = this.buscar((K)key);
        if(v != null)
        {
            this.borrarAVL((K)key);
        }
        return v;
    }
    public void putAll(Map<? extends K, ? extends V> m)
    {
        Collection v = m.values();
        Iterator iv = v.iterator();
        Collection k = m.keySet();
        Iterator ik = k.iterator();
        while(iv.hasNext() && ik.hasNext())
        {
            K key = (K)ik.next();
            V value = (V)iv.next();
            this.put(key, value);
        }
    }
    public void clear()
    {
        R.setBd(true);
        R.setLd(R);
        R.setBi(false);
        R.setLi(R);
        length = 0;
    }
    public Set<K> keySet()
    {
        co.edu.udea.logic.truthtabletree.Set set = new co.edu.udea.logic.truthtabletree.Set();
        Object keys[] = new Object[0];
        if(orden.equals(ArbolLista.INORDEN))
        {
            keys = this.inordenMejorado(ArbolLista.TYPE_KEY);
        }
        else if(orden.equals(ArbolLista.PREORDEN))
        {
            keys = this.preordenMejorado(ArbolLista.TYPE_KEY);
        }
        else if(orden.equals(ArbolLista.POSORDEN))
        {
            keys = this.posordenMejorado(ArbolLista.TYPE_KEY);
        }
        else
        {
            keys = this.inordenMejorado(ArbolLista.TYPE_KEY);
        }
        set.addAll(Arrays.asList(keys));
        return set;
    }
    public Collection<V> values()
    {
        co.edu.udea.logic.truthtabletree.Set set = new co.edu.udea.logic.truthtabletree.Set();
        Object values[] = new Object[0];
        if(orden.equals(ArbolLista.INORDEN))
        {
            values = this.inordenMejorado(ArbolLista.TYPE_VALUE);
        }
        else if(orden.equals(ArbolLista.PREORDEN))
        {
            values = this.preordenMejorado(ArbolLista.TYPE_VALUE);
        }
        else if(orden.equals(ArbolLista.POSORDEN))
        {
            values = this.posordenMejorado(ArbolLista.TYPE_VALUE);
        }
        else
        {
            values = this.inordenMejorado(ArbolLista.TYPE_VALUE);
        }
        set.addAll(Arrays.asList(values));
        return set;
    }

    public Set<Entry<K, V>> entrySet()
    {
        co.edu.udea.logic.truthtabletree.Set set = new co.edu.udea.logic.truthtabletree.Set();
        Object entries[] = new Object[0];
        if(orden.equals(ArbolLista.INORDEN))
        {
            entries = this.inordenMejorado(ArbolLista.TYPE_NODE);
        }
        else if(orden.equals(ArbolLista.PREORDEN))
        {
            entries = this.preordenMejorado(ArbolLista.TYPE_NODE);
        }
        else if(orden.equals(ArbolLista.POSORDEN))
        {
            entries = this.posordenMejorado(ArbolLista.TYPE_NODE);
        }
        else
        {
            entries = this.inordenMejorado(ArbolLista.TYPE_NODE);
        }
        set.addAll(Arrays.asList(entries));
        return set;
    }
}
