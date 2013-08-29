package co.edu.udea.logic.truthtabletree;
public interface Joint extends java.util.Set
{
    public Joint intersection(Joint b);
    public Joint union(Joint b);
    public Joint complement(Joint b);
    public boolean isSubset(Joint b);
}
