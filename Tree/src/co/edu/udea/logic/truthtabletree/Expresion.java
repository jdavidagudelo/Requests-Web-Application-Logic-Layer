package co.edu.udea.logic.truthtabletree;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Stack;
/**
 * Clase que representa una expresión aritmetica o booleana realizada en un lenguaje de alto nivel.
 */
public class Expresion implements Serializable, Clonable
{
    /**
     * Árbol binario AVL que contiene las variables de la expresión, el identificador de las variables
     * debe ser único.
     */
    private ArbolLista<String, Variable> variables = null;
    /**
     * Este árbol binario representa la expresión, de tal manera que cada hoja es un operando de la
     * expresión y cada nodo interno representa una operación, al recorrer este árbol en posorden se obtiene la expresión posfijo
     * correspondiente a la expresión original, esta es la que será utilizada para realizar la evaluación de la expresión, para
     * cualquier conjunto de valores de las variables definidas.
     */
    private ArbolLista operaciones = null;
    /**
     * String que contiene la expresión representada escrita en notación infijo. Al operar las expresiones esta puede ser modificada eliminando
     * espacios en blanco y caracteres sobrantes.
     */
    private String expresion = null;
    /**
     * Constructor que permite crear una expresión con los árboles de variables y operaciones
     * especificados como argumentos
     */
    public Expresion(ArbolLista variables, ArbolLista operaciones)
    {
        this.operaciones = operaciones;
        this.variables = variables;
    }
    public Expresion(Expresion exp)
    {
        this.expresion = exp.expresion.toString();
        this.operaciones = new ArbolLista(exp.operaciones);
        this.variables = new ArbolLista(exp.variables);
    }
    public Expresion(String expresion) throws Exception
    {
        this.expresion = expresion;
        this.crearExpresion();
    }
    public void setExpresion(String expresion)
    {
        this.expresion = expresion;
    }
    public String getExpresion()
    {
        return expresion;
    }
    /**
     * Método que permite incializar las variables de la expresión, suponiendo que en la expresión todas las variables
     * son del mismo tipo. Para esto se utiliza la clase Class que representa la clase de los objetos que se desean utilizar como
     * valores de las variables de la expresión, esta clase contiene métodos y constructores de la clase, los cuales pueden ser utilizados
     * conociendolos y realizando castings y otras modificaciones. En este caso solo interesa crear un nuevo objeto de la clase
     * con el constructor por defecto, el cual no recibe argumentos.
     * @param c objeto de la clase Class que permite acceder a todos los métodos de la clase que esta representa y permitira crear una nueva
     * instancia de los objetos que seran utilizados como variables en la expresión
     */
    public void inicializarVariables(Class c) throws Exception
    {
        //Se obtiene el constructor por defecto de la clase c
        Constructor cons = c.getConstructor();
        //se obtiene el arreglo que contiene las variables ordenadas de manera ascendente según el id
        Object inorden[] = variables.inordenMejorado();
        //se recorre todo el arreglo de variables
        for(int i = 0; i < inorden.length; i++)
        {
            //se obtiene la variable en la posición actual del recorrido inorden
            Variable v = (Variable)inorden[i];
            //se establece el valor almacenado en la variable utilizando el método newInstance de la clase Constructor el cual
            //para este caso es equivalente a crear un nuevo objeto de la clase ingresada como argumento sin parametros en el constructor
            v.setValue(cons.newInstance());
        }
    }
    /**
     * Este método permite obtener un arreglo que contiene los identificadores de cada una de las variables
     * que conforman la expresión, este arreglo estará ordenado alfabeticamente.
     * @return Un arreglo que contiene los identificadores de las variables de la expresión ordenados ascendentemente.
     */
    public String[] variables()
    {
        //Se obtiene el arreglo obtenido al recorrer el arbol binario que contiene las variables en inorden, con esto
        //se obtiene dicho arreglo ordenado de manera ascendente de acuerdo con los identificadores de las variables
        Object inorden[] = variables.inordenMejorado();
        String vars[] = new String[inorden.length];
        //se almacenan los identificadores de las variables en un arreglo
        for(int i = 0; i < inorden.length; i++)
        {
            if(inorden[i] instanceof Variable)
            {
                Variable v = (Variable)inorden[i];
                String s = v.getId();
                vars[i] = s;
            }
        }
        return vars;
    }
    /**
     * Método utilizado para CODIFICAR EXPRESIONES BOOLEANAS COMO SUMA DE PRODUCTOS, esto significa que en dicho
     * arreglo deben aparecer todas las posibles combinaciones de las variables que generen en la expresión una sálida TRUE,
     * así por ejemplo la expresión: a&b&c|!a&!b&c|!a&!b&!c podría ser representada en binario como: 111|001|000 y en decimal como
     * 7|1|0, se debe tener en cuenta que el orden de las variables será alfabetico, es decir un conjunto de variables como a, b y c
     * siempre generara esa codificación para la expresión mostrada.
     */
    public int[] codigos() throws Exception
    {
        //se obtiene el arreglo de variables ordenada ascendentemente
        Object inorden[] = variables.inordenMejorado();
        Booleano bol = new Booleano();
        this.inicializarVariables(Booleano.class);
        Stack pila = new Stack();
        int i = 0;
        //el número máximo de codificaciones que es posible generar con n variables es 2^n, por lo tanto se hara un ciclo
        //para evaluar la expresión para cada una de dichas codificaciones, para conocer cuales producen sálida TRUE y por lo
        //tanto deben ser incluidas en el arreglo de codigos
        int p = (int)Math.pow(2, inorden.length);
        for(i = 0; i < p; i++)
        {
            //Arreglo de booleanos que representa el valor de i codificado en binario,
            //este arreglo tiene tantos elementos como variables tiene la expresión
            Booleano b[] = this.decodificar(i);
            //se recorre el arreglo de booleanos para establecer los valores de las variables con
            //los cuales se evaluará la expresión
            for(int j = 0; j < b.length; j++)
            {
                if(inorden[j] instanceof Variable)
                {
                    //se obtiene la variable en la posición j del arreglo de variables
                    Variable v = (Variable)inorden[j];
                    //se obtiene el valor de la variable booleana actual
                    bol = (Booleano)v.getValue();
                    //se establece el valor de la variable booleana actual, con el codificado anteriormente
                    bol.setValor(b[j].getValor());
                }
            }
            //se evalua la expresión luego de modificar los valores de las variables
            Booleano res = this.evaluarBooleano();
            if(res == null)
            {
                throw new Exception("Expresion mal escrita");
            }
            //si al evaluar la expresión el resultado es TRUE se debe agregar el valor codificado en el arreglo de codigos
            if(res.getValor())
            {
                pila.push(i);
            }
        }
        int codigos[] = new int[pila.size()];
        for(i = pila.size() - 1; i >= 0; i--)
        {
            codigos[i] = (Integer)pila.pop();
        }
        return codigos;
    }
    private void crearExpresion() throws Exception
    {
        if(expresion == null)
        {
            throw new NullPointerException("Argumento inválido");
        }
        Tokenizer tokenizer = new Tokenizer(expresion);
        if(!tokenizer.isClosed("(", ")"))
        {
            throw new Exception("Falta cerrar todos los parentesis de la expresión");
        }
        Stack<Symbol> pila = new Stack<Symbol>();
        variables = new ArbolLista();
        operaciones = new ArbolLista();
        //el arbol de variables se enhebrará inorden con el fin de hacer este recorrido más eficiente
        variables.setOrden(ArbolLista.INORDEN);
        Stack<Symbol> res = new Stack<Symbol>(), pilaInfijo = new Stack<Symbol>();
        int i;
        //valor booleano utilizado para conocer cuando un operador debe ser unario o binario, dado que este debe ser agregado
        //en la pila de la expresión posfija de manera diferente a como se agregaria una expresión binaria
        boolean ope = true;
        //variable para almacenar los substrings
        Symbol temp = null;
        //se eliminan los espacios en blanco que no inciden en la sintaxis, es decir los que no estan contenidos dentro de nombres de variables
        //se recorre toda la expresion infija
        while(tokenizer.getIndex() < tokenizer.getCode().length())
        {
            //Se obtiene el siguente nombre del infijo, a partir de la posicion actual,
            //este es o una constante, una variable, o el simbolo de una operación
            temp = tokenizer.nextSymbol();
            //se evalua si el simbolo actual no es un parentesis abierto o cerrado
            if(!temp.equals(Symbol.OP_LEFT_PARENTHESIS) & !temp.equals(Symbol.OP_RIGHT_PARENTHESIS))
            {
                //se agrega el valor actual a la pila que contendrá la expresión en infijo, pero sin parentesis
                pilaInfijo.push(temp);
            }
            //se evalua si el nombre contiene mas de un caracter
            if(!temp.isOperator())
            {
                if(!ope)
                {
                    throw new Exception("El operando: \""+temp.getName()+"\" en la linea: "+temp.getLine()+" , columna: "+temp.getColumn()+
                            " no tiene operador asociado");
                }
                //dado que el nombre actual contiene mas de 2 caracteres es el nombre de una variable, por lo tanto
                //se debe apilar en el resultado
                res.push(temp);
                //se inserta la nueva variable en el arbol de expresiones en caso de que no exista ya en este, para este caso
                //específico será un objeto de la clase Booleano
                variables.insertarAVL(temp.getName(), new Variable(null, temp.getName()));
                //dado que se hallo una variable en la expresión el siguiente operador debe ser binario
                ope = false;
                //una vez se halla la variable se apilan en el resultado todos los operadores unarios que se encuentren en la pila
                while(!pila.isEmpty() && pila.peek().equals(Symbol.OP_NOT))
                {
                    res.push(pila.pop());
                }
            }
            else if(temp.equals(Symbol.OP_LEFT_PARENTHESIS))
            {
                //si antes del siguiente operador no aparece un operando se supone que es un operador unario
                ope = true;
                pila.push(temp);
            }
            else if(temp.equals(Symbol.OP_RIGHT_PARENTHESIS))
            {
                //una vez se cierra un parentesis necesariamente debe aparecer un operador binario, otro caso indicaria un error en
                //la expresión
                ope = false;
                //se apilan en el resultado todos los simbolos existentes antes del próximo simbolo de parentesis izquierdo
                while(!pila.isEmpty() && !pila.peek().equals(Symbol.OP_LEFT_PARENTHESIS))
                {
                    res.push(pila.pop());
                }
                //se elimina el simbolo de parentesis izquierdo
                if(!pila.isEmpty())
                {
                    pila.pop();
                }
            }
            else if(temp.equals(Symbol.OP_NOT))
            {
                if(ope)
                {
                    pila.push(temp);
                }
                else
                {
                    throw new Exception("El operador \"!\" en la linea: "+temp.getLine()+", columna: "+temp.getColumn()+
                            " No puede ser utilizado como operador binario");
                }
            }
            else if(temp.equals(Symbol.OP_PLUS) | temp.equals(Symbol.OP_MINUS))
            {
                pila.push(temp);
                ope = true;
            }
            else if(temp.isBinaryOperator())
            {
                Symbol top = null;
                if(!pila.isEmpty())
                {
                    top = (Symbol)pila.peek();
                }
                //se recorre la pila de operadores y se desapilan en el resultado todos los operadores hasta encontrar uno
                //con una precedencia dentro de la pila mayor que la precendencia fuera de la pila del operador actual
                while(top != null && (!pila.isEmpty() && top.getPdp() >= temp.getPfp()))
                {
                    //elemento en el tope de la pila
                    top = (Symbol)pila.pop();
                    //se agrega el elemento en el tope de la pila al resultado
                    res.push(top);
                    //si la pila no esta vacia se obtiene el siguiente elemento de la cima y se continua el ciclo si es
                    //necesario
                    if(!pila.isEmpty())
                    {
                        top = (Symbol)pila.peek();
                    }
                }
                //se inserta el operador actual en al pila
                pila.push(temp);
                //si aparece otro operador antes de una variable este debe ser unario
                ope = true;
            }
            else
            {
                throw new Exception("El simbolo: \""+temp.getName()+"\" en la linea: "+temp.getLine()+", columna: "+temp.getColumn() +
                        " es inválido");
            }
        }    
        //se insertan en el resultado los valores que aun queden en la pila
        while(!pila.isEmpty())
        {
            res.push(pila.pop());
        }
        //se obtiene un arreglo que contiene la expresión en posfijo
        Object posfijo[] = new Object[res.size()];
        i = posfijo.length-1;
        while(!res.isEmpty())
        {
            posfijo[i] = res.pop();
            i = i - 1;
        }
        //se obtiene un arreglo que contiene la expresión en infijo
        Object infijo[] = new Object[pilaInfijo.size()];
        i = infijo.length - 1;
        while(!pilaInfijo.isEmpty())
        {
            infijo[i] = pilaInfijo.pop();
            i = i - 1;
        }
        //se crea el árbol de expresión con base en los recorridos posorden e inorden hallados previamente
        operaciones = ArbolLista.crearArbolPos(posfijo, infijo);
        //se enhebra en posorden el árbol de expresión con el fin de hacer más eficiente el recorrido posorden en dicho árbol
        operaciones.posorden();
        operaciones.setAVL(false);
        operaciones.setSearch(false);
        tokenizer.eliminarBlancos();
        //se establece la expresión que contendra el objeto
        this.setExpresion(tokenizer.getCode().toString());
    }
    /**
     * Metodo que permite obtener la expresión representada por el string ingresado como argumento.
     * @param expresion la expresión para la cual se desea construir el árbol
     */
    public static Expresion arbolExpresiones(String expresion) throws Exception
    {
        Tokenizer tokenizer = new Tokenizer(expresion);
        if(!tokenizer.isClosed("(", ")"))
        {
            throw new Exception("Falta cerrar todos los parentesis de la expresión");
        }
        Stack<Symbol> pila = new Stack<Symbol>();
        ArbolLista variables = new ArbolLista();
        ArbolLista operaciones = new ArbolLista();
        //el arbol de variables se enhebrará inorden con el fin de hacer este recorrido más eficiente
        variables.setOrden(ArbolLista.INORDEN);
        Stack<Symbol> res = new Stack<Symbol>(), pilaInfijo = new Stack<Symbol>();
        int i;
        //valor booleano utilizado para conocer cuando un operador debe ser unario o binario, dado que este debe ser agregado
        //en la pila de la expresión posfija de manera diferente a como se agregaria una expresión binaria
        boolean ope = true;
        //variable para almacenar los substrings
        Symbol temp = null;
        //se eliminan los espacios en blanco que no inciden en la sintaxis, es decir los que no estan contenidos dentro de nombres de variables
        //se recorre toda la expresion infija
        while(tokenizer.getIndex() < tokenizer.getCode().length())
        {
            //Se obtiene el siguente nombre del infijo, a partir de la posicion actual,
            //este es o una constante, una variable, o el simbolo de una operación
            temp = tokenizer.nextSymbol();
            //se evalua si el simbolo actual no es un parentesis abierto o cerrado
            if(!temp.equals(Symbol.OP_LEFT_PARENTHESIS) & !temp.equals(Symbol.OP_RIGHT_PARENTHESIS))
            {
                //se agrega el valor actual a la pila que contendrá la expresión en infijo, pero sin parentesis
                pilaInfijo.push(temp);
            }
            //se evalua si el nombre contiene mas de un caracter
            if(!temp.isOperator())
            {
                if(!ope)
                {
                    throw new Exception("El operando: \""+temp.getName()+"\" en la linea: "+temp.getLine()+" , columna: "+temp.getColumn()+
                            " no tiene operador asociado");
                }
                //dado que el nombre actual contiene mas de 2 caracteres es el nombre de una variable, por lo tanto
                //se debe apilar en el resultado
                res.push(temp);
                //se inserta la nueva variable en el arbol de expresiones en caso de que no exista ya en este, para este caso
                //específico será un objeto de la clase Booleano
                variables.insertarAVL(temp.getName(), new Variable(null, temp.getName()));
                //dado que se hallo una variable en la expresión el siguiente operador debe ser binario
                ope = false;
                //una vez se halla la variable se apilan en el resultado todos los operadores unarios que se encuentren en la pila
                while(!pila.isEmpty() && pila.peek().equals(Symbol.OP_NOT))
                {
                    res.push(pila.pop());
                }
            }
            else
            {
                //si se halla el simbolo de parentesis izquierdo se apila en la pila de operadores
                if(temp.equals(Symbol.OP_LEFT_PARENTHESIS))
                {
                    //si antes del siguiente operador no aparece un operando se supone que es un operador unario
                    ope = true;
                    pila.push(temp);
                }
                else
                {
                    //si se halla un parentesis derecho se apilan en el resultado todos los operadores hasta el siguiente simbolo
                    //de parentesis izquierdo y luego se elimina de la pila ese parentesis
                    if(temp.equals(Symbol.OP_RIGHT_PARENTHESIS))
                    {
                        //una vez se cierra un parentesis necesariamente debe aparecer un operador binario, otro caso indicaria un error en
                        //la expresión
                        ope = false;
                        //se apilan en el resultado todos los simbolos existentes antes del próximo simbolo de parentesis izquierdo
                        while(!pila.isEmpty() && !pila.peek().equals(Symbol.OP_LEFT_PARENTHESIS))
                        {
                            res.push(pila.pop());
                        }
                        //se elimina el simbolo de parentesis izquierdo
                        if(!pila.isEmpty())
                        {
                            pila.pop();
                        }
                    }
                    else
                    {
                        //se debe evaluar el operador "!" como un caso especial dado que es el unico operador que solo puede ser unario, por lo tanto
                        //en caso de que en la expresión se halla escrito de otra manera la expresión estara incorrecta
                        if(temp.equals(Symbol.OP_NOT))
                        {
                            if(ope)
                            {
                                pila.push(temp);
                            }
                            else
                            {
                                throw new Exception("El operador \"!\" en la linea: "+temp.getLine()+", columna: "+temp.getColumn()+
                                        " No puede ser utilizado como operador binario");
                            }
                        }
                        else
                        {
                            //si el operador es unario se apila en la pila de operadores
                            if(temp.equals(Symbol.OP_PLUS) | temp.equals(Symbol.OP_MINUS))
                            {
                                pila.push(temp);
                                ope = true;
                            }
                            else
                            {
                                //se evalua si el caracter actual es una operacion
                                if(temp.isBinaryOperator())
                                {
                                    Symbol top = null;
                                    if(!pila.isEmpty())
                                    {
                                        top = (Symbol)pila.peek();
                                    }
                                    //se recorre la pila de operadores y se desapilan en el resultado todos los operadores hasta encontrar uno
                                    //con una precedencia dentro de la pila mayor que la precendencia fuera de la pila del operador actual
                                    while(top != null && (!pila.isEmpty() && top.getPdp() >= temp.getPfp()))
                                    {
                                        //elemento en el tope de la pila
                                        top = (Symbol)pila.pop();
                                        //se agrega el elemento en el tope de la pila al resultado
                                        res.push(top);
                                        //si la pila no esta vacia se obtiene el siguiente elemento de la cima y se continua el ciclo si es
                                        //necesario
                                        if(!pila.isEmpty())
                                        {
                                            top = (Symbol)pila.peek();
                                        }
                                    }
                                    //se inserta el operador actual en al pila
                                    pila.push(temp);
                                    //si aparece otro operador antes de una variable este debe ser unario
                                    ope = true;
                                }
                                else
                                {
                                    throw new Exception("El simbolo: \""+temp.getName()+"\" en la linea: "+temp.getLine()+", columna: "+temp.getColumn() +
                                            " es inválido");
                                }
                            }
                        }
                    }
                }
            }
        }
        //se insertan en el resultado los valores que aun queden en la pila
        while(!pila.isEmpty())
        {
            res.push(pila.pop());
        }
        //se obtiene un arreglo que contiene la expresión en posfijo
        Object posfijo[] = new Object[res.size()];
        i = posfijo.length-1;
        while(!res.isEmpty())
        {
            posfijo[i] = res.pop();
            i = i - 1;
        }
        //se obtiene un arreglo que contiene la expresión en infijo
        Object infijo[] = new Object[pilaInfijo.size()];
        i = infijo.length - 1;
        while(!pilaInfijo.isEmpty())
        {
            infijo[i] = pilaInfijo.pop();
            i = i - 1;
        }
        //se crea el árbol de expresión con base en los recorridos posorden e inorden hallados previamente
        operaciones = ArbolLista.crearArbolPos(posfijo, infijo);
        //se enhebra en posorden el árbol de expresión con el fin de hacer más eficiente el recorrido posorden en dicho árbol
        operaciones.posorden();
        operaciones.setAVL(false);
        operaciones.setSearch(false);
        //se crea un objeto de la clase Expresión con base en los arboles creados previamente
        Expresion ex = new Expresion(variables, operaciones);
        tokenizer.eliminarBlancos();
        //se establece la expresión que contendra el objeto
        ex.setExpresion(tokenizer.getCode().toString());
        return ex;
    }
    public static ArbolLista arbolExpresion(String expresion) throws Exception
    {
        
        Tokenizer tokenizer = new Tokenizer(expresion);
        if(!tokenizer.isClosed("(", ")"))
        {
            throw new Exception("Falta cerrar todos los parentesis de la expresión");
        }
        Stack<Symbol> pila = new Stack<Symbol>();
        ArbolLista operaciones = new ArbolLista();
        //el arbol de variables se enhebrará inorden con el fin de hacer este recorrido más eficiente
        Stack<Symbol> res = new Stack<Symbol>(), pilaInfijo = new Stack<Symbol>();
        int i;
        //valor booleano utilizado para conocer cuando un operador debe ser unario o binario, dado que este debe ser agregado
        //en la pila de la expresión posfija de manera diferente a como se agregaria una expresión binaria
        boolean ope = true;
        //variable para almacenar los substrings
        Symbol temp = null;
        //se eliminan los espacios en blanco que no inciden en la sintaxis, es decir los que no estan contenidos dentro de nombres de variables
        //se recorre toda la expresion infija
        while(tokenizer.getIndex() < tokenizer.getCode().length())
        {
            //Se obtiene el siguente nombre del infijo, a partir de la posicion actual,
            //este es o una constante, una variable, o el simbolo de una operación
            temp = tokenizer.nextSymbol();
            //se evalua si el simbolo actual no es un parentesis abierto o cerrado
            if(!temp.equals(Symbol.OP_LEFT_PARENTHESIS) & !temp.equals(Symbol.OP_RIGHT_PARENTHESIS))
            {
                //se agrega el valor actual a la pila que contendrá la expresión en infijo, pero sin parentesis
                pilaInfijo.push(temp);
            }
            //se evalua si el nombre contiene mas de un caracter
            if(!temp.isOperator())
            {
                if(!ope)
                {
                    throw new Exception("El operando: \""+temp.getName()+"\" en la linea: "+temp.getLine()+" , columna: "+temp.getColumn()+
                            " no tiene operador asociado");
                }
                //dado que el nombre actual contiene mas de 2 caracteres es el nombre de una variable, por lo tanto
                //se debe apilar en el resultado
                res.push(temp);
                //dado que se hallo una variable en la expresión el siguiente operador debe ser binario
                ope = false;
                //una vez se halla la variable se apilan en el resultado todos los operadores unarios que se encuentren en la pila
                while(!pila.isEmpty() && pila.peek().equals(Symbol.OP_NOT))
                {
                    res.push(pila.pop());
                }
            }
            else
            {
                //si se halla el simbolo de parentesis izquierdo se apila en la pila de operadores
                if(temp.equals(Symbol.OP_LEFT_PARENTHESIS))
                {
                    //si antes del siguiente operador no aparece un operando se supone que es un operador unario
                    ope = true;
                    pila.push(temp);
                }
                else
                {
                    //si se halla un parentesis derecho se apilan en el resultado todos los operadores hasta el siguiente simbolo
                    //de parentesis izquierdo y luego se elimina de la pila ese parentesis
                    if(temp.equals(Symbol.OP_RIGHT_PARENTHESIS))
                    {
                        //una vez se cierra un parentesis necesariamente debe aparecer un operador binario, otro caso indicaria un error en
                        //la expresión
                        ope = false;
                        //se apilan en el resultado todos los simbolos existentes antes del próximo simbolo de parentesis izquierdo
                        while(!pila.isEmpty() && !pila.peek().equals(Symbol.OP_LEFT_PARENTHESIS))
                        {
                            res.push(pila.pop());
                        }
                        //se elimina el simbolo de parentesis izquierdo
                        if(!pila.isEmpty())
                        {
                            pila.pop();
                        }
                    }
                    else
                    {
                        //se debe evaluar el operador "!" como un caso especial dado que es el unico operador que solo puede ser unario, por lo tanto
                        //en caso de que en la expresión se halla escrito de otra manera la expresión estara incorrecta
                        if(temp.equals(Symbol.OP_NOT))
                        {
                            if(ope)
                            {
                                pila.push(temp);
                            }
                            else
                            {
                                throw new Exception("El operador \"!\" en la linea: "+temp.getLine()+", columna: "+temp.getColumn()+
                                        " No puede ser utilizado como operador binario");
                            }
                        }
                        else
                        {
                            //si el operador es unario se apila en la pila de operadores
                            if(temp.equals(Symbol.OP_PLUS) | temp.equals(Symbol.OP_MINUS))
                            {
                                pila.push(temp);
                                ope = true;
                            }
                            else
                            {
                                //se evalua si el caracter actual es una operacion
                                if(temp.isBinaryOperator())
                                {
                                    Symbol top = null;
                                    if(!pila.isEmpty())
                                    {
                                        top = (Symbol)pila.peek();
                                    }
                                    //se recorre la pila de operadores y se desapilan en el resultado todos los operadores hasta encontrar uno
                                    //con una precedencia dentro de la pila mayor que la precendencia fuera de la pila del operador actual
                                    while(top != null && (!pila.isEmpty() && top.getPdp() >= temp.getPfp()))
                                    {
                                        //elemento en el tope de la pila
                                        top = (Symbol)pila.pop();
                                        //se agrega el elemento en el tope de la pila al resultado
                                        res.push(top);
                                        //si la pila no esta vacia se obtiene el siguiente elemento de la cima y se continua el ciclo si es
                                        //necesario
                                        if(!pila.isEmpty())
                                        {
                                            top = (Symbol)pila.peek();
                                        }
                                    }
                                    //se inserta el operador actual en al pila
                                    pila.push(temp);
                                    //si aparece otro operador antes de una variable este debe ser unario
                                    ope = true;
                                }
                                else
                                {
                                    throw new Exception("El simbolo: \""+temp.getName()+"\" en la linea: "+temp.getLine()+", columna: "+temp.getColumn() +
                                            " es inválido");
                                }
                            }
                        }
                    }
                }
            }
        }
        //se insertan en el resultado los valores que aun queden en la pila
        while(!pila.isEmpty())
        {
            res.push(pila.pop());
        }
        //se obtiene un arreglo que contiene la expresión en posfijo
        Object posfijo[] = new Object[res.size()];
        i = posfijo.length-1;
        while(!res.isEmpty())
        {
            posfijo[i] = res.pop();
            i = i - 1;
        }
        //se obtiene un arreglo que contiene la expresión en infijo
        Object infijo[] = new Object[pilaInfijo.size()];
        i = infijo.length - 1;
        while(!pilaInfijo.isEmpty())
        {
            infijo[i] = pilaInfijo.pop();
            i = i - 1;
        }
        //se crea el árbol de expresión con base en los recorridos posorden e inorden hallados previamente
        operaciones = ArbolLista.crearArbolPos(posfijo, infijo);
        //se enhebra en posorden el árbol de expresión con el fin de hacer más eficiente el recorrido posorden en dicho árbol
        operaciones.posorden();
        operaciones.setAVL(false);
        operaciones.setSearch(false);
        return operaciones;
    }
    /**
     * Método que permite convertir el entero ingresado como argumento en un arreglo de Booleanos
     * de la misma logitud que el árbol de variables.
     * @param i el entero que se desea convertir en un arreglo de booleanos.
     * @return arreglo de booleanos que representa el número ingresado como argumento.
     */
    public Booleano[] decodificar(int i)
    {
        if(i < 0)
        {
            return null;
        }
        Booleano b[] = new Booleano[variables.getLength()];
        for(int j = 0; j < b.length; j++)
        {
            b[j] = new Booleano();
        }
        int c = i;
        int j = b.length - 1;
        while(j >= 0)
        {
            if(c%2 == 1)
            {
                b[j].setValor(Booleano.TRUE);
            }
            else
            {
                b[j].setValor(Booleano.FALSE);
            }
            c = c/2;
            j = j - 1;
        }
        return b;
    }
    /**
     * Este método permite evaluar esta expresión booleana teniendo en cuenta los valores de las variables y
     * la expresión indicada. Para esto utiliza una pila y el recorrido posorden del árbol de expresión,
     * cada vez que en el recorrido posorden se halla un operando este se apila, cada vez que se halla un operador
     * se desapilan tantos operandos de la pila como sea necesario y se realiza la operación con ellos, en este caso
     * para las operaciones &, |, &&, || se requieren dos operandos y son conmutativos, para la operacion ! se requiere solo un
     * operando, luego el resultado de la operación se apila y se continua el proceso hasta finalizar el recorrido posorden,
     * si es correcta la expresión la pila debe contener un único elemento que corresponde al resultado de evaluar la expresión.
     * @return el resultado de evaluar la expresión para el conjunto de valores de las variables definido actualmente.
     */
    public Booleano evaluarBooleano() throws Exception
    {
        //Se obtiene el arreglo que contiene el recorrido posorden del arbol de expresión que es equivalente a la expresión escrita
        //en posfijjo
        Object pos[] = operaciones.posordenMejorado();
        Stack pila = new Stack();
        Booleano x = null;
        int i = 0;
        //se recorre la expresión en posorden
        while(i < pos.length)
        {
            Symbol s = (Symbol)pos[i];
            //se evalua si el elemento actual de  el recorrido posorden no es un operador
            if(!s.isOperator())
            {
                //se obtiene la variable cuyo identificador corresponde al identificador indicado en el árbol de expresión
                Variable v = (Variable)(variables.buscar(s.getName()));
                if(v == null)
                {
                    throw new Exception("El simbolo: "+s.getName()+" en la linea"+s.getLine()+", columna: "+s.getColumn()+" no es válido.");
                }
                //se apila el valor de la variable correspondiente al identificador indicado en el árbol de expresión
                pila.push(v.getValue());
            }
            else
            {
                
                if(s.equals(Symbol.OP_OR) | s.equals(Symbol.OP_ORC))
                {
                    if(pila.isEmpty())
                    {
                        throw new Exception("Al operando: "+s.getName()+" en la línea: "+s.getLine()+", columna: "+s.getColumn()+
                                " le faltan 2 operandos para ser evaluado");
                    }
                    Booleano op1 = (Booleano)pila.pop();
                    if(pila.isEmpty())
                    {
                        throw new Exception("Al operando: "+s.getName()+" en la línea: "+s.getLine()+", columna: "+s.getColumn()+
                                " le falta un operando para ser evaluado");
                    }
                    Booleano op2 = (Booleano)pila.pop();
                    pila.push(op1.or(op2));
                }
                else
                {
                    if(s.equals(Symbol.OP_AND) | s.equals(Symbol.OP_ANDC))
                    {
                        if(pila.isEmpty())
                        {
                            throw new Exception("Al operando: "+s.getName()+" en la línea: "+s.getLine()+", columna: "+s.getColumn()+
                                    " le faltan 2 operandos para ser evaluado");
                        }
                        Booleano op1 = (Booleano)pila.pop();
                        if(pila.isEmpty())
                        {
                            throw new Exception("Al operando: "+s.getName()+" en la línea: "+s.getLine()+", columna: "+s.getColumn()+
                                    " le falta un operando para ser evaluado");
                        }
                        Booleano op2 = (Booleano)pila.pop();
                        pila.push(op1.and(op2));
                    }
                    else
                    {
                        if(s.equals(Symbol.OP_NOT))
                        {
                            if(pila.isEmpty())
                            {
                                throw new Exception("Al operando: "+s.getName()+" en la línea: "+s.getLine()+", columna: "+s.getColumn()+
                                        " le falta un operando para ser evaluado");
                            }
                            Booleano op1 = (Booleano)pila.pop();
                            pila.push(op1.not());
                        }
                        else
                        {
                            throw new Exception("El operador: "+s.getName()+" en la línea: "+s.getLine()+", columna: "+s.getColumn()+" es inválido");
                        }
                    }
                }
            }
            i = i + 1;
        }
        if(pila.size() != 1)
        {
            
            throw new Exception("La expresión esta mal asociada");
        }
        if(!pila.isEmpty())
        {
            x = (Booleano)pila.pop();
        }
        return x;
    }
    public Expresion()
    {
    }
    public ArbolLista getOperaciones()
    {
        return operaciones;
    }
    public ArbolLista getVariables()
    {
        return variables;
    }
    public void setOperaciones(ArbolLista operaciones)
    {
        this.operaciones = operaciones;
    }
    public void setVariables(ArbolLista variables)
    {
        this.variables = variables;
    }
    public Object clonar() 
    {
        return new Expresion(this);
    }
}
