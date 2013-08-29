/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;
import java.io.Serializable;
public class Tabla implements Serializable, Clonable
{
    private String variables[] = new String[0];
    private StringBuilder text = new StringBuilder("");
    private int codigos[] = new int[0];
    private Expresion expresion = null;
    private int m = 0, n = 0;
    public Tabla()
    {
    }
    public Tabla(Tabla tabla)
    {
        this.variables = new String[tabla.variables.length];
        for(int i = 0; i < this.variables.length; i++)
        {
            this.variables[i] = tabla.variables[i].toString();
        }
        this.codigos = new int[tabla.codigos.length];
        for(int i = 0; i < this.codigos.length; i++)
        {
            this.codigos[i] = new Integer(tabla.codigos[i]);
        }
        this.text = new StringBuilder(tabla.text.toString());
        this.m = tabla.m;
        this.n = tabla.n;
        if(tabla.expresion != null)
        this.expresion = (Expresion)tabla.expresion.clonar();
    }
    public void setText(StringBuilder text)
    {
        this.text = text;
    }
    public StringBuilder getText()
    {
        return text;
    }
    public void setExpresion(Expresion expresion) throws Exception
    {
        this.expresion = expresion;
        this.setVariables(expresion.variables());
        this.setCodigos(expresion.codigos());
    }
    public Expresion getExpresion()
    {
        return expresion;
    }
    public int[] getCodigos()
    {
        return codigos;
    }
    public String[] getVariables()
    {
        return variables;
    }
    public int getM()
    {
        return m;
    }
    public int getN()
    {
        return n;
    }
    public void setCodigos(int[] codigos)
    {
        this.codigos = codigos;
        this.m = codigos.length ;
    }
    public void setVariables(String[] variables)
    {
        this.variables = variables;
        this.n = variables.length;
    }
    public void setM(int m)
    {
        this.m = m;
    }
    public void setN(int n)
    {
        this.n = n;
    }

    public Object clonar() 
    {
        return new Tabla(this);
    }
}
