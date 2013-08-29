package co.edu.udea.languages.grammar;
/**
 * Clase abstracta utilizada para representar cualquier elemento incluido dentro de una gramática.
 * Este podrá ser un terminal, no terminal, simbolo de acción etc.
 */
public abstract class Token 
{
    protected Object atributo;
    public void setAtributo(Object atributo) {
        this.atributo = atributo;
    }
    public Object getAtributo() {
        return atributo;
    }
    /**
     * Identificador del token
     */
    protected String nombre;
    public Token() {
    }

    public Token(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public abstract boolean isVivo();
    public abstract void setVivo(boolean vivo);
}