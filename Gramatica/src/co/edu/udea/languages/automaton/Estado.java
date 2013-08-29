package co.edu.udea.languages.automaton;
import java.io.Serializable;
/**
 * Clase que permite representar un Estado de un automata finito.
 */
public class Estado implements Serializable
{
    /**
     * Valor que indica si este estado es de aceptación o no.
     */
    private boolean aceptacion = false;
    /**
     * Variable que indica el indice del estado dentro de la colección que lo contiene.
     */
    private Integer index = 0;
    /**
     * Variable que contiene un valor que permite identificar el estado y además proporciona una forma de
     * presentar el estado al usuario.
     */
    private Object id = null;
    public Estado() 
    {
    }
    /**
     * Se crea un nuevo estado con el indice ingresado como argumento.
     * @param index indice que indica la posición de este Estado dentro de la 
     * colección que lo contiene.
     */
    public Estado(Integer index)
    {
        this.index = index;
    }
    /**
     * Se crea un nuevo esta con el indice ingresado como argumento.
     * @param aceptacion indica si el estado es de aceptacion o no.
     * @param id identificador que permite diferenciar este estado.
     * @param index indice que indica la posición de este Estado dentro de la 
     * colección que lo contiene.
     */
    public Estado(Integer index, boolean aceptacion, Object id)
    {
        this.index = index;
        this.id = id;
        this.aceptacion = aceptacion;
    }
    /**
     * Se crea un nuevo esta con el indice ingresado como argumento.
     * @param aceptacion indica si el estado es de aceptacion o no.
     * @param index indice que indica la posición de este Estado dentro de la 
     * colección que lo contiene.
     */
    public Estado(Integer index, Boolean aceptacion) {
        this.aceptacion = aceptacion;
        this.index = index;
    }
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Estado)
        {
            Estado e = (Estado)o;
            return this == e || (id != null && id.equals(e.id));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    @Override
    public String toString()
    {
        return String.valueOf(id);
    }
    /**
     * Indica si el estado es de aceptación.
     * @return true si el estado es de aceptación, false en caso contrario.
     */
    public boolean isAceptacion() {
        return aceptacion;
    }

    /**
     * Establece si el estado es de aceptación.
     * @param aceptacion indica si el estado es de aceptación.
     */
    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }
    /**
     * Indica el indice del estado dentro de la colección que lo contiene.
     * @return indice del estado dentro de la colección que lo contiene.
     */
    public Integer getIndex() {
        return index;
    }
    /**
     * Establece el indice de este estado dentro de la colección que la contiene.
     * @param index el indice del estado dentro de la colección que lo contiene.
     */
    public void setIndex(Integer index) {
        this.index = index;
    }
    /**
     * Obtiene el identificador del estado.
     * @return el identificador del estado.
     */
    public Object getId() {
        return id;
    }
    /**
     * Establece le identificador de este estado.
     * @param id el identificador de este estado.
     */
    public void setId(Object id) {
        this.id = id;
    }
}
