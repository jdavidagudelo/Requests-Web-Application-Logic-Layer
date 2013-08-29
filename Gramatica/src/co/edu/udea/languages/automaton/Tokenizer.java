/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.languages.automaton;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Esta clase permite dividir en tokens una expresión, además permite conocer la
 * ubicación exacta de dichos tokens en la expresión.
 */
public class Tokenizer implements Serializable
{
    /**
     * Contiene el String que se desea partir utilizando este Tokenizer.
     */
    private StringBuilder code = null;
    /**
     * Indice en el cual se encuentra el recorrido del String
     */
    private int index = 0;
    private int line = 1, column = 1;
    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setLine(int line) {
        this.line = line;
    }
    /**
     * Método que permite obtener el siguiente token valido teniendo en cuenta el 
     * ArrayList de separadores especificado.
     * @param separadores ArrayList en el que se incluyen los simbolos que serán utilizados para
     * dividir los tokens del String.
     * @return el próximo token del String que se encuentra en el intervalo deseado.
     */
    public Symbol next(ArrayList separadores)
    {
        int i = code.length();
        int j = index;
        int ind = 0;
        String si = null;
        //se halla el indice del separador más próximo, que será el punto a partir del cual
        //se partira el String
        for(int k = 0; k < separadores.size(); k++)
        {
            String s = (String)separadores.get(k);
            ind = code.indexOf(s, j);
            if(ind >= 0 && ind < i)
            {
                i = ind;
                si = s;
            }
        }
        //si entre el indice inicial y indice del separador más cercano habían caracteres,
        //se ha hallado el siguiente token, en caso contrario se debe recorrer el String hasta 
        //encontrarlo
        String next = code.substring(j, i);
        while(j == i && si != null && i < code.length())
        {
            //se elimina el separador actual del String de salida
            j = i + si.length();
            i = code.length();
            //se hala el siguiente separador más próximo
            for(int k = 0; k < separadores.size(); k++)
            {
                String s = (String)separadores.get(k);
                ind = code.indexOf(s, j);
                if(ind >= 0 && ind < i)
                {
                    i = ind;
                    si = s;
                }
            }
            //se obtiene el siguiente token, si es vacio se continua con el ciclo
            next = code.substring(j, i);
        }
        //si no hay separadores en el String se retorna todo hasta el final
        if(si == null)
        {
            next = code.substring(j, code.length());
            i = code.length();
        }
        //si el siguiente es la secuencia nula esto se debe indicar
        if(next.isEmpty())
        {
            return null;
        }
        //se avanza em lineas y columnas hasta el indice inicial del siguiente token
        for(int k = index; k < j; k++)
        {
            char c = code.charAt(k);
            if(c == '\n')
            {
                column = 1;
                line = line + 1;
            }
            else
            {
                if(c == '\t')
                {
                    column = column + 8;
                }
                else
                {
                    column = column + 1;
                }
            }
        }
        int col = column;
        int l = line;
        //se avanza hasta el final del token
        for(int k = j; k < i; k++)
        {
            char c = code.charAt(k);
            if(c == '\n')
            {
                column = 1;
                line = line + 1;
            }
            else
            {
                if(c == '\t')
                {
                    column = column + 8;
                }
                else
                {
                    column = column + 1;
                }
            }
        }
        //el indice se establece en el fin del token actual para continuar en la próxima llamada
        //al método
        index = i;
        return new Symbol(next,l, col);
    }

    @Override
    public String toString() {
        return code.toString();
    }

    public int getColumn() {
        return column;
    }
    public void setColumn(int column)
    {
        this.column = column;
    }
    public int getLine()
    {
        return line;
    }
    public int getIndex()
    {
        return index;
    }
    public Tokenizer(StringBuilder code)
    {
        this.code = code;
    }
    public Tokenizer(String code)
    {
        this.code = new StringBuilder(code);
    }
    public Tokenizer()
    {
    }
    public StringBuilder getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = new StringBuilder(code);
    }
    public void setCode(StringBuilder code)
    {
        this.code = code;
    }
}
