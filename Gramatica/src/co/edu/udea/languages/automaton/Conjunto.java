/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.languages.automaton;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * Clase utilizada para representar un conjunto de datos diferentes. 
 * Esta clase implementa todos los métodos de las interfaces Conjunto utilizando
 * una lista doblemente ligada circular con nodo cabeza.
 */
public class Conjunto extends ArrayList
{
    public Conjunto()
    {
        super();
    }
    public boolean equals(Conjunto c)
    {
        if(this.size() != c.size())
        {
            return false;
        }
        Iterator it = this.iterator();
        while(it.hasNext())
        {
            if(!c.contains(it.next()))
            {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Conjunto)
        {
            return this.equals((Conjunto)o);
        }
        return false;
    }
    @Override
    public boolean add(Object o)
    {
        if(!this.contains(o))
        {
           super.add(o);
           return true;
        }
        return false;
    }
    /**
     * Método que permite establecer si en un conjunto de estados se encuentra un
     * estado con el id ingresado como argumento.
     * @param id identificador que permite diferenciar los estados dentro de un conjunto
     * @return true si existe un estado con el id especificado, false en caso contrario
     */
    public boolean contieneEstado(Object id)
    {
        Iterator it = this.iterator();
        while(it.hasNext())
        {
            Object obj = it.next();
            if(obj != null && obj instanceof Estado)
            {
                Estado e = (Estado)obj;
                if(e.getId().equals(id))
                {
                    return true;
                }
            }
            else if(obj != null)
            {
                return false;
            }
        }
        return false;
    }
    /**
     * Método que permite establecer el indice del Estado que tiene como id el especificado como argumento.
     * @param id id del estado que se desea buscar en este conjunto
     * @return el indice en el cual se encuentra el estado con el id especificado como argumento o -1 en caso de que no exista
     */
    public int indexOfEstado(Object id)
    {
        Iterator it = this.iterator();
        int i = 0;
        while(it.hasNext())
        {
            Object obj = it.next();
            if(obj != null && obj instanceof Estado)
            {
                Estado e = (Estado)obj;
                if(e.getId().equals(id))
                {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
    
}
