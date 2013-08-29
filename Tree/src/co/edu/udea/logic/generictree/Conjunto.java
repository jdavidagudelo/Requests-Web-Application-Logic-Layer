/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

public interface Conjunto extends java.util.Set
{
    public Conjunto interseccion(Conjunto b);
    public Conjunto union(Conjunto b);
    public Conjunto complemento(Conjunto b);
    public boolean pertenece(Object d);
    public boolean isSubconjunto(Conjunto b);
}
