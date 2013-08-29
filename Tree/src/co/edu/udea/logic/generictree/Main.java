/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

/**
 *
 * @author Daniel F Agudelo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VentanaArbol xy = new VentanaArbol();
        ArbolLg arbol = new ArbolLg();
        arbol.construirArbol("(a(b,c,d(f,g,h),e(i,j(k(l,m,n,o)))))");
        //xy.crearArbol(arbol);
        //xy.construirArbol();
        ArbolLista arbolL = arbol.convertir();
        xy.setBinario(true);
        xy.crearArbol(arbolL);
        xy.construirArbol();
        xy.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        xy.setVisible(true);
    }

}
