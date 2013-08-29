package co.edu.udea.languages.grammar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * Clase que permite representar un lado de una producción de una gramática.
 * Dicho lado se compondrá de una colección de tokens.
 */
public class Lado
{
    /**
     * Tokens incluidos dentro de este lado.
     */
    private Collection<Token> elementos = new ArrayList<Token>();
    public Lado() {
    }
    public Lado(Collection<Token> elementos) {
        this.elementos = elementos;
    }
    public Collection<Token> getElementos() {
        return elementos;
    }
    private Token actual = null;
    private Iterator<Token> elements;
    /**
     * Evalua si este lado corresponde al lado derecho de una producción lineal por la derecha.
     * El lado es lineal por la derecha si es de la forma T*<B> o T*, para reconocer este hecho,
     * se utiliza una gramática simple para realizar un reconocimiento descendente recursivo.
     */
    public boolean isLinealDerecha()
    {
        elements = elementos.iterator();
        actual = elements.next();
        //se llama el método recursivo
        nts();
        if(actual.equals(new Terminal("")) || actual.equals(new NoTerminal("")))
        {
            return true;
        }
        return false;
    }
    public void ntt()
    {
        if(actual instanceof Terminal)
        {
            leer();
            ntt();
        }
    }
    /**
     * Permite leer el siguiente token dentro del lado.
     */
    public void leer()
    {
        if(elements.hasNext())
        {
            actual = elements.next();
        }
        else
        {
            actual = new NoTerminal("");
        }
    }
    public void nts()
    {
        ntt();
        if(actual instanceof NoTerminal)
        {
            leer();
        }
    }
    public void setElementos(Collection<Token> elementos) {
        this.elementos = elementos;
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("");
        Iterator<Token> it = elementos.iterator();
        while(it.hasNext())
        {
            sb.append(it.next().toString());
        }
        return sb.toString();
    }
}
