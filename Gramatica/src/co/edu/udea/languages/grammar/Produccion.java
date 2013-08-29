/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package co.edu.udea.languages.grammar;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * Clase utilizada para representar una producción de una gramática como un lado izquierdo y un lado derecho.
 */
public class Produccion 
{
    /**
     * El lado derecho de la producción.
     */
    private Lado ladoDerecho = new Lado();
    /**
     * El lado izquierdo de la producción.
     */
    private Lado ladoIzquierdo = new Lado();
    /**
     * Método que permite obtener el no terminal del lado izquierdo de esta producción.
     * @return el no terminal del lado izquierdo de esta producción.
     */
    public NoTerminal getNoTerminalIzquierdo()
    {
        NoTerminal nt = null;
        Iterator it = ladoIzquierdo.getElementos().iterator();
        if(it.hasNext())
        {
            nt = (NoTerminal)it.next();
        }
        return nt;
    }
    /**
     * Crea una producción a partir de un String ingresado comon argumento.
     * @param produccion String que contiene el un producción.
     */
    public Produccion(String produccion)
    {
        //se crea el lado derecho de la producción
        this.ladoDerecho = Produccion.ladoDerecho(produccion);
        //se crea el lado izquierdo de la producción
        this.ladoIzquierdo = Produccion.ladoIzquierdo(produccion);
    }
    public Produccion() 
    {
    }

    /**
     * Método que permite obtener el lado izquierdo de una producción.
     * @param produccion producción que se desea procesar.
     * @return el lado izquierdo de la producción ingresada como argumento
     */
    public static Lado ladoIzquierdo(String produccion)
    {   
        int inicioProduccion = produccion.indexOf("<") + 1;
        String s = produccion.substring(inicioProduccion, produccion.indexOf(">"));
        ArrayList li = new ArrayList();
        NoTerminal nt = new NoTerminal(s);
        li.add(nt);
        return new Lado(li);
    }
    /**
     * Método que permite establecer si esta producción corresponde a una gramática lineal por la derecha.
     * @return true si la producción corresponde a un gramática lineal por la derecha, false en caso contrario
     */
    public boolean isLinealDerecha()
    {
        return ladoDerecho.isLinealDerecha();
    }
    /**
     * @param s el String que se desea validar
     * @return true si el String ingresado es una variable valida, false en caso contrario
     */
    private static boolean valido(String s)
    {
        if(s.isEmpty())
        {
            return false;
        }
        int i = 0;
        while(i < s.length())
        {
            if(!Character.isLetterOrDigit(s.charAt(i)))
            {
                return false;
            }
            i++;
        }
        return true;
    }
    /**
     * Método que permite obtener el lado derecho de una producción.
     * @param produccion producción que se desea procesar.
     * @return el lado derecho de la producción ingresada como argumento
     */
    public static Lado ladoDerecho(String produccion)
    {
        String s = produccion.substring(produccion.indexOf("=")+1);
        int i = 0;
        int j = 0;
        ArrayList ld = new ArrayList();
        if(s.isEmpty())
        {
            ld.add(new Terminal(""));
        }
        while(i < s.length())
        {
            if(s.charAt(i) == '<')
            {
                j = s.indexOf(">", i+1);
                if(j < 0)
                {
                    j = i + 1;
                    String actual1 = s.substring(i, j);
                    Terminal t = new Terminal(actual1);
                    ld.add(t);
                    i = j;
                }
                else
                {
                    String actual1 = s.substring(i+1, j);
                    if(valido(actual1))
                    {
                        NoTerminal nt = new NoTerminal(actual1);
                        ld.add(nt);
                        i = j + 1;
                    }
                    else
                    {
                        j = i + 1;
                        actual1 = s.substring(i, j);
                        Terminal t = new Terminal(actual1);
                        ld.add(t);
                        i = j;
                    }
                }
            }
            else
            {
                j = i + 1;
                String actual1 = s.substring(i, j);
                Terminal t = new Terminal(actual1);
                ld.add(t);
                i = j;
            }
        } 
        return new Lado(ld);
    }
    public Produccion(Lado ladoDerecho, Lado ladoIzquierdo) {
        this.ladoDerecho = ladoDerecho;
        this.ladoIzquierdo = ladoIzquierdo;
    }

    public Lado getLadoDerecho() 
    {
        return ladoDerecho;
    }

    public Lado getLadoIzquierdo() {
        return ladoIzquierdo;
    }

    public void setLadoDerecho(Lado ladoDerecho) {
        this.ladoDerecho = ladoDerecho;
    }

    public void setLadoIzquierdo(Lado ladoIzquierdo) {
        this.ladoIzquierdo = ladoIzquierdo;
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("");
        sb.append(ladoIzquierdo.toString());
        sb.append("::=");
        sb.append(ladoDerecho.toString());
        return sb.toString();
    }
}
