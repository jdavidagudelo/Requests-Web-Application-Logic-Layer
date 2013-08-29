package co.edu.udea.languages.grammar;
/**
 * Representa un terminal incluido dentro de una gramática
 */
public class Terminal extends Token 
{
    public Terminal(String nombre) {
        this.nombre = nombre;
    }
    public Terminal() {
    }
    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String getNombre() {
        return nombre;
    }
    public boolean equals(Terminal t)
    {
        return nombre.equals(t.nombre);
    }
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Terminal)
        {
            return this.equals((Terminal)o);
        }
        return false;
    }

    @Override
    public String toString()
    {
        return nombre;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 99 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    /**
     * Por naturaleza los terminales son vivos.
     * @return true
     */
    @Override
    public boolean isVivo() {
        return true;
    }

    @Override
    public void setVivo(boolean vivo) 
    {
    }
}
