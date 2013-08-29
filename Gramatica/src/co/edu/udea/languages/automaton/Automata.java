package co.edu.udea.languages.automaton;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;
/**
 * Clase que permite representar un automata finito deterministico.
 */
public class Automata implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Conjunto de Lexemas que representan los simbolos de entrada del automata finito.
     */
    private Conjunto simbolosEntrada = new Conjunto();
    /**
     * Tabla que incluye las transiciones de estados del automata finito.
     * Cada fila representa un estado del automata finito y cada columna representa 
     * un simbolo de entrada, el elemento transiciones[i][j] representa un conjunto de los estados
     * a los cuales debe realizar la transición el automata finito para el estado ubicado en la
     * posición i del conjunto de estados y la entrada correspondientes al Lexema j del conjunto de
     * simbolos de entrada.
     */
    private Conjunto transiciones[][] = new Conjunto[0][0];
    /**
     * Conjunto que representa los estados iniciales del automata finito.
     */
    private Conjunto estadoInicial = new Conjunto();
    /**
     * Conjunto de estados de este automata finito.
     */
    private Conjunto estados = new Conjunto();
    /**
     * Pila en la cual se almacenan los errores cometidos en la entrada de un automata finito.
     */
    private Stack pilaErrores = new Stack();
    /**
     * Nombre de este automata finito, puede ser cualquier String.
     */
    private String name = "automata";
    /**
     * Establece el nombre de este automata finito.
     * @param name cualquier String utilizada para identificar este automata finito.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * El nombre de este automata.
     * @return String que permite identificar este automata finito.
     */
    public String getName() {
        return name;
    }
    /**
     * Estable la pila de errores de este automata finito.
     * @param pilaErrores pila de los errores en la entrada de este automata finito.
     */
    public void setPilaErrores(Stack pilaErrores) {
        this.pilaErrores = pilaErrores;
    }
    /**
     * Retorna la pila de errores de este automata finito.
     * @return pila de errores de este automata finito
     */
    public Stack getPilaErrores() {
        return pilaErrores;
    }
    /**
     * Método que permite validar si el String ingresado como argumento es aceptado por este automata finito.
     * Este método inicia en el estado inicial del automata finito.
     * @param s String que se desea evalar utilizando este automata finito.
     * @return true si el parámetro s es aceptado por este automata finito, false en caso contrario.
     */
    public boolean validar(String s)
    {
        //valida el parámetro s a partir del estado inicial del automata 
        return validar(s, this.getEstadoInicial());
    }
    /**
     * Se crea un automata a partir de un String representado como una tabla.
     * @param automata String que contiene el automata finito, un ejemplo de este automata puede ser:
     *      a   b   ce
     * a    b   c   a   0
     * b    c   b   a   1
     * c    a   c   b   0
     * Este String como argumento creara un automata finito con 3 estados identificados por a,b,c 
     * b es un estado de aceptación. Los simbolos de entrada son: a,b,ce. El simbolo de entrada ec
     * indica que al entrar c o e esta será la transicion a realizar.
     * el 0 indica que el estado no es de aceptación.
     * Para incluir un espacio en blanco es necesario entrar \b, para incluir una tabulación \t
     * y para un salto de linea \n.
     */
    public Automata(String automata)
    {
        estados = new Conjunto();
        //Automata utilizado para validar el nombre de los simbolos de entrada.
        Automata r = new Automata();
        //automata utilizado para validar el nombre de los estados del automata finito.
        Automata r1 = new Automata();
        //Tabla de transiciones del automata finito que reconoce los simbolos de entrada
        Integer x[][] = {{1,1,2},{1,1,2},{3,1,1},{3,3,3}};
        //Tabla de transiciones del automata finito que reconoce los estados del automata finito.
        Integer x1[][] = {{1},{1}};
        //Lexema que incluye todos los simbolos posibles, sin incluir los espacios en blanco y los simbolos b, n y t que seran utilizados luego del \. 
        Lexema lex = new Lexema("0123456789acdefghijklmopqrsuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_ñÑáéíóúÁÉÍÓÚ"
                + "!|\"@·#$~%€&¬/()=?¿'¡*^`[]+´¨Ç{}-.,;:<>´");
        //Lexema que incluye digitos y letras mayusculas y minusculas.
        Lexema lex1 = new Lexema("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_ñÑáéíóúÁÉÍÓÚ");
        //Conjunto que contiene los lexemas del automata que reconocera los simboloes de entrada.
        Conjunto sLex = new Conjunto();
        sLex.add(lex);
        sLex.add(new Lexema("bnt"));
        sLex.add(new Lexema("\\"));
        Conjunto ac = new Conjunto();
        ac.add(1);
        r.crearAutomata(x, sLex, ac);
        sLex.clear();
        sLex.add(lex1);
        r1.crearAutomata(x1, sLex, ac);
        simbolosEntrada = new Conjunto();
        //se parte el automata finito utilizando como separadores los espacios en blanco.
        Tokenizer t = new Tokenizer(automata);
        ArrayList blancos = new ArrayList();
        blancos.add(" ");
        blancos.add("\t");
        blancos.add("\n");
        //primer token del String ingresado como argumento
        Symbol s = t.next(blancos);
        int li = 0;
        if(s!= null)
        {
            li = s.getLine();
        }
        //arrayList que contiene cada uno de los tokens obtenido a partir del String ingresado como argumento
        ArrayList tokens = new ArrayList();
        //se recorren todos los tokens del String ingresado como argumento
        while(s != null)
        {
            //Verifica si la linea no corresponde a la primera linea que contiene un token
            if(s.getLine() == li)
            {
                //Se valida el simbolo de entrada especificado en el Token
                if(!r.validar(s.getName()))
                {
                    //Se agrega el error en la entrada a la pila de errores
                    pilaErrores.push("El token: "+s+" en la linea: "+s.getLine()+", columna: "+
                            s.getColumn()+" no es válido");
                }
                //se reemplazan los tokens de los simbolos de entrada para eliminar los caracteres especiales \\, \b,\t y \n
                s.setName(Automata.replace(s.getName(), "\\\\\\b", "\\b\\\\"));
                s.setName(Automata.replace(s.getName(), "\\\\\\t", "\\t\\\\"));
                s.setName(Automata.replace(s.getName(), "\\\\\\n", "\\n\\\\"));
                s.setName(Automata.replace(s.getName(), "\\\\b", "b\\ "));
                s.setName(Automata.replace(s.getName(), "\\\\t", "t\\ "));
                s.setName(Automata.replace(s.getName(), "\\\\n", "n\\ "));
                s.setName(Automata.replace(s.getName(), " ", ""));
                s.setName(Automata.replace(s.getName(), "\\b", " "));
                s.setName(Automata.replace(s.getName(), "\\n", "\n"));
                s.setName(Automata.replace(s.getName(), "\\t", "\t"));
                s.setName(Automata.replace(s.getName(), "\\\\", "\\"));
                if(!Lexema.isValid(s.getName()))
                {
                    //Se agrega el error en la entrada a la pila de errores
                    pilaErrores.push("El Lexema: "+s+" en la linea: "+s.getLine()+", columna: "+
                            s.getColumn()+" no es válido");
                }
            }
            else
            {
                //se evalua si el nombre del estado es valido
                if(!r1.validar(s.getName()))
                {
                    //se incluye el error de la entrada en la pila de errores
                    pilaErrores.push("El token: "+s+" en la linea: "+s.getLine()+", columna: "+
                            s.getColumn()+" no es válido");
                }
            }
            //Se agrega el nuevo token en el ArrayList
            tokens.add(s);
            //se halla el siguiente token de la entrada
            s = t.next(blancos);
        }
        int l = 0;
        int i = 0;
        //se recorren los tokens obtenidos previamente
        while(i < tokens.size())
        {
            //Se obtiene el siguiente token del ArrayList
            Symbol linea = (Symbol)tokens.get(i);
            //se evalua si los tokens de la linea actual corresponden a los simbolos de entrada del automata finito
            if(l == 0)
            {
                //se obtiene la linea del token actual
                int line = linea.getLine();
                //se agrega el token en el conjunto de simbolos de entrada
                simbolosEntrada.add(new Lexema(linea.getName()));
                i++;
                if(i < tokens.size())
                {
                    Symbol next = (Symbol)tokens.get(i);
                    //se recorren los tokens hasta el final de la linea actual
                    while(line == next.getLine() && i < tokens.size())
                    {
                        //se incluye el token actual en el conjunto de simbolos de entrada
                        if(simbolosEntrada.add(new Lexema(next.getName())))
                        {
                            i++;
                            if(i < tokens.size())
                            {
                                //se obtiene el siguiente token del ArrayList
                                next = (Symbol)tokens.get(i);
                            }
                        }
                        else
                        {
                            //se agrega null para permitir validar apropiadamente el resto de la tabla y mostrar errores
                            simbolosEntrada.add(null);
                            pilaErrores.add("El simbolo: "+next.getName()+" en la linea: "+next.getLine()+", columna: "+
                                    next.getColumn()+" está contenido dentro de otro lexema ingresado");
                            i++;
                            if(i < tokens.size())
                            {
                                next = (Symbol)tokens.get(i);
                            }
                        }
                    }
                }
                l = l + 1;
            }
            else
            {
                int line = linea.getLine();
                Estado e = null;
                //evalua si el token actual corresponde a un estado existente
                if(!estados.contieneEstado(linea.getName()))
                {
                    //se crea el nuevo estado con indice l-1
                    e = new Estado(l - 1);
                    //se establece el id del nuevo estado con el nombre del token
                   e.setId(linea.getName());
                   //se incluye el estado en el conjunto de estados
                   estados.add(e);
                   l = l+ 1;
                }
                else
                {
                    pilaErrores.push("El estado: "+linea.getName()+" en la linea: "+linea.getLine()+
                                ", columna: "+linea.getColumn()+" ya fue definido previamente.");
                }
                int tran = 0;
                i++;
                //se evalua si aun existen tokens para terminar de especificar el estado actual
                if(i < tokens.size())
                {
                    Symbol next = (Symbol)tokens.get(i);
                    //se recorre toda la linea actual
                    while(line == next.getLine() && i < tokens.size() && tran < simbolosEntrada.size())
                    {
                        i++;
                        if(i < tokens.size())
                        {
                            next = (Symbol)tokens.get(i);
                            tran++;
                        }
                    }
                    //evalua si los tokens includos en la linea actual son apropiados
                    if(tran == simbolosEntrada.size())
                    {
                        if(i < tokens.size() && line == next.getLine())
                        {
                            i++;
                            //establece si el estado es de rechazo o de aceptación
                            if(next.getName().equals("0") && e != null)
                            {
                                //el estado no es de aceptación
                                e.setAceptacion(false);
                            }
                            else if(next.getName().equals("1") && e != null)
                            {
                                //el estado es de aceptación
                                e.setAceptacion(true);
                            }
                            else
                            {
                                pilaErrores.push("El simbolo: "+next.getName()+" en la linea: "+next.getLine()+
                                ", columna: "+next.getColumn()+" no es válido.");
                            }
                        }
                        else
                        {
                            pilaErrores.push("El estado "+linea.getName()+" no tiene suficientes transiciones asociadas");
                        }
                    }
                    else
                    {
                        i++;
                        pilaErrores.push("El estado "+linea.getName()+" no tiene suficientes transiciones asociadas");
                    }
                }
                else
                {
                        pilaErrores.push("El estado "+linea.getName()+" no tiene suficientes transiciones asociadas");
                }
            }
        }
        //se crean las transiciones del nuevo automata finito
        transiciones = new Conjunto[estados.size()][simbolosEntrada.size()];
        l = 0;
        i = 0;
        //se recorren todos los tokens extraidos
        while(i < tokens.size())
        {
            Symbol linea = (Symbol)tokens.get(i);
            if(l == 0)
            {
                int line = linea.getLine();
                i++;
                if(i < tokens.size())
                {
                    Symbol next = (Symbol)tokens.get(i);
                    //se avanza hasta la proxima linea del argumento
                    while(line == next.getLine() && i < tokens.size())
                    {
                        i++;
                        if(i < tokens.size())
                        {
                            next = (Symbol)tokens.get(i);
                        }
                    }
                }
                l = l + 1;
            }
            else
            {
                int line = linea.getLine();
                int tran = 0;
                i++;
                if(i < tokens.size())
                {
                    int index = estados.indexOfEstado(linea.getName());
              
                    Symbol next = (Symbol)tokens.get(i);
                    if(index < 0)
                    {
                        pilaErrores.push("El estado: "+linea.getName()+" en la linea: "+linea.getLine()+
                                ", columna: "+linea.getColumn()+" no fue definido.");
                    }
                    //se recorre la siguiente linea de los tokens
                    while(line == next.getLine() && i < tokens.size() && tran < simbolosEntrada.size() && index >= 0)
                    {
                        i++;
                        transiciones[index][tran] = new Conjunto();
                        //se halla el indice del estado actual
                        int ind = estados.indexOfEstado(next.getName());
                        if(ind >= 0)
                        {
                            //se establece la nueva transición del automata
                            transiciones[index][tran].add(estados.get(ind));
                        }
                        else
                        {
                            pilaErrores.push("El estado: "+next.getName()+" en la linea: "+next.getLine()+
                                    ", columna: "+next.getColumn()+" no fue definido");
                        }
                        if(i < tokens.size())
                        {
                            next = (Symbol)tokens.get(i);
                            tran++;
                        }
                    }
                    if(tran == simbolosEntrada.size())
                    {
                        i++;
                    }
                }
            }
        }
        if(estados.isEmpty())
        {
            pilaErrores.push("No se encontaron estados en la tabla ingresada.");
            return;
        }
        estadoInicial = new Conjunto();
        estadoInicial.add(estados.get(0));
    }
    /**
     * Método utilizado para reemplazar en el String s el String i por el String f todas las veces que i se presente
     * dentro de s.
     * @param s el String en el que se desea realizar el reemplazo
     * @param i el String que se desea reemplazar
     * @param f el String que se utilizara como reemplazo
     * @return String en el cual se han reemplazado todas las ocurrencias de i por f
     */
    public static String replace(String s, String i, String f)
    {
        StringBuilder n = new StringBuilder("");
        int j = 0;
        int k = s.indexOf(i, j);
        while(k >= 0)
        {
            n.append(s.substring(j, k));
            j = k + i.length();
            k = s.indexOf(i, j);
            n.append(f);
        }
        n.append(s.substring(j));
        return n.toString();
    }
    /**
     * Se obtiene el indice del caracter ingresado como argumento.
     * @param c caracter al que se desea obtener el indice dentro del conjunto de lexemas
     * @return indice del caracter ingresado como argumento dentro del conjunto de lexemas o
     * -1 en caso de que el caracter no este ingresado en ninguno de los lexemas incluidos en los
     * simbolos de entrada.
     */
    public int indexOf(Character c)
    {
        int index = -1;
        int j = 0;
        Iterator i = simbolosEntrada.iterator();
        while(i.hasNext())
        {
            Lexema l = (Lexema)i.next();
            if(l.contains(c))
            {
                index = j;
            }
            j++;
        }
        return index;
    }
    /**
     * Vector booleano utilizado para realizar el recorrido por los estados del automata finito.
     */
    private boolean visitados[];
    /**
     * Método utilizado para obtener las secuencias minimas generadas por este automata finito.
     * @return retorna un ArrayList que contiene todas las secuencias de longitud mínima que
     * pueden ser reconocidas por este automata finito. Es importante anotar que por cuestiones de
     * eficiencia los automatas utilizados para realizar este proceso pueden ser deterministicos y no deterministicos,
     * dado que en ciertas ocasiones a pesar de que los automatas finitos deterministicos son más eficientes que
     * los no deterministicos, los requerimiento de almacenamiento para un automata finito deterministico pueden llegar
     * a ser insostenibles.
     */
    public ArrayList<String> secuenciasMinimas()
    {
        //no se ha visitado ningún estado
        visitados = new boolean[estados.size()];
        for(int i = 0; i < visitados.length; i++)
        {
            visitados[i] = false;
        }
        //se comienza el algoritmo de búsqueda de minimos
        return this.secuenciasMinimas(this.getEstadoInicial());
    }
    /**
     * Método que permite obtener las secuencias minimas que pueden ser obtenidas comenzando el reconocimiento
     * en el estado ingresado como argumento. Si el estado es de error o en nulo, se retorna null, en caso de
     * que el estado resulte ser de Aceptacion se retorna un ArrayList vacio que indica que la secuencia minima
     * es la secuencia nula, en otro caso, se deben recorrer todos los estados a los cuales se puede realizar transición
     * partiendo desde el estado ingresado cmo argumento, y en caso de que los estados de transición no hallan sido
     * visitados se llama el algoritmo recursivamente para continuar la busqueda.
     * @param e el estado a partir del cual se desea realizar la busqueda de secuencias minimas.
     * @return las secuencias minimas que se pueden reconocer con este automata finito.
     */
    public ArrayList<String> secuenciasMinimas(Estado e)
    {
        //el estado es null o de error
        if(e == null || this.isError(e))
        {
            return null;
        }
        //el estado es de aceptacion, secuencia mínima la secuencia nula.
        if(e.isAceptacion())
        {
            return new ArrayList<String>();
        }
        //indice del estado ingresado como argumento
        int i = e.getIndex();
        //contendrá las secuencias mínimas
        ArrayList<String> minimos = new ArrayList<String>();
        //contendrá la longitud de la secuencia mínima
        int sMin = Integer.MAX_VALUE;
        //se recorre la fila correspondiente al estado ingresado como argumento en la tabla de transiciones
        for(int j = 0; j < simbolosEntrada.size(); j++)
        {
            //se obtiene el conjunto de estados al cual se realiza transición
            Conjunto c = transiciones[i][j];
            //se obtiene el iterador para recorrer el conjunto de estados
            Iterator it = c.iterator();
            while(it.hasNext())
            {
                //se obtiene el estado de transicion
                Estado estado = (Estado)it.next();
                //si estado no ha sido visitado o es un Estado de Aceptación se debe llamar 
                //el algoritmo recursivamente
                if(!visitados[estado.getIndex()] || estado.isAceptacion())
                {
                    //el estado actual ya fue visitado
                    visitados[e.getIndex()] = true;
                    //s contendrá la secuencia mínima que se puede obtener a partir de estado, impidiendo ciclo
                    //hasta e
                    ArrayList<String> s = this.secuenciasMinimas(estado);
                    //el estado de transición se indica como no visitado con el objetivo de 
                    //que en la próxima llamada recursiva pueda ser visitado, esto implica 
                    //Backtracking!
                    visitados[estado.getIndex()] = false;
                    //el conjunto de secuencias minimas no es null
                    if(s != null)
                    {
                        //el conjunto de secuencias obtenidas indica que la secuencia mínima es secuencia nula
                        if(s.isEmpty())
                        {
                            //si el estado al que se realizó a transición es de Aceptación
                            //la secuencia mínima será el símbolo de entrada para el cual se 
                            //realizó la transición
                            if(estado.isAceptacion())
                            {
                                //todos los elementos actualmente en el ArrayList no son secuencias mínimas
                                if(sMin > 1)
                                {
                                    minimos.clear();
                                    sMin = 1;
                                }
                                minimos.add(((Lexema)simbolosEntrada.get(j)).toString());
                            }
                        }
                        else
                        {
                            String x = s.get(0);
                            //se evalua si s contiene secuencias mínimas
                            if(x.length()+1 <= sMin)
                            {
                                //si la longitud de las secuencias de s es menor que 
                                //la longitud mínima de las secuencias se borran y se modifica 
                                //el valor de la longitud minima
                                if(x.length()+1 < sMin)
                                {
                                    minimos.clear();
                                    //la longitud de al secuencia más la longitud de el símbolo de entrada actual
                                    sMin = x.length()+1;
                                }
                                //se concatenan todas las secuencias mínimas obtenidas con el simbolo de entrada actual
                                for(int k = 0; k < s.size(); k++)
                                {
                                    String ss = ((Lexema)simbolosEntrada.get(j)).toString()+s.get(k);
                                    minimos.add(ss);
                                }
                            }
                        }
                    }
                }
            }
        }
        return minimos;
    }
    /**
     * Método utilizado para obtener el indice del estado de error en un automata finito.
     * Dicho estado se define como el estado para el cual todas las transiciones realizadas
     * son hacia el mismo estado, y además el estado es de rechazo.
     * @return el indice del arreglo de estados en el cual se encuentra el error, o -1 en caso de que no exista.
     */
    public int indexOfError()
    {
        for(int i = 0; i < estados.size(); i++)
        {
            Estado e = (Estado)estados.get(i);
            //evalua si el estado actual es de error
            boolean error = this.isError(e);
            if(error)
            {
                return i;
            }
        }
        return -1;
    }
    /**
     * Método utilizado para evaluar si el estado actual es un estado de error.
     * @param e estado que se desea evaluar si es o no un estado de error.
     * @return true si e es un estado de error, false en caso contrario
     */
    public boolean isError(Estado e)
    {
        int i = e.getIndex();
        boolean error = !e.isAceptacion();
        for(int j = 0; j < simbolosEntrada.size() && error; j++)
        {
            Conjunto s = transiciones[i][j];
            if(s.size() > 1)
            {
                return false;
            }
            Iterator it = s.iterator();
            if(it.hasNext())
            {
                Estado x = (Estado)it.next();
                error = x.equals(e);
            }
            else
            {
                error = false;
            }
        }
        return error;
    }
    /**
     * Método utilizado para agregar un estado de error al automata finito, en caso de que este no tenga uno.
     */
    public void agregarError()
    {
        Estado error = null;
        int iError = this.indexOfError();
        if(iError < 0)
        {
            error = new Estado(estados.size(), false, "error");
            estados.add(error);
        }
        else
        {
            error = (Estado)estados.get(iError);
        }
        Conjunto nuevasTransiciones[][] = new Conjunto[estados.size()][simbolosEntrada.size()];
        for(int i = 0; i < estados.size(); i++)
        {
            for(int j = 0; j < simbolosEntrada.size(); j++)
            {
                Conjunto c = null;
                if(i < estados.size() - 1)
                {
                    c = transiciones[i][j];
                }
                if(c == null)
                {
                    c = new Conjunto();
                }
                if(c.isEmpty())
                {
                    c.add(error);
                }
                nuevasTransiciones[i][j] = c;
            }
        }
        this.transiciones = nuevasTransiciones;
    }
    /**
     * Método que permite crear un automata finito a partir de un archivo de texto ingresado como argumento que
     * contiene un String que se encuentra escrito de acuerdo al formato establecido.
     * @param file archivo de texto plano que contiene una tabla que representa un automata finito deterministico.
     * @return Automata finito deterministico correspondiente al archivo especificado como argumento
     * @throws FileNotFoundException, IOException
     */
    public static Automata fromFile(java.io.File file) throws FileNotFoundException, IOException
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
        return new Automata(s.toString());
    }
    /**
     * Método que permite particionar un automata finito para realizar la simplificación de un automata finito.
     * @param conjuntos arreglo que contiene cada una de las particiones previas efectuadas en el automata finito,
     * inicialmente contiene solo 2 conjuntos que corresponden a los estados de aceptación y los de rechazo.
     * @param i corresponde al indice del conjunto que se desea particionar
     * @param t valor que indica el numero de conjuntos que han sido creados mediante la realización de las particiones.
     * si se realizan particiones su valor se incrementara.
     * @param k indice dentro del conjunto de simbolos de entrada que corresponde al simbolo de entrada que se
     * probara para realizar la partición.
     * @return el número de conjuntos que resultan luego de realizar la partición.
     */
    public int particionar(int i, int t, int k, Conjunto[] conjuntos)
    {
        //conjunto que se desea particionar
        Conjunto set = conjuntos[i];
        //conjuntos previamente construidos
        Conjunto temp[] = new Conjunto[t];
        //se recorre el conjunto actual
        Iterator ite = set.iterator();
        while(ite.hasNext())
        {
            //estado actual
            Estado e = (Estado)ite.next();
            Iterator it = transiciones[e.getIndex()][k].iterator();
            //estado actual
            
            Estado s = (Estado)it.next();
            //se establece el indice del estado actual en el arreglo de conjuntos, luego los 
            //estados que queden ubicados en los mismos conjuntos deben quedar unidos en uno solo
            //luego de finalizar esta iteración
            int index = index(conjuntos, s);
            //si el conjunto actual no ha sido incializado se incializa
            if(temp[index] == null)
            {
                temp[index] = new Conjunto();
            }
            //Se agrega el estado e en la lista de estados
            temp[index].add(e);
            
        }
        int j = 0;
        //se obtiene el primer elemento no nulo del arreglo de estados
        while(j  < temp.length - 1 && temp[j] == null)
        {
            j++;
        }
        conjuntos[i] = temp[j];
        j++;
        //se almacenan todos los elementos no nulos del arreglo 
        //de estados temporales en el conjunto inicial
        while(j < temp.length)
        {
            if(temp[j] != null)
            {
                //se incrementa el número de conjuntos
                conjuntos[t] = temp[j];
                t++;
            }
            j++;
        }
        return t;
    }
    /**
     * Método que evalua si el conjunto ingresado como argumento contiene almenos un
     * estado de aceptación.
     * @param estados conjunto de estados
     * @return true si el conjunto ingresado como argumento contiene almenos un estado de aceptación, 
     * false en caso contrario
     */
    public boolean aceptacion(Conjunto estados)
    {
        Iterator i = estados.iterator();
        while(i.hasNext())
        {
            Estado e = (Estado)i.next();
            if(e.isAceptacion())
            {
                return true;
            }
        }
        return false;
    }
    /**
     * Método que permite evaluar si este automata finito es deterministico.
     * @return true si el automata es deterministico, false en caso contrario
     */
    public boolean isDeterministico()
    {
        for(int i = 0; i < estados.size(); i++)
        {
            for(int j = 0; j < simbolosEntrada.size(); j++)
            {
                if(transiciones[i][j].size() > 1)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    /**
     * Método utilizado para convertir un automata finito no determinístico a determinístico,
     * para esto va creando nuevos estado según vayan apareciendo. Aunque al convertir un
     * automata finito no determinístico a determínistico se puede mejorar la eficiencia,
     * para automatas finitos con 30 estados, para convertirlo a no deterministico en peor de los
     * casos se requerirían 2^30 nuevos estados, por lo tanto se debe tener en cuenta este hecho a
     * la hora de decidir que tipo de automata utilizar.
     */
    public void convertirDeterministico()
    {
        Conjunto nuevosEstados = new Conjunto();
        Conjunto nuevasTransiciones[][] = new Conjunto[(int)Math.pow(2, estados.size())][simbolosEntrada.size()];
        int k = 1;
        Estado e = new Estado(0, aceptacion(estadoInicial));
        e.setId(estadoInicial);
        nuevosEstados.add(e);
        for(int i = 0; i < k; i++)
        {
            e = (Estado)nuevosEstados.get(i);
            for(int j = 0; j < simbolosEntrada.size(); j++)
            {
                Conjunto s = (Conjunto)e.getId();
                Iterator ite = s.iterator();
                nuevasTransiciones[i][j] = new Conjunto();
                Conjunto id = new Conjunto();
                while(ite.hasNext())
                {
                    Estado estado = (Estado)ite.next();
                    Conjunto sa = transiciones[estado.getIndex()][j];
                    if(!sa.isEmpty())
                    {
                        id.addAll(sa);
                    }
                }
                Estado n = new Estado(k, aceptacion(id));
                n.setId(id);
                if(!nuevosEstados.contains(n) && !id.isEmpty())
                {
                    nuevosEstados.add(n);
                    nuevasTransiciones[i][j].add(n);
                    k++;
                }
                else if(!id.isEmpty())
                {
                    nuevasTransiciones[i][j].add(nuevosEstados.get(nuevosEstados.indexOf(n)));
                }
            }
        }
        transiciones = new Conjunto[k][simbolosEntrada.size()];
        for(int i = 0; i < k; i++)
        {
            System.arraycopy(nuevasTransiciones[i], 0, transiciones[i], 0, simbolosEntrada.size());
        }
        estados = nuevosEstados;
        estadoInicial = new Conjunto();
        estadoInicial.add(estados.get(0));
    }
    /**
     * Método utilizado para eliminar los estados inalcanzables de un automata finito
     * no deterministico.
     */
    public void eliminarInalcanzables()
    {
        //conjunto que contendrá los estados luego de la simplificación
        Conjunto nuevosEstados = new Conjunto();
        Conjunto nuevasTransiciones[][] = new Conjunto[estados.size()][simbolosEntrada.size()];
        int k = 1;
        int z = 0;
        Iterator it = estadoInicial.iterator();
        nuevosEstados.add((Estado)(it.next()));
        //se recorren todas los estados del automata finito y se agregan al conjunto de simbolos de entrada
        //solo los estados que van apareciendo
        for(int i = 0; i < k && i < estados.size(); i++)
        {
            //se obtiene el indice del estado actual en el conjunto de estados
            z = ((Estado)nuevosEstados.get(i)).getIndex();
            for(int j = 0; j < simbolosEntrada.size(); j++)
            {
                it = transiciones[z][j].iterator();
                Estado e = (Estado)it.next();
                nuevasTransiciones[i][j] = new Conjunto();
                nuevasTransiciones[i][j].add(e);
                if(!nuevosEstados.contains(e))
                {
                    nuevosEstados.add(e);
                    k++;
                }
            }
        }
        transiciones = new Conjunto[k][simbolosEntrada.size()];
        for(int i = 0; i < k & i < estados.size(); i++)
        {
            System.arraycopy(nuevasTransiciones[i], 0, transiciones[i], 0, simbolosEntrada.size());
        }
        for(int i = 0; i < nuevosEstados.size(); i++)
        {
            ((Estado)nuevosEstados.get(i)).setIndex(i);
        }
        estados = nuevosEstados;
    }
    /**
     * Método que permite obtener la representación como tabla de este automata finito.
     * @return representación como tabla del automata finito
     */
    public String getTabla()
    {
        StringBuilder x = new StringBuilder("\t");
        Iterator it = simbolosEntrada.iterator();
        while(it.hasNext())
        {
            Lexema l = (Lexema)it.next();
            String s = l.getSimbolos();
            s = Automata.replace(s, "\\", "\\\\");
            s = Automata.replace(s, " ", "\\b");
            s = Automata.replace(s, "\n", "\\n");
            s = Automata.replace(s, "\t", "\\t");
            x.append(s).append("\t");
        }
        x.append("\n");
        for(int i = 0; i < estados.size(); i++)
        {
            x.append(((Estado)estados.get(i)).getId()).append("\t");
            for(int j = 0; j < simbolosEntrada.size(); j++)
            {
                if(this.isDeterministico() && it.hasNext())
                {
                    it = transiciones[i][j].iterator();
                    Estado e = (Estado)it.next();
                    x.append(e.getId()).append("\t");
                }
                else
                {
                    x.append(transiciones[i][j]).append("\t");
                }
            }
            x.append(((Estado)estados.get(i)).isAceptacion()? '1' : '0').append("\n");
        }
        return x.toString();
    }
    @Override
    public String toString()
    {
        return this.getName();
    }
    /**
     * Método utilizado para unir estados equivalentes en un automata finito deterministico.
     */
    public void reducir()
    {
        //solo hay un estado, no hay simplificación que se pueda efectuar
        if(estados.size() <= 1)
        {
            return;
        }
        Conjunto conjuntos[] = new Conjunto[estados.size()];
        //los conjuntos se inicializan en null
        for(int i = 0; i < conjuntos.length; i++)
        {
            conjuntos[i] = null; 
        }
        conjuntos[0] = new Conjunto();
        conjuntos[1] = new Conjunto();
        //se comienza con dos conjuntos, los estados de aceptacion y los estados de rechazo.
        for(int i = 0; i < estados.size(); i++)
        {
            if(((Estado)estados.get(i)).isAceptacion())
            {
                //estado de aceptacion
                conjuntos[1].add(estados.get(i));
            }
            else
            {
                //estado de rechazo
                conjuntos[0].add(estados.get(i));
            }
        }
        //solo hay estados de aceptación
        if(conjuntos[0].isEmpty())
        {
            Conjunto nuevosEstados = new Conjunto();
            Estado e = new Estado(0, true, conjuntos[1]);
            nuevosEstados.add(e);
            //se crea un automata finito con un estado
            Conjunto nuevasTransiciones[][] = new Conjunto[1][simbolosEntrada.size()];
            for(int j = 0; j < simbolosEntrada.size(); j++)
            {
                nuevasTransiciones[0][j] = new Conjunto();
                nuevasTransiciones[0][j].add(e);
            }
            estadoInicial.clear();
            estadoInicial.add(e);
            estados = nuevosEstados;
            transiciones = nuevasTransiciones;
            return;
        }
        //solo hay estados de rechazo
        if(conjuntos[1].isEmpty())
        {
            Conjunto nuevosEstados = new Conjunto();
            Estado e = new Estado(0, false, conjuntos[0]);
            nuevosEstados.add(e);
            //se crea un automata finito de un estado
            Conjunto nuevasTransiciones[][] = new Conjunto[1][simbolosEntrada.size()];
            for(int j = 0; j < simbolosEntrada.size(); j++)
            {
                nuevasTransiciones[0][j] = new Conjunto();
                nuevasTransiciones[0][j].add(e);
            }
            estadoInicial.clear();
            estadoInicial.add(e);
            estados = nuevosEstados;
            transiciones = nuevasTransiciones;
            return;
        }
        int i = 0;
        int t = 2;
        while(i < t)
        {
            //se probarán todos los conjuntos para cada simbolo de entrada
            for(int k = 0; k < simbolosEntrada.size(); k++)
            {
                //se particiona el conjunto actual
                t = this.particionar(i, t, k, conjuntos);
                //en caso de que se haya particionado el conjunto, se deben probar todos los
                //conjuntos particionados previamente
                for(int j = 0; j <= i; j++)
                {
                    //se prueba el conjunto para todos los simbolos de entrada
                    for(int s = 0; s < simbolosEntrada.size(); s++)
                    {
                        t = this.particionar(j, t, s, conjuntos);
                    }
                }
            }
            i++;
        }
        Estado inicial = (Estado)estados.get(0);
        //se obtiene el indice del conjunto en el cual se encuentra el estado inicial del automata finito
        int index = index(conjuntos, inicial);
        Conjunto nuevosEstados = new Conjunto();
        //se crea un estado que tiene como id el conjunto correspondiente al estado inicial del automata finito
        Estado e = new Estado(0, aceptacion(conjuntos[index]), conjuntos[index]);
        estadoInicial.clear();
        estadoInicial.add(e);
        nuevosEstados.add(e);
        int k = 1;
        //se crean los nuevos estados simplificados
        for(i = 0; i < t; i++)
        {
            if(i != index)
            {
                if(k != index)
                {
                    nuevosEstados.add(new Estado(k, aceptacion(conjuntos[k]), conjuntos[k]));   
                }
                else
                {
                    nuevosEstados.add(new Estado(k, aceptacion(conjuntos[0]), conjuntos[0]));
                }
                k++;
            }
        }
        Conjunto nuevasTransiciones[][] = new Conjunto[t][simbolosEntrada.size()];
        Conjunto s = conjuntos[index];
        Estado estate = (Estado)s.get(0);
        //se establecen las transiciones del estado inicial del nuevo automata finito
        for(int j = 0; j < simbolosEntrada.size(); j++)
        {
            Conjunto c = transiciones[estate.getIndex()][j];
            if(c.size() > 0)
            {
            Estado next = (Estado)(transiciones[estate.getIndex()][j]).get(0);
            int indice = index(conjuntos, next);
            nuevasTransiciones[0][j] = new Conjunto();
            if(indice == index)
            {
                nuevasTransiciones[0][j].add(nuevosEstados.get(0));
            }
            else
            {
                if(indice !=0)
                {
                    nuevasTransiciones[0][j].add(nuevosEstados.get(indice));
                }
                else
                {
                    nuevasTransiciones[0][j].add(nuevosEstados.get(index));
                }
            }
            }
            else
            {
                nuevasTransiciones[0][j] = new Conjunto();
            }
        }
        //se establecen las transiciones del automata finito
        for(i = 1; i < t; i++)
        {
            if(i != index)
            {
                s = conjuntos[i];
            }
            else
            {
                s = conjuntos[0];
            }
            estate = (Estado)s.get(0);
            for(int j = 0; j < simbolosEntrada.size(); j++)
            {
                Conjunto c = transiciones[estate.getIndex()][j];
                if(c.size() > 0)
                {
                Estado next = (Estado)(transiciones[estate.getIndex()][j]).get(0);
                int indice = index(conjuntos, next);
                nuevasTransiciones[i][j] = new Conjunto();
                if(indice == index)
                {
                    nuevasTransiciones[i][j].add(nuevosEstados.get(0));
                }
                else
                {
                    if(indice !=0)
                    {
                        nuevasTransiciones[i][j].add(nuevosEstados.get(indice));
                    }
                    else
                    {
                        nuevasTransiciones[i][j].add(nuevosEstados.get(index));
                    }
                }
                }
                else
                {
                    nuevasTransiciones[i][j] = new Conjunto();
                }
            }
        }
        estados = nuevosEstados;
        transiciones = nuevasTransiciones;
    }
    /**
     * Obtiene el indice del estado ingresado como argumento dentro del arreglo de conjuntos, es decir
     * el conjunto que contiene el estado ingresado como parametro.
     * @param e Estado que se desea buscar en el arreglo de conjuntos ingresado como argumento.
     * @param x arreglo de cojuntos que contienen estados del automata finito particionados
     * @return indice del conjunto que contiene el estado ingresado como arguemnto o -1 si no existe
     */
    public int index(Conjunto[] x, Estado e)
    {
        for(int i = 0; i < x.length; i++)
        {
            if(x[i] != null && x[i].contains(e))
            {
                return i;
            }
        }
        return -1;
    }
    /**
     * Método qu permite evaluar si el automata finito acepta el String ingresado como arguemnto.
     * @param estadoInicial el estado a partir del cual se desea evaluar la entrada
     * @param s String que se desea validar con el automata finito
     * @return true si el valor de entrada es aceptado por el automata finito, false en caso contrario
     */
    public boolean validar(String s, Estado estadoInicial)
    {
        int i = 0;
        Estado e = estadoInicial;
        if(e == null || s == null)
        {
            return false;
        }
        //se recorren todos los caracteres de entrada
        while(i < s.length())
        {
            Character c = s.charAt(i);
            //se evalua la columna del estado al cual se debe realizar la transición
            int j = this.indexOf(c);
            if(j < 0)
            {
                return false;
            }
            Iterator it = ((Conjunto)transiciones[e.getIndex()][j]).iterator();
            e = (Estado)it.next();
            i++;
        }
        return e.isAceptacion();
    }
    /**
     * Contsructor sin argumentos
     */
    public Automata() 
    {
    }
    /**
     * Crea un nuevo automata a partir de los parametros de entrada.
     * @param aceptacion Conjunto que contiene los indices de los estados de aceptación 
     * del automata finito.
     * @param simbolosEntrada los simbolos de entrada del automata finito
     * @param x arreglo bidimensional qye contiene las transiciones de los automatas finitos.
     */
    public void crearAutomata(Integer x[][], Conjunto simbolosEntrada, Conjunto aceptacion)
    {
        this.simbolosEntrada = (Conjunto)simbolosEntrada.clone();
        transiciones = new Conjunto[x.length][x[0].length];
        estados = new Conjunto();
        for(int i = 0; i < x.length; i++)
        {
            estados.add(new Estado(i, aceptacion.contains(i),i));
        }
        estadoInicial = new Conjunto();
        estadoInicial.add((Estado)estados.get(0));
        for(int i = 0; i < x.length; i++)
        {
            for(int j = 0; j < x[0].length; j++)
            {
                transiciones[i][j] = new Conjunto();
                transiciones[i][j].add(estados.get(x[i][j]));
            }
        }
    }
    public Automata(Conjunto simbolosEntrada, Conjunto[][] transiciones, Conjunto estados, Conjunto estadoInicial) 
    {
        this.simbolosEntrada = simbolosEntrada;
        this.transiciones = transiciones;
        this.estadoInicial = estadoInicial;
    }
    public Estado getEstadoInicial() {
        return estadoInicial.isEmpty()? null : (Estado)(estadoInicial.iterator().next());
    }
    public void setEstados(Conjunto estados) {
        this.estados = estados;
    }
    public Conjunto getEstados() {
        return estados;
    }
    public Conjunto getSimbolosEntrada() {
        return simbolosEntrada;
    }
    public void setEstadoInicial(Conjunto estadoInicial) {
        this.estadoInicial = estadoInicial;
    }
    public void setSimbolosEntrada(Conjunto simbolosEntrada) {
        this.simbolosEntrada = simbolosEntrada;
    }
    public Conjunto[][] getTransiciones() {
        return transiciones;
    }
    public void setTransiciones(Conjunto[][] transiciones) {
        this.transiciones = transiciones;
    }
    @Override
    public boolean equals(Object x)
    {
        if(x instanceof Automata)
        {
            return this.getTabla().equals(((Automata)x).getTabla());
        }
        return false;
    }
    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 13 * hash + (this.simbolosEntrada != null ? this.simbolosEntrada.hashCode() : 0);
        hash = 13 * hash + Arrays.deepHashCode(this.transiciones);
        hash = 13 * hash + (this.estadoInicial != null ? this.estadoInicial.hashCode() : 0);
        hash = 13 * hash + (this.estados != null ? this.estados.hashCode() : 0);
        hash = 13 * hash + (this.pilaErrores != null ? this.pilaErrores.hashCode() : 0);
        return hash;
    }
}
