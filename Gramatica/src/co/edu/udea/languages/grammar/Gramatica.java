package co.edu.udea.languages.grammar;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import co.edu.udea.languages.automaton.*;
/**
 * Esta clase permite representar una grámatica. Está grmática consta de un simbolo inicial,
 * un conjunto  determinales, no terminales y un conjunto de producciones.
 */
public class Gramatica 
{
    /**
     * Simbolo inicial de esta gramática
     */
    private NoTerminal simboloInicial = new NoTerminal();
    /**
     * Conjunto de producciones de esta gramática
     */
    private Collection<Produccion> producciones;
    /**
     * Conjunto de terminales de esta gramática
     */
    private Hashtable<String, Terminal> terminales = new Hashtable<String, Terminal>();
    /**
     * Conjunto de no terminales de esta gramática
     */
    private Hashtable<String, NoTerminal> noTerminales = new Hashtable<String, NoTerminal>();
    
    public NoTerminal getSimboloInicial() {
        return simboloInicial;
    }

    public void setTerminales(Hashtable<String, Terminal> terminales) {
        this.terminales = terminales;
    }

    public void setSimboloInicial(NoTerminal simboloInicial) {
        this.simboloInicial = simboloInicial;
    }
    public void setNoTerminales(Hashtable<String, NoTerminal> noTerminales) {
        this.noTerminales = noTerminales;
    }

    
    public Hashtable<String, Terminal> getTerminales() {
        return terminales;
    }

    public Hashtable<String, NoTerminal> getNoTerminales() {
        return noTerminales;
    }

    public Gramatica(Collection<Produccion> producciones, Hashtable<String, Terminal> terminales,
            Hashtable<String, NoTerminal> noTerminales) {
        this.producciones = producciones;
        this.terminales = terminales;
        this.noTerminales = noTerminales;
    }
    public Gramatica() 
    {
    }
    /**
     * Permite crear una gramática a partir del String ingresado como argumento.
     * @param s String a partir del cual se desea crear la gramática, dicho String
     * debería cumplir con las siguientes restricciones:
     * 1. Las producciones deben estar separadas por saltos de linea.
     * 2. Los lados izquierdo de las producciones deben contener un unico no terminal, el cual deberá estar
     * encerrado como se muestra a continuacion <nombre>, donde nombre es cualquier nombre valido.
     * 3. El lado izquierdo y el lado derecho de cada produccion estaran separados por el símbolo "::=", en el lado
     * izquierdo se incluye el no terminal izquierdo, en el lado derecho se incluye cualquier hilera de simbolos.
     */
    public Gramatica(String s)
    {
        Gramatica g = Gramatica.crearGramatica(s);
        this.noTerminales = g.noTerminales;
        this.terminales = g.terminales;
        this.producciones = g.producciones;
        this.simboloInicial = g.simboloInicial;
    }
    /**
     * Este método retorna la grámatica correspondiente al String ingresado como argumento.
     * @param g la gramatica que se desea crear.
     * @return la gramatica correspondiente al String ingresado como argumento
     */
    public static Gramatica crearGramatica(String g)
    {
        Tokenizer t = new Tokenizer(g);
        ArrayList blancos = new ArrayList();
        blancos.add("\n");
        //primer token del String ingresado como argumento
        Symbol s = t.next(blancos);
        ArrayList tokens = new ArrayList();
        //se parte el String por los saltos de linea
        while(s != null)
        {
            tokens.add(s.toString());
            s = t.next(blancos);
        }
        ArrayList<Produccion> producciones = new ArrayList<Produccion>();
        for(int i = 0; i < tokens.size(); i++)
        {
            Produccion p = new Produccion((String)tokens.get(i));
            producciones.add(p);
        }
        Gramatica gramatica = new Gramatica(producciones);
        return gramatica;
    }
    /**
     * Método utilizado para evaluar si el lado derecho de un producción de una gramática es vivo.
     * Para esto se recorren todos los simbolos del lado derecho, si alguno es muerto, retorna false,
     * en caso contrario true.
     * @param l lado que se desea evaluar si es vivo o no.
     * @return true si l es vivo, false en caso contrario
     */
    public boolean isVivo(Lado l)
    {
        Iterator<Token> it = l.getElementos().iterator();
        while(it.hasNext())
        {
            Token e = it.next();
            //e es un NoTerminal se debe evaluar si es vivo o no
            if(e instanceof NoTerminal)
            {
                NoTerminal nt = noTerminales.get(e.toString());
                if(!nt.isVivo())
                {
                    //un no terminal del lado derecho es muerto, por lo tanto
                    //el lado derecho es muerto
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Método utilizado para inicializar los conjuntos de terminales, no terminales, y producciones
     * de esta gramática a partir del conjunto de producciones ingresado como argumento.
     * @param producciones un conjunto de producciones a partir de las cuales se desean crear los conjuntos
     * que componen esta gramática.
     */
    private void inicializar(Collection<Produccion> producciones)
    {
        terminales = new Hashtable<String, Terminal>();
        noTerminales = new Hashtable<String, NoTerminal>();
        Iterator<Produccion> it = producciones.iterator();
        boolean inicial = true;
        while(it.hasNext())
        {
            Produccion p = it.next();
            Lado ld = p.getLadoDerecho();
            Iterator<Token> itld = ld.getElementos().iterator();
            //se recorre el lado derecho de la producción actual
            while(itld.hasNext())
            {
                Token e = itld.next();
                if(e instanceof Terminal)
                {
                    //se mapea el Terminal en una tabla Hash, su clave será su nombre
                    terminales.put(e.toString(), (Terminal)e);
                }
                if(e instanceof NoTerminal)
                {
                    //se mapea el no terminal en una tabla hash, su clave será su <nombre>, nombre es el nombre del
                    //No terminal
                    noTerminales.put(e.toString(), (NoTerminal)e);
                }
            }
            Lado li = p.getLadoIzquierdo();
            Iterator<Token> itli = li.getElementos().iterator();
            //se recorre el lado izquierdo de la producción
            while(itli.hasNext())
            {
                Token e = itli.next();
                if(e instanceof Terminal)
                {
                    terminales.put(e.toString(), (Terminal)e);
                }
                if(e instanceof NoTerminal)
                {
                    noTerminales.put(e.toString(), (NoTerminal)e);
                    //el no terminal del lado izquierdo es el simbolo inicial de la gramática
                    if(inicial)
                    {
                        this.simboloInicial = (NoTerminal)e;
                        inicial = false;
                    }
                }
            }
            
        }
    }
    /**
     * Método utilizado para evaluar si una gramática es muerta o no, para esto simplemente se eliminan 
     * todos los no terminales muertor de la gramática, si la gramática despues de eliminar los
     * no terminales muertor es vacia, entonces será muerta.
     * @return true si la gramática es muerta, false en caso contrario.
     */
    public boolean esMuerta()
    {
        this.eliminarMuertos();
        return this.producciones.isEmpty();
    }
    /**
     * Crea una nueva gramática a partir del conjunto de producciones ingresado como argumento.
     * @param producciones un conjunto de producciones
     */
    public Gramatica(Collection<Produccion> producciones) 
    {
        this.producciones = producciones;
        this.inicializar(producciones);
    }
    public Collection<Produccion> getProducciones() {
        return producciones;
    }
    /**
     * Método que permite eliminar todos los No terminales muertos de una gramática.
     * El algoritmo utilizado recorre todas las producciones y evalua cuales lados derechos son vivos
     * y los incluye en el conjunto de vivos, el ciclo se repite hasta que no se incluyan más no terminales en 
     * el conjunto de vivos. Finalmente con base en el conjunto de vivos, se eliminan de la gramática todas las
     * producciones que contengan los no terminales que no hagan parte del conjunto de vivos.
     */
    public void eliminarMuertos()
    {
        //contiene los no terminales vivos
        HashSet nVivos = new HashSet();
        int nActual = 0;
        Produccion p = null;
        do
        {
            //si no se modifica vivos.size() el algoritmo finaliza
            nActual = nVivos.size();
            Iterator<Produccion> it = producciones.iterator();
            //se recorren todas las producciones de la gramática
            while(it.hasNext())
            {
                p = it.next();
                Lado ld = p.getLadoDerecho();
                Lado li = p.getLadoIzquierdo();
                Iterator<Token> itd = li.getElementos().iterator();
                Token e = itd.next();
                NoTerminal nt = noTerminales.get(e.toString());
                if(this.isVivo(ld))   
                {
                    nVivos.add(nt);
                    //el lado derecho de la producción actual es vivo, 
                    //por lo tanto el no terminal del lado izquierdo también será vivo
                    nt.setVivo(true);
                    if(nt.equals(simboloInicial))
                    {
                        simboloInicial.setVivo(true);
                    }
                }
            }
        }while(nActual != nVivos.size());
        Enumeration<NoTerminal> en = noTerminales.elements();
        HashSet<NoTerminal> nMuertos = new HashSet<NoTerminal>();
        //se obtiene el conjunto de los no terminales que no pertenecen al conjunto de vivos
        while(en.hasMoreElements())
        {
            NoTerminal nt = en.nextElement();
            if(!nVivos.contains(nt))
            {
                noTerminales.remove(nt.toString());
                nMuertos.add(nt);
            }
        }
        Iterator<Produccion> it = producciones.iterator();
        Collection<Produccion> nuevasProducciones = new ArrayList<Produccion>();
        nuevasProducciones.addAll(producciones);
        while(it.hasNext())
        {
            p = it.next();
            Collection li = p.getLadoIzquierdo().getElementos();
            Iterator<NoTerminal> ite = nMuertos.iterator();
            //se evalua si el lado izquierdo de la producción actual contiene 
            //un no terminal muerto, en dicho caso se elimina
            while(ite.hasNext())
            {
                if(li.contains(ite.next()))
                {
                    nuevasProducciones.remove(p);
                }
            }
            ite = nMuertos.iterator();
            Collection ld = p.getLadoDerecho().getElementos();
            //se evalua si el lado derecho de la producción actual contiene 
            //un no terminal muerto, en dicho caso se elimina
            while(ite.hasNext())
            {
                if(ld.contains(ite.next()))
                {
                    nuevasProducciones.remove(p);
                }
            }
        }
        if(!simboloInicial.isVivo())
        {
            //se garantiza que la gramática es muerta
            nuevasProducciones.clear();
        }
        this.producciones = nuevasProducciones;
        this.inicializar(producciones);
    }
    /**
     * Método que permite eliminar las producciones con no terminales inalcanzables.
     * El algoritmo utiliza un conjunto de no terminales alcanzables que inicialmente contiene el 
     * simbolo inicial de la grámatica y que luego se agregan no terminales a dicho conjunto de
     * acuerdo con el lado derecho de las producciones que tengan como lado derecho a dicho simbolo inicial,
     * el algoritmo finaliza cuando no se agregan no terminales al conjunto.
     * Las producciones que contienen los no terminales no incluidos en el conjunto de alcanzables son eliminadas
     * de la gramática.
     */
    public void eliminarInalcanzables()
    {
        Iterator<Produccion> it = producciones.iterator();
        HashSet<NoTerminal> alcanzables = new HashSet<NoTerminal>();
        Produccion p;
        NoTerminal nt = null;
        Iterator<Token> li = null;
        NoTerminal n = null;
        boolean inicial = true;
        while(it.hasNext())
        { 
            p = it.next();
            li = p.getLadoIzquierdo().getElementos().iterator();
            nt = (NoTerminal)li.next();
            n = noTerminales.get(nt.toString());
            //el simbolo incial siempre es alcanzable
            if(inicial)
            {
                n.setAlcanzable(true);
                inicial = false;
                alcanzables.add(n);
            }
            //si el no terminal actual es alcanzable se deben recorrer todas las producciones
            //en las cuales dicho no terminal es lado izquierdo y modificar el conjunto de alcanzables.
            if(n.isAlcanzable())
            {
                Iterator itn = producciones.iterator();
                //se recorren las producciones de la gramática
                while(itn.hasNext())
                {
                    Produccion pn = (Produccion)itn.next();
                    //se evalua si el lado izquierdo de la producción actual es el no terminal que se desea evaluar
                    if(pn.getNoTerminalIzquierdo().equals(nt))
                    {
                        Iterator<Token> ld = pn.getLadoDerecho().getElementos().iterator();
                        //se recorren todos los simbolos del lado derecho de la producción actual
                        while(ld.hasNext())
                        {
                            Token e = ld.next();
                            if(e instanceof NoTerminal)
                            {
                                //el no terminal actual se agrega al conjunto de alcanzables
                                NoTerminal no = noTerminales.get(e.toString());
                                alcanzables.add(no);
                                no.setAlcanzable(true);
                            }
                        }       
                    }
                }
            }
        }
        Enumeration<NoTerminal> en = noTerminales.elements();
        HashSet<NoTerminal> nInalcanzables = new HashSet<NoTerminal>();
        while(en.hasMoreElements())
        {
            nt = en.nextElement();
            if(!alcanzables.contains(nt))
            {
                noTerminales.remove(nt.toString());
                nInalcanzables.add(nt);
            }
        }
        it = producciones.iterator();
        Collection<Produccion> nuevasProducciones = new ArrayList<Produccion>();
        nuevasProducciones.addAll(producciones);
        //se recorren todas las producciones y se eliminan las que tengan no terminales inalcanzables
        while(it.hasNext())
        {
            p = it.next();
            Collection lii = p.getLadoIzquierdo().getElementos();
            Iterator<NoTerminal> ite = nInalcanzables.iterator();
            while(ite.hasNext())
            {
                if(lii.contains(ite.next()))
                {
                    nuevasProducciones.remove(p);
                }
            }
            ite = nInalcanzables.iterator();
            Collection ld = p.getLadoDerecho().getElementos();
            while(ite.hasNext())
            {
                if(ld.contains(ite.next()))
                {
                    nuevasProducciones.remove(p);
                }
            }
        }
        this.producciones = nuevasProducciones;
        this.inicializar(producciones);
    }
    public void setProducciones(Collection<Produccion> producciones) {
        this.producciones = producciones;
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("");
        Iterator<Produccion> it = producciones.iterator();
        int i = 1;
        while(it.hasNext())
        {
            sb.append(i).append(". ");
            sb.append(it.next().toString()).append("\n");
            i++;
        }
        return sb.toString();
    }
    /**
     * Método que permite evaluar si el conjunto de producciones ingresado como argumento ya continene
     * una producción cuyo lado izquierdo corresponda con el no terminal ingresado como argumento.
     * @param nt el no terminal que se desea buscar en la lista de producciones
     * @param producciones las producciones en las que desea conocer si el no terminal es lado izquierdo
     * @return true si el no terminal es la do izquierdo de alguna producción, false en caso contrario 
     */
    public boolean contieneGeneradoras(NoTerminal nt, Collection<Produccion> producciones)
    {
        Iterator<Produccion> it = producciones.iterator();
        while(it.hasNext())
        {
            Produccion p = it.next();
            if(p.getNoTerminalIzquierdo().equals(nt))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * Método que permite obtener el conjunto de todas las producciones cuyo lado izquierdo corresponde al
     * no terminal ingresado como argumento y que está incluidas dentro del conjunto de producciones
     * ingresado como argumento. Este método es válido solo para grámaticas LINEALES POR LA DERECHA.
     * @param nt el no terminal para el cual se desean hallar las producciones en las cuales es el lado izquierdo
     * @param producciones el conjunto de producciones en el que se desea buscar las producciones cuyo lado 
     * izquierdo es el no terminal ingresado como argumento.
     * @return el conjunto de producciones incluidas en el conjunto ingresado como argumento cuyo lado izquierdo
     * es el no terminal ingresado como argumento.
     */
    public Collection<Produccion> generadoras(NoTerminal nt, Collection<Produccion> producciones)
    {
        ArrayList generadoras = new ArrayList();
        Iterator<Produccion> itp = this.producciones.iterator();
        while(itp.hasNext())
        {
            Produccion p = itp.next();
            NoTerminal nte = p.getNoTerminalIzquierdo();
            Lado ld = p.getLadoDerecho();
            Iterator<Token> itt = ld.getElementos().iterator();
            Token t = itt.next();
            //si el lado izquierdo de la producción actual es el no terminal ingresado como argumento,
            //y el lado derecho de la producción es un no terminal, y dicho no terminal no es el mismo
            //que el no terminal del lado izquierdo, se llama el método de manera recursiva para incluir
            //en la lista de producciones generadoras todas las producciones hasta las cuales se puede llegar
            //a partir de esta producción cuyo lado derecho comience con un Terminal. Además es necesario garantizar que
            //el no terminal que se incluye en la lista de generadoras no exista ya, dado que esto puede generar recursión
            //infinita y un error en tiempo de ejecución
            if(nte.equals(nt) && t instanceof NoTerminal && !t.equals(nte) && !this.contieneGeneradoras(nt, producciones))
            {
                //se agregan las producciones generadores del no terminal t.
                generadoras.addAll(generadoras((NoTerminal)t, producciones));
            }
        }
        Iterator<Produccion> it = producciones.iterator();
        while(it.hasNext())
        {
            Produccion p = it.next();
            NoTerminal nte = p.getNoTerminalIzquierdo();
            Lado ld = p.getLadoDerecho();
            Iterator<Token> itt = ld.getElementos().iterator();
            Token t = itt.next();
            //se agregan la producciones cuyo lado derecho comienza con un terminal y
            //cuyo lado izquierdo es el no terminal ingresado como argumento
            if(nte.equals(nt) && t instanceof Terminal)
            {
                generadoras.add(p);
            }
        }
        return generadoras;
    }
    /**
     * Método que permite generar un nuevo nombre que no haya sido incluido en la lista de no terminales de la gramatica.
     * @return un nombre valido de un nuevo no terminal que se desea agregar a la gramática.
     */
    public String nombreValidoNoTerminal()
    {
        String nombre = "NoTerminal";
        boolean existe = true;
        existe = noTerminales.containsKey("<"+nombre+">");
        for(int i = 0; existe == true; i++)
        {
            existe = noTerminales.containsKey("<"+nombre+i+">");
            if(!existe)
            {
                nombre = nombre + i;
            }
        }
        return nombre;
    }
    /**
     * Método que permite convertir una gramática lineal por la derecha a la forma especial.
     * Con este objetivo, en primer lugar modifica todas las producciones de la forma:
     * <A>::=T+<B>
     * <A>::=T*
     * Para modificar las producciones que no sean lineales por la derecha, crea nuevos no terminales,
     * y crea nuevas producciones, hasta que las producciones de esta forma cumplan con las condiciones
     * de un gramática de la forma especial.
     * Para las producciones de la forma:
     * <A>::=<B>
     * Una vez se realizó la transformación de los primeros 2 casos se procede a reemplazar el no terminal <B> en 
     * por los lados derechos de todas las producciones en las cuales <B> es el lado izquierdo y que comiencen con un 
     * terminal o la secuencia nula.
     */
    public void convertirFormaEspecial()
    {
        //si la gramática no es lineal por la derecha no se modifica
        if(!this.isLinealDerecha())
        {
            return;
        }
        ArrayList nuevasProducciones = new ArrayList();
        Iterator<Produccion> it = producciones.iterator();
        //se recorren todas las producciones de la gramatica.
        while(it.hasNext())
        {
            Produccion p = it.next();
            Iterator<Token> itp = p.getLadoDerecho().getElementos().iterator();
            Token e = itp.next();
            //esta produccion permitirá crear las nuevas producciones que se crearán para garantizar que
            //la gramática el lineal por la derecha
            Produccion np = new Produccion();
            //el lado izquierdo de la producción actual será el simbolo inicial de la gramática
            np.setLadoIzquierdo(p.getLadoIzquierdo());
            //ArrayList usado para almacenar los lados derechos de las producciones
            ArrayList ld = new ArrayList();
            //el lado derecho de la producción comienza con un Terminal
            if(e instanceof Terminal)
            {
                //el terminal e se agrega en el lado derecho de una producción
                ld.add(e);
                if(itp.hasNext())
                {
                    //el lado derecho de la producción contiene más de un simbolo
                    Token es = itp.next();
                    //se recorre el lado derecho de la producción hasta el final, o hasta encontrar un
                    //no terminal, y se crean tantos no terminales como sea necesario para garantizar que la
                    //gramática es de la forma especial
                    while(es instanceof Terminal && itp.hasNext())
                    {
                        //se crea un nuevo no terminal con un nombre que no existe aun en el conjunto de no terminales
                        NoTerminal nt = new NoTerminal(this.nombreValidoNoTerminal());
                        //el nuevo no terminal se agrega al lado derecho de una nueva producción
                        ld.add(nt);
                        //se agrega el nuevo no terminal al conjunto de no terminales
                        noTerminales.put(nt.toString(), nt);
                        //se establece el lado derecho de la nueva producción
                        np.getLadoDerecho().setElementos(ld);
                        //se agrega la nueva producción al conjunto de producciones
                        nuevasProducciones.add(np);
                        //se crea una nueva producción
                        np = new Produccion();
                        //el lado izquierdo de la nueva producción será el nuevo no terminal
                        np.getLadoIzquierdo().getElementos().add(nt);
                        //se crea un nuevo lado derecho para la nueva producción correspondiente al
                        //nuevo no terminal
                        ld = new ArrayList();
                        //se agrega el nuevo terminal actual al nuevo lado derecho
                        ld.add(es);
                        //se obtiene el siguiente simbolo del lado derecho de la producción actual
                        es = itp.next();
                    }
                    if(es instanceof NoTerminal)
                    {
                        //el lado derecho de la nueva producción termina con un no terminal
                        ld.add(es);
                        np.getLadoDerecho().setElementos(ld);
                        nuevasProducciones.add(np);
                    }
                    if(es instanceof Terminal)
                    {
                        //el lado derecho de la producción actual termina con un terminal, 
                        //por lo tanto se debe crear un nuevo no terminal, que tenga una producción
                        //cuyo lado derecho sea la secuencia nula
                        NoTerminal nt = new NoTerminal(this.nombreValidoNoTerminal());
                        ld.add(nt);
                        noTerminales.put(nt.toString(), nt);
                        np.getLadoDerecho().setElementos(ld);
                        nuevasProducciones.add(np);
                        np = new Produccion();
                        np.getLadoIzquierdo().getElementos().add(nt);
                        ld = new ArrayList();
                        ld.add(es);
                        nt = new NoTerminal(this.nombreValidoNoTerminal());
                        noTerminales.put(nt.toString(), nt);
                        ld.add(nt);
                        np.getLadoDerecho().setElementos(ld);
                        nuevasProducciones.add(np);
                        np = new Produccion();
                        np.getLadoIzquierdo().getElementos().add(nt);
                        ld = new ArrayList();
                        //el lado derecho del nuevo terminal será la secuencia nula
                        ld.add(new Terminal(""));
                        np.getLadoDerecho().setElementos(ld);
                        nuevasProducciones.add(np);
                    }
                }
                else if(!e.equals(new Terminal("")))
                {
                    //el lado derecho de la producción actual contiene un unico terminal
                    //se crea un nuevo no terminal y se crea una producción donde se defina como
                    //la secuencia nula
                    NoTerminal nt = new NoTerminal(this.nombreValidoNoTerminal());
                    ld.add(nt);
                    noTerminales.put(nt.toString(), nt);
                    np.getLadoDerecho().setElementos(ld);
                    nuevasProducciones.add(np);
                    np = new Produccion();
                    np.getLadoIzquierdo().getElementos().add(nt);
                    ld = new ArrayList();
                    ld.add(new Terminal(""));
                    np.getLadoDerecho().setElementos(ld);
                    nuevasProducciones.add(np);
                }
                else
                {
                    //el lado derecho de la producción actual es la secuencia nula, por lo tanto no requiere modificación
                    np.getLadoDerecho().getElementos().add(new Terminal(""));
                    nuevasProducciones.add(np);
                }
            }
        }
        boolean inicial = false;
        it = nuevasProducciones.iterator();
        //se evalua si existe una producción en la cual se defina el simbolo inicial de la
        //gramática
        if(it.hasNext())
        {
            Produccion p = (Produccion)it.next();
            NoTerminal nt = p.getNoTerminalIzquierdo();
            if(!nt.equals(simboloInicial))
            {
                inicial = true;       
            }
        }
        it = producciones.iterator();
        while(it.hasNext())
        {
            Produccion p = it.next();
            Iterator<Token> itp = p.getLadoDerecho().getElementos().iterator();
            Token e = itp.next();
            itp = p.getLadoIzquierdo().getElementos().iterator();
            NoTerminal es = (NoTerminal)itp.next();
            if(e instanceof NoTerminal)
            {
                //El lado derecho de la producción actual comienza con un no terminal, por lo tanto
                //se obtienen todas las producciones cuyo lado izquierdo sea dicho terminal y su lado derecho 
                //comience con un Terminal o la secuencia nula
                Collection<Produccion> pro = this.generadoras((NoTerminal)e, nuevasProducciones);
                Iterator<Produccion> itpro = pro.iterator();
                while(itpro.hasNext())
                {
                    //se recorren todas las producciones cuyo lado izquierdo es el no terminal con el que comienza el
                    //lado derecho de la producción actual
                    Produccion pa = itpro.next();
                    //se crea una nueva producción
                    Produccion np = new Produccion();
                    //el lado izquierdo de la nueva producción será el lado izquierdo de la producción actual en la gramática
                    np.getLadoIzquierdo().getElementos().add(es);
                    //el lado derecho será el lado derecho de la producción actual en la lista de producciones generadoras
                    np.setLadoDerecho(pa.getLadoDerecho());
                    if(inicial)
                    {
                        if(np.getNoTerminalIzquierdo().equals(simboloInicial))
                        {
                            //la nueva producción define el símbolo inicial de la gramática, y dicho simbolo no habia
                            //sido definido previamente con ninguna producción, por lo tanto se debe insertar dicha producción
                            //al principio de la lista para indicar claramente cual es el simbolo incial de la gramática
                            nuevasProducciones.add(0, np);
                            inicial = false;
                        }
                    }
                    else
                    {
                        //se agrega la nueva producción a la lista de producciones
                        nuevasProducciones.add(np);
                    }
                }
            }
        }
        //se inicializa la gramática con las producciones modificadas, y como es posible que haya no terminales que sean 
        //inalcanzables se eliminan
        this.producciones = nuevasProducciones;
        this.inicializar(producciones);
        this.eliminarInalcanzables();
    }
    /**
     * Método que permite evaluar si la gramática es lineal por la derecha
     * @return true si la gramática es lineal por la derecha, false en caso contrario
     */
    public boolean isLinealDerecha()
    {
        Iterator<Produccion> it = producciones.iterator();
        while(it.hasNext())
        {
            if(!it.next().isLinealDerecha())
            {
                return false;
            }
        }
        return true;
    }
    /**
     * Método que permite obtener un automata finito a partir de una gramática de la forma especial.
     * @return automata finito reconocedor de las secuencias generadas por la gramática
     */
    public Automata getAutomata()
    {
        Automata aut = new Automata();
        if(producciones.isEmpty())
        {
            return aut;
        }
        Conjunto simbolosEntrada = new Conjunto();
        Conjunto estados = new Conjunto();
        Enumeration<Terminal> e = terminales.elements();
        Conjunto estadoInicial = new Conjunto();
        while(e.hasMoreElements())
        {
            Terminal t = e.nextElement();
            if(!t.equals(new Terminal("")))
            {
                //los simbolos de entrada del automata serán los terminales de la gramática
                simbolosEntrada.add(new Lexema(t.toString()));
            }
        }
        Enumeration<NoTerminal> et = noTerminales.elements();
        int i = 0;
        Produccion inicial = producciones.iterator().next();
        Token eInicial = inicial.getLadoIzquierdo().getElementos().iterator().next();
        Estado ei = new Estado(i, false, eInicial);
        estados.add(ei);
        estadoInicial.add(ei);
        i++;
        while(et.hasMoreElements())
        {
            NoTerminal nt = et.nextElement();
            if(!nt.equals(eInicial))
            {
                //los no terminales de la gramática serán los estados del automata finito
                Estado es = new Estado(i, false, nt);
                estados.add(es);
                i++;
            }
        }
        Conjunto transiciones[][] = new Conjunto[estados.size()][simbolosEntrada.size()];
        Iterator<Produccion> it = producciones.iterator();
        for(int f = 0; f < estados.size(); f++)
        {
            for(int c = 0; c < simbolosEntrada.size(); c++)
            {
                transiciones[f][c] = new Conjunto();
            }
        }
        while(it.hasNext())
        {
            Produccion p = it.next();
            Token li = p.getLadoIzquierdo().getElementos().iterator().next();
            int f = estados.indexOfEstado(li);
            Estado estadoActual = (Estado)estados.get(f);
            Iterator<Token> ite = p.getLadoDerecho().getElementos().iterator();
            Terminal t = (Terminal)ite.next();
            if(t.equals(new Terminal("")))
            {
                //si el lado derecho de una producción es secuencia nula, el estado correspondiente al no terminal
                //del lado izquierdo de la producción es de aceptación
                estadoActual.setAceptacion(true);
            }
            else
            {
                //se establece la transición de acuerdo con el simbolo de entrada
                int c = simbolosEntrada.indexOf(new Lexema(t.toString()));
                NoTerminal nt = (NoTerminal)ite.next();
                int ft = estados.indexOfEstado(nt);
                Estado estadoTransicion = (Estado)estados.get(ft);
                transiciones[f][c].add(estadoTransicion);
            }
        }
        aut.setSimbolosEntrada(simbolosEntrada);
        aut.setEstadoInicial(estadoInicial);
        aut.setEstados(estados);
        aut.setTransiciones(transiciones);
        //el automata finito puede ser no deterministico
        return aut;
    }
    public static Gramatica fromFile(java.io.File file) throws FileNotFoundException, IOException
    {
        BufferedReader x = new BufferedReader(new FileReader(file));
        StringBuilder s = new StringBuilder("");
        String sr = x.readLine();
        //se recorren todas las lineas del archivo
        while(sr != null)
        {
            s.append(sr).append("\n");
            sr = x.readLine();
        }
        return new Gramatica(s.toString());
    }
}