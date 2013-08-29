/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

/**
 *
 * @author jdavid.agudelo
 */
public class NodoLg
{
    private Object dato = null;
    private NodoLg liga = null;
    private boolean sw = false;


    public NodoLg()
    {
        dato = null;
        liga = null;
        sw = false;
    }
    public NodoLg(Object dato)
    {
        liga = null;
        sw = false;
        if(dato instanceof NodoGrafico)
        {
            NodoGrafico o = (NodoGrafico)dato;
            o.setNodoLg(this);
        }
        this.dato = dato;
    }

    public Object getDato() {
        return dato;
    }

    public NodoLg getLiga() {
        return liga;
    }

    public boolean isSw() {
        return sw;
    }

    public void setDato(Object dato) 
    {
        if(dato instanceof NodoGrafico)
        {
            NodoGrafico o = (NodoGrafico)dato;
            o.setNodoLg(this);
        }
        this.dato = dato;
    }

    public void setLiga(NodoLg liga) 
    {
        this.liga = liga;
    }

    public void setSw(boolean sw) {
        this.sw = sw;
    }
}
