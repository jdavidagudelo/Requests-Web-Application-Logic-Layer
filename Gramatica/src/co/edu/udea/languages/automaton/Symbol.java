package co.edu.udea.languages.automaton;
import java.io.Serializable;
/**
 * Clase que permite representar un simbolo obtenido de una expresión,
 * almacenando el nombre del simbolo, la linea y la columna en la cual se encuentra
 * el simbolo en el String que lo contiene, un valor boolean que indica si el 
 * simbolo es o no un operador, en cuyo caso contiene la precedencia fuera de la
 * pila, la precedencia dentro de la pila y el número de operandos del operador 
 * especificado.
 */
public class Symbol implements Serializable
{
    /**
     * Nombre del simbolo
     */
    private String name = null;
    /**
     * true si el simbolo es un operador, false en otro caso
     */
    private boolean operator = false;
    /**
     * Precedencia fuera de la pila si el simbolo es un operador
     */
    private int pfp = 0;
    /**
     * Precedencia dentro de la pila si el simbolo es un operador
     */
    private int pdp = 0;
    /**
     * Número de operandos del operador si el simbolo es un operador, en
     * otro caso 0.
     */
    private int operands = 0;
    /**
     * Linea en la cual se encuentra el simbolo en el String que lo contiene, 
     * teniendo en cuenta que la primera linea corresponde al número 1
     */
    private int line = 1;
    /**
     * Columna en la cual se encuentra el primer caracter del simbolo en el String 
     * que lo contiene, teniendo en cuenta que la primera columna corresponde al número 1
     * y en cada nueva linea se reinicia el valor de la columna.
     */
    private int column = 1;
    //Constantes que representan los operadores, junto al correspondiente número de operandos
    public static final Symbol OP_ADD = new Symbol("+", 2);
    public static final Symbol OP_SUB = new Symbol("-", 2);
    public static final Symbol OP_PRODUCT = new Symbol("*", 2);
    public static final Symbol OP_QUOTIENT = new Symbol("/", 2);
    public static final Symbol OP_MODULE = new Symbol("%", 2);
    public static final Symbol OP_NOT = new Symbol("!", 1);
    public static final Symbol OP_MINUS = new Symbol("-", 1);
    public static final Symbol OP_PLUS = new Symbol("+", 1);
    public static final Symbol OP_NEGATE = new Symbol("~", 1);
    public static final Symbol OP_POW = new Symbol("^", 2);
    public static final Symbol OP_OR = new Symbol("|", 2);
    public static final Symbol OP_ORC = new Symbol("||", 2);
    public static final Symbol OP_AND = new Symbol("&", 2);
    public static final Symbol OP_ANDC = new Symbol("&&", 2);
    public static final Symbol OP_BIGGER = new Symbol(">", 2);
    public static final Symbol OP_LESS = new Symbol("<", 2);
    public static final Symbol OP_ASSIGN = new Symbol("=", 2);
    public static final Symbol OP_EQUAL = new Symbol("==", 2);
    public static final Symbol OP_NOT_EQUAL = new Symbol("!=", 2);
    public static final Symbol OP_BIGGER_OR_EQUAL = new Symbol(">=", 2);
    public static final Symbol OP_LESS_OR_EQUAL = new Symbol("<=", 2);
    public static final Symbol OP_LEFT_PARENTHESIS  = new Symbol("(", 2);
    public static final Symbol OP_RIGHT_PARENTHESIS = new Symbol(")", 2);
    public static final Symbol OP_ASSIGN_ADD = new Symbol("+=", 2);
    public static final Symbol OP_ASSIGN_SUB = new Symbol("-=", 2);
    public static final Symbol OP_ASSIGN_PRODUCT = new Symbol("*=", 2);
    public static final Symbol OP_ASSIGN_QUOTIENT = new Symbol("/=", 2);
    public static final Symbol OP_ASSIGN_MODULE = new Symbol("%=", 2);
    public static final Symbol OP_ASSIGN_AND = new Symbol("&=", 2);
    public static final Symbol OP_ASSIGN_OR = new Symbol("|=", 2);
    public static final Symbol OP_ASSIGN_POW = new Symbol("^=", 2);
    public static final Symbol OP_SHIFT_LEFT_LOGICAL = new Symbol("<<", 2);
    public static final Symbol OP_SHIFT_RIGHT_LOGICAL_WITH_SIGN = new Symbol(">>", 2);
    public static final Symbol OP_SHIFT_RIGHT_LOGICAL_WITHOUT_SIGN = new Symbol(">>>", 2);
    public static final Symbol OP_ASSIGN_SHIFT_RIGHT_LOGICAL_WITH_SIGN = new Symbol(">>=", 2);
    public static final Symbol OP_ASSIGN_SHIFT_LEFT_LOGICAL = new Symbol("<<=", 2);
    public static final Symbol OP_ASSIGN_SHIFT_RIGHT_LOGICAL_WITHOUT_SIGN = new Symbol(">>>=", 2);
    public Symbol()
    {
    }
    /**
     * Constructor que crea un nuevo simbolo que es un operando con el nombre ingresado como argumento
     * y con linea 1 y columna 1
     * @param name el nombre del simbolo
     */
    public Symbol(String name)
    {
        this(name, 1, 1);
    }
    /**
     * Constructor que crea un nuevo simbolo que es un operando con el nombre, la linea y la columna
     * ingresados como argumento
     * @param column la columna en la cual comienza el simbolo en el String que lo contiene
     * @param line la linea en la cual se encuentra el simolo en el String que lo contiene
     * @param name el nombre del simbolo que debe ser un operando
     */
    public Symbol(String name, int line, int column)
    {
        this.name = name;
        this.line = line;
        this.column = column;
        this.operator = false;
    }
    /**
     * Constructor que crea una copia del simbolo ingresado
     * como argumento.
     * @param s el simbolo que se desea copiar
     */
    public Symbol(Symbol s)
    {
        this.name = s.name.toString();
        this.operator = s.operator;
        this.operands = s.operands;
        this.pdp = s.pdp;
        this.pfp = s.pfp;
        this.line = s.line;
        this.column = s.column;
    }
    /**
     * Constructor que permite crear un nuevo operador con el nombre, 
     * número de operandos, precedencia fuera de la pila, precendencia dentro de 
     * la pila, linea y columna especificados como argumento.
     * @param column la columna en la cual se encuentra el simbolo en el String que lo contiene
     * @param line la linea en la cual se encuentra el simbolo en el String que lo contiene
     * @param name el nombre del operador
     * @param operands el número de operandos del operador
     * @param pdp la precedencia dentro de la pila del operador
     * @param pfp la precedencia fuera de la pila del operador
     */
    public Symbol(String name, int operands, int pdp, int pfp, int line, int column)
    {
        this.name = name;
        this.operands = operands;
        this.pdp = pdp;
        this.pfp = pfp;
        this.operator = true;
        this.line = line;
        this.column = column;
    }
    /**
     * Constructor que permite crear un nuevo operador con el nombre, número de operadores,
     * precedencia dentro de la pila y precedencia fuera de la pila especificados 
     * como argumento, en la linea 1 y columna 1.
     * @param name el nombre del operador
     * @param operands el número de operandos del operador
     * @param pdp precedencia dentro de la pila del operador
     * @param pfp precedencia fuera de la pila del operador
     */
    public Symbol(String name, int operands, int pdp, int pfp)
    {
        this(name, operands, pdp, pfp, 1, 1);
    }
    /**
     * Constructor que permite crear un nuevo operador con el nombre especificado como
     * argumento y el número de operandos especificado, en la linea 1, columna 1, pdp 0 y pfp 0.
     * @param name el nombre del operando
     * @param operands el número de operandos del operador
     */
    public Symbol(String name, int operands)
    {
        this(name, operands, 0, 0, 1, 1);
    }
    /**
     * Establece la precedencia dentro de la pila de este operador.
     * @param pfp la precedencia dentro de la pila de este operador
     */
    public void setPfp(int pfp)
    {
        this.pfp = pfp;
    }
    /**
     * Establece la precedencia fuera de la pila de este operador.
     * @param pdp la precedencia dentro de la pila de este operador
     */
    public void setPdp(int pdp)
    {
        this.pdp = pdp;
    }
    /**
     * Obtiene la precedencia dentro de la pila de este operador.
     * @return la precedencia dentro de la pila de este operador
     */
    public int getPfp()
    {
        return pfp;
    }
    /**
     * Obtiene la precedencia fuera de la pila de este operador.
     * @return la precedencia fuera de la pila de este operador.
     */
    public int getPdp()
    {
        return pdp;
    }
    /**
     * Método que permite evaluar si el nombre de este Simbolo es igual al del 
     * String ingresado como argumento.
     * @param x un String para ser comparado con el nombre de este simbolo
     * @return true si el nombre de este simbolo es igual al String ingresado como argumento
     */
    public boolean equals(String x)
    {
        return this.name.equals(x);
    }
    /**
     * Método que permite establecer la linea en la cual se encuentra el primer caracter del 
     * simbolo en el String que lo contiene, 
     * teniendo en cuenta que la primera linea corresponde al número 1.
     * @param line la linea en la cual se encuentra el primer carácter del simbolo en el String que lo contiene.
     */
    public void setLine(int line)
    {
        this.line = line;
    }
    /**
     * Método que permite establecer la columna en la cual se encuentra el primer caracter del simbolo en
     * el String que lo contiene, teniendo en cuenta que la primera columna corresponde al número 1.
     * @param column la columna en la cual se encuentra el primer carácter del simbolo en el String que lo contiene.
     */
    public void setColumn(int column)
    {
        this.column = column;
    }
    /**
     * Método que permite obtener la linea en la cual se encuentra el primer caracter del 
     * simbolo en el String que lo contiene, 
     * teniendo en cuenta que la primera linea corresponde al número 1.
     * @return la linea en la cual se encuentra el primer caracter del simbolo en el String que lo contiene.
     */
    public int getLine()
    {
        return line;
    }
    /**
     * Método que permite obtener la columna en la cual se encuentra el primer caracter del simbolo en
     * el String que lo contiene, teniendo en cuenta que la primera columna corresponde al número 1.
     * @return la columna en la cual se encuentra el primer caracter del simbolo en el String que lo contiene.
     */
    public int getColumn()
    {
        return column;
    }
    /**
     * Método utilizado para obtener el número de carácteres en el nombre del Simbolo.
     * @return el número de carácteres del nombre del simbolo.
     */
    public int length()
    {
        return name != null? name.length() : 0;
    }
    /**
     * Método que permite conocer si dos simbolos son iguales,
     * para esto deben tener el mismo nombre, ser ambos operadores o ambos
     * ser operandos, y en caso de que sean operadores tener el mismo
     * número de operandos.
     * @param x otro Symbol para ser comparado con este Symbol
     * @return true si ambos Symbol son iguales, en otro caso false
     */
    public boolean equals(Symbol x)
    {
        if(name != null && x.name != null)
        {
            if(this.name.equals(x.name))
            {
                if(this.operator == x.operator)
                {
                    if(this.operands == x.operands)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Symbol? this.equals((Symbol)obj) : false;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 71 * hash + (this.operator ? 1 : 0);
        hash = 71 * hash + this.operands;
        return hash;
    }
    /**
     * Retorna true si este simbolo es un operador binario, false en otro caso
     * @return true si este simbolo es un operador binario, false en otro caso
     */
    public boolean isBinaryOperator()
    {
        return this.operands == 2 && !this.equals(Symbol.OP_RIGHT_PARENTHESIS) && 
                !this.equals(Symbol.OP_LEFT_PARENTHESIS);
    }
    /**
     * Método que permite obtener el nombre de este simbolo.
     * @return el nombre de este simbolo.
     */
    public String getName()
    {
        return name;
    }
    /**
     * Método que permite establecer si este simbolo es un operador o no.
     * @param operator true si el simbolo es un operador, false en caso contrario
     */
    public void setOperator(boolean operator)
    {
        this.operator = operator;
    }
    /**
     * Método que permite conocer si este simbolo es un operador o no.
     * @return true si este simbolo es un operador, false en caso contrario.
     */
    public boolean isOperator()
    {
        return operator;
    }
    /**
     * Método que permite obtener el número de operandos de este simbolo en caso de que sea un operador.
     * @return 0 si el simbolo no es un operador, en otro caso retorna el numero de operandos requeridos 
     * para evaluar el operador.
     */
    public int getOperands()
    {
        return operands;
    }
    /**
     * Método que permite establecer el número de operandos requeridos por este operador.
     * @param operands el número de operandos requeridos para evaluar este operador.
     */
    public void setOperands(int operands)
    {
        this.operands = operands;
    }
    /**
     * Método que permite establecer el nombre de este simbolo.
     * @param name el nombre de este simbolo.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    @Override
    public String toString()
    {
        return name;
    }
    /**
     * Método que permite crear una copia de este simbolo.
     */
    public Object clonar() 
    {
        return new Symbol(this);
    }
}
