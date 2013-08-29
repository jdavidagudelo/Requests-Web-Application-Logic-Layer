/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

import java.util.Collection;
import java.util.Iterator;

public class Set implements Conjunto
{
    private NodoDoble head = null;
    public static final Set EMPTY = new Set();
    public Set()
    {
        head = new NodoDoble(null);
        head.setLd(head);
        head.setLi(head);
    }
    public NodoDoble getPrimero()
    {
        return head.getLd();
    }
    public void setHead(NodoDoble head)
    {
        this.head = head;
    }
    public NodoDoble getHead()
    {
        return head;
    }
    public void insertar(Object d)
    {
        if(d == null)
        {
            return;
        }
        NodoDoble nuevo = new NodoDoble(d);
        NodoDoble x = this.getPrimero();
        while(x != head)
        {
            if(x.getValue().equals(d))
            {
                return;
            }
            x = x.getLd();
        }
        x = this.getHead();
        nuevo.setLd(x.getLd());
        x.getLd().setLi(nuevo);
        nuevo.setLi(x);
        x.setLd(nuevo);
    }
    public Conjunto interseccion(Conjunto b)
    {
        Set nuevo = Set.EMPTY;
        if(b instanceof Set)
        {
            NodoDoble x = this.getPrimero();
            while(x != this.getHead())
            {
                if(b.pertenece(x.getValue()))
                {
                    nuevo.insertar(x.getValue());
                }
            }
        }
        return nuevo;
    }

    public Set union(Conjunto b)
    {
        Set nuevo = Set.EMPTY;
        if(b instanceof Set)
        {
            NodoDoble x = this.getPrimero();
            while(x != this.getHead())
            {
                nuevo.insertar(x.getValue());
            }
            x = ((Set)b).getHead().getLd();
            while(x != ((Set)b).getHead())
            {
                nuevo.insertar(x.getValue());
            }
        }
        return nuevo;
    }

    public Conjunto complemento(Conjunto b)
    {

        return null;
    }

    public boolean pertenece(Object d)
    {
        NodoDoble x = this.getPrimero();
        while(x != this.getHead())
        {
            if(x.getValue().equals(d))
            {
                return true;
            }
            x = x.getLd();
        }
        return false;
    }

    public boolean isSubconjunto(Conjunto b)
    {
        if(b instanceof Set)
        {
            Set cb = (Set)b;
            NodoDoble x = cb.getPrimero();
            while(x != cb.getHead())
            {
                if(!this.pertenece(x.getValue()))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean add(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
