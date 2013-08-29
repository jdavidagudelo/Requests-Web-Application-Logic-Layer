/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

/**
 *
 * @author Daniel F Agudelo
 */
public class ArbolB
{
    private NodoB arbol[] = null;
    private NodoB raiz = null;
    private int n = 0;
    public void setRaiz(NodoB raiz)
    {
        this.raiz = raiz;
    }
    public NodoB getRaiz()
    {
        return raiz;
    }
    public ArbolB()
    {
    }
    public ArbolB(int n, int size)
    {
        arbol = new NodoB[size];
        this.n = n;
    }
    public int getN()
    {
        return n;
    }
    public void setN(int n)
    {
        this.n = n;
    }
    public NodoB[] getArbol()
    {
        return arbol;
    }
    public void setArbol(NodoB[] arbol)
    {
        this.arbol = arbol;
    }
    public void mostrar()
    {
        NodoB x = raiz;
        if(x != null)
        {
            x.mostrar();

        }
    }
    public void insertar(Object dato)
    {
        if(dato instanceof Comparable)
        {
            if(raiz == null)
            {
                raiz = new NodoB(n);
                raiz.getDatos()[0] = (Comparable)dato;
                raiz.setM(1);
            }
            else
            {
                NodoB x = raiz;
                int i = 0;
                if(x != null)
                {
                    if(x.getHijos()[0] != 0)
                    {
                        while(i < x.getM() && x.getDatos() != null && x.getDatos()[i].compareTo(dato) > 0)
                        {
                            i = i + 1;
                        }
                    }
                    else
                    {
                        if(x.getM()  < x.getN())
                        {
                            i = 0;
                            while(i < x.getM() && x.getDatos()[i].compareTo(dato) < 0)
                            {
                                i = i + 1;
                            }
                            if(x.getDatos()[i] != null)
                            {
                                int j = x.getDatos().length - 1;
                                while(j > i)
                                {
                                    x.getDatos()[j] = x.getDatos()[j - 1];
                                    j = j - 1;
                                }
                                x.getDatos()[i] = (Comparable)dato;
                            }
                            else
                            {
                                x.getDatos()[i] = (Comparable)dato;
                                x.setM(x.getM() + 1);
                            }
                        }
                    }

                }
            }
        }
    }
}
