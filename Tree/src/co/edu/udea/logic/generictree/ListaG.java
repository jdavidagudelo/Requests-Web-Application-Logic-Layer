/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;
import java.util.*;
public class ListaG
{
    private NodoLg primero, ultimo;
    private int j;
    public ListaG()
    {
        primero = ultimo = null;
    }
    @Override
    public String toString()
    {
        String toString = "("+toString(primero)+")";
        return toString;
    }
    private String toString(NodoLg x)
    {
        StringBuffer actual = new StringBuffer("");
        if(x.isSw())
        {
            actual.append("(");
            actual.append(toString((NodoLg)x.getDato()));
            actual.append(")");
        }
        else
        {
            actual.append(x.getDato());
        }
        if(x.getLiga() != null)
        {
            if(!x.getLiga().isSw())
            {
                actual.append(",");
            }
            actual.append(toString(x.getLiga()));
        }
        return actual.toString();
    }
    public void mostrar(NodoLg x)
    {
        if(x.isSw())
        {
            System.out.print("(");
            mostrar((NodoLg)x.getDato());
            System.out.print(")");
            if(x.getLiga() != null)
            {
                System.out.print(",");
                mostrar(x.getLiga());
            }
        }
        else
        {
            System.out.print(x.getDato());
            if(x.getLiga() != null)
            {
                System.out.print(",");
                mostrar(x.getLiga());
            }
        }
    }
    public NodoLg getUltimo()
    {
        return ultimo;
    }
    public NodoLg getPrimero()
    {
        return primero;
    }
    public NodoLg consigLg(String s)
    {
        NodoLg x = new NodoLg();
        if(j < s.length() - 1)
        {
            if(s.charAt(j) == '(')
            {
                x.setSw(true);
                j = j + 1;
                x.setDato(consigLg(s));
            }
            else
            {
                x.setSw(false);
                x.setDato(s.charAt(j));
                j = j + 1;
            }
            if(j < s.length() - 1 && s.charAt(j) == ',')
            {
                j = j + 1;
                x.setLiga(consigLg(s));
            }
            else
            {
                x.setLiga(null);
                ultimo = x;
                j = j + 1;
            }
        }
        return x;
    }
    public void crear(String s)
    {
        j = 1;
        primero = consigLg(s);
    }
    public void construirLg(String s)
    {
        Stack pila = new Stack();
        int n = s.length();
        if(n < 3)
        {
            return;
        }
        char actual;
        NodoLg x = new NodoLg();
        primero = x;
        ultimo = x;
        for(int i = 1; i < n - 1; i++)
        {
            actual = s.charAt(i);
            if(Character.isLetterOrDigit(actual))
            {
                ultimo.setDato(actual);
            }
            if(actual == ',')
            {
                x = new NodoLg();
                ultimo.setLiga(x);
                ultimo = x;
            }
            if(actual == '(')
            {
                pila.push(ultimo);
                x = new NodoLg();
                ultimo.setSw(true);
                ultimo.setDato(x);
                ultimo = x;
            }
            if(actual == ')')
            {
                ultimo = (NodoLg)pila.pop();
            }
        }
    }
}
