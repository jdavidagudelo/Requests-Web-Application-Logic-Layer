/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package co.edu.udea.languages.automaton;

import java.io.Serializable;
/**
 * Clase que permite representar un conjunto de caracteres que será utilizado para reconocer
 * un conjunto regular. Un lexema se define como un String por ejemplo: 
 * digitos: "0123456789", es un lexema que contiene los digitos de 0-9.
 * letras: "abcdefghijklmnopqrstuvwxyz".
 */
public class Lexema implements Serializable
{
    /**
     * Conjunto de caracteres que están contenidos en este lexema.
     */
    private String simbolos;
    public Lexema() 
    {
    }
    @Override
    public String toString()
    {
        return simbolos;
    }
    /**
     * Se crea un nuevo lexema a partir del String ingresado como argumento.
     * @param simbolos String que contiene los carácteres que se incluyen dentro dñ lexema.
     */
    public Lexema(String simbolos) 
    {
        this.simbolos = simbolos;
    }
    /**
     * Método que permite evaluar si este lexema contiene el carácter ingresado como arguemnto.
     * @param c carácter que se desea evaluar si está incluido dentro de este lexema.
     * @return true si este lexema contiene el caracter ingresado como argumento, false en caso contrario.
     */
    public boolean contains(Character c)
    {
        return simbolos.indexOf(c) >= 0;
    }
    /**
     * Método que permite obtener el String que representa este Lexema.
     * @return el String que representa este Lexema.
     */
    public String getSimbolos() {
        return simbolos;
    }
    /**
     * Método utilizado para evaluar si un determinado String no contiene caracteres repetidos.
     * @param s el String que se desea validar
     * @return true si el String es valido, false en caso contrario
     */
    public static boolean isValid(String s)
    {
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(s.substring(i+1).indexOf(c) >= 0)
            {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Lexema)
        {
            Lexema l = (Lexema)o;
            return l.simbolos.equals(this.simbolos);
        }
        return false;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.simbolos != null ? this.simbolos.hashCode() : 0);
        return hash;
    }
    public void setSimbolos(String simbolos) {
        this.simbolos = simbolos;
    }
    
}
