/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

/**
 *
 * @author Daniel F Agudelo
 */
public class NodoB
{
    private int m = 0, n = 0;
    private Comparable datos[] = null;
    private NodoB lp = null;
    private int hijos[] = null;
    public NodoB()
    {
    }
    public NodoB(int n)
    {
        this.n = n;
        datos = new Comparable[n - 1];
        hijos = new int[n];
    }
    public void setLp(NodoB lp)
    {
        this.lp = lp;
    }
    public NodoB getLp()
    {
        return lp;
    }
    public void setN(int n)
    {
        this.n = n;
    }
    public int getN()
    {
        return n;
    }
    public Comparable[] getDatos()
    {
        return datos;
    }
    public int[] getHijos()
    {
        return hijos;
    }
    public void setDatos(Comparable[] datos)
    {
        this.datos = datos;
    }
    public void mostrar()
    {
        for(int i = 0; i < datos.length; i++)
        {
            System.out.print(datos[i]+",");
        }
        System.out.println();
    }
    public int getM()
    {
        return m;
    }
    public void setHijos(int[] hijos)
    {
        this.hijos = hijos;
    }
    public void setM(int m)
    {
        this.m = m;
    }
}
