/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;
import java.io.Serializable;
public class Variable implements Comparable, Serializable, Clonable
{
    /**
     * Valor almacenado en la variable
     */
    private Object value = null;
    /**
     * Identificador de la variable.
     */
    private String id = null;
    /**
     * Constructor sin argumentos.
     */
    public Variable()
    {
    }
    public Variable(Variable variable)
    {
        if(variable.value instanceof Clonable)
        {
            Clonable c = (Clonable)variable.value;
            this.value = c.clonar();
        }
        else
        {
            this.value = variable.value;
        }
        this.id = variable.id.toString();
    }
    /**
     * Constructor que crea una nueva variable con el valor y el nombre ingresados como argumento.
     * @param id el identificador de la variable.
     * @param value el valor almacenado en la variable.
     */
    public Variable(Object value, String id)
    {
        this.value = value;
        this.id = id;
    }
    /**
     * Retorna el identificador de la variable.
     * @return El identificador de la variable.
     */
    public String getId()
    {
        return id;
    }
    /**
     * Retorna el valor almacenado en la variable.
     * @return  el valor almacenado en la variable.
     */
    public Object getValue()
    {
        return value;
    }
    /**
     * Establece el identificador de esta variable.
     * @param id el identificador de esta variable.
     */
    public void setId(String id)
    {
        this.id = id;
    }
    /**
     * Establece el valor almacenado en esta variable
     * @param el valor de esta variable.
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
    /**
     * retorna un String que contiene esta variable.
     * @return un String de la forma id = value.
     */
    @Override
    public String toString()
    {
        StringBuilder toString = new StringBuilder("");
        toString = toString.append(id.toString()).append("=").append(value.toString());
        return toString.toString();
    }
    /**
     * Método utilizado para comparar esta variable con otra teniendo en cuenta el valor de sus identificadores.
     */
    public int compareTo(Object o)
    {
        if(o instanceof Variable)
        {
            Variable v = (Variable)o;
            return this.id.compareTo(v.id);
        }
        return -1;
    }

    public Object clonar() 
    {
        return new Variable(this);
    }
}
