/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Daniel F Agudelo
 */
public class Cola implements Conjunto
{
    private NodoSimple primero = null, ultimo = null;
    public Cola()
    {
    }
    public NodoSimple getPrimero()
    {
        return primero;
    }
    public NodoSimple getUltimo()
    {
        return ultimo;
    }
    public void setPrimero(NodoSimple primero)
    {
        this.primero = primero;
    }
    public void setUltimo(NodoSimple ultimo)
    {
        this.ultimo = ultimo;
    }
    public boolean isEmpty()
    {
        return primero == null;
    }
    public void encolar(Object dato)
    {
        NodoSimple x = new NodoSimple(dato);
        if(isEmpty())
        {
            primero = ultimo = x;
        }
        else
        {
            ultimo.setSig(x);
            ultimo = x;
        }
    }
    public Object desencolar()
    {
        Object d = null;
        if(!isEmpty())
        {
            d = primero.getDato();
            primero = primero.getSig();
        }
        return d;
    }
    public Object peek()
    {
        return primero.getDato();
    }

    public Conjunto interseccion(Conjunto b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Conjunto union(Conjunto b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Conjunto complemento(Conjunto b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean pertenece(Object d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSubconjunto(Conjunto b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean add(Object e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
