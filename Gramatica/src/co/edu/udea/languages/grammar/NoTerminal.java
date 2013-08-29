package co.edu.udea.languages.grammar;
public class NoTerminal extends Token
{
    public NoTerminal(Object atributo, String nombre) {
        super(nombre);
        this.atributo = atributo;
    }
    public NoTerminal(Object atributo) {
        this.atributo = atributo;
    }
    private boolean alcanzable = false;
    public void setAlcanzable(boolean alcanzable) {
        this.alcanzable = alcanzable;
    }

    public boolean isAlcanzable() {
        return alcanzable;
    }

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }
    private boolean vivo = false;
    @Override
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
    @Override
    public boolean isVivo() {
        return vivo;
    }
    public NoTerminal() {
    }

    public NoTerminal(String nombre) {
        this.nombre = nombre;
    }
    
    public boolean equals(NoTerminal nt)
    {
        return nombre.equals(nt.nombre);
    }
    @Override
    public String toString()
    {
        return "<"+nombre+">";
    }
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof NoTerminal)
        {
            return this.equals((NoTerminal)o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }
}
