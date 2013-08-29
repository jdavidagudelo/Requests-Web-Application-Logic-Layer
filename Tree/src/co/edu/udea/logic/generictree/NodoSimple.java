package co.edu.udea.logic.generictree;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel F Agudelo
 */
public class NodoSimple
{
    private NodoSimple sig = null;
    private Object dato = null;
    public NodoSimple()
    {
    }
    public NodoSimple(Object dato)
    {
        this.dato = dato;
    }
    public void setDato(Object dato)
    {
        this.dato = dato;
    }
    public void setSig(NodoSimple sig)
    {
        this.sig = sig;
    }
    public NodoSimple getSig()
    {
        return sig;
    }
    public Object getDato()
    {
        return dato;
    }
}

