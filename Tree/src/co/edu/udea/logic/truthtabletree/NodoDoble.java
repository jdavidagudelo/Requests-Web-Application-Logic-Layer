/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;
import java.io.Serializable;
import java.util.Map.Entry;
public class NodoDoble<K, V>  implements Serializable, Entry<K, V>
{
    /**
     * Objeto generico que contiene el valor almacenado en este nodo
     */
    private V value = null;
    /**
     * Nodo que representa el hijo izquierdo de este nodo en el árbol binario que lo contiene o la hebra
     * izquierda del nodo en caso de que este no tenga hijo izquierdo, su campo bi sea false y el árbol
     * que lo contiene este enhebrado.
     */
    private NodoDoble li = null;
    /**
     * Nodo que representa el hijo derecho de este nodo en el árbol binario que lo contiene o la hebra derecha
     * de este nodo en caso de que el árbol no tenga hijo derecho, su campi de bd sea false y el árbol que lo
     * contiene este enhebrado.
     */
    private NodoDoble ld = null;
    /**
     * Nodo que representa el padre de este nodo en el árbol binario que lo contiene, si es la raiz retorna null.
     */
    private NodoDoble lp = null;
    /**
     * Si este valor es false el nodo puede tener hebra derecha, en caso contrario debe tener un hijo derecho o
     * no tener hebra derecha.
     */
    private boolean bd = true;
    /**
     * Si este valor es false el nodo puede tener hebra izquierda, en caso contrario debe tener hijo izquierdo o
     * no tener hebra izquierda.
     */
    private boolean bi = true;
    /**
     * Objeto generico que contiene la clave que permitira acceder a este nodo. Esta se utiliza para casos en los cuales
     * acceder al valor almacenado en el nodo y compararlo con otro podria no ser eficiente.
     */
    private K key = null;
    /**
     * Este valor representa el factor de balance de este nodo, este dato es utilizado para realizar el balanceo del árbol
     * cada vez que su valor sea +2 o -2 en caso de que el árbol que contenga el nodo sea AVL.
     */
    private Integer fb = 0;
    /**
     * Constructor sin argumentos
     */
    public NodoDoble()
    {
    }
    /**
     * Método que permite establecer la clave de este nodo.
     * @param key La nueva clave de este nodo
     */
    public void setKey(Object key)
    {
        this.key = (K)key;
    }
    /**
     * Método que permite obtener la clave de este nodo.
     * @return la clave de este nodo
     */
    public K getKey()
    {
        return key;
    }
    /**
     * Método que permite establecer el padre de este nodo
     * @param lp el nuevo padre de este nodo en el árbol que lo contiene
     */
    public void setLp(NodoDoble lp)
    {
        this.lp = lp;
    }
    /**
     * Método que permite obtener el padre de este nodo
     * @return el padre de este nodo
     */
    public NodoDoble getLp()
    {
        return lp;
    }
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NodoDoble(NodoDoble otro)
    {
        if(otro != null)
        {
            this.bd = otro.bd;
            this.bi = otro.bi;
            this.fb = otro.fb;
            if(otro.value != null && otro.value instanceof String)
            {
                this.setValue(((String)otro.value).toString());
            }
            else if(otro.value != null && otro.value instanceof Integer)
            {
                this.setValue(new Integer((Integer)otro.value));
            }
            else if(otro.value != null && otro.value instanceof Clonable)
            {
                this.setValue(((Clonable)otro.value).clonar());
            }
            else
            {
                this.setValue(otro.value);
            }
            if(otro.key != null && otro.key instanceof String)
            {
                this.setKey(((String)otro.key).toString());
            }
            else if(otro.key != null && otro.key instanceof Integer)
            {
                this.setKey(new Integer((Integer)otro.key));
            }
            else if(otro.key != null && otro.key instanceof Clonable)
            {
                this.setKey(((Clonable)otro.key).clonar());
            }
            else
            {
                this.setKey(otro.key);
            }
            if(otro.isBd())
            {
                this.setLd(new NodoDoble(otro.ld));
            }
            if(otro.isBi())
            {
                this.setLi(new NodoDoble(otro.li));
            }
        }
    }
    /***/
    public NodoDoble(NodoGrafico value)
    {
        this.value = (V)value;
        bd = true;
        bi = true;
        if(value != null)
        {
            value.setNodoDoble(this);
            key = (K)value.getDato();
            value.setNodoDoble(this);
        }
    }
    public NodoDoble(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
    public NodoDoble(Object value)
    {
        this.value = (V)value;
        bd = true;
        bi = true;
        if(this.value != null && this.value instanceof NodoGrafico)
        {
            NodoGrafico date = (NodoGrafico)value;
            this.key = (K)date.getDato();
            date.setNodoDoble(this);
        }
        else if(this.value != null && this.value instanceof Variable)
        {
            Variable date = (Variable)value;
            this.key = (K)date.getId();
        }
        else if(this.value != null && this.value instanceof Symbol)
        {
            Symbol s = (Symbol)this.value;
            this.key = (K)s.getName();
        }
        else
        {
            this.key = (K)this.value;
        }
    }
    public void setFb(Integer fb)
    {
        this.fb = fb;
    }
    public Integer getFb()
    {
        return fb;
    }
    public void setBi(boolean bi)
    {
        this.bi = bi;
    }
    public void setBd(boolean bd)
    {
        this.bd = bd;
    }
    /**
     * Método que indica si este nodo tiene hebra izquierda.
     * @return retirna el valor de bi se la liga izquierda de este nodo no es null, en caso contrario retorna false
     */
    public boolean isBi()
    {
        return li == null? false : bi;
    }
    /**
     * Método que indica si este nodo tiene hebra derecha.
     * @return retirna el valor de bd se la liga derecha de este nodo no es null, en caso contrario retorna false
     */
    public boolean isBd()
    {
        return ld == null? false : bd;
    }
    public V getValue()
    {
        return value;
    }
    public NodoDoble getLd()
    {
        return ld;
    }
    public NodoDoble getLi()
    {
        return li;
    }
    public V setValue(Object value)
    {
        if(value instanceof NodoGrafico)
        {
            NodoGrafico grafico = (NodoGrafico)value;
            grafico.setNodoDoble(this);
        }
        this.value = (V)value;
        return (V)value;
      }
    /**
     * Método que permite establecer la liga derecha de este nodo, además establece este nodo como
     * el padre del nodo ingresado como parametro en caso de que sea diferente de null y que este nodo no pueda
     * tener hebra derecha.
     * @param li la nueva liga derecha de este nodo
     */
    public void setLd(NodoDoble ld)
    {
        if(ld != null && this.bd)
        {
            ld.setLp(this);
        }
        this.ld = ld;
    }
    /**
     * Método que permite establecer la liga izquierda de este nodo, además establece este nodo como
     * el padre del nodo ingresado como parametro en caso de que sea diferente de null y que este nodo no
     * pueda tener hebra izquierda.
     * @param li la nueva liga izquierda de este nodo
     */
    public void setLi(NodoDoble li)
    {
        if(li != null && this.bi)
        {
            li.setLp(this);
        }
        this.li = li;
    }
    @Override
    public String toString()
    {
        return value != null? value.toString() : null;
    }
}
