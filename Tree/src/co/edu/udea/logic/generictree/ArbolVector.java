/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

import java.util.ArrayList;

/**
 *
 * @author Daniel F Agudelo
 */
public class ArbolVector
{
    private Object vector[] = null;
    private Integer n = 0;
    public void setVector(Object[] vector)
    {
        this.vector = vector;
    }
    public Object[] getVector()
    {
        return vector;
    }
    public ArbolVector()
    {
        n = 6;
        vector = new Object[64];
    }
    public ArbolVector(int n)
    {
        this.n = n;
        vector = new Object[(int)(Math.pow(2, n)) - 1];
        for(int i = 0; i < vector.length; i++)
        {
            vector[i] = null;
        }
    }
    public void menor(int i)
    {
        int j = 2*i + 2;
        int k = 2*j + 1;
        while(vector[k] != null)
        {
            k = 2*k + 1;
        }
        vector[i] = vector[k];
        this.eliminar(k);
    }
    public void eliminar(int i)
    {
        int j = 2*i + 1;
        int k = 2*i + 2;
        if(vector[j] == null)
        {
            if(vector[k] == null)
            {
                vector[i] = null;
            }
            else
            {
                vector[i] = vector[k];System.out.println(vector[k]);
                int ip = 2*i + 2;
                k = 2*ip + 2;
                j = 2*ip + 1;
                while(ip < vector.length && j < vector.length && k < vector.length && vector[ip] != null)
                {
                    vector[ip] = vector[j];
                    ip = 2*ip + 1;
                    j = 2*ip + 1;
                }
            }
        }
        else
        {
            if(vector[k] == null)
            {
                vector[i] = vector[j];
                int ip = 2*i + 1;
                k = 2*ip + 2;
                j = 2*ip + 1;
                while(ip < vector.length && j < vector.length && k < vector.length && (vector[j] != null | vector[k] != null))
                {
                    vector[ip] = vector[j];
                    vector[ip + 1] = vector[k];
                    ip = j;
                    k = 2*ip + 2;
                    j = 2*ip + 1;
                }
            }
            else
            {
                this.menor(i);
            }
        }
    }
    public void borrar(Object dato)
    {

        int i = 0;
        if(vector[i] == null)
        {
            return;
        }
        while(i < vector.length && vector[i] != null)
        {
            if(dato instanceof Comparable & vector[i] instanceof Comparable & dato.getClass() == vector[i].getClass())
            {
                Comparable z = (Comparable)dato;
                Comparable w = (Comparable)vector[i];
                if(z.compareTo(w) < 0)
                {
                    i = 2*i + 1;
                }
                else
                {
                    if(z.compareTo(w) > 0)
                    {
                        i = 2*i + 2;
                    }
                    else
                    {
                        this.eliminar(i);
                    }

                }
            }
            else
            {
                return;
            }
        }
    }
    public int hojas(int i)
    {
        int hojas = 0;
        int j = 2*i + 1;
        int k = 2*i + 2;
        if(vector[j] != null)
        {
            if(vector[k] != null)
            {
                hojas = hojas + hojas(j) + hojas(k);
            }
            else
            {
                hojas = hojas + hojas(j);
            }
        }
        else
        {
            if(vector[k] != null)
            {
                hojas = hojas + hojas(k);
            }
            else
            {
                hojas = hojas + 1;
            }
        }
        return hojas;
    }
    public int altura(int i)
    {
        if(vector[i] == null)
        {
            return 0;
        }
        int h = 1;
        int hMax = 1;
        int j = 2*i + 1;
        if(vector[j] != null)
        {
            h = 1 + altura(j);
        }
        if(h > hMax)
        {
            hMax = h;
        }
        j = 2*i + 2;
        if(vector[j] != null)
        {
            h = 1 + altura(j);
        }
        if(h > hMax)
        {
            hMax = h;
        }
        return hMax;
    }
    public void insertar(Object dato)
    {
        int i = 0;
        if(vector[i] == null)
        {
            vector[i] = dato;
            return;
        }
        while(i < vector.length && vector[i] != null)
        {
            if(dato instanceof Comparable & vector[i] instanceof Comparable & dato.getClass() == vector[i].getClass())
            {
                Comparable z = (Comparable)dato;
                Comparable w = (Comparable)vector[i];
                if(z.compareTo(w) < 0)
                {
                    i = 2*i + 1;
                    if(i < vector.length && vector[i] == null)
                    {
                        vector[i] = dato;
                        return;
                    }
                }
                else
                {
                    if(z.compareTo(w) > 0)
                    {
                        i = 2*i + 2;
                        if(i < vector.length && vector[i] == null)
                        {
                            vector[i] = dato;
                            return;
                        }
                    }
                    else
                    {
                        return;
                    }

                }
            }
            else
            {
                return;
            }
        }
    }
    public void inorden(int i)
    {
        if(i < (int)(Math.pow(2, n)) - 1 && vector[i] != null)
        {
            inorden(2*i + 1);
            System.out.print(vector[i]+",");
            inorden(2*i + 2);
        }
    }
    public void posorden(int i)
    {
        if(i < (int)(Math.pow(2, n)) - 1 && vector[i] != null)
        {
            posorden(2*i+1);
            posorden(2*i+2);
            System.out.print(vector[i]+",");
        }
    }
    public void preorden(int i)
    {
        if(i < (int)(Math.pow(2, n)) - 1 && vector[i] != null)
        {
            System.out.print(vector[i]+",");
            preorden(2*i+1);
            preorden(2*i+2);
        }
    }
}
