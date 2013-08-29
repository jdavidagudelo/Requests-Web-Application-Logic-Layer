/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;
/**
 *
 * @author Daniel F Agudelo
 */
public class NodoDoble
{
    private NodoDoble li = null, ld = null, lp = null;
    private boolean bd = true, bi = true;
    private Object value = null;
    private Object key = null;
    private Integer fb = 0;
    public NodoDoble()
    {
    }
    public void setKey(Object key)
    {
        this.key = key;
    }
    public Object getKey()
    {
        return key;
    }
    public void setLp(NodoDoble lp)
    {
        this.lp = lp;
    }
    public NodoDoble getLp()
    {
        return lp;
    }
    public NodoDoble(NodoGrafico value)
    {
        this.value = value;
        bd = true;
        bi = true;
        if(value != null)
        {
            value.setNodoDoble(this);
            key = value.getDato();
        }
    }
    public NodoDoble(Object value)
    {
        this.value = value;
        bd = true;
        bi = true;
        if(this.value != null && this.value instanceof NodoGrafico)
        {
            NodoGrafico date = (NodoGrafico)value;
            key = date.getDato();
            date.setNodoDoble(this);
        }
        else
        {
            this.key = value;
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
    public boolean isBi()
    {
        return li == null? false : bi;
    }
    public boolean isBd()
    {
        return ld == null? false : bd;
    }
    public Object getValue()
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
    public void setValue(Object value)
    {
        if(value instanceof NodoGrafico)
        {
            NodoGrafico grafico = (NodoGrafico)value;
            grafico.setNodoDoble(this);
        }
        this.value = value;
    }
    public void setLd(NodoDoble ld)
    {
        if(ld != null && this.bd)
        {
            ld.setLp(this);
        }
        this.ld = ld;
    }
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
