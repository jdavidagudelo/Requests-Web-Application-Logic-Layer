/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;

import java.io.Serializable;
import java.util.Stack;
public class Tokenizer implements Serializable
{
    private StringBuilder code = null;
    private boolean unary = true;
    private int index = 0;
    private int line = 1, column = 1;
    public void setIndex(int index)
    {
        this.index = index;
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
    public Symbol nextSymbol() throws Exception
    {
        //se incializa una variable en el indice indicado
        int j = index;
        while(j < code.length() && (Character.isWhitespace(code.charAt(j))))
        {
            if(code.charAt(j) == '\t')
            {
                column = column + 8;
            }
            else
            {
                if(code.charAt(j) == '\n')
                {
                    line = line + 1;
                    column = 1;
                }
                else
                {
                    column = column + 1;
                }
            }
            j = j + 1;
        }
        int li = line, ci = column;
        int i = j;
        while(i < code.length() && Character.isLetterOrDigit(code.charAt(i)))
        {
            column = column + 1;
            i = i + 1;
        }
        //si no logro incrementar el indice, significa que el valor hallado es un operador
        if(i == j)
        {
            column = column + 1;
            i = i + 1;
            int num = 0;
            if(i > 0 && code.charAt(i - 1) == '!')
            {
                if(unary)
                {
                    num = 1;
                }
                else
                {
                    throw new Exception("El operador \"!\" en la linea: "+li+", columna: "+ci+
                            " No puede ser utilizado como operador binario");
                }
            }
            if(unary & code.charAt(i - 1) !='(' & code.charAt(i - 1) != ')')
            {
                num = 1;
            }
            if(code.charAt(i - 1) != ')')
            {
                unary = true;
            }
            if(i < code.length() && i > 0 && (((code.charAt(i - 1) == '!' || code.charAt(i - 1) == '=' ||
                    code.charAt(i - 1) == '>' || code.charAt(i - 1) == '<' || code.charAt(i - 1) == '+' ||
                    code.charAt(i - 1) == '-' || code.charAt(i - 1) == '*' || code.charAt(i - 1) == '/' ||
                    code.charAt(i - 1) == '%' || code.charAt(i - 1) == '^' || code.charAt(i - 1) == '&' || code.charAt(i - 1) == '|') &&
                    code.charAt(i) == '=') || (code.charAt(i - 1) == '&' && code.charAt(i) == '&') ||
                    (code.charAt(i - 1) == '|' && code.charAt(i) == '|') || (code.charAt(i - 1) == '>' && code.charAt(i) == '>') ||
                    (code.charAt(i - 1) == '<' && code.charAt(i) == '<')))
            {
                column = column + 1;
                i = i + 1;
                if(i < code.length() && i > 1 &&((code.charAt(i - 2) == '>' && code.charAt(i - 1) == '>' && code.charAt(i) == '>') ||
                         (code.charAt(i - 2) == '>' && code.charAt(i - 1) == '>' && code.charAt(i) == '=') ||
                         (code.charAt(i - 2) == '<' && code.charAt(i - 1) == '<' && code.charAt(i) == '=')))
                {
                    column = column + 1;
                    i = i + 1;
                    if(i < code.length() && i > 2 && (code.charAt(i - 3) == '>' && code.charAt(i - 2) == '>' && code.charAt(i - 1) == '>' &&
                            code.charAt(i) == '='))
                    {
                        column = column + 1;
                        i = i + 1;
                    }
                }
                num = 2;
            }
            String temp = code.substring(j, i);
            if(num == 0)
            {
                num = 2;
            }
            Symbol s = new Symbol(temp, num, Tokenizer.pdp(temp), Tokenizer.pfp(temp), li, ci);
            while(i < code.length() && (Character.isWhitespace(code.charAt(i))))
            {
                if(code.charAt(i) == '\t')
                {
                    column = column + 8;
                }
                else
                {
                    if(code.charAt(i) == '\n')
                    {
                        line = line + 1;
                        column = 1;
                    }
                    else
                    {
                        column = column + 1;
                    }
                }
                i = i + 1;
            }
            index = i;
            return s;
        }
        unary = false;
        String temp = code.substring(j, i);
        Symbol s = new Symbol(temp, li, ci);
        while(i < code.length() && (Character.isWhitespace(code.charAt(i))))
        {
            if(code.charAt(i) == '\t')
            {
                column = column + 8;
            }
            else
            {
                if(code.charAt(i) == '\n')
                {
                    line = line + 1;
                    column = 1;
                }
                else
                {
                    column = column + 1;
                }
            }
            i = i + 1;
        }
        index = i;
        //se retorna un substring desde el indice ingresado, hasta el indice hallado
        return s;
    }
    /**
     * Metodo utilizado para eliminar del un StringBuffer todos los espacios en blanco hacia adelante y atras
     * del string ingresado como argumento, el entero direccion indica si solo se eliminaran los espacios en blanco
     * anteriores o ambos.
     * @param direccion si es 1 solo se eliminan lo espacios en blanco antes del string, en caso contrario se eliminan en ambas direcciones
     * @param string es el StringBuffer que se desea modificar
     * @param valor el String a partir del cual se desean eliminar los espacios en blanco
     */
    public StringBuilder eliminarBlancos(String valor) throws Exception
    {
        int indice, k;
        //se encuentra la primera ocurrecia del string a partir del cual se desean eliminar los espacios en blanco
        indice = code.indexOf(valor, 0);
        //se recorren todos los valores del texto que contengan el string a partir del cual se desean eliminar los espacios en blanco
        while(indice >= 0)
        {
            k = indice + 1;
            //se recorre el stringBuffer hacia adelante del string a partir del cual se desean eliminar los espacios en blanco
            //y se eliminan todos los espacios en blanco encontrados hasta el proximo
            //caracter que no sea un espacio en blanco
            while(k < code.length() && Character.isWhitespace(code.charAt(k)))
            {
                code.deleteCharAt(k);
                if(((valor.equals(">") || valor.equals("!") || valor.equals("<") || valor.equals("=") || valor.equals("+") ||
                        valor.equals("-") || valor.equals("*") || valor.equals("/") || valor.equals("^") || valor.equals("&") ||
                        valor.equals("|") || valor.equals("%")) && code.charAt(k) == '=') ||
                        (valor.equals("&") && code.charAt(k) == '&') || (valor.equals("|") && code.charAt(k) == '|') ||
                        (valor.equals(">") && code.charAt(k) == '>') ||(valor.equals("<") && code.charAt(k) == '<'))
                {
                    throw new Exception("El simbolo: "+valor+code.charAt(k)+" no puede tener espacios");
                }
            }
            k = indice - 1;
            //se recorre el stringBuffer hacia atras del string a partir del cual se desean eliminar los espacios en blanco
            //y se eliminan todos los espacios en blanco encontrados hasta el proximo
            //caracter que no sea un espacio en blanco
            while(k >= 0 && Character.isWhitespace(code.charAt(k)))
            {
                code.deleteCharAt(k);
                indice = indice - 1;
                k = k - 1;
            }
            //se siguen buscando el siguiente indice del string a partir del cual se desean eliminar los espacios en blanco
            indice = code.indexOf(valor, indice + 1);
        }
        return code;
    }
    /**
     * Método que permite conocer la precedencia fuera de la pila de un operador, es utilizado para saber cuando apilar un nuevo operador en
     * la pila.
     * @param el operador para el cual se desea evaluar la precedencia
     * @return valor entero que indica la precedencia del operador fuera de la pila
     */
    public static int pfp(String x)
    {
        if(x.equals("^") || x.equals("%"))
        {
            return 9;
        }
        if(x.equals("*") || x.equals("/"))
        {
            return 8;
        }
        if(x.equals("+") || x.equals("-"))
        {
            return 7;
        }
        if(x.equals("<<") || x.equals(">>") || x.equals(">>>"))
        {
            return 6;
        }
        if(x.equals(">") || x.equals("<") || x.equals(">=") || x.equals("<=") || 
                x.equals("==") || x.equals("!="))
        {
            return 5;
        }
        if(x.equals("!"))
        {
            return 4;
        }
        if(x.equals("&") || x.equals("&&"))
        {
            return 3;
        }
        if(x.equals("|") || x.equals("||"))
        {
            return 2;
        }
        if(x.equals("=") || x.equals("+=") || x.equals("-=") || x.equals("*=") || x.equals("/=") || 
                x.equals("%=") || x.equals("&=") || x.equals("|=") || x.equals("^=") || 
                x.equals(">>=") || x.equals("<<=") || x.equals(">>>="))

        {
            return 1;
        }
        if(x.equals("("))
        {
            return 10;
        }
        return 10;
    }
    /**
     * Método que permite conocer la precedencia dentro de la pila de un operador, es utilizado para saber cuando apilar un nuevo operador en
     * la pila.
     * @param el operador para el cual se desea evaluar la precedencia
     * @return valor entero que indica la precedencia del operador dentro de la pila
     */
    public static int pdp(String x)
    {
        if(x.equals("^") || x.equals("%"))
        {
            return 9;
        }
        if(x.equals("*") || x.equals("/"))
        {
            return 8;
        }
        if(x.equals("+") || x.equals("-"))
        {
            return 7;
        }
        if(x.equals("<<") || x.equals(">>") || x.equals(">>>"))
        {
            return 6;
        }
        if(x.equals(">") || x.equals("<") || x.equals(">=") || x.equals("<=") || x.equals("==") ||
                x.equals("!="))
        {
            return 5;
        }
        if(x.equals("!"))
        {
            return 4;
        }
        if(x.equals("&") || x.equals("&&"))
        {
            return 3;
        }
        if(x.equals("|") || x.equals("||"))
        {
            return 2;
        }
        if(x.equals("=") || x.equals("+=") || x.equals("-=") || x.equals("*=") || x.equals("/=") || 
                x.equals("%=") || x.equals("&=") || x.equals("|=") || x.equals("^=") ||
                x.equals(">>=") || x.equals("<<=") || x.equals(">>>="))
        {
            return 1;
        }
        if(x.equals("("))
        {
            return 0;
        }
        return 9;
    }
    /**
     * Este metodo recibe como argumento un StringBuffer que se supone contiene un polinomio correcto,
     * se eliminan todos los espacios en blanco que se pueden obviar, es decir los espacios en blanco
     * antes y despues de los simbolos de ^, +, -, (, ) y antes de la primera letra del nombre de la variable,
     * en caso de que los espacios en blaco se encuentren en el nombre de la variable, un coeficiente, o un exponente
     * durante el proceso de lectura del polinomio se considerara que el polinomio no es correcto
     */
    public void eliminarBlancos() throws Exception
    {
        //se eliminan los espacios en blanco antes y despues de los simbolos para realizar las operaciones
        code = this.eliminarBlancos("^");
        code = this.eliminarBlancos("+");
        code = this.eliminarBlancos("*");
        code = this.eliminarBlancos("/");
        code = this.eliminarBlancos("%");
        code = this.eliminarBlancos("-");
        code = this.eliminarBlancos("&");
        code = this.eliminarBlancos("|");
        code = this.eliminarBlancos("!");
        code = this.eliminarBlancos("(");
        code = this.eliminarBlancos(")");
        code = this.eliminarBlancos(">");
        code = this.eliminarBlancos("<");
        code = this.eliminarBlancos("=");
        int i = 0;
        while(i < code.length() && Character.isWhitespace(code.charAt(i)))
        {
            code.deleteCharAt(i);
        }
        i = code.length() - 1;
        while(i >= 0 && Character.isWhitespace(code.charAt(i)))
        {
            code.deleteCharAt(i);
            i = i - 1;
        }
    }
    /**
     * Método que permite evaluar si en una expresión los simbolos estan correctamente apareados, como por ejemplo los parentesis.
     * @param expresion la expresión para la cual se desea evaluar si los simbolos se encuentran apareados correctamente
     * @param si el simbolo incial que se supone ser cerrado por el final, por ejemplo ( para el caso de los parentesis
     * @param sf simbolo final que se supone cerrara el inicial, por ejemplo ) para el caso de los parentesis
     * @return valor booleano que indica si los simbolos ingresados se encuentran apareados correctamente en la expresión
     */
    public boolean isClosed(String si, String sf)
    {
        //pila que almacena las ocurrencias del simbolo inicial
        Stack pila = new Stack();
        String sbi = null, sbf = null;
        int li = si.length(), lf = sf.length();
        for(int i = 0; i < code.length(); i++)
        {
            if(i + li <= code.length())
            {
                //se obtiene el substring candidato a ser el simbolo inicial
                sbi = code.substring(i, i + li);
            }
            else
            {
                sbi = null;
            }
            if(i + lf <= code.length())
            {
                //se obtiene el substring candidato a se el simbolo final
                sbf = code.substring(i, i + lf);
            }
            else
            {
                sbf = null;
            }
            //si el substring obtenido es el simbolo inicial se almacena en la pila
            if(sbi != null && sbi.equals(si))
            {
                pila.push(si);
                i = i + li - 1;
            }
            //si el substring obtenido es el simbolo final se desapila un elemento de la pila, 
            //en caso de que esta se encuentre vacia se supone que la expresión estaba mal escrita
            if(sbf != null && sbf.equals(sf))
            {
                if(pila.isEmpty())
                {
                    return false;
                }
                pila.pop();
                i = i + lf - 1;
            }
        }
        //se la pila esta vacia la expresión esta correcta
        return pila.isEmpty();
    }
}
