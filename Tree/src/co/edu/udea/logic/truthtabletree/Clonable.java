package co.edu.udea.logic.truthtabletree;
/**
 * Esta interfaz permite crear una copia de un objeto de la clase que la 
 * implementa, las caracteristicas de dicha copia dependerá de las necesidades 
 * del programa.
 */
public interface Clonable 
{
    /**
     * Este método permite obtener un objeto que corresponde a una copia
     * de un Objeto de la clase que ha implementado esta interfaz. Las 
     * caracteristicas de la copia seran definidas por el programador.
     */
    public Object clonar();
}
