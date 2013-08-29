package co.edu.udea.logic.truthtabletree;

public class Main {
    public static void main(String[] args) throws Exception
    {
        VentanaArbol xy = new VentanaArbol();
        xy.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        System.out.println('\t'+"x");
        int h = 0;
        int x = h++;
        h += ~((2*3+5-4*8)>>2);
        Tokenizer t = new Tokenizer("//**                *****\n//***/\n");
        System.out.println(t.isClosed("//", "\n")+"como es esto"+(100|1010913));
        Expresion ex = new Expresion("(h+j-k-m)+-+-+(x-y+k)^(x+y)%(s+w+q)*(x-y+1+a)>=0 && (k+1-3+j)*4>=67*6-45/(68*6)");
        ArbolLista ar = ex.getOperaciones().subarbol(ex.getOperaciones().getRaiz().getLd());
        xy.crearArbol(ar);
        Set y = (Set)ar.entrySet();
        Set nm = (Set)y.subList(1, 7);
        Object d[] = nm.toArray();
        for(int j = 0; j < d.length; j++)
        {
            Object obj = d[j];
            System.out.println(obj);
        }
        xy.construirArbol();
        xy.setVisible(true);
    }
}
