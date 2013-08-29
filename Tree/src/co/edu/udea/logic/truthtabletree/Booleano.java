package co.edu.udea.logic.truthtabletree;
import java.io.Serializable;
/**
 * Clase que permite representa una variable booleana como un objeto.
 */
public class Booleano implements Serializable, Clonable
{
    /**
     * Constante que representa el valor booleano true.
     */
    public static final boolean TRUE = true;
    /**
     * Constante que representa el valor booleano false.
     */
    public static final boolean FALSE = false;
    /**
     * Variable que indica el valor de verdad de este objeto Booleano, se inicializa en false.
     */
    private boolean valor = Booleano.FALSE;
    /**
     * Método que permite obtener el valor booleano correspondiente al String ingresado como
     * argumento. Si el String ingresado es "true" se crea una nueva variable Booleano cuyo
     * valor es true, si es "false" se crea una variable Booleana con valor false, si el String es
     * cualquier otro valor retorna null.
     * @param value un String correspondiente a un valor Booleano
     */
    public static Booleano valueOf(String value)
    {
        if(value.equals("true"))
        {
            return new Booleano(true);
        }
        if(value.equals("false"))
        {
            return new Booleano(false);
        }
        return null;
    }
    /**
     * Constructor de copia del Booleano especificado como argumento
     * @param booleano el Objeto Booleano que se desea copiar
     */
    public Booleano(Booleano booleano)
    {
        this.valor = booleano.valor;
    }
    /**
     * Crea un nuevo Booleano correspondiente al String especificado
     * @param value String que representa un valor Booleano
     */
    public Booleano(String value)
    {
        this.valor = Booleano.valueOf(value).valor;
    }
    /**
     * Constructor que crea un nuevo Booleano de esta clase inicializado en false.
     */
    public Booleano()
    {
    }
    /**
     * Constructor que crea un nuevo Booleano con base en el tipo primitivo boolean.
     */
    public Booleano(boolean b)
    {
        if(b)
        {
            this.valor = Booleano.TRUE;
        }
        else
        {
            this.valor = Booleano.FALSE;
        }
    }
    /**
     * Método que permite obtener el resultado de realizar la operación AND entre dos booleanos.
     * @param b objeto de la clase Booleano con el cual este debe realizar la operación AND.
     * @return el resultado de realizar la operación AND entre este Booleano y el Booleano ingresado como parametro.
     */
    public Booleano and(Booleano b)
    {
        if(!this.valor | !b.valor)
        {
            return new Booleano(false);
        }
        return new Booleano(true);
    }
    /**
     * Método que permite obtener el resultado de realizar la operación OR entre dos booleanos.
     * @param b objeto de la clase Booleano con el cual este debe realizar la operación OR.
     * @return el resultado de realizar la operación OR entre este Booleano y el Booleano ingresado como parametro.
     */
    public Booleano or(Booleano b)
    {
        if(!this.valor & !b.valor)
        {
            return new Booleano(false);
        }
        return new Booleano(true);
    }
    /**
     * Método que permite obtener la negación de este Booleano.
     * @return Booleano que es la negación de este.
     */
    public Booleano not()
    {
        if(!this.valor)
        {
            return new Booleano(true);
        }
        return new Booleano(false);
    }
    public boolean getValor()
    {
        return valor;
    }
    public void setValor(boolean valor)
    {
        this.valor = valor;
    }
    public boolean equals(Booleano b)
    {
        return this.valor == b.valor;
    }
    @Override
    public String toString()
    {
        if(this.getValor())
        {
            return "true";
        }
        return "false";
    }
    /**
     * Obtiene una copia de este Objeto
     * @return una copia de este Booleano
     */
    public Object clonar() 
    {
        return new Booleano(this);
    }
}