package co.edu.udea.logic.generictree;
import java.util.Stack;
public class ArbolLista
{
    private NodoDoble R = null;
    private boolean search = true;
    private boolean AVL = true;
    /**
     * @deprecated
     */
    private NodoDoble ant = null;
    public static final int PREORDEN = 0, INORDEN = 1, POSORDEN = 2, NULL = 3;
    private Integer orden = NULL;
    private Integer length = 0;
    public void setSearch(boolean search)
    {this.buscar(R);
        this.search = search;
    }
    public boolean isSearch()
    {
        return search;
    }
    public void setR(NodoDoble R)
    {
        this.R = R;
    }
    public static ArbolLista arbolExpresion(String expresion)
    {
        Stack pila = new Stack();
        Stack res = new Stack(), pilaInfijo = new Stack();
        int scan;
        boolean ope = false;
        //variable para almacenar los substrings
        String temp = null;
        scan = 0;
        //se recorre toda la expresion infija
        while(scan < expresion.length())
        {
            //Se obtiene el siguente nombre del infijo, a partir de la posicion actual,
            //este es o una constante, una variable, o el simbolo de una operacion
            temp = siguienteNombre(expresion, scan);
            if(!temp.equals("(") & !temp.equals(")"))
            {
                pilaInfijo.push(temp);
            }
            //se evalua si el nombre contiene mas de un caracter
            if(temp.length() > 2)
            {
                //dado que el nombre actual contiene mas de un caracter es el nombre de una variable, por lo tanto
                //se debe apilar en el resultado
                res.push(temp);
                ope = false;
                while(!pila.isEmpty() & pila.peek().equals("!"))
                {
                    res.push(pila.pop());
                }
            }
            //se evalua si el nombre actual contiene un unico caracter
            if(temp.length() == 1 | temp.length() == 2)
            {
                if(Character.isLetterOrDigit(temp.charAt(0)))
                {
                    ope = false;
                    res.push(temp);
                    while(!pila.isEmpty() && pila.peek().equals("!"))
                    {
                        res.push(pila.pop());
                    }
                }
                if(temp.equals("("))
                {
                    ope = true;
                    pila.push(temp);
                }
                if(temp.equals(")"))
                {
                    ope = false;
                    while(!pila.isEmpty() && !pila.peek().equals("("))
                    {
                        res.push(pila.pop());
                    }
                    //se elimina el simbolo de parentesis izquierdo
                    if(!pila.isEmpty())
                    {
                        pila.pop();
                    }
                }
                if(temp.equals("!"))
                {
                    ope= true;
                    pila.push(temp);
                }
                //se evalua si el caracter actual es una operacion
                if(temp.equals("*") | temp.equals("+") | temp.equals("-") | temp.equals("/") | temp.equals("&") | temp.equals("|") | temp.equals("%")
                         | temp.equals(">") | temp.equals("<") | temp.equals(">=") | temp.equals("<=") | temp.equals("!=") | temp.equals("==")
                         | temp.equals("&&") | temp.equals("||"))
                {
                    //se realiza un ciclo para agregar los operandos de la pila en el resultado
                    if(ope)
                    {
                        pila.push(temp);
                    }
                    else
                    {
                        String top = null;
                        if(!pila.isEmpty())
                        {
                            top = (String)pila.peek();
                        }
                        while(top != null && (!pila.isEmpty() && pdp(top) >= pfp(temp)))
                        {
                            top = (String)pila.pop();
                            res.push(top);
                            if(!pila.isEmpty())
                            {
                                top = (String)pila.peek();
                            }
                        }
                        //se inserta el caracter actual en al pila
                        pila.push(temp);
                    }
                    ope = true;
                }
            }
            //se avanza en el infijo
            scan = scan + temp.length();
        }
        //se insertan en el resultado los valores que aun queden en la pila
        while(!pila.isEmpty())
        {
            res.push(pila.pop());
        }
        Object posfijo[] = new Object[res.size()];
        int i = posfijo.length-1;
        while(!res.isEmpty())
        {
            posfijo[i] = res.pop();

            i = i - 1;
        }
        Object infijo[] = new Object[pilaInfijo.size()];
        i = infijo.length - 1;
        while(!pilaInfijo.isEmpty())
        {
            infijo[i] = pilaInfijo.pop();
            i = i - 1;
        }
        ArbolLista nuevo = ArbolLista.crearArbolPos(posfijo, infijo);
        return nuevo;
    }
    public static int pfp(String x)
    {
        if(x.equals("^") | x.equals("%"))
        {
            return 8;
        }
        if(x.equals("*") | x.equals("/"))
        {
            return 6;
        }
        if(x.equals("+") | x.equals("-"))
        {
            return 5;
        }
        if(x.equals(">") | x.equals("<") | x.equals(">=") | x.equals("<=") | x.equals("==") | x.equals("!="))
        {
            return 4;
        }
        if(x.equals("!"))
        {
            return 3;
        }
        if(x.equals("&") | x.equals("&&"))
        {
            return 2;
        }
        if(x.equals("|") | x.equals("||"))
        {
            return 1;
        }
        if(x.equals("("))
        {
            return 8;
        }
        return 8;
    }
    public static int pdp(String x)
    {
        if(x.equals("^") | x.equals("%"))
        {
            return 7;
        }
        if(x.equals("*") | x.equals("/"))
        {
            return 6;
        }
        if(x.equals("+") | x.equals("-"))
        {
            return 5;
        }
        if(x.equals(">") | x.equals("<") | x.equals(">=") | x.equals("<=") | x.equals("==") | x.equals("!="))
        {
            return 4;
        }
        if(x.equals("!"))
        {
            return 3;
        }
        if(x.equals("&") | x.equals("&&"))
        {
            return 2;
        }
        if(x.equals("|") | x.equals("||"))
        {
            return 1;
        }
        if(x.equals("("))
        {
            return 0;
        }
        return 8;
    }
    /**
     * Metodo que permite hallar el siguiente token de una expresion infija, teniendo en cuenta las condiciones impuestas
     */
    private static String siguienteNombre(String a, int index)
    {
        //variable booleana uutilizada para recorrer el texto ingresado hasta el proximo operador
        boolean continuar = true;
        //se incializa una variable en el indice indicado
        int i = index;
        //se recorre el texto ingresado hasta encontrar un operador, o hasta el final
        while(continuar & i < a.length())
        {
            //si el caracter actual es un operador se detiene el recorrido del texto
            if(a.charAt(i) == '+' | a.charAt(i) == '-' | a.charAt(i) == '*' | a.charAt(i) == '/' | a.charAt(i) == '(' | a.charAt(i) == ')'
                     | a.charAt(i) == '&' | a.charAt(i) == '|' | a.charAt(i) == '>' | a.charAt(i) == '<' | a.charAt(i) == '%' | a.charAt(i) == '!'
                      | a.charAt(i) == '=')
            {
                continuar = false;
            }
            //si el caracter actual no es un operador se continua avanzando en el texto
            else
            {
                i = i + 1;
            }
        }
        //si no logro incrementar el indice, significa que el valor hallado es un operador
        if(i == index)
        {
            i = i + 1;
            if(i < a.length() && (((a.charAt(i - 1) == '!' | a.charAt(i - 1) == '=' | a.charAt(i - 1) == '>' | a.charAt(i - 1) == '<') & a.charAt(i) == '=') |
                    (a.charAt(i - 1) == '&' & a.charAt(i) == '&') | (a.charAt(i - 1) == '|' & a.charAt(i) == '|')))
            {
                i = i + 1;
            }
        }
        //se retorna un substring desde el indice ingresado, hasta el indice hallado
        return a.substring(index, i);
    }
    public ArbolLista copia()
    {
        Object pre[] = this.preordenMejorado(this.getR());
        Object in[] = this.inordenMejorado(this.getR());
        Object inC[] = new Object[in.length];
        Object preC[] = new Object[pre.length];
        boolean existencias[] = new boolean[pre.length];
        int k = 0;
        int i = 0;
        for(i = 0; i < existencias.length; i++)
        {
            existencias[i] = false;
        }
        for(i = 0; i < pre.length; i++)
        {
            if(pre[i] instanceof Integer)
            {
                Integer grafico = null, aux = null;
                aux = (Integer)pre[i];
                grafico = (Integer) aux;
                k = 0;
                while(k < pre.length && (!pre[i].equals(in[k]) | existencias[k]))
                {
                    k = k + 1;
                }
                existencias[k] = true;
                preC[i] = grafico;
                inC[k] = grafico;
            }
            if(pre[i] instanceof NodoGrafico)
            {
                NodoGrafico grafico = null, aux = null;
                aux = (NodoGrafico)pre[i];
                try 
                {
                    grafico = (NodoGrafico) aux.clone();
                } 
                catch (CloneNotSupportedException ex)
                {
                }
                k = 0;
                while(k < pre.length && (!pre[i].equals(in[k]) | existencias[k]))
                {
                    k = k + 1;
                }
                existencias[k] = true;
                preC[i] = grafico;
                inC[k] = grafico;
            }

        }
        ArbolLista arbol = ArbolLista.crearArbolPre(preC, inC);
        if(this.getOrden().equals(ArbolLista.INORDEN))
        {
            arbol.inorden();
        }
        else
        {
            if(this.getOrden().equals(ArbolLista.PREORDEN))
            {
                arbol.preorden();
            }
            else
            {
                if(this.getOrden().equals(ArbolLista.POSORDEN))
                {
                    arbol.posorden();
                }
            }
        }
        if(this.isAVL())
        {
            Stack pila = new Stack(), pila1 = new Stack();
            NodoDoble pp = this.getR();
            NodoDoble p = this.getRaiz();
            NodoDoble x = arbol.getRaiz(), ax = arbol.getR();
            while(p != null && (p.isBd() | p.isBi() | !pila.isEmpty()))
            {
                x.setFb(p.getFb());
                if(!p.isBd())
                {
                    if(p.isBi())
                    {
                        x = x.getLi();
                        p = p.getLi();
                    }
                    else
                    {
                        if(!pila.isEmpty())
                        {
                            pp = (NodoDoble)pila.pop();
                            p = pp.getLd();
                        }
                        if(!pila1.isEmpty())
                        {
                            ax = (NodoDoble)pila1.pop();
                            x = ax.getLd();
                        }
                    }
                }
                else
                {
                    if(p.isBi())
                    {
                        pila1.push(x);
                        x = x.getLi();
                        pila.push(p);
                        p = p.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                        p = p.getLd();
                    }
                }
            }
            if(p != null & x != null)
            {
                x.setFb(p.getFb());
            }
        }
        arbol.setLength(this.getLength());
        arbol.setAVL(this.isAVL());
        return arbol;
    }
    public void desenhebrar()
    {
        if(orden.equals(INORDEN))
        {
            this.desenhebrarIn();
        }
        if(orden.equals(POSORDEN))
        {
            this.desenhebrarPos();
        }
        if(orden.equals(PREORDEN))
        {
            this.desenhebrarPre();
        }
    }
    public void desenhebrarPre()
    {
        NodoDoble x = this.siguientePre(R);
        NodoDoble y = x;
        while(x != R && x != null)
        {
            x = this.siguientePre(x);
            if(!y.isBd())
            {
                y.setLd(null);
                y.setBd(true);
            }
            if(!y.isBi())
            {
                y.setLi(null);
                y.setBi(true);
            }
            y = x;
        }
        orden = NULL;
    }
    public void desenhebrarPos()
    {
        NodoDoble x = this.siguientePos(R);
        NodoDoble y = x;
        while(x != R && x != null)
        {
            x = this.siguientePos(x);
            if(!y.isBd())
            {
                y.setLd(null);
                y.setBd(true);
            }
            if(!y.isBi())
            {
                y.setLi(null);
                y.setBi(true);
            }
            y = x;
        }
        orden = NULL;
    }
    public void desenhebrarIn()
    {
        NodoDoble x = this.siguienteIn(R);
        NodoDoble y = x;
        while(x != R && x != null)
        {
            x = this.siguienteIn(x);
            if(!y.isBd())
            {
                y.setLd(null);
                y.setBd(true);
            }
            if(!y.isBi())
            {
                y.setLi(null);
                y.setBi(true);
            }
            y = x;
        }
        orden = NULL;
    }
    public void setPreordenN(Object[] preordenN)
    {
        this.preordenN = preordenN;
    }
    public void setPosordenN(Object[] posordenN)
    {
        this.posordenN = posordenN;
    }
    public void setOrden(Integer orden)
    {
        this.orden = orden;
    }
    public void setInordenN(Object[] inordenN)
    {
        this.inordenN = inordenN;
    }
    public void setCount(int count)
    {
        this.count = count;
    }
    public void setAnt(NodoDoble ant)
    {
        this.ant = ant;
    }
    public Object[] getPreordenN()
    {
        return preordenN;
    }
    public Object[] getPosordenN()
    {
        return posordenN;
    }
    public Integer getOrden()
    {
        return orden;
    }
    public int getCount()
    {
        return count;
    }
    public NodoDoble getAnt()
    {
        return ant;
    }
    public void setAVL(boolean AVL)
    {
        this.AVL = AVL;
    }
    public boolean isAVL()
    {
        return AVL;
    }
    public ArbolLista()
    {
        R = new NodoDoble(null);
        R.setBd(true);
        R.setBi(false);
        R.setLd(R);
        R.setLi(R);
    }
    public Integer getLength()
    {
        return length;
    }
    public void setLength(Integer length)
    {
        this.length = length;
    }
    public NodoDoble getR()
    {
        return R;
    }
    public NodoDoble getRaiz()
    {
        return R.isBi()? R.getLi() : null;
    }
    public int hojas(NodoDoble x)
    {
        int hojas = 0;
        if(x.isBd())
        {
            if(x.isBi())
            {
                hojas = hojas + hojas(x.getLi()) + hojas(x.getLd());
            }
            else
            {
                hojas = hojas + hojas(x.getLd());
            }
        }
        else
        {
            if(x.isBi())
            {
                hojas = hojas + hojas(x.getLi());
            }
            else
            {
                hojas = hojas + 1;
            }
        }
        return hojas;
    }
    public int altura(NodoDoble x)
    {
        if(x == null)
        {
            return 0;
        }
        int h = 1;
        int hMax = 1;
        if(x.isBd())
        {
            h = 1 + altura(x.getLd());
        }
        else
        {
            h = 1 + altura(null);
        }
        if(h > hMax)
        {
            hMax = h;
        }
        if(x.isBi())
        {
            h = 1 + altura(x.getLi());
        }
        else
        {
            h = 1 + altura(null);
        }
        if(h > hMax)
        {
            hMax = h;
        }
        return hMax;
    }
    public void menor(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        if(orden.equals(NULL))
        {
            while(p.getLi() != null)
            {
                p = p.getLi();
            }
            NodoDoble pp = padre(p);
            x.setValue(p.getValue());
            x.setKey(p.getKey());
            this.eliminarNodo(p, pp);
        }
        else
        {
            while(p.isBi())
            {
                p = p.getLi();
            }
            NodoDoble pp = padre(p);
            x.setValue(p.getValue());
            x.setKey(p.getKey());
            this.eliminarNodo(p, pp);
        }
        return;
    }
    public void rebalancearArbol(NodoDoble q)
    {
        NodoDoble pq = null;
        if(q.getFb().equals(0))
        {
            pq = padre(q);
            if(pq == null)
            {
                return;
            }
            if(pq.getFb().equals(0))
            {
                if(orden.equals(NULL))
                {
                    if(q == pq.getLd())
                    {
                        pq.setFb(1);
                    }
                    else
                    {
                        pq.setFb(-1);
                    }
                }
                else
                {
                    if(q == pq.getLd() & pq.isBd())
                    {
                        pq.setFb(1);
                    }
                    else
                    {
                        pq.setFb(-1);
                    }
                }
            }
            else
            {
                if(orden.equals(NULL))
                {
                    if(q == pq.getLd())
                    {
                        pq.setFb(pq.getFb() + 1);
                    }
                    else
                    {
                        pq.setFb(pq.getFb() - 1);
                    }
                }
                else
                {
                    if(q == pq.getLd() & pq.isBd())
                    {
                        pq.setFb(pq.getFb() + 1);
                    }
                    else
                    {
                        pq.setFb(pq.getFb() - 1);
                    }
                }
                this.rotarMenosAltura(pq);
            }
        }
        else
        {
            if(q.getFb().equals(2) | q.getFb().equals(-2))
            {
                rotarMenosAltura(q);
            }
        }
    }
    public void eliminarNodo(NodoDoble p, NodoDoble q)
    {
        if(orden.equals(NULL))
        {
            if(p.getLd() == null & p.getLi() == null)
            {
                if(q == R)
                {
                    R.setLi(R);
                    R.setBi(false);
                    return;
                }
                if(p == q.getLd())
                {
                    q.setFb(q.getFb() + 1);
                    this.eliminarNormal(p, q);
                    if(q.getFb().equals(1))
                    {
                        return;
                    }
                    this.rebalancearArbol(q);
                }
                else
                {
                    if(p == q.getLi())
                    {
                        q.setFb(q.getFb() - 1);
                        this.eliminarNormal(p, q);
                        if(q.getFb().equals(-1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                }
            }
            else
            {
                if(p.getLd() == null)
                {
                    if(p == q.getLd())
                    {
                        q.setFb(q.getFb() + 1);
                        this.eliminarNormal(p, q);
                        if(q.getFb().equals(1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                    else
                    {
                        if(p == q.getLi())
                        {
                            q.setFb(q.getFb() - 1);
                            this.eliminarNormal(p, q);
                            if(q.getFb().equals(-1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                    }
                }
                else
                {
                    if(p.getLi() == null)
                    {
                        if(p == q.getLd())
                        {
                            q.setFb(q.getFb() + 1);
                            this.eliminarNormal(p, q);
                            if(q.getFb().equals(1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                        else
                        {
                            if(p == q.getLi())
                            {
                                q.setFb(q.getFb() - 1);
                                this.eliminarNormal(p, q);
                                if(q.getFb().equals(-1))
                                {
                                    return;
                                }
                                this.rebalancearArbol(q);
                            }
                        }
                    }
                    else
                    {
                        this.menor(p);
                    }
                }
            }
        }
        if(orden.equals(PREORDEN))
        {
            if(!p.isBd() & !p.isBi())
            {
                if(q == R)
                {
                    R.setLi(R);
                    R.setBi(false);
                    return;
                }
                if(p == q.getLd() & q.isBd())
                {
                    q.setFb(q.getFb() + 1);
                    this.eliminarPre(p, q);
                    if(q.getFb().equals(1))
                    {
                        return;
                    }
                    this.rebalancearArbol(q);
                }
                else
                {
                    if(p == q.getLi() & q.isBi())
                    {
                        q.setFb(q.getFb() - 1);
                        this.eliminarPre(p, q);
                        if(q.getFb().equals(-1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                }
            }
            else
            {
                if(!p.isBd())
                {
                    if(p == q.getLd() & q.isBd())
                    {
                        q.setFb(q.getFb() + 1);
                        this.eliminarPre(p, q);
                        if(q.getFb().equals(1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                    else
                    {
                        if(p == q.getLi() & q.isBi())
                        {
                            q.setFb(q.getFb() - 1);
                            this.eliminarPre(p, q);
                            if(q.getFb().equals(-1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                    }
                }
                else
                {
                    if(!p.isBi())
                    {
                        if(p == q.getLd() & q.isBd())
                        {
                            q.setFb(q.getFb() + 1);
                            this.eliminarPre(p, q);
                            if(q.getFb().equals(1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                        else
                        {
                            if(p == q.getLi() & q.isBi())
                            {
                                q.setFb(q.getFb() - 1);
                                this.eliminarPre(p, q);
                                if(q.getFb().equals(-1))
                                {
                                    return;
                                }
                                this.rebalancearArbol(q);
                            }
                        }
                    }
                    else
                    {
                       this.menor(p);
                    }
                }
            }
        }
        if(orden.equals(POSORDEN))
        {
            if(!p.isBd() & !p.isBi())
            {
                if(q == R)
                {
                    R.setLi(R);
                    R.setBi(false);
                    return;
                }
                if(p == q.getLd() & q.isBd())
                {
                    q.setFb(q.getFb() + 1);
                    this.eliminarPos(p, q);
                    if(q.getFb().equals(1))
                    {
                        return;
                    }
                    this.rebalancearArbol(q);
                }
                else
                {
                    if(p == q.getLi() & q.isBi())
                    {
                        q.setFb(q.getFb() - 1);
                        this.eliminarPos(p, q);
                        if(q.getFb().equals(-1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                }
            }
            else
            {
                if(!p.isBd())
                {
                    if(p == q.getLd() & q.isBd())
                    {
                        q.setFb(q.getFb() + 1);
                        this.eliminarPos(p, q);
                        if(q.getFb().equals(1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                    else
                    {
                        if(p == q.getLi() & q.isBi())
                        {
                            q.setFb(q.getFb() - 1);
                            this.eliminarPos(p, q);
                            if(q.getFb().equals(-1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                    }
                }
                else
                {
                    if(!p.isBi())
                    {
                        if(p == q.getLd() & q.isBd())
                        {
                            q.setFb(q.getFb() + 1);
                            this.eliminarPos(p, q);
                            if(q.getFb().equals(1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                        else
                        {
                            if(p == q.getLi() & q.isBi())
                            {
                                q.setFb(q.getFb() - 1);
                                this.eliminarPos(p, q);
                                if(q.getFb().equals(-1))
                                {
                                    return;
                                }
                                this.rebalancearArbol(q);
                            }
                        }
                    }
                    else
                    {
                        this.menor(p);
                    }
                }
            }
        }
        if(orden.equals(INORDEN))
        {
            if(!p.isBd() & !p.isBi())
            {
                if(q == R)
                {
                    R.setLi(R);
                    R.setBi(false);
                    return;
                }
                if(p == q.getLd() & q.isBd())
                {
                    q.setFb(q.getFb() + 1);
                    this.eliminarIn(p, q);
                    if(q.getFb().equals(1))
                    {
                        return;
                    }
                    this.rebalancearArbol(q);
                }
                else
                {
                    if(p == q.getLi() & q.isBi())
                    {
                        q.setFb(q.getFb() - 1);
                        this.eliminarIn(p, q);
                        if(q.getFb().equals(-1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                }
            }
            else
            {
                if(!p.isBd())
                {
                    if(p == q.getLd() & q.isBd())
                    {
                        q.setFb(q.getFb() + 1);
                        this.eliminarIn(p, q);
                        if(q.getFb().equals(1))
                        {
                            return;
                        }
                        this.rebalancearArbol(q);
                    }
                    else
                    {
                        if(p == q.getLi() & q.isBi())
                        {
                            q.setFb(q.getFb() - 1);
                            this.eliminarIn(p, q);
                            if(q.getFb().equals(-1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                    }
                }
                else
                {
                    if(!p.isBi())
                    {
                        if(p == q.getLd() & q.isBd())
                        {
                            q.setFb(q.getFb() + 1);
                            this.eliminarIn(p, q);
                            if(q.getFb().equals(1))
                            {
                                return;
                            }
                            this.rebalancearArbol(q);
                        }
                        else
                        {
                            if(p == q.getLi() & q.isBi())
                            {
                                q.setFb(q.getFb() - 1);
                                this.eliminarIn(p, q);
                                if(q.getFb().equals(-1))
                                {
                                    return;
                                }
                                this.rebalancearArbol(q);
                            }
                        }
                    }
                    else
                    {
                        this.menor(p);
                    }
                }
            }
        }
        return;
    }
    public boolean borrarAVL(NodoDoble x, NodoDoble ax)
    {
        length = length - 1;
        if(this.isAVL())
        {
            this.eliminarNodo(x, ax);
        }
        else
        {
            if(orden.equals(NULL))
            {
                this.eliminarNormal(x, ax);
            }
            if(orden.equals(INORDEN))
            {
                this.eliminarIn(x, ax);
            }
            if(orden.equals(POSORDEN))
            {
                this.eliminarPos(x, ax);
            }
            if(orden.equals(PREORDEN))
            {
                this.eliminarPre(x, ax);
            }
        }
        return true;
    }
    public boolean borrarAVL(Object d)
    {
        if(this.isAVL() & this.isSearch())
        {
            NodoDoble p = this.getRaiz();
            NodoDoble q = R;
            if(orden.equals(NULL))
            {
                if(this.getRaiz() == null)
                {
                    return false;
                }
                while(p != null)
                {
                    if(d instanceof Comparable & p.getKey() instanceof Comparable & p.getKey().getClass() == d.getClass())
                    {
                        Comparable w = (Comparable)d;
                        Comparable z = (Comparable)p.getKey();
                        if(w.compareTo(z) < 0)
                        {
                            if(p.getLi() == null)
                            {
                                return false;
                            }
                            q = p;
                            p = p.getLi();
                        }
                        else
                        {
                            if(w.compareTo(z) > 0)
                            {
                                if(p.getLd() == null)
                                {
                                    return false;
                                }
                                else
                                {
                                    q = p;
                                    p = p.getLd();
                                }
                            }
                            else
                            {
                                length = length - 1;
                                this.eliminarNodo(p, q);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            else
            {
                q = R;
                if(this.getRaiz() == null)
                {
                    return false;
                }
                while(p != R)
                {
                    if(d instanceof Comparable & p.getKey() instanceof Comparable & d.getClass() == p.getKey().getClass())
                    {
                        Comparable w = (Comparable)d;
                        Comparable z = (Comparable)p.getKey();
                        if(w.compareTo(z) < 0)
                        {
                            q = p;
                            if(!p.isBi())
                            {
                                return false;
                            }
                            else
                            {
                                p = p.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) > 0)
                            {
                                q = p;
                                if(!p.isBd())
                                {
                                    return false;
                                }
                                else
                                {
                                    p = p.getLd();
                                }
                            }
                            else
                            {
                                length = length - 1;
                                this.eliminarNodo(p, q);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        else
        {
            if(orden.equals(NULL))
            {
                return this.borrarNormal(d);
            }
            if(orden.equals(INORDEN))
            {
                return this.borrarIn(d);
            }
            if(orden.equals(PREORDEN))
            {
                return this.borrarPre(d);
            }
            if(orden.equals(POSORDEN))
            {
                return this.borrarPos(d);
            }
        }
        return false;
    }
    public void rotarMenosAltura(NodoDoble z)
    {
        NodoDoble x = z;
        NodoDoble px = null;
        NodoDoble aux = null;
        if(orden.equals(NULL))
        {
            while(x != R)
            {
                px = padre(x);
                aux = null;
                if(x.getFb().equals(2))
                {
                    if(x.getLi().getFb().equals(1))
                    {
                        aux = this.unaRotDer(x, x.getLi());
                        if(px.getLd() == x)
                        {
                            px.setFb(px.getFb() + 1);
                            px.setLd(aux);
                        }
                        else
                        {
                            px.setFb(px.getFb() - 1);
                            px.setLi(aux);
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(-1))
                        {
                            aux = this.dobleRotDer(x, x.getLi());
                            if(x == px.getLd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLi().getFb().equals(0))
                            {
                                aux = this.unaRotDerEs(x, x.getLi());
                                if(x == px.getLd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                                return;
                            }
                        }
                    }
                }
                else
                {
                    if(x.getFb().equals(-2))
                    {
                        if(x.getLd().getFb().equals(-1))
                        {
                            aux = unaRotIzq(x, x.getLd());
                            if(px.getLd() == x)
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(1))
                            {
                                aux = this.dobleRotIzq(x, x.getLd());
                                if(x == px.getLd())
                                {
                                    px.setFb(px.getFb() + 1);
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setFb(px.getFb() - 1);
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                if(x.getLd().getFb().equals(0))
                                {
                                    aux = this.unaRotIzqEs(x, x.getLd());
                                    if(x == px.getLd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(px != null)
                        {
                            if(x == px.getLd() & x.getFb().equals(0))
                            {
                                px.setFb(px.getFb() + 1);
                            }
                            else
                            {
                                if(x.getFb().equals(0))
                                {
                                    px.setFb(px.getFb() - 1);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                x = px;
            }
        }
        if(orden.equals(POSORDEN))
        {
            while(x != R)
            {
                px = padre(x);
                aux = null;
                if(x.getFb().equals(2))
                {
                    if(x.getLi().getFb().equals(1))
                    {
                        aux = this.unaRotDerPos(x, x.getLi());
                        if(px.getLd() == x & px.isBd())
                        {
                            px.setFb(px.getFb() + 1);
                            px.setLd(aux);
                        }
                        else
                        {
                            px.setFb(px.getFb() - 1);
                            px.setLi(aux);
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(-1))
                        {
                            aux = this.dobleRotDerPos(x, x.getLi());
                            if(x == px.getLd() & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLi().getFb().equals(0))
                            {
                                aux = this.unaRotDerEsPos(x, x.getLi());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                                return;
                            }
                        }
                    }
                }
                else
                {
                    if(x.getFb().equals(-2))
                    {
                        if(x.getLd().getFb().equals(-1))
                        {
                            aux = unaRotIzqPos(x, x.getLd());
                            if(px.getLd() == x & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(1))
                            {
                                aux = this.dobleRotIzqPos(x, x.getLd());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setFb(px.getFb() + 1);
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setFb(px.getFb() - 1);
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                if(x.getLd().getFb().equals(0))
                                {
                                    aux = this.unaRotIzqEsPos(x, x.getLd());
                                    if(x == px.getLd() & px.isBd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(px != null)
                        {
                            if(x == px.getLd() & x.getFb().equals(0))
                            {
                                px.setFb(px.getFb() + 1);
                            }
                            else
                            {
                                if(x.getFb().equals(0))
                                {
                                    px.setFb(px.getFb() - 1);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                x = px;
            }
        }
        if(orden.equals(PREORDEN))
        {
            while(x != R)
            {
                px = padre(x);
                aux = null;
                if(x.getFb().equals(2))
                {
                    if(x.getLi().getFb().equals(1))
                    {
                        aux = this.unaRotDerPre(x, x.getLi());
                        if(px.getLd() == x & px.isBd())
                        {
                            px.setFb(px.getFb() + 1);
                            px.setLd(aux);
                        }
                        else
                        {
                            px.setFb(px.getFb() - 1);
                            px.setLi(aux);
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(-1))
                        {
                            aux = this.dobleRotDerPre(x, x.getLi());
                            if(x == px.getLd() & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLi().getFb().equals(0))
                            {
                                aux = this.unaRotDerEsPre(x, x.getLi());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                                return;
                            }
                        }
                    }
                }
                else
                {
                    if(x.getFb().equals(-2))
                    {
                        if(x.getLd().getFb().equals(-1))
                        {
                            aux = unaRotIzqPre(x, x.getLd());
                            if(px.getLd() == x & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(1))
                            {
                                aux = this.dobleRotIzqPre(x, x.getLd());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setFb(px.getFb() + 1);
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setFb(px.getFb() - 1);
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                if(x.getLd().getFb().equals(0))
                                {
                                    aux = this.unaRotIzqEsPre(x, x.getLd());
                                    if(x == px.getLd() & px.isBd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(px != null)
                        {
                            if(x == px.getLd() & x.getFb().equals(0))
                            {
                                px.setFb(px.getFb() + 1);
                            }
                            else
                            {
                                if(x.getFb().equals(0))
                                {
                                    px.setFb(px.getFb() - 1);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                x = px;
            }
        }
        if(orden.equals(INORDEN))
        {
            while(x != R)
            {
                px = padre(x);
                aux = null;
                if(x.getFb().equals(2))
                {
                    if(x.getLi().getFb().equals(1))
                    {
                        aux = this.unaRotDerIn(x, x.getLi());
                        if(px.getLd() == x & px.isBd())
                        {
                            px.setFb(px.getFb() + 1);
                            px.setLd(aux);
                        }
                        else
                        {
                            px.setFb(px.getFb() - 1);
                            px.setLi(aux);
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(-1))
                        {
                            aux = this.dobleRotDerIn(x, x.getLi());
                            if(x == px.getLd() & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLi().getFb().equals(0))
                            {
                                aux = this.unaRotDerEsIn(x, x.getLi());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                                return;
                            }
                        }
                    }
                }
                else
                {
                    if(x.getFb().equals(-2))
                    {
                        if(x.getLd().getFb().equals(-1))
                        {
                            aux = unaRotIzqIn(x, x.getLd());
                            if(px.getLd() == x & px.isBd())
                            {
                                px.setFb(px.getFb() + 1);
                                px.setLd(aux);
                            }
                            else
                            {
                                px.setFb(px.getFb() - 1);
                                px.setLi(aux);
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(1))
                            {
                                aux = this.dobleRotIzqIn(x, x.getLd());
                                if(x == px.getLd() & px.isBd())
                                {
                                    px.setFb(px.getFb() + 1);
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setFb(px.getFb() - 1);
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                if(x.getLd().getFb().equals(0))
                                {
                                    aux = this.unaRotIzqEsIn(x, x.getLd());
                                    if(x == px.getLd() & px.isBd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    else
                    {
                        if(px != null)
                        {
                            if(x == px.getLd() & x.getFb().equals(0))
                            {
                                px.setFb(px.getFb() + 1);
                            }
                            else
                            {
                                if(x.getFb().equals(0))
                                {
                                    px.setFb(px.getFb() - 1);
                                }
                                else
                                {
                                    return;
                                }
                            }
                        }
                    }
                }
                x = px;
            }
        }
    }
    public NodoDoble unaRotDer(NodoDoble p, NodoDoble q)
    {
        p.setLi(q.getLd());
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoDoble unaRotIzq(NodoDoble p, NodoDoble q)
    {
        p.setLd(q.getLi());
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoDoble dobleRotDer(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLd();
        q.setLd(r.getLi());
        r.setLi(q);
        p.setLi(r.getLd());
        r.setLd(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(-1);
                q.setFb(0);
            }
            else
            {
                p.setFb(0);
                q.setFb(1);
            }
        }
        r.setFb(0);
        return r;
    }
    public NodoDoble dobleRotIzq(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLi();
        q.setLi(r.getLd());
        r.setLd(q);
        p.setLd(r.getLi());
        r.setLi(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(0);
                q.setFb(-1);
            }
            else
            {
                p.setFb(1);
                q.setFb(0);
            }
        }
        r.setFb(0);
        return r;
    }
    public NodoDoble unaRotDerEs(NodoDoble p, NodoDoble q)
    {
        p.setLi(q.getLd());
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    public NodoDoble unaRotIzqEs(NodoDoble p, NodoDoble q)
    {
        p.setLd(q.getLi());
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    public NodoDoble unaRotDerPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble ap = this.anteriorPre(p);
        if(!ap.isBd())
        {
            ap.setLd(q);
        }
        if(q.isBd())
        {
            NodoDoble aux = this.anteriorPre(q.getLd());
            p.setBi(true);
            p.setLi(q.getLd());
            if(!aux.isBd())
            {
                aux.setLd(p);
            }
            if(!q.getLd().isBi())
            {
                q.getLd().setLi(p);
            }
        }
        else
        {
            p.setBi(false);
            p.setLi(q.getLi());
            if(!p.isBd())
            {
                p.setLd(q.getLi().getLd());
            }
            if(!q.getLi().getLd().isBi())
            {
                q.getLi().getLd().setLi(p);
            }
            q.getLi().setLd(p);
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoDoble unaRotIzqPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble ap = this.anteriorPre(p);
        NodoDoble aq = this.anteriorPre(q);
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
            if(p.isBi())
            {
                if(!q.getLi().isBi())
                {
                    q.getLi().setLi(aq);
                }
            }
            if(!aq.isBd())
            {
                aq.setLd(q.getLi());
            }
        }
        else
        {
           p.setBd(false);
           p.setLd(q.getLd());
           if(!q.getLd().isBi())
           {
               q.getLd().setLi(p);
           }
        }
        if(!p.isBi())
        {
            p.setLi(q);
        }
        if(!ap.isBd())
        {
            ap.setLd(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoDoble dobleRotDerPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLd();
        NodoDoble ap = anteriorPre(p);
        NodoDoble arld = null;
        if(r.isBd())
        {
          arld = anteriorPre(r.getLd());
        }
        NodoDoble ar = anteriorPre(r);
        NodoDoble x = null;
        if(r.isBi())
        {
            if(q.isBi())
            {
                if(!ar.isBd())
                {
                    ar.setLd(r.getLi());
                }
            }
            if(!r.getLi().isBi())
            {
                r.getLi().setLi(ar);
            }
            q.setBd(true);
            q.setLd(r.getLi());
        }
        else
        {
            if(!ar.isBd())
            {
                ar.setLd(p);
            }
            q.setBd(false);
            if(!q.isBi())
            {
                q.setLd(p);
                q.setLi(r);
            }
            else
            {
                q.setLd(q.getLi());
                q.getLi().setLi(q);
                q.setLd(q.getLi());
            }
        }
        if(r.isBd())
        {
            if(!arld.isBd())
            {
                arld.setLd(p);
            }
            if(!r.getLd().isBi())
            {
                r.getLd().setLi(p);
            }
            p.setBi(true);
            p.setLi(r.getLd());
        }
        else
        {
            if(!p.isBd())
            {
                p.setLd(r.getLd());
                if(!r.getLd().isBi())
                {
                    r.getLd().setLi(p);
                }
            }
            if(r.isBi())
            {
                if(p.isBd())
                {
                    x = this.anteriorPre(p.getLd());
                    p.setBi(false);
                    p.setLi(x);
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(p);
                    }
                    x.setLd(p);
                    if(!ar.isBd())
                    {
                        ar.setLd(x);
                    }
                    x.setLi(ar);
                }
            }
            else
            {
                p.setBi(false);
                p.setLi(q);
            }
        }
        if(!ap.isBd())
        {
            ap.setLd(r);
        }
        r.setBi(true);
        r.setLi(q);
        r.setBd(true);
        r.setLd(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(-1);
                q.setFb(0);
            }
            else
            {
                p.setFb(0);
                q.setFb(1);
            }
        }
        r.setFb(0);
        return r;
    }
    public NodoDoble dobleRotIzqPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLi();
        NodoDoble arld = null;
        if(r.isBd())
        {
            arld = this.anteriorPre(r.getLd());
        }
        NodoDoble aq = this.anteriorPre(q);
        NodoDoble ap = this.anteriorPre(p);
        NodoDoble x = null;
        if(r.isBd())
        {
            if(!arld.isBd())
            {
                arld.setLd(q);
            }
            q.setBi(true);
            q.setLi(r.getLd());
            if(!r.getLd().isBi())
            {
                r.getLd().setLi(q);
            }
            if(!aq.isBd())
            {
                aq.setLd(r.getLi());
            }
            if(!r.getLi().isBi())
            {
                r.getLi().setLi(aq);
            }
        }
        else
        {
            q.setBi(false);
            if(r.isBi())
            {
                x = r.getLi();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBd())
                    {
                        x = x.getLd();
                    }
                    else
                    {
                        x = x.getLi();
                    }
                }
                q.setLi(x);
                x.setLd(q);
            }
            else
            {
                q.setLi(p);
            }
            if(!q.isBd())
            {
                q.setLd(r.getLd());
            }
            else
            {
                if(!q.getLd().isBi())
                {
                    q.getLd().setLi(q);
                }
            }
            if(!r.getLd().isBi())
            {
                if(aq != p)
                {
                    r.getLd().setLi(aq);
                }
                else
                {
                    r.getLd().setLi(q);
                }
            }
        }
        if(r.isBi())
        {
            p.setBd(true);
            p.setLd(r.getLi());
            if(!r.getLi().isBi())
            {
                r.getLi().setLi(aq);
            }
        }
        else
        {
            if(!p.isBi())
            {
                if(!p.getLi().isBd())
                {
                    p.getLi().setLd(r);
                }
                p.setLi(r);
                p.setBd(false);
                p.setLd(q);
            }
            else
            {
                p.setBd(false);
                p.setLd(p.getLi());
            }
        }
        if(!ap.isBd())
        {
            ap.setLd(r);
        }
        if(!aq.isBd())
        {
            if(r.isBi())
            {
                aq.setLd(r.getLi());
            }
            else
            {
                aq.setLd(q);
            }
        }
        r.setBd(true);
        r.setLd(q);
        r.setBi(true);
        r.setLi(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(0);
                q.setFb(-1);
            }
            else
            {
                p.setFb(1);
                q.setFb(0);
            }
        }
        r.setFb(0);
        return r;
    }
    public NodoDoble unaRotDerEsPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble ap = anteriorPre(p);
        NodoDoble x = null;
        if(!ap.isBd())
        {
            ap.setLd(q);
        }
        if(q.isBd())
        {
            NodoDoble aux = this.anteriorPre(q.getLd());
            if(!p.isBd())
            {
                p.setLd(q.getLd());
            }
            p.setBi(true);
            p.setLi(q.getLd());
            if(!aux.isBd())
            {
                aux.setLd(p);
            }
            if(!q.getLd().isBi())
            {
                q.getLd().setLi(p);
            }
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    public NodoDoble unaRotIzqEsPre(NodoDoble p, NodoDoble q)
    {
        NodoDoble ap = this.anteriorPre(p);
        NodoDoble aq = this.anteriorPre(q);
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
            if(p.isBi())
            {
                if(!q.getLi().isBi())
                {
                    q.getLi().setLi(aq);
                }
            }
            if(!aq.isBd())
            {
                aq.setLd(q.getLi());
            }
        }
        if(!p.isBi())
        {
            p.setLi(q);
        }
        if(!ap.isBd())
        {
            ap.setLd(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    public NodoDoble unaRotDerPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble x = null;
        NodoDoble sp = this.siguientePos(p);
        if(!p.isBd())
        {
            if(!p.getLd().isBi())
            {
                p.getLd().setLi(q);
            }
            p.setLd(q);
        }
        if(q.isBd())
        {
            p.setBi(true);
            p.setLi(q.getLd());
            if(p.isBd())
            {
                x = p.getLd();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(q.getLd());
                if(!q.getLd().isBd())
                {
                    q.getLd().setLd(x);
                }
            }
            else
            {
                if(!q.getLd().isBd())
                {
                    q.getLd().setLd(p);
                }
            }
        }
        else
        {
            if(!q.getLi().isBd())
            {
                q.getLi().setLd(p);
            }
            p.setBi(false);
            p.setLi(q.getLi());
        }
        if(!sp.isBi())
        {
            sp.setLi(q);
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoDoble unaRotIzqPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble sp = siguientePos(p);
        NodoDoble x = null;
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
            if(!q.getLi().isBd())
            {
                if(!q.getLi().getLd().isBi())
                {
                    q.getLi().getLd().setLi(p);
                }
                else
                {
                    x = q.getLd();
                    while(x.isBi() | x.isBd())
                    {
                        if(x.isBi())
                        {
                            x = x.getLi();
                        }
                        else
                        {
                            x = x.getLd();
                        }
                    }
                    x.setLi(p);
                }
                q.getLi().setLd(p);
            }
            else
            {
                x = q.getLd();
                while(x.isBi() | x.isBd())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(p);
            }
        }
        else
        {
           p.setBd(false);
           p.setLd(q.getLd());
           if(!q.getLd().isBi())
           {
               if(!q.getLd().getLi().isBd())
               {
                   q.getLd().getLi().setLd(p);
               }
               if(!p.isBi())
               {
                   p.setLi(q.getLd().getLi());
               }
               q.getLd().setLi(p);
           }
        }
        if(!sp.isBi())
        {
            sp.setLi(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoDoble dobleRotDerPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble x = null;
        NodoDoble r = q.getLd();
        NodoDoble sp = siguientePos(p);
        if(r.isBi())
        {
            q.setBd(true);
            q.setLd(r.getLi());
            if(!r.getLi().isBd())
            {
                if(!r.getLi().getLd().isBi())
                {
                    r.getLi().getLd().setLi(q);
                }
                r.getLi().setLd(q);
            }
        }
        else
        {
            if(!r.getLi().isBd())
            {
                r.getLi().setLd(q);
            }
            if(!r.isBd())
            {
                q.setBd(false);
                q.setLd(p);
            }
            else
            {
                x = r.getLd();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                q.setBd(false);
                q.setLd(x);
                if(!x.isBi())
                {
                    x.setLi(q);
                }
            }
            if(!q.isBi())
            {
                q.setLi(r.getLi());
            }
            else
            {
                if(!q.getLi().isBd())
                {
                    q.getLi().setLd(q);
                }
            }
        }
        if(r.isBd())
        {
            if(!r.getLd().isBd())
            {
                x = p.getLd();
                while(x.isBi())
                {
                    x = x.getLi();
                }
                r.getLd().setLd(x);
                if(!x.isBi())
                {
                    x.setLi(r.getLd());
                }
            }
            else
            {
                x = p.getLd();
                while(x.isBi() | x.isBd())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                if(!x.isBi())
                {
                    x.setLi(r.getLd());
                }
            }
            if(p.isBd())
            {
                if(!p.getLd().isBi())
                {
                    p.getLd().setLi(r.getLd());
                }
            }
            if(!r.getLd().isBi() & !r.getLd().isBd())
            {
                r.getLd().setLi(q);
            }
            else
            {
                x = r.getLd();
                while(x.isBi() | x.isBd())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(q);
            }
            if(!sp.isBi())
            {
                sp.setLi(r);
            }
            p.setBi(true);
            p.setLi(r.getLd());
        }
        else
        {
            if(!sp.isBi())
            {
                sp.setLi(r);
            }
            if(!p.isBd())
            {
                p.setLd(r);
                p.setBi(false);
                p.setLi(q);
            }
            else
            {
                p.setBi(false);
                p.setLi(p.getLd());
            }
        }
        r.setBi(true);
        r.setLi(q);
        r.setBd(true);
        r.setLd(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(-1);
                q.setFb(0);
            }
            else
            {
                p.setFb(0);
                q.setFb(1);
            }
        }
        r.setFb(0);
        return r;
    }
    public NodoDoble dobleRotIzqPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble x = null;
        NodoDoble r = q.getLi();
        NodoDoble sp = this.siguientePos(p);
        NodoDoble sr = this.siguientePos(r);
        if(r.isBd())
        {
            if(!sr.isBi())
            {
                sr.setLi(r.getLd());
            }
            q.setBi(true);
            q.setLi(r.getLd());
            x = r.getLd();
            while(x.isBd() | x.isBi())
            {
                if(x.isBi())
                {
                    x = x.getLi();
                }
                else
                {
                    x = x.getLd();
                }
            }
            x.setLi(p);
            if(!r.getLd().isBd())
            {
                if(q.isBd())
                {
                    x = q.getLd();
                    while(x.isBi() | x.isBd())
                    {
                        if(x.isBi())
                        {
                            x = x.getLi();
                        }
                        else
                        {
                            x = x.getLd();
                        }
                    }
                    r.getLd().setLd(x);
                    if(!x.isBi())
                    {
                        x.setLi(r.getLd());
                    }
                }
                else
                {
                    r.getLd().setLd(q);
                }
            }
        }
        else
        {
            q.setBi(false);
            q.setLi(p);
            if(!q.isBd())
            {
                q.setLd(r);
            }
        }
        if(r.isBi())
        {
            p.setBd(true);
            p.setLd(r.getLi());
            if(!r.getLi().isBd())
            {
                r.getLi().setLd(p);
            }
        }
        else
        {
            p.setBd(false);
            if(!r.isBd())
            {
                p.setLd(q);
                if(!r.getLd().isBi())
                {
                    r.getLd().setLi(p);
                }
            }
            else
            {
                x = r.getLd();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(p);
                p.setLd(x);
            }
            if(!p.isBi())
            {
                p.setLi(r.getLi());
                if(!r.getLi().isBd())
                {
                    r.getLi().setLd(p);
                }
            }
            else
            {
                if(!p.getLi().isBd())
                {
                    p.getLi().setLd(p);
                }
            }
        }
        if(!sp.isBi())
        {
            sp.setLi(r);
        }
        r.setBd(true);
        r.setLd(q);
        r.setBi(true);
        r.setLi(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(0);
                q.setFb(-1);
            }
            else
            {
                p.setFb(1);
                q.setFb(0);
            }
        }
        r.setFb(0);
        return r;
    }
    public NodoDoble unaRotDerEsPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble x = null;
        NodoDoble sp = this.siguientePos(p);
        if(!p.isBd())
        {
            if(!p.getLd().isBi())
            {
                p.getLd().setLi(q);
            }
            p.setLd(q);
        }
        if(q.isBd())
        {
            p.setBi(true);
            p.setLi(q.getLd());
            if(p.isBd())
            {
                x = p.getLd();
                while(x.isBd() | x.isBi())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(q.getLd());
                if(!q.getLd().isBd())
                {
                    q.getLd().setLd(x);
                }
            }
            else
            {
                if(!q.getLd().isBd())
                {
                    q.getLd().setLd(p);
                }
            }
        }
        if(!sp.isBi())
        {
            sp.setLi(q);
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    public NodoDoble unaRotIzqEsPos(NodoDoble p, NodoDoble q)
    {
        NodoDoble sp = this.siguientePos(p);
        NodoDoble x = null;
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
            if(!q.getLi().isBd())
            {
                if(!q.getLi().getLd().isBi())
                {
                    q.getLi().getLd().setLi(p);
                }
                else
                {
                    x = q.getLd();
                    while(x.isBi() | x.isBd())
                    {
                        if(x.isBi())
                        {
                            x = x.getLi();
                        }
                        else
                        {
                            x = x.getLd();
                        }
                    }
                    x.setLi(p);

                }
                q.getLi().setLd(p);
                if(!p.isBi())
                {
                    p.setLi(q.getLi());
                }
            }
            else
            {
                x = q.getLd();
                while(x.isBi() | x.isBd())
                {
                    if(x.isBi())
                    {
                        x = x.getLi();
                    }
                    else
                    {
                        x = x.getLd();
                    }
                }
                x.setLi(p);
            }
        }
        if(!sp.isBi())
        {
            sp.setLi(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    public NodoDoble unaRotDerIn(NodoDoble p, NodoDoble q)
    {
        if(q.isBd())
        {
            p.setBi(true);
            p.setLi(q.getLd());
        }
        else
        {
            p.setBi(false);
            p.setLi(q);
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoDoble unaRotIzqIn(NodoDoble p, NodoDoble q)
    {
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
        }
        else
        {
           p.setBd(false);
           p.setLd(q);
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoDoble dobleRotDerIn(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLd();
        if(r.isBi())
        {
            q.setBd(true);
            q.setLd(r.getLi());
        }
        else
        {
            q.setBd(false);
            q.setLd(r);
        }
        r.setBi(true);
        r.setLi(q);
        if(r.isBd())
        {
            p.setBi(true);
            p.setLi(r.getLd());
        }
        else
        {
            p.setBi(false);
            p.setLi(r);
        }
        r.setBd(true);
        r.setLd(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(-1);
                q.setFb(0);
            }
            else
            {
                p.setFb(0);
                q.setFb(1);
            }
        }
        r.setFb(0);
        return r;
    }
    public NodoDoble dobleRotIzqIn(NodoDoble p, NodoDoble q)
    {
        NodoDoble r = q.getLi();
        if(r.isBd())
        {
            q.setBi(true);
            q.setLi(r.getLd());
        }
        else
        {
            q.setBi(false);
            q.setLi(r);
        }
        r.setBd(true);
        r.setLd(q);
        if(r.isBi())
        {
            p.setBd(true);
            p.setLd(r.getLi());
        }
        else
        {
            p.setBd(false);
            p.setLd(r);
        }
        r.setBi(true);
        r.setLi(p);
        if(r.getFb().equals(0))
        {
            p.setFb(0);
            q.setFb(0);
        }
        else
        {
            if(r.getFb().equals(1))
            {
                p.setFb(0);
                q.setFb(-1);
            }
            else
            {
                p.setFb(1);
                q.setFb(0);
            }
        }
        r.setFb(0);
        return r;
    }
    public NodoDoble unaRotDerEsIn(NodoDoble p, NodoDoble q)
    {
        if(q.isBd())
        {
            p.setBi(true);
            p.setLi(q.getLd());
        }
        q.setBd(true);
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    public NodoDoble unaRotIzqEsIn(NodoDoble p, NodoDoble q)
    {
        if(q.isBi())
        {
            p.setBd(true);
            p.setLd(q.getLi());
        }
        q.setBi(true);
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    public void balancearNormal(NodoDoble x, NodoDoble q, NodoDoble pivote, NodoDoble pp)
    {
        if(x.getKey() instanceof Comparable)
        {
            Comparable w = null, z = null;
            z = (Comparable)x.getKey();
            Integer aux = pivote.getFb();
            if(pivote.getKey() instanceof Comparable)
            {
                w = (Comparable)pivote.getKey();
                if(z.compareTo(w) < 0)
                {
                    pivote.setFb(aux + 1);
                    q = pivote.getLi();
                }
                else
                {
                    pivote.setFb(aux - 1);
                    q = pivote.getLd();
                }
            }
            NodoDoble p = q;
            while(p != x & p.getKey() instanceof Comparable)
            {
                w = (Comparable)p.getKey();
                if(z.compareTo(w) < 0)
                {
                    p.setFb(1);
                    p = p.getLi();
                }
                else
                {
                    p.setFb(-1);
                    p = p.getLd();
                }
            }
        }
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                q = this.unaRotDer(pivote, q);
            }
            else
            {
                q = this.dobleRotDer(pivote, q);
            }
        }
        else
        {
            if(q.getFb().equals(-1))
            {
                q = this.unaRotIzq(pivote, q);
            }
            else
            {
                q = this.dobleRotIzq(pivote, q);
            }
        }
        if(pivote == this.getRaiz())
        {
            this.setRaiz(q);
            return;
        }
        if(pp.getLi() == pivote)
        {
            pp.setLi(q);
        }
        else
        {
            pp.setLd(q);
        }
    }
    public void balancearPre(NodoDoble x, NodoDoble q, NodoDoble pivote, NodoDoble pp)
    {
        if(x.getKey() instanceof Comparable)
        {
            Comparable w = null, z = null;
            z = (Comparable)x.getKey();
            Integer aux = pivote.getFb();
            if(pivote.getKey() instanceof Comparable)
            {
                w = (Comparable)pivote.getKey();
                if(z.compareTo(w) < 0)
                {
                    pivote.setFb(aux + 1);
                    q = pivote.getLi();
                }
                else
                {
                    pivote.setFb(aux - 1);
                    q = pivote.getLd();
                }
            }
            NodoDoble p = q;
            while(p != x & p.getKey() instanceof Comparable)
            {
                w = (Comparable)p.getKey();
                if(z.compareTo(w) < 0)
                {
                    p.setFb(1);
                    p = p.getLi();
                }
                else
                {
                    p.setFb(-1);
                    p = p.getLd();
                }
            }
        }
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        Integer fp = pivote.getFb(), fq = q.getFb();
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                q = this.unaRotDerPre(pivote, q);
            }
            else
            {
                q = this.dobleRotDerPre(pivote, q);
            }
        }
        else
        {
            if(q.getFb().equals(-1))
            {
                q = this.unaRotIzqPre(pivote, q);
            }
            else
            {
                q = this.dobleRotIzqPre(pivote, q);
            }
        }
        if(pivote == this.getRaiz())
        {
            this.setRaiz(q);
            return;
        }
        if(pp.getLi() == pivote)
        {
            pp.setLi(q);
        }
        else
        {
            pp.setLd(q);
        }
    }
    public void balancearPos(NodoDoble x, NodoDoble q, NodoDoble pivote, NodoDoble pp)
    {
        if(x.getKey() instanceof Comparable)
        {
            Comparable w = null, z = null;
            z = (Comparable)x.getKey();
            Integer aux = pivote.getFb();
            if(pivote.getKey() instanceof Comparable)
            {
                w = (Comparable)pivote.getKey();
                if(z.compareTo(w) < 0)
                {
                    pivote.setFb(aux + 1);
                    q = pivote.getLi();
                }
                else
                {
                    pivote.setFb(aux - 1);
                    q = pivote.getLd();
                }
            }
            NodoDoble p = q;
            while(p != x & p.getKey() instanceof Comparable)
            {
                w = (Comparable)p.getKey();
                if(z.compareTo(w) < 0)
                {
                    p.setFb(1);
                    p = p.getLi();
                }
                else
                {
                    p.setFb(-1);
                    p = p.getLd();
                }
            }
        }
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                q = this.unaRotDerPos(pivote, q);
            }
            else
            {
                q = this.dobleRotDerPos(pivote, q);
            }
        }
        else
        {
            if(q.getFb().equals(-1))
            {
                q = this.unaRotIzqPos(pivote, q);
            }
            else
            {
                q = this.dobleRotIzqPos(pivote, q);
            }
        }
        if(pivote == this.getRaiz())
        {
            this.setRaiz(q);
            return;
        }
        if(pp.getLi() == pivote & pp.isBi())
        {
            pp.setLi(q);
        }
        else
        {
            pp.setLd(q);
        }
    }
    public void balancearIn(NodoDoble x, NodoDoble q, NodoDoble pivote, NodoDoble pp)
    {
        if(x.getKey() instanceof Comparable)
        {
            Comparable w = null, z = null;
            z = (Comparable)x.getKey();
            Integer aux = pivote.getFb();
            if(pivote.getKey() instanceof Comparable)
            {
                w = (Comparable)pivote.getKey();
                if(z.compareTo(w) < 0)
                {
                    pivote.setFb(aux + 1);
                    q = pivote.getLi();
                }
                else
                {
                    pivote.setFb(aux - 1);
                    q = pivote.getLd();
                }
            }
            NodoDoble p = q;
            while(p != x & p.getKey() instanceof Comparable)
            {
                w = (Comparable)p.getKey();
                if(z.compareTo(w) < 0)
                {
                    p.setFb(1);
                    p = p.getLi();
                }
                else
                {
                    p.setFb(-1);
                    p = p.getLd();
                }
            }
        }
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                q = this.unaRotDerIn(pivote, q);
            }
            else
            {
                q = this.dobleRotDerIn(pivote, q);
            }
        }
        else
        {
            if(q.getFb().equals(-1))
            {
                q = this.unaRotIzqIn(pivote, q);
            }
            else
            {
                q = this.dobleRotIzqIn(pivote, q);
            }
        }
        if(pivote == this.getRaiz())
        {
            this.setRaiz(q);
            return;
        }
        if(pp.getLi() == pivote)
        {
            pp.setLi(q);
        }
        else
        {
            pp.setLd(q);
        }
    }
    public boolean insertarAVL(Object d)
    {
        if(this.isAVL() & this.isSearch())
        {
            NodoDoble x = new NodoDoble(d);
            NodoDoble p, q, pivote, pp;
            if(orden.equals(NULL))
            {
                if(!R.isBi())
                {
                    length = length + 1;
                    this.setRaiz(x);
                    return true;
                }
                p = this.getRaiz();
                q = null;
                pivote = this.getRaiz();
                pp = null;
                while(p != null)
                {
                    if(p.getKey() instanceof Comparable & x.getKey() instanceof Comparable & p.getKey().getClass() == x.getKey().getClass())
                    {
                        Comparable z = null, w = null;
                        z = (Comparable)x.getKey();
                        w = (Comparable)p.getKey();
                        if(z.compareTo(w) == 0)
                        {
                            return false;
                        }
                        if(!p.getFb().equals(0))
                        {
                            pivote = p;
                            pp = q;
                        }
                        q = p;
                        if(z.compareTo(w) < 0)
                        {
                            p = p.getLi();
                            if(p == null)
                            {
                                length = length + 1;
                                this.conectarNormal(q, x, true);
                                this.balancearNormal(x, q, pivote, pp);
                                return true;
                            }
                        }
                        else
                        {
                            p = p.getLd();
                            if(p == null)
                            {
                                length = length + 1;
                                this.conectarNormal(q, x, false);
                                this.balancearNormal(x, q, pivote, pp);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(PREORDEN))
            {
                if(!R.isBi())
                {
                    length = length + 1;
                    this.setRaiz(x);
                    x.setBd(false);
                    x.setBi(false);
                    x.setLd(R);
                    x.setLi(R);
                    return true;
                }
                p = this.getRaiz();
                q = R;
                pivote = this.getRaiz();
                pp = R;
                while(p != R)
                {
                    if(p.getKey() instanceof Comparable && x.getKey() instanceof Comparable && p.getKey().getClass() == x.getKey().getClass())
                    {
                        Comparable z = null, w = null;
                        z = (Comparable)x.getKey();
                        w = (Comparable)p.getKey();
                        if(z.compareTo(w) == 0)
                        {
                            return false;
                        }
                        if(!p.getFb().equals(0))
                        {
                            pivote = p;
                            pp = q;
                        }
                        q = p;
                        if(z.compareTo(w) < 0)
                        {
                            if(!p.isBi())
                            {
                                length = length + 1;
                                this.conectarPre(p, x, true);
                                this.balancearPre(x, p, pivote, pp);
                                return true;
                            }
                            else
                            {
                                p = p.getLi();
                            }
                        }
                        else
                        {
                            if(!p.isBd())
                            {
                                length = length + 1;
                                this.conectarPre(p, x, false);
                                this.balancearPre(x, p, pivote, pp);
                                return true;
                            }
                            else
                            {
                                p = p.getLd();
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(POSORDEN))
            {
                if(!R.isBi())
                {
                    length = length + 1;
                    this.setRaiz(x);
                    x.setBd(false);
                    x.setBi(false);
                    x.setLd(R);
                    x.setLi(R);
                    return true;
                }
                p = this.getRaiz();
                q = R;
                pivote = this.getRaiz();
                pp = R;
                while(p != R)
                {
                    if(p.getKey() instanceof Comparable & x.getKey() instanceof Comparable & p.getKey().getClass() == x.getKey().getClass())
                    {
                        Comparable z = null, w = null;
                        z = (Comparable)x.getKey();
                        w = (Comparable)p.getKey();
                        if(z.compareTo(w) == 0)
                        {
                            return false;
                        }
                        if(!p.getFb().equals(0))
                        {
                            pivote = p;
                            pp = q;
                        }
                        q = p;
                        if(z.compareTo(w) < 0)
                        {
                            if(!p.isBi())
                            {
                                length = length + 1;
                                this.conectarPos(p, x, true);
                                this.balancearPos(x, p, pivote, pp);
                                return true;
                            }
                            else
                            {
                                p = p.getLi();
                            }
                        }
                        else
                        {
                            if(!p.isBd())
                            {
                                length = length + 1;
                                this.conectarPos(p, x, false);
                                this.balancearPos(x, p, pivote, pp);
                                return true;
                            }
                            else
                            {
                                p = p.getLd();
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(INORDEN))
            {
                if(!R.isBi())
                {
                    length = length + 1;
                    this.setRaiz(x);
                    x.setBd(false);
                    x.setBi(false);
                    x.setLd(R);
                    x.setLi(R);
                    return true;
                }
                p = this.getRaiz();
                q = R;
                pivote = this.getRaiz();
                pp = R;
                while(p != R)
                {
                    if(p.getKey() instanceof Comparable & x.getKey() instanceof Comparable & p.getKey().getClass() == x.getKey().getClass())
                    {
                        Comparable z = null, w = null;
                        z = (Comparable)x.getKey();
                        w = (Comparable)p.getKey();
                        if(z.compareTo(w) == 0)
                        {
                            return false;
                        }
                        if(!p.getFb().equals(0))
                        {
                            pivote = p;
                            pp = q;
                        }
                        q = p;
                        if(z.compareTo(w) < 0)
                        {
                            if(!p.isBi())
                            {
                                length = length + 1;
                                this.conectarIn(p, x, true);
                                this.balancearIn(x, p, pivote, pp);
                                return true;
                            }
                            else
                            {
                                p = p.getLi();
                            }
                        }
                        else
                        {
                            if(!p.isBd())
                            {
                                length = length + 1;
                                this.conectarIn(p, x, false);
                                this.balancearIn(x, p, pivote, pp);
                                return true;
                            }
                            else
                            {
                                p = p.getLd();
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        else
        {
            if(orden.equals(NULL))
            {
                return this.insertarNormal(d);
            }
            if(orden.equals(INORDEN))
            {
                return this.insertarIn(d);
            }
            if(orden.equals(POSORDEN))
            {
                return this.insertarPos(d);
            }
            if(orden.equals(PREORDEN))
            {
                return this.insertarPre(d);
            }
        }
        return false;
    }
    public void menorPos(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        while(p.isBi())
        {
            p = p.getLi();
        }
        NodoDoble pp = padre(p);
        x.setValue(p.getValue());
        x.setKey(p.getKey());
        this.eliminarPos(p, pp);
    }
    public void eliminarPos(NodoDoble x, NodoDoble ax)
    {
        NodoDoble aux = null;
        if(x == ax.getLi() & ax.isBi())
        {
            if(!x.isBi())
            {
                if(!x.isBd())
                {
                    if(!x.getLi().isBd())
                    {
                        x.getLi().setLd(x.getLd());
                    }
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(x.getLi());
                    }
                    ax.setBi(false);
                    if(ax.isBd())
                    {
                        ax.setLi(ax.getLd());
                    }
                    else
                    {
                        ax.setLi(x.getLi());
                    }
                }
                else
                {
                    aux = this.siguientePos(x);
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLd());
                    }
                    if(!x.getLd().isBd())
                    {
                        x.getLd().setLd(aux);
                    }
                    ax.setLi(x.getLd());
                }
            }
            else
            {
                if(!x.isBd())
                {
                    aux = this.siguientePos(x);
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLi());
                    }
                    if(!x.getLi().isBd())
                    {
                        x.getLi().setLd(aux);
                    }
                    ax.setLi(x.getLi());
                }
                else
                {
                    this.menorPos(x);
                }
            }
        }
        else
        {
            if(!x.isBi())
            {
                if(!x.isBd())
                {
                    aux = this.siguientePos(ax);
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(x.getLi());
                    }
                    if(!x.getLi().isBd())
                    {
                        x.getLi().setLd(x.getLd());
                    }
                    ax.setBd(false);
                    ax.setLd(aux);
                    return;
                }
                else
                {
                    aux = this.siguientePos(x);
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLd());
                    }
                    if(!x.getLd().isBd())
                    {
                        x.getLd().setLd(aux);
                    }
                    ax.setLd(x.getLd());
                }
            }
            else
            {
                if(!x.isBd())
                {
                    aux = this.siguientePos(x);
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLi());
                    }
                    if(!x.getLi().isBd())
                    {
                        x.getLi().setLd(aux);
                    }
                    ax.setLd(x.getLi());
                }
                else
                {
                    this.menorPos(x);
                }
            }
        }
    }
    public boolean borrarPos(Object dato)
    {
        if(this.isSearch())
        {
            if(orden.equals(POSORDEN))
            {
                NodoDoble x = R.getLi(), aux = null;
                NodoDoble ax = R;
                if(R == x)
                {
                    return false;
                }
                while(x != R)
                {
                    if(x.getKey() instanceof Comparable & dato instanceof Comparable & x.getKey().getClass() == dato.getClass())
                    {
                        Comparable z = (Comparable)dato;
                        Comparable w = (Comparable)x.getKey();
                        if(w.compareTo(z) > 0)
                        {
                            if(!x.isBi())
                            {
                                return false;
                            }
                            else
                            {
                                ax = x;
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(!x.isBd())
                                {
                                    return false;
                                }
                                else
                                {
                                    ax = x;
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                AVL = false;
                                length = length - 1;
                                this.eliminarPos(x, ax);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(NULL))
            {
                return this.borrarNormal(dato);
            }
            if(orden.equals(INORDEN))
            {
                return this.borrarIn(dato);
            }
            if(orden.equals(PREORDEN))
            {
                return this.borrarPre(dato);
            }
        }
        return false;
    }
    public void setRaiz(NodoDoble x)
    {
        R.setBi(true);
        R.setLi(x);
    }
    public static ArbolLista convertir(ArbolLg x)
    {
        ArbolLista z = new ArbolLista();
        NodoDoble q = null, r = null;
        Stack pila = new Stack(), pila1 = new Stack();
        NodoLg p = x.getPrimero(), aux = null;
        q = new NodoDoble(p.getDato());
        z.setRaiz(q);
        p = p.getLiga();
        if(p != null)
        {
            z.setLength(z.getLength() + 1);
            if(p.isSw())
            {
                r = new NodoDoble(((NodoLg)p.getDato()).getDato());
                q.setLi(r);
                q = r;
            }
            else
            {
                r = new NodoDoble(p.getDato());
                q.setLi(r);
                q = r;
            }
        }
        while(p != null)
        {
            z.setLength(z.getLength() + 1);
            if(p.isSw())
            {
                aux = p.getLiga();
                if(aux != null)
                {
                    pila.push(aux);
                }
                p = ((NodoLg)p.getDato()).getLiga();
                if(p != null)
                {
                    if(p.isSw())
                    {
                        if(aux != null)
                        {
                            if(aux.isSw())
                            {
                                r = new NodoDoble(((NodoLg)aux.getDato()).getDato());
                                pila1.push(r);
                                q.setLd(r);
                            }
                            else
                            {
                                r = new NodoDoble(aux.getDato());
                                pila1.push(r);
                                q.setLd(r);
                            }
                        }
                        r = new NodoDoble(((NodoLg)p.getDato()).getDato());
                        q.setLi(r);
                        q = r;
                    }
                    else
                    {
                        if(aux != null)
                        {
                            if(aux.isSw())
                            {
                                r = new NodoDoble(((NodoLg)aux.getDato()).getDato());
                                pila1.push(r);
                                q.setLd(r);
                            }
                            else
                            {
                                r = new NodoDoble(aux.getDato());
                                pila1.push(r);
                                q.setLd(r);
                            }
                        }
                        r = new NodoDoble(p.getDato());
                        q.setLi(r);
                        q = r;
                    }
                }
            }
            else
            {
                p = p.getLiga();
                if(p != null)
                {
                    if(p.isSw())
                    {
                        r = new NodoDoble(((NodoLg)p.getDato()).getDato());
                        q.setLd(r);
                        q = r;
                    }
                    else
                    {
                        r = new NodoDoble(p.getDato());
                        q.setLd(r);
                        q = r;
                    }
                }
            }
            if(p == null & !pila.isEmpty())
            {
                p = ((NodoLg)pila.pop());
                if(!pila1.isEmpty())
                {
                    q = (NodoDoble)pila1.pop();
                }
            }
        }
        z.setAVL(false);
        return z;
    }
    public void menorIn(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        while(p.isBi())
        {
            p = p.getLi();
        }
        NodoDoble pp = padre(p);
        x.setValue(p.getValue());
        x.setKey(p.getKey());
        this.eliminarIn(p, pp);
    }
    public void eliminarIn(NodoDoble x, NodoDoble ax)
    {
        NodoDoble aux = null;
        {
            if(ax.getLd() == x)
            {
                if(!x.isBd())
                {
                    if(x.isBi())
                    {
                        ax.setBd(true);
                        ax.setLd(x.getLi());
                        aux = this.anteriorIn(x);
                        aux.setBd(false);
                        aux.setLd(this.siguienteIn(x));
                    }
                    else
                    {
                        ax.setBd(false);
                        ax.setLd(this.siguienteIn(x));
                        return;
                    }
                }
                else
                {
                    if(!x.isBi())
                    {
                        ax.setBd(true);
                        ax.setLd(x.getLd());
                        aux = this.siguienteIn(x);
                        aux.setBi(false);
                        aux.setLi(this.anteriorIn(x));
                    }
                    else
                    {
                        this.menorIn(x);
                    }
                }
            }
            else
            {
                if(!x.isBd())
                {
                    if(x.isBi())
                    {
                        ax.setBi(true);
                        ax.setLi(x.getLi());
                        aux = anteriorIn(x);
                        aux.setBd(false);
                        aux.setLd(siguienteIn(x));
                    }
                    else
                    {
                        ax.setBi(false);
                        ax.setLi(x.getLi());
                    }
                }
                else
                {
                    if(!x.isBi())
                    {
                        ax.setBi(true);
                        ax.setLi(x.getLd());
                        aux = siguienteIn(x);
                        aux.setBi(false);
                        aux.setLi(this.anteriorIn(x));
                    }
                    else
                    {
                        this.menorIn(x);
                    }
                }
            }
        }
    }
    public boolean borrarIn(Object dato)
    {
        if(this.isSearch())
        {
            if(orden.equals(INORDEN))
            {
                NodoDoble x = R.getLi(), aux = null;
                NodoDoble ax = R;
                if(R == x)
                {
                    return false;
                }
                while(x != R)
                {
                    if(x.getKey() instanceof Comparable & dato instanceof Comparable & x.getKey().getClass() == dato.getClass())
                    {
                        Comparable z = (Comparable)dato;
                        Comparable w = (Comparable)x.getKey();
                        if(w.compareTo(z) > 0)
                        {
                            if(!x.isBi())
                            {
                                return false;
                            }
                            else
                            {
                                ax = x;
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(!x.isBd())
                                {
                                    return false;
                                }
                                else
                                {
                                    ax = x;
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                AVL = false;
                                length = length - 1;
                                this.eliminarIn(x, ax);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(NULL))
            {
                return this.borrarNormal(dato);
            }
            if(orden.equals(PREORDEN))
            {
                return this.borrarPre(dato);
            }
            if(orden.equals(POSORDEN))
            {
                return this.borrarPos(dato);
            }
        }
        return false;
    }
    public void menorPre(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        while(p.isBi())
        {
            p = p.getLi();
        }
        NodoDoble pp = padre(p);
        x.setValue(p.getValue());
        x.setKey(p.getKey());
        this.eliminarPre(p, pp);
    }
    public void eliminarPre(NodoDoble x, NodoDoble ax)
    {
        NodoDoble aux = null;
        if(x == ax.getLd() & ax.isBd())
        {
            if(!x.isBi())
            {
                if(!x.isBd())
                {
                    aux = x.getLi();
                    if(!aux.isBd())
                    {
                        aux.setLd(x.getLd());
                    }
                    aux = x.getLd();
                    if(!aux.isBi())
                    {
                        aux.setLi(x.getLi());
                    }
                    ax.setBd(false);
                    if(ax.isBi())
                    {
                        ax.setLd(ax.getLi());
                    }
                    else
                    {
                        ax.setLd(x.getLd());
                    }
                }
                else
                {
                    aux = this.anteriorPre(x);
                    if(!aux.isBd())
                    {
                        aux.setLd(x.getLd());
                    }
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(aux);
                    }
                    ax.setLd(x.getLd());
                }
            }
            else
            {
                if(!x.isBd())
                {
                    aux = this.anteriorPre(x);
                    ax.setLd(x.getLi());
                    if(!aux.isBd())
                    {
                        aux.setLd(x.getLi());
                    }
                    if(!x.getLi().isBi())
                    {
                        x.getLi().setLi(aux);
                    }
                }
                else
                {
                    this.menorPre(x);
                }
            }
        }
        else
        {
            if(!x.isBi())
            {
                if(!x.isBd())
                {
                    aux = this.anteriorPre(ax);
                    ax.setBi(false);
                    ax.setLi(aux);
                    if(ax.isBd())
                    {
                        aux = ax.getLd();
                        if(!aux.isBi())
                        {
                            aux.setLi(ax);
                        }
                    }
                    else
                    {
                        ax.setLd(x.getLd());
                    }
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(ax);
                    }
                }
                else
                {
                    if(!ax.isBd())
                    {
                        ax.setLd(x.getLd());
                    }
                    if(!x.getLd().isBi())
                    {
                        x.getLd().setLi(ax);
                    }
                    ax.setLi(x.getLd());
                }
            }
            else
            {
                if(!x.isBd())
                {
                    if(!ax.isBd())
                    {
                        ax.setLd(x.getLi());
                    }
                    if(!x.getLi().isBi())
                    {
                        x.getLi().setLi(ax);
                    }
                    ax.setLi(x.getLi());
                }
                else
                {
                    this.menorPre(x);
                }
            }
        }
    }
    public boolean borrarPre(Object dato)
    {
        if(this.isSearch())
        {
            if(orden.equals(PREORDEN))
            {
                NodoDoble x = R.getLi(), aux = null;
                NodoDoble ax = R;
                if(R == x)
                {
                    return false;
                }
                while(x != R)
                {
                    if(x.getKey() instanceof Comparable & dato instanceof Comparable & x.getKey().getClass() == dato.getClass())
                    {
                        Comparable z = (Comparable)dato;
                        Comparable w = (Comparable)x.getKey();
                        if(w.compareTo(z) > 0)
                        {
                            if(!x.isBi())
                            {
                                return false;
                            }
                            else
                            {
                                ax = x;
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(!x.isBd())
                                {
                                    return false;
                                }
                                else
                                {
                                    ax = x;
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                AVL = false;
                                length = length - 1;
                                this.eliminarPre(x, ax);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(NULL))
            {
                return this.borrarNormal(dato);
            }
            if(orden.equals(INORDEN))
            {
                return this.borrarIn(dato);
            }
            if(orden.equals(POSORDEN))
            {
                return this.borrarPos(dato);
            }
        }
        return false;
    }
    public void conectarIn(NodoDoble x, NodoDoble nuevo, boolean izquierda)
    {
        if(izquierda)
        {
            nuevo.setBd(false);
            nuevo.setBi(false);
            x.setBi(true);
            nuevo.setLd(x);
            nuevo.setLi(x.getLi());
            x.setLi(nuevo);
            return;
        }
        else
        {
            nuevo.setBd(false);
            nuevo.setBi(false);
            x.setBd(true);
            nuevo.setLi(x);
            nuevo.setLd(x.getLd());
            x.setLd(nuevo);
            return;
        }
    }
    public boolean insertarIn(Object dato)
    {
        if(this.isSearch())
        {
            NodoDoble nuevo = new NodoDoble(dato);
            if(orden.equals(INORDEN))
            {
                NodoDoble x = R.getLi();
                if(R == x)
                {
                    length = length + 1;
                    R.setBi(true);
                    nuevo.setBi(false);
                    nuevo.setBd(false);
                    nuevo.setLi(R);
                    nuevo.setLd(R);
                    R.setLi(nuevo);
                    return true;
                }
                while(x != R)
                {
                    if(x.getKey() instanceof Comparable & nuevo.getKey() instanceof Comparable & x.getKey().getClass() == nuevo.getKey().getClass())
                    {
                        Comparable w = null, z = null;
                        w = (Comparable)x.getKey();
                        z = (Comparable)nuevo.getKey();
                        if(w.compareTo(z) > 0)
                        {
                            if(!x.isBi())
                            {
                                length = length + 1;
                                AVL = false;
                                this.conectarIn(x, nuevo, true);
                                return true;
                            }
                            else
                            {
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(!x.isBd())
                                {
                                    length = length + 1;
                                    AVL = false;
                                    this.conectarIn(x, nuevo, false);
                                    return true;
                                }
                                else
                                {
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(NULL))
            {
                return this.insertarNormal(dato);
            }
            if(orden.equals(PREORDEN))
            {
                return this.insertarPre(dato);
            }
            if(orden.equals(POSORDEN))
            {
                return this.insertarPos(dato);
            }
        }
        return false;
    }
    public void conectarGeneral(NodoDoble x, NodoDoble nuevo)
    {
        if(x.getLi() == null)
        {
            x.setLi(nuevo);
        }
        else
        {
            NodoDoble aux = x.getLi();
            x.setLi(nuevo);
            nuevo.setLd(aux);
        }
    }
    public void eliminarGeneral(NodoDoble ax, NodoDoble x)
    {
        if(ax.getLd() == x)
        {
            if(x.isBi())
            {
                if(x.isBd())
                {
                    NodoDoble aux = x.getLi();
                    while(aux.isBd())
                    {
                        aux = aux.getLd();
                    }
                    aux.setLd(x.getLd());
                    ax.setLd(x.getLi());
                }
                else
                {
                    x.setValue(x.getLi().getValue());
                    x.setKey(x.getLi().getKey());
                    this.eliminarGeneral(x, x.getLi());
                }
            }
            else
            {
                if(x.isBd())
                {
                    ax.setLd(x.getLd());
                }
                else
                {
                    ax.setLd(null);
                }
            }
        }
        else
        {
            if(x.isBi())
            {
                if(x.isBd())
                {
                    NodoDoble aux = x.getLi();
                    while(aux.isBd())
                    {
                        aux = aux.getLd();
                    }
                    aux.setLd(x.getLd());
                    ax.setLi(x.getLi());
                }
                else
                {
                    x.setValue(x.getLi().getValue());
                    x.setKey(x.getLi().getKey());
                    this.eliminarGeneral(x, x.getLi());
                }
            }
            else
            {
                if(x.isBd())
                {
                    ax.setLi(x.getLd());
                }
                else
                {
                    ax.setLi(null);
                }
            }
        }
    }
    public void conectarNormal(NodoDoble x, NodoDoble nuevo, boolean izquierda)
    {
        if(izquierda)
        {
            x.setLi(nuevo);
        }
        else
        {
            x.setLd(nuevo);
        }
    }
    public boolean insertarNormal(Object dato)
    {
        if(this.isSearch())
        {
            NodoDoble nuevo = new NodoDoble(dato);
            if(orden.equals(NULL))
            {
                NodoDoble x = R.getLi();
                if(R == x)
                {
                    length = length + 1;
                    AVL = false;
                    R.setBi(true);
                    R.setLi(nuevo);
                    return true;
                }
                while(x != null)
                {
                    if(x.getKey() instanceof Comparable & nuevo.getKey() instanceof Comparable & x.getKey().getClass() == nuevo.getKey().getClass())
                    {
                        Comparable w = null, z = null;
                        w = (Comparable)x.getKey();
                        z = (Comparable)nuevo.getKey();
                        if(w.compareTo(z) > 0)
                        {
                            if(x.getLi() == null)
                            {
                                length = length + 1;
                                AVL = false;
                                this.conectarNormal(x, nuevo, true);
                                return true;
                            }
                            else
                            {
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(x.getLd() == null)
                                {
                                    length = length + 1;
                                    AVL = false;
                                    this.conectarNormal(x, nuevo, false);
                                    return true;
                                }
                                else
                                {
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(INORDEN))
            {
                return this.insertarIn(dato);
            }
            if(orden.equals(PREORDEN))
            {
                return this.insertarPre(dato);
            }
            if(orden.equals(POSORDEN))
            {
                return this.insertarPos(dato);
            }
        }
        return false;
    }
    public Object buscar(Object key)
    {
        if(this.isSearch())
        {
            NodoDoble x = R.getLi();
            if(orden.equals(NULL))
            {
                while(x != null && x != R)
                {
                    if(x.getKey() instanceof Comparable & key instanceof Comparable & x.getKey().getClass() == key.getClass())
                    {
                        Comparable w = null, z = null;
                        w = (Comparable)x.getKey();
                        z = (Comparable)key;
                        if(w.compareTo(z) > 0)
                        {
                            if(x.getLi() == null)
                            {
                                return null;
                            }
                            else
                            {
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(x.getLd() == null)
                                {
                                    return null;
                                }
                                else
                                {
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                return x.getValue();
                            }
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
            }
            else
            {
                while(x != R)
                {
                    if(x.getKey() instanceof Comparable & key instanceof Comparable & x.getKey().getClass() == key.getClass())
                    {
                        Comparable w = null, z = null;
                        w = (Comparable)x.getKey();
                        z = (Comparable)key;
                        if(w.compareTo(z) > 0)
                        {
                            if(!x.isBi())
                            {
                                return null;
                            }
                            else
                            {
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(!x.isBd())
                                {
                                    return null;
                                }
                                else
                                {
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                return x.getValue();
                            }
                        }
                    }
                    else
                    {
                        return null;
                    }

                }
            }
        }
        return null;
    }
    public NodoDoble anteriorPre(NodoDoble x)
    {
        NodoDoble p = x.getLi();
        if(x.isBi())
        {
            p = this.padre(x);
            if(x == p.getLd() & p.isBd())
            {
                if(p.isBi())
                {
                    p = p.getLi();
                    while(p.isBi() | p.isBd())
                    {
                        if(p.isBd())
                        {
                            p = p.getLd();
                        }
                        else
                        {
                            p = p.getLi();
                        }
                    }
                }
            }
        }
        return p;
    }
    public void conectarPre(NodoDoble x, NodoDoble nuevo, boolean izquierda)
    {
        if(izquierda)
        {
            x.setBi(true);
            nuevo.setBd(false);
            nuevo.setBi(false);
            if(x.isBd())
            {
                nuevo.setLd(x.getLd());
                if(!x.getLd().isBi())
                {
                    x.getLd().setLi(nuevo);
                }
                nuevo.setLi(x);
            }
            else
            {
                nuevo.setLd(x.getLd());
                if(!x.getLd().isBi())
                {
                    x.getLd().setLi(nuevo);
                }
                nuevo.setLi(x);
                x.setLd(nuevo);
            }
            x.setLi(nuevo);
        }
        else
        {
            nuevo.setBi(false);
            nuevo.setBd(false);
            x.setBd(true);
            if(x.isBi())
            {
                NodoDoble p = x.getLi();
                if(p.isBi())
                {
                    while(!p.isBd() && p.isBi())
                    {
                        p = p.getLi();
                    }
                }
                while((p.isBd() | p.isBi()) & p != R)
                {
                    if(p.isBd())
                    {
                        p = p.getLd();
                    }
                    else
                    {
                        p = p.getLi();
                    }
                }
                if(p != R)
                {
                    p.setLd(nuevo);
                    nuevo.setLi(p);
                }
                NodoDoble aux = x;
                NodoDoble paux = this.padre(aux);
                while(aux == paux.getLd() | !paux.isBd())
                {
                    aux = paux;
                    paux = this.padre(aux);
                }
                aux = paux;
                if(aux != null)
                {
                    p = aux.getLd();
                }
                else
                {
                    p = R;
                }
                if(!p.isBi())
                {
                    p.setLi(nuevo);
                }
                nuevo.setLd(p);
            }
            else
            {
                nuevo.setLd(x.getLd());
                nuevo.setLi(x);
                if(!x.getLd().isBi())
                {
                    x.getLd().setLi(nuevo);
                }
            }
            x.setLd(nuevo);
        }
    }
    public boolean insertarPre(Object dato)
    {
        if(this.isSearch())
        {
            NodoDoble nuevo = new NodoDoble(dato);
            if(orden.equals(PREORDEN))
            {
                NodoDoble x = R.getLi();
                if(R == x)
                {
                    length = length + 1;
                    AVL = false;
                    R.setBi(true);
                    nuevo.setBi(false);
                    nuevo.setBd(false);
                    nuevo.setLi(R);
                    nuevo.setLd(R);
                    R.setLi(nuevo);
                    return true;
                }
                while(x != R)
                {
                    if(x.getKey() instanceof Comparable & nuevo.getKey() instanceof Comparable & x.getKey().getClass() == nuevo.getKey().getClass())
                    {
                        Comparable w = null, z = null;
                        w = (Comparable)x.getKey();
                        z = (Comparable)nuevo.getKey();
                        if(w.compareTo(z) > 0)
                        {
                            if(!x.isBi())
                            {
                                length = length + 1;
                                AVL = false;
                                this.conectarPre(x, nuevo, true);
                                return true;
                            }
                            else
                            {
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(!x.isBd())
                                {
                                    length = length + 1;
                                    AVL = false;
                                    this.conectarPre(x, nuevo, false);
                                    return true;
                                }
                                else
                                {
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(NULL))
            {
                return this.insertarNormal(dato);
            }
            if(orden.equals(INORDEN))
            {
                return this.insertarIn(dato);
            }
            if(orden.equals(POSORDEN))
            {
                return this.insertarPos(dato);
            }
        }
        return false;
    }
    public int anchoDerecha(NodoDoble x)
    {
        int ancho = 0;
        if(x != null)
        {
            if(x.isBd())
            {
                ancho = anchoDerecha(x.getLd()) + 1;
            }
            else
            {
                ancho = 1;
            }
        }
        return ancho;
    }
    public int anchoIzquierda(NodoDoble x)
    {
        int ancho = 0;
        if(x != null)
        {
            if(x.isBi())
            {
                ancho = anchoIzquierda(x.getLi()) + 1;
            }
            else
            {
                ancho = 1;
            }
        }
        return ancho;
    }
    public NodoDoble siguientePre(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        if(x.isBd())
        {
            if(x.isBi())
            {
                p = x.getLi();
            }
        }
        return p;
    }
    public NodoDoble siguientePos(NodoDoble x)
    {
        NodoDoble p = null;
        if(x == R)
        {
            p = x.getLi();
            if(p == x)
            {
                return R;
            }
            while(p.isBi() | p.isBd())
            {
                if(p.isBi())
                {
                    p = p.getLi();
                }
                else
                {
                    p = p.getLd();
                }
            }
            return p;
        }
        p = x.getLd();
        if(x.isBd())
        {
            NodoDoble pp = padre(x);
            p = pp;
            if(p == R)
            {
                return p;
            }
            if(x == pp.getLi())
            {
                if(p.isBd())
                {
                    p = p.getLd();
                    if(p == x)
                    {
                        return pp;
                    }
                    while(p.isBd() | p.isBi())
                    {
                        if(p.isBi())
                        {
                            p = p.getLi();
                        }
                        else
                        {
                            p = p.getLd();
                        }
                    }
                }
            }
        }
        return p;
    }
    public void conectarPos(NodoDoble x, NodoDoble nuevo, boolean izquierda)
    {
        if(izquierda)
        {
            nuevo.setBd(false);
            nuevo.setBi(false);
            NodoDoble p = x;
            if(p.isBd())
            {
                p = p.getLd();
                while(p.isBd() | p.isBi())
                {
                    if(p.isBi())
                    {
                        p = p.getLi();
                    }
                    else
                    {
                        p = p.getLd();
                    }
                }
            }
            nuevo.setLd(p);
            NodoDoble aux = x.getLi();
            if(p != x)
            {
                nuevo.setLi(p.getLi());
                if(!p.getLi().isBd() )
                {
                    p.getLi().setLd(nuevo);
                }
                p.setLi(nuevo);
            }
            else
            {
                nuevo.setLi(aux);
                if(!aux.isBd())
                {
                    aux.setLd(nuevo);
                }
            }
            x.setBi(true);
            x.setLi(nuevo);
        }
        else
        {
            nuevo.setBd(false);
            nuevo.setBi(false);
            if(x.isBi())
            {
                nuevo.setLi(x.getLi());
                if(!x.getLi().isBd())
                {
                    x.getLi().setLd(nuevo);
                }
                nuevo.setLd(x);
            }
            else
            {
                if(!x.getLi().isBd())
                {
                    x.getLi().setLd(nuevo);
                }
                nuevo.setLi(x.getLi());
                nuevo.setLd(x);
                x.setBi(false);
                x.setLi(nuevo);
            }
            x.setBd(true);
            x.setLd(nuevo);
        }
    }
    public boolean insertarPos(Object dato)
    {
        NodoDoble nuevo = new NodoDoble(dato);
        if(orden.equals(POSORDEN))
        {
            NodoDoble x = R.getLi();
            if(R == x)
            {
                length = length + 1;
                AVL = false;
                R.setBi(true);
                nuevo.setBi(false);
                nuevo.setBd(false);
                nuevo.setLi(R);
                nuevo.setLd(R);
                R.setLi(nuevo);
                return true;
            }
            while(x != R)
            {
                if(x.getKey() instanceof Comparable & nuevo.getKey() instanceof Comparable & x.getKey().getClass() == nuevo.getKey().getClass())
                {
                    Comparable w = null, z = null;
                    w = (Comparable)x.getKey();
                    z = (Comparable)nuevo.getKey();
                    if(w.compareTo(z) > 0)
                    {
                        if(!x.isBi())
                        {
                            length = length + 1;
                            AVL = false;
                            this.conectarPos(x, nuevo, true);
                            return true;
                        }
                        else
                        {
                            x = x.getLi();
                        }
                    }
                    else
                    {
                        if(w.compareTo(z) < 0)
                        {
                            if(!x.isBd())
                            {
                                length = length + 1;
                                AVL = false;
                                this.conectarPos(x, nuevo, false);
                                return true;
                            }
                            else
                            {
                                x = x.getLd();
                            }
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        if(orden.equals(NULL))
        {
            return this.insertarNormal(dato);
        }
        if(orden.equals(PREORDEN))
        {
            return this.insertarPre(dato);
        }
        if(orden.equals(INORDEN))
        {
            return this.insertarIn(dato);
        }
        return false;
    }
    public NodoDoble padre(NodoDoble x)
    {
        return x != null? x.getLp() : null;
    }
    public NodoDoble padre(NodoDoble x, NodoDoble px)
    {
        if(px == x || x == null || px == null)
        {
            return null;
        }
        if(x == px.getLi() || x == px.getLd())
        {
            return px;
        }
        NodoDoble pi = padre(x,px.getLi());
        if(pi == null)
        {
            return padre(x,px.getLd());
        }
        return pi;
    }
    @Deprecated
    public NodoDoble padreB(NodoDoble x)
    {
        Stack pila = new Stack();
        NodoDoble pp = R;
        NodoDoble p = R.getLi();
        if(p == pp)
        {
            return null;
        }
        while(p.isBd() | p.isBi() | !pila.isEmpty())
        {
            if(p == x)
            {
                return pp;
            }
            if(!p.isBd())
            {
                if(p.isBi())
                {
                    pp = p;
                    p = p.getLi();
                }
                else
                {
                    if(!pila.isEmpty())
                    {
                        pp = (NodoDoble)pila.pop();
                        p = pp.getLd();
                    }
                }
            }
            else
            {
                if(p.isBi())
                {
                    pila.push(p);
                    pp = p;
                    p = p.getLi();
                }
                else
                {
                    pp = p;
                    p = p.getLd();
                }
            }
        }
        if(p == x)
        {
            return pp;
        }
        return null;
    }
    public void menorNormal(NodoDoble x)
    {
        NodoDoble p = x.getLd();
        while(p.getLi() != null)
        {
            p = p.getLi();
        }
        NodoDoble pp = padre(p);
        x.setValue(p.getValue());
        x.setKey(p.getKey());
        this.eliminarNormal(p, pp);
    }
    public void eliminarNormal(NodoDoble x, NodoDoble ax)
    {
        if(ax.getLd() == x)
        {
            if(x.getLd() == null)
            {
                ax.setLd(x.getLi());
            }
            else
            {
                if(x.getLi() == null)
                {
                    ax.setLd(x.getLd());
                }
                else
                {
                    this.menorNormal(x);
                }
            }
        }
        else
        {
            if(x.getLd() == null)
            {
                ax.setLi(x.getLi());
                if(ax == R && x.getLi() == null)
                {
                    R.setBi(false);
                    R.setLi(R);
                }
            }
            else
            {
                if(x.getLi() == null)
                {
                    ax.setLi(x.getLd());
                }
                else
                {
                    this.menorNormal(x);
                }
            }
        }
    }
    public boolean borrarNormal(Object dato)
    {
        if(this.isSearch())
        {
            if(orden.equals(NULL))
            {
                NodoDoble ax = R;
                NodoDoble x = R.getLi();
                while(x != null)
                {
                    if(x.getKey() instanceof Comparable & dato instanceof Comparable & x.getKey().getClass() == dato.getClass())
                    {
                        Comparable z = (Comparable)dato;
                        Comparable w = (Comparable)x.getKey();
                        if(w.compareTo(z) > 0)
                        {
                            if(x.getLi() == null)
                            {
                                return false;
                            }
                            else
                            {
                                ax = x;
                                x = x.getLi();
                            }
                        }
                        else
                        {
                            if(w.compareTo(z) < 0)
                            {
                                if(x.getLd() == null)
                                {
                                    return false;
                                }
                                else
                                {
                                    ax = x;
                                    x = x.getLd();
                                }
                            }
                            else
                            {
                                AVL = false;
                                length = length - 1;
                                this.eliminarNormal(x, ax);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            if(orden.equals(INORDEN))
            {
                return this.borrarIn(dato);
            }
            if(orden.equals(POSORDEN))
            {
                return this.borrarPos(dato);
            }
            if(orden.equals(PREORDEN))
            {
                return this.borrarPre(dato);
            }
        }
        return false;
    }
    @Deprecated
    public int size(NodoDoble x)
    {
        int size = 0;
        if(x != null)
        {
            size = 1 + size(x.getLi());
            size = size + size(x.getLd());
        }
        return size;
    }
    public int sizeP(NodoDoble x)
    {
        if(x == null)
        {
            return 0;
        }
        Stack pila = new Stack();
        NodoDoble p = x;
        int size = 0;
        NodoDoble pp = x.getLi();
        if(p == pp)
        {
            return size;
        }
        size = 1;
        while(p != R && (p.isBd() | p.isBi() | !pila.isEmpty()))
        {
            size = size + 1;
            if(!p.isBd())
            {
                if(p.isBi())
                {
                    pp = p;
                    p = p.getLi();
                }
                else
                {
                    if(!pila.isEmpty())
                    {
                        pp = (NodoDoble)pila.pop();
                        p = pp.getLd();
                    }
                }
            }
            else
            {
                if(p.isBi())
                {
                    pila.push(p);
                    pp = p;
                    p = p.getLi();
                }
                else
                {
                    pp = p;
                    p = p.getLd();
                }
            }
        }
        return size;
    }
    public Object[] niveles(NodoDoble r)
    {
        Object niveles[] = new Object[length];
        int i = 0;
        if(r == R)
        {
            r = r.getLi();
        }
        if(r.isBi() | r.isBd())
        {
            Cola cola = new Cola();
            cola.encolar(r);
            NodoDoble x = null;
            while(!cola.isEmpty())
            {
                x = (NodoDoble)cola.desencolar();
                niveles[i] = x.getValue();
                i = i + 1;
                System.out.print(x.getValue()+",");
                if(x.getLi() != null | x.isBi())
                {
                    cola.encolar(x.getLi());
                }
                if(x.getLd() != null | x.isBd())
                {
                    cola.encolar(x.getLd());
                }
            }
        }
        return niveles;
    }
    @Deprecated
    public ArbolLista crearArbolPreIn(Object pre[], Object in[])
    {
        ArbolLista nuevo = null;
        if(pre.length == in.length)
        {
            nuevo = new ArbolLista();
            nuevo.setRaiz(consabPreIn(0, pre.length - 1, 0, pre, in));
            nuevo.setLength(this.sizeP(nuevo.getRaiz()));
        }
        return nuevo;
    }
    @Deprecated
    public ArbolLista crearArbolPosIn(Object pre[], Object in[])
    {
        ArbolLista nuevo = null;
        if(pre.length == in.length)
        {
            nuevo = new ArbolLista();
            nuevo.setRaiz(consabPosIn(0, pre.length - 1, pre.length - 1, pre, in));
            nuevo.setLength(this.sizeP(nuevo.getRaiz()));
        }
        return nuevo;
    }
    public static ArbolLista crearArbolPre(Object pre[], Object in[])
    {
        if(pre.length == in.length && pre.length > 0)
        {
            return consabPre(0, pre.length - 1, 0, pre, in);
        }
        return new ArbolLista();
    }
    public static ArbolLista crearArbolPos(Object pos[], Object in[])
    {
        if(pos.length == in.length)
        {
            return consabPos(0, pos.length - 1, pos.length - 1, pos, in);
        }
        return new ArbolLista();
    }
    public static ArbolLg convertir(ArbolLista arbol)
    {
        ArbolLg arbolg = new ArbolLg();
        Stack pila = new Stack(), pila1 = new Stack();
        NodoDoble p = arbol.getRaiz();
        NodoLg x = new NodoLg(p.getValue()), sx = null;
        arbolg.setPrimero(x);
        p = p.getLi();
        while(p != null && (p.isBd() | p.isBi() | (!pila.isEmpty() & !pila1.isEmpty())))
        {
            if(p.isBi())
            {
                if(p.isBd())
                {
                    sx = new NodoLg();
                    sx.setSw(true);
                    x.setLiga(sx);
                    pila1.push(sx);
                    pila.push(p.getLd());
                    x = sx;
                    sx = new NodoLg(p.getValue());
                    x.setDato(sx);
                    x = sx;
                    p = p.getLi();
                }
                else
                {
                    sx = new NodoLg();
                    sx.setSw(true);
                    x.setLiga(sx);
                    x = sx;
                    sx = new NodoLg(p.getValue());
                    x.setDato(sx);
                    x = sx;
                    p = p.getLi();
                }
            }
            else
            {
                if(p.isBd())
                {
                    sx = new NodoLg(p.getValue());
                    x.setLiga(sx);
                    x = sx;
                    p = p.getLd();
                }
                else
                {
                    sx = new NodoLg(p.getValue());
                    x.setLiga(sx);
                    if(!pila.isEmpty() && !pila1.isEmpty())
                    {
                        x = (NodoLg)pila1.pop();
                        p = (NodoDoble)pila.pop();
                    }
                }
            }
            if(!p.isBd() && !p.isBd() & pila.isEmpty() & pila1.isEmpty())
            {
                sx = new NodoLg(p.getValue());
                x.setLiga(sx);
            }
        }
        return arbolg;
    }
    public static ArbolLista consabPos(int lin, int lis, int i, Object pos[], Object in[])
    {
        ArbolLista hi = new ArbolLista();
        ArbolLista hd = new ArbolLista();
        int k = lin;
        if(lis > lin)
        {
            while(in[k] != pos[i] & k < lis)
            {
                k = k + 1;
            }
            if(k > lin)
            {
                if(k < lis)
                {
                    hd = consabPos(k + 1, lis, i - 1, pos, in);
                }
                i = i - hd.getLength();
                hi = consabPos(lin, k - 1, i - 1, pos, in);
            }
            else
            {
                if(k < lis)
                {
                    hd = consabPos(k + 1, lis, i - 1, pos, in);
                }
            }
        }
        NodoDoble x = null;
        x = new NodoDoble(in[k]);
        x.setLi(hi.getRaiz());
        x.setLd(hd.getRaiz());
        ArbolLista ax = new ArbolLista();
        ax.setLength(1 + hd.getLength() + hi.getLength());
        ax.setRaiz(x);
        return ax;
    }
    @Deprecated
    public NodoDoble consabPosIn(int lin, int lis, int i, Object pos[], Object in[])
    {
        NodoDoble hi = null;
        NodoDoble hd = null;
        int k = lin;
        if(lis > lin)
        {
            while(in[k] != pos[i] & k < lis)
            {
                k = k + 1;
            }
            if(k > lin)
            {
                if(k < lis)
                {
                    hd = consabPosIn(k + 1, lis, i-1, pos, in);
                }
                i = i - sizeP(hd);
                hi = consabPosIn(lin, k - 1, i - 1, pos, in);
            }
            else
            {
                if(k < lis)
                {
                    hd = consabPosIn(k + 1, lis, i - 1, pos, in);
                }
            }
        }
        NodoDoble x = null;
        x = new NodoDoble(in[k]);
        x.setLi(hi);
        x.setLd(hd);
        return x;
    }
    public static ArbolLista consabPre(int lin, int lis, int i, Object pre[], Object in[])
    {
        ArbolLista hi = new ArbolLista();
        ArbolLista hd = new ArbolLista();
        int k = lin;
        if(lis > lin)
        {
            while( k < lis && in[k] != pre[i])
            {
                k = k + 1;
            }
            if(k > lin)
            {
                hi = consabPre(lin, k - 1, i + 1, pre, in);
                if(k < lis)
                {
                    i = i + hi.getLength();
                    hd = consabPre(k + 1, lis, i + 1, pre, in);
                }
            }
            else
            {
                if(k < lis)
                {
                    hd = consabPre(k + 1, lis, i + 1, pre, in);
                }
            }
        }
        NodoDoble x = null;
        x = new NodoDoble(in[k]);
        x.setLi(hi.getRaiz());
        x.setLd(hd.getRaiz());
        ArbolLista ax = new ArbolLista();
        ax.setLength(1+hd.getLength()+hi.getLength());
        ax.setRaiz(x);
        return ax;
    }
    @Deprecated
    public NodoDoble consabPreIn(int lin, int lis, int i, Object pre[], Object in[])
    {
        NodoDoble hi = null;
        NodoDoble hd = null;
        int k = lin;
        if(lis > lin)
        {
            while(in[k] != pre[i] & k < lis)
            {
                k = k + 1;
            }
            if(k > lin)
            {
                hi = consabPreIn(lin, k - 1, i + 1, pre, in);
                if(k < lis)
                {
                    i = i + sizeP(hi);
                    hd = consabPreIn(k + 1, lis, i+1, pre, in);
                }
            }
            else
            {
                if(k < lis)
                {
                    hd = consabPreIn(k + 1, lis, i + 1, pre, in);
                }
            }
        }
        NodoDoble x = null;
        x = new NodoDoble(in[k]);
        x.setLi(hi);
        x.setLd(hd);
        return x;
    }
    public NodoDoble anteriorIn(NodoDoble x)
    {
        NodoDoble p = null;
        p = x.getLi();
        if(x.isBi())
        {
            while(p != null && p.isBd())
            {
                p = p.getLd();
            }
        }
        return p;
    }
    public NodoDoble siguienteIn(NodoDoble x)
    {
        NodoDoble p = null;
        p = x.getLd();
        if(x.isBd())
        {
            while(p != null && p.isBi())
            {
                p = p.getLi();
            }
        }
        return p;
    }
    public Object[] preordenPre(NodoDoble r)
    {
        Object preorden[] = new Object[length];
        NodoDoble p = siguientePre(r);
        int i = 0;
        while(p != R)
        {
            preorden[i] = p.getValue();
            i = i + 1;
            p = this.siguientePre(p);
        }
        return preorden;
    }
    public Object[] inordenMejorado(NodoDoble r)
    {
        Object inorden[] = null;
        if(orden.equals(INORDEN))
        {
            inorden = this.inordenIn(r);
        }
        else
        {
            inorden = this.inordenNormalP(r);
        }
        return inorden;
    }
    public Object[] inordenNormalP(NodoDoble r)
    {
        Object inorden[] = new Object[length];
        int i = 0;
        NodoDoble x = r.getLi();
        if(x == r)
        {
            return inorden;
        }
        Stack pila = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
                inorden[i] = x.getValue();
                i = i + 1;
                if(x.isBd())
                {
                    x = x.getLd();
                }
                else
                {
                    x = null;
                }
            }
            else
            {
                pila.push(x);
                if(x.isBi())
                {
                    x = x.getLi();
                }
                else
                {
                    x = null;
                }
            }
        }
        return inorden;
    }
    @Deprecated
    public void inordenNormal(NodoDoble r)
    {
        if(r != null)
        {
            if(r.isBi())
            {
                inordenNormal(r.getLi());
            }
            else
            {
                inordenNormal(null);
            }
            if(r != R)
            {
                inordenN[count] = r.getValue();
                count = count + 1;
            }
            if(r.isBd())
            {
                if(r != R)
                {
                    inordenNormal(r.getLd());
                }
            }
            else
            {
                inordenNormal(null);
            }
        }
    }
    public Object[] inordenIn(NodoDoble r)
    {
        Object inorden[] = new Object[length];
        NodoDoble p = siguienteIn(r);
        int i = 0;
        while(p != r & p!= null)
        {
            inorden[i] = p.getValue();
            i = i + 1;
            p = siguienteIn(p);
        }
        return inorden;
    }
    public Object[] preordenMejorado(NodoDoble r)
    {
        Object preorden[] = null;
        if(orden.equals(PREORDEN))
        {
            preorden = this.preordenPre(r);
        }
        else
        {
            preorden = this.preordenNormalP(r);
        }
        return preorden;
    }
    @Deprecated
    private Object preordenN[] = null;
    @Deprecated
    private Object inordenN[] = null;
    @Deprecated
    private Object posordenN[] = null;
    @Deprecated
    private int count = 0;
    @Deprecated
    public void preordenNormal(NodoDoble r)
    {
        if(r != null)
        {
            if(r != R)
            {
                preordenN[count] = r.getValue();
                count = count + 1;
            }
            if(r.isBi())
            {
                preordenNormal(r.getLi());
            }
            else
            {
                preordenNormal(null);
            }
            if(r.isBd())
            {
                if(r != R)
                {
                    preordenNormal(r.getLd());
                }
            }
            else
            {
                preordenNormal(null);
            }
        }
    }
    public Object[] preordenNormalP(NodoDoble r)
    {
        Object preorden[] = new Object[length];
        NodoDoble x = r.getLi();
        if(x == r)
        {
            return preorden;
        }
        int i = 0;
        Stack pila = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
            }
            else
            {
                preorden[i] = x.getValue();
                i = i + 1;
                if(x.isBd())
                {
                    pila.push(x.getLd());
                }
                else
                {
                    pila.push(null);
                }
                if(x.isBi())
                {
                    x = x.getLi();
                }
                else
                {
                    x = null;
                }
            }
        }
        return preorden;
    }
    @Deprecated
    public void posordenNormal(NodoDoble r)
    {
        if(r != null)
        {
            if(r.isBi())
            {
                posordenNormal(r.getLi());
            }
            else
            {
                posordenNormal(null);
            }
            if(r.isBd())
            {
                if(r != R)
                {
                    posordenNormal(r.getLd());
                }
            }
            else
            {
                posordenNormal(null);
            }
            if(r != R)
            {
                posordenN[count] = r.getValue();
                count = count + 1;
            }
        }
    }
    public Object[] posordenMejorado(NodoDoble r)
    {
        Object posorden[] = null;
        if(orden.equals(POSORDEN))
        {
            posorden = this.posordenPos(r);
        }
        else
        {
            posorden = this.posordenNormalP(r);
        }
        return posorden;
    }
    public Object[] posordenPos(NodoDoble r)
    {
        Object posorden[] = new Object[length];
        int i = 0;
        NodoDoble p = siguientePos(r);
        while(p != R)
        {
            posorden[i] = p.getValue();
            i = i + 1;
            p = siguientePos(p);
        }
        return posorden;
    }
    public Object[] posordenNormalP(NodoDoble r)
    {
        Object posorden[] = new Object[length];
        int i = 0;
        NodoDoble x = r.getLi();
        if(x == r)
        {
            return posorden;
        }
        Stack pila = new Stack(), vuelta = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
            }
            else
            {
                vuelta.push(x);
                if(x.isBi())
                {
                    pila.push(x.getLi());
                }
                else
                {
                    pila.push(null);
                }
                if(x.isBd())
                {
                    x = x.getLd();
                }
                else
                {
                    x = null;
                }
            }
        }
        while(!vuelta.isEmpty())
        {
            x = (NodoDoble)vuelta.pop();
            posorden[i] = x.getValue();
            i = i + 1;
        }
        return posorden;
    }
    @Deprecated
    public void enhebrarInorden()
    {
        ant = R;
        inorden(R.getLi());
        if(!ant.isBd())
        {
            ant.setBd(false);
            ant.setLd(R);
        }
        orden = INORDEN;
    }
    @Deprecated
    public void enhebrarPreorden()
    {
        ant = R;
        preorden(R.getLi());
        if(!ant.isBd())
        {
            ant.setBd(false);
            ant.setLd(R);
        }
        orden = PREORDEN;
    }
    @Deprecated
    public void enhebrarPosorden()
    {
        ant = R;
        posorden(R.getLi());
        if(!ant.isBd())
        {
            ant.setBd(false);
            ant.setLd(R);
        }
        orden = POSORDEN;
    }
    @Deprecated
    public void inorden(NodoDoble x)
    {
        if(x != null)
        {
            if(x.isBi())
            {
                inorden(x.getLi());
            }
            else
            {
                inorden(null);
            }
            if(!x.isBi())
            {
                x.setBi(false);
                x.setLi(ant);
            }
            if(!ant.isBd() & ant != R)
            {
                ant.setBd(false);
                ant.setLd(x);
            }
            ant = x;
            if(x.isBd())
            {
                inorden(x.getLd());
            }
            else
            {
                inorden(null);
            }
        }
    }
    @Deprecated
    public void preorden(NodoDoble x)
    {
        if(x != null)
        {
            NodoDoble aux = x.getLi();
            boolean isBi = x.isBi();
            if(!x.isBi())
            {
                x.setBi(false);
                x.setLi(ant);
            }
            if(!ant.isBd() & ant != R)
            {
                ant.setBd(false);
                ant.setLd(x);
            }
            ant = x;
            if(isBi)
            {
                preorden(aux);
            }
            else
            {
                preorden(null);
            }
            if(x.isBd())
            {
                preorden(x.getLd());
            }
            else
            {
                preorden(null);
            }
        }
    }
    public void preorden()
    {
        orden = PREORDEN;
        NodoDoble x = R.getLi();
        NodoDoble ax = R;
        if(x == ax)
        {
            return;
        }
        Stack pila = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
            }
            else
            {
                boolean isBi = x.isBi();
                NodoDoble aux = x.getLi();
                if(!x.isBi())
                {
                    x.setBi(false);
                    x.setLi(ax);
                }
                if(!ax.isBd())
                {
                    ax.setBd(false);
                    ax.setLd(x);
                }
                ax = x;
                if(isBi)
                {
                    x = aux;
                }
                else
                {
                    x = null;
                }
                if(ax.isBd())
                {
                    pila.push(ax.getLd());
                }
                else
                {
                    pila.push(null);
                }
            }
        }
        if(!ax.isBd())
        {
            ax.setBd(false);
            ax.setLd(R);
        }
    }
    public void inorden()
    {
        orden = INORDEN;
        NodoDoble x = R.getLi();
        NodoDoble ax = R;
        if(x == ax)
        {
            return;
        }
        Stack pila = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
                if(!x.isBi())
                {
                    x.setBi(false);
                    x.setLi(ax);
                }
                if(!ax.isBd())
                {
                    ax.setBd(false);
                    ax.setLd(x);
                }
                ax = x;
                if(x.isBd())
                {
                    x = x.getLd();
                }
                else
                {
                    x = null;
                }
            }
            else
            {
                pila.push(x);
                if(x.isBi())
                {
                    x = x.getLi();
                }
                else
                {
                    x = null;
                }
            }
        }
        if(!ax.isBd())
        {
            ax.setBd(false);
            ax.setLd(R);
        }
    }
    public void posorden()
    {
        orden = POSORDEN;
        NodoDoble x = R.getLi();
        NodoDoble ax = R;
        if(x == ax)
        {
            return;
        }
        Stack pila = new Stack(), vuelta = new Stack();
        while(x != null || !pila.isEmpty())
        {
            if(x == null)
            {
                x = (NodoDoble)pila.pop();
            }
            else
            {
                vuelta.push(x);
                if(x.isBi())
                {
                    pila.push(x.getLi());
                }
                else
                {
                    pila.push(null);
                }
                if(x.isBd())
                {
                    x = x.getLd();
                }
                else
                {
                    x = null;
                }
            }
        }
        while(!vuelta.isEmpty())
        {
            x = (NodoDoble)vuelta.pop();
            if(!ax.isBd() | ax.getLd() == null)
            {
                ax.setBd(false);
                ax.setLd(x);
            }
            if(!x.isBi() | x.getLi() == null)
            {
                x.setBi(false);
                x.setLi(ax);
            }
            ax = x;
        }
        if(!ax.isBd())
        {
            ax.setBd(false);
            ax.setLd(R);
        }
    }
    @Deprecated
    public void posorden(NodoDoble x)
    {
        if(x != null)
        {
            if(x.isBi())
            {
                posorden(x.getLi());
            }
            else
            {
                posorden(null);
            }
            if(x.isBd())
            {
                posorden(x.getLd());
            }
            else
            {
                posorden(null);
            }
            if(!x.isBi())
            {
                x.setBi(false);
                x.setLi(ant);
            }
            if(!ant.isBd())
            {
                ant.setBd(false);
                ant.setLd(x);
            }
            ant = x;
        }
    }
}
