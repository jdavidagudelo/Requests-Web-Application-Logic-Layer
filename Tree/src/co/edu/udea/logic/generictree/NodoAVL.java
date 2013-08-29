/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

/**
 *
 * @author Daniel F Agudelo
 */
public class NodoAVL
{
    private NodoAVL li = null, ld = null, lp = null;
    private Object dato = null;
    private Integer fb = 0;
    public NodoAVL()
    {
    }
    public void setLp(NodoAVL lp) {
        this.lp = lp;
    }


    public NodoAVL getLp() {
        return lp;
    }

    public NodoAVL(Object dato)
    {
        this.dato = dato;
    }
    public Object getDato() {
        return dato;
    }

    public Integer getFb() {
        return fb;
    }

    public NodoAVL getLd() {
        return ld;
    }

    public NodoAVL getLi() {
        return li;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public void setFb(Integer fb) {
        this.fb = fb;
    }

    public void setLd(NodoAVL ld) {
        this.ld = ld;
    }

    public void setLi(NodoAVL li) {
        this.li = li;
    }
}
