/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udea.logic.generictree;
import java.util.*;
public class ArbolLg
{
    private NodoLg primero = null, ultimo = null;
    private ArbolLista arbol = new ArbolLista();
    public ArbolLg()
    {
        primero = ultimo = null;
    }
    public ArbolLg copy()
    {
        ArbolLg copia = new ArbolLg();
        copia.setPrimero(this.copia(primero));
        return copia;
    }
    public boolean equals(ArbolLg x, ArbolLg y)
    {
        return equals(x.getPrimero(), y.getPrimero());
    }
    public boolean equals(NodoLg x, NodoLg y)
    {
        if(x == null && y == null)
        {
            return true;
        }
        if(x != null && y != null)
        {
            if(x.isSw() == y.isSw())
            {
                if(x.isSw()) 
                {
                    return equals(x.getLiga(), y.getLiga()) && equals((NodoLg)x.getDato(), (NodoLg)y.getDato());
                }
                else
                {
                    return equals(x.getLiga(), y.getLiga()) && x.getDato().equals(y.getDato());
                }
            }
        }
        return false;
    }
    public NodoLg copia(NodoLg x)
    {
        if(x == null)
        {
            return null;
        }
        NodoLg c = null;
        if(x.isSw())
        {
            c = new NodoLg();
            c.setSw(true);
            c.setDato(this.copia((NodoLg)x.getDato()));
            c.setLiga(this.copia(x.getLiga()));
        }
        else
        {
            c = new NodoLg(x.getDato());
            c.setLiga(this.copia(x.getLiga()));
        }
        return c;
    }
    public ArbolLg crearTree(String s)
    {
        ArbolLg x = new ArbolLg();
        j = 1;
        p = true;
        x.setPrimero(this.crearLista(s));
        return x;
    }
    private int j = 0;
    public boolean p = true;
    public NodoLg crearLista(String s)
    {
        if(j < s.length())
        {
            NodoLg x = new NodoLg();
            System.out.println(s.charAt(j));
            if(j < s.length())
            {
                x.setDato(s.charAt(j));
                j = j + 1;
            }
            if(j < s.length() && s.charAt(j) == '(')
            {
                if(!p)
                {
                x.setSw(true);
                j = j + 1;
                x.setDato(crearLista(s));
                }
                p = false;
                x.setLiga(crearLista(s));
            }
            else if(j < s.length() && s.charAt(j) == ',')
            {
                j = j + 1;
                x.setLiga(crearLista(s));
            }
            else
            {
            System.out.println(s.charAt(j));
               
            }
            return x;
        }
        return null;
    }
    public NodoLg getUltimo()
    {
        return ultimo;
    }
    public int hojas()
    {
        Stack pila = new Stack();
        if(primero == null)
        {
            return 0;
        }
        NodoLg p = primero.getLiga();
        if(p == null)
        {
            return 1;
        }
        int h = 0;
        while(p != null)
        {
            if(!p.isSw())
            {
                h = h + 1;
            }
            else
            {
                if(p.getLiga() != null)
                {
                    pila.push(p.getLiga());
                }
                p = (NodoLg)p.getDato();
            }
            p = p.getLiga();
            if(!pila.isEmpty() & p == null)
            {
                p = (NodoLg)pila.pop();
            }
        }
        return h;
    }
    public NodoLg getPrimero()
    {
        return primero;
    }
    public void borrar(NodoLg dato)
    {
        if(dato != null)
        {

        }
    }
    public void construirArbol(ArbolLista arbol)
    {
        Stack pila = new Stack(), pila1 = new Stack();
        NodoDoble p = arbol.getRaiz();
        NodoLg x = new NodoLg(p.getValue()), sx = null;
        this.setPrimero(x);
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
        this.arbol = arbol;
    }
    public void setArbol(ArbolLista arbol)
    {
        this.arbol = arbol;
    }
    public ArbolLista getArbol()
    {
        return arbol;
    }
    public ArbolLg copia()
    {
        String string = this.toString();
        ArbolLg copia = new ArbolLg();
        copia.construirArbol(string);
        return copia;
    }
    public void borrar(NodoDoble ax, NodoDoble x)
    {
        this.arbol.eliminarGeneral(ax, x);
        this.construirArbol(this.arbol);
    }
    public void insertar(NodoDoble x, NodoDoble nuevo)
    {
        this.arbol.conectarGeneral(x, nuevo);
        this.construirArbol(this.arbol);
    }
    public ArbolLista convertir()
    {
        ArbolLista z = new ArbolLista();
        NodoDoble q = null, r = null;
        Stack pila = new Stack(), pila1 = new Stack();
        NodoLg p = this.getPrimero(), aux = null;
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
        z.setSearch(false);
        return z;
    }
    public int altura(NodoLg r)
    {
        if(r == null)
        {
            return 0;
        }
        NodoLg p = r.getLiga();
        if(p == null)
        {
            return 1;
        }
        int hMax = 2, h = 2;
        while(p != null)
        {
            if(p.isSw())
            {
                h = 1 + altura((NodoLg)p.getDato());
                if(h > hMax)
                {
                    hMax = h;
                }
            }
            p = p.getLiga();
        }
        return hMax;
    }
    public NodoLg padre(Object x)
    {
        Stack pila = new Stack(), pila1 = new Stack();
        if(primero.getDato().equals(x))
        {
            return null;
        }
        NodoLg pp = null;
        pila.push(pp);
        pp = primero;
        NodoLg p = primero.getLiga();
        while(p != null)
        {
            if(p.isSw())
            {
                pila.push(p.getLiga());
                p = (NodoLg)p.getDato();
                if(p.getDato().equals(x))
                {
                    return pp;
                }
                pila1.push(pp);
                pp = p;
            }
            else
            {
                if(p.getDato()!= null && p.getDato().equals(x))
                {
                    return pp;
                }
            }
            p = p.getLiga();
            while(p == null & !pila.isEmpty())
            {
                p = (NodoLg)pila.pop();
                if(!pila1.isEmpty())
                {
                    pp = (NodoLg)pila1.pop();
                }
            }
        }
        return pp;
    }
    public Object[] ancestros(Object elemento)
    {
        Object ancestro[] = null;
        int i = 0;
        Object dato = null;
        Stack pila = new Stack(), pilaAncestros = new Stack();
        NodoLg x = this.getPrimero();
        while(x != null)
        {
            if(x.isSw())
            {
                pila.push(x.getLiga());
                x = (NodoLg)x.getDato();
                pilaAncestros.push(x.getDato());
                if(x.getDato().equals(elemento))
                {
                    i = pilaAncestros.size();
                    ancestro = new Object[i + 1];
                    while(!pilaAncestros.empty())
                    {
                        dato = pilaAncestros.pop();
                        ancestro[i] = dato;
                        i = i - 1;
                    }
                    ancestro[i] = this.getPrimero().getDato();
                    return ancestro;
                }
            }
            else
            {
                if(x.getDato().equals(elemento))
                {
                    i = pilaAncestros.size();
                    ancestro = new Object[i + 1];
                    while(!pilaAncestros.empty())
                    {
                        dato = pilaAncestros.pop();
                        ancestro[i] = dato;
                        i = i - 1;
                    }
                    ancestro[i] = this.getPrimero().getDato();
                    return ancestro;
                }
            }
            x = x.getLiga();
            while(x == null & !pila.isEmpty())
            {
                if(!pilaAncestros.isEmpty())
                {
                    pilaAncestros.pop();
                }
                x = (NodoLg)pila.pop();
            }
            if(x != null && x.getDato().equals(elemento))
            {
                pilaAncestros.push(x.getDato());
                i = pilaAncestros.size();
                ancestro = new Object[i + 1];
                while(!pilaAncestros.empty())
                {
                    dato = pilaAncestros.pop();
                    ancestro[i] = dato;
                    i = i - 1;
                }
                ancestro[i] = this.getPrimero().getDato();
                return ancestro;
            }
        }
        i = pilaAncestros.size();
        ancestro = new Object[i + 1];
        while(!pilaAncestros.empty())
        {
            dato = pilaAncestros.pop();
            ancestro[i] = dato;
            i = i - 1;
        }
        ancestro[i] = this.getPrimero().getDato();
        return ancestro;
    }
    public int hojas(NodoLg r)
    {
        if(r == null)
        {
            return 0;
        }
        NodoLg p = r.getLiga();
        if(p == null)
        {
            return 1;
        }
        int h = 0;
        while(p != null)
        {
            if(p.isSw())
            {
                h = h + hojas((NodoLg)p.getDato());
            }
            else
            {
                h = h + 1;
            }
            p = p.getLiga();
        }
        return h;
    }
    public int grado(NodoLg r)
    {
        if(r == null)
        {
            return 0;
        }
        NodoLg p = r.getLiga();
        if(p == null)
        {
            return 0;
        }
        int n = 0, grMax = 0, gr = 0;
        while(p != null)
        {
            n = n + 1;
            if(p.isSw())
            {
                gr = grado((NodoLg)p.getDato());
                if(gr > grMax)
                {
                    grMax = gr;
                }
            }
            p = p.getLiga();
        }
        if(n > grMax)
        {
            grMax = n;
        }
        return grMax;
    }
    public void mostrar()
    {
        Stack pila = new Stack();
        System.out.print("(");
        NodoLg x = primero;
        while(x != null)
        {
            if(x.isSw())
            {
                System.out.print("(");
                pila.push(x.getLiga());
                x = (NodoLg)x.getDato();              
            }
            else
            {
                System.out.print(x.getDato());
                if(x.getLiga() != null & x != primero)
                {
                    System.out.print(",");
                }
                if(x == primero & x.getLiga() != null)
                {
                    System.out.print("(");
                }
            }
            x = x.getLiga();
            if(x == null)
            {
                while(x == null & !pila.isEmpty())
                {
                    System.out.print(")");
                    if(!pila.isEmpty())
                    {
                        x = ((NodoLg)pila.pop());
                    }
                }
                if(x!= null)
                {
                    System.out.print(",");
                    if(x.isSw())
                    {
                        System.out.print(((NodoLg)x.getDato()).getDato());
                    }
                }
            }
            else
            {
                if(x.isSw())
                {
                    System.out.print(((NodoLg)x.getDato()).getDato());
                }
            }
        }
        System.out.print(")");
        System.out.print(")");
    }
    @Override
    public String toString()
    {
        StringBuilder toString = new StringBuilder("");
        Stack pila = new Stack();
        toString.append("(");
        NodoLg x = primero;
        while(x != null)
        {
            if(x.isSw())
            {
                toString.append("(");
                pila.push(x.getLiga());
                x = (NodoLg)x.getDato();
            }
            else
            {
                toString.append(x.getDato());
                if(x.getLiga() != null & x != primero)
                {
                    toString.append(",");
                }
                if(x == primero & x.getLiga() != null)
                {
                    toString.append("(");
                }
            }
            x = x.getLiga();
            if(x == null)
            {
                while(x == null & !pila.isEmpty())
                {
                    toString.append(")");
                    x = (NodoLg)pila.pop();
                }
                if(x!= null)
                {
                    toString.append(",");
                    if(x.isSw())
                    {
                        toString.append(((NodoLg)x.getDato()).getDato());
                    }
                }
            }
            else
            {
                if(x.isSw())
                {
                    toString.append(((NodoLg)x.getDato()).getDato());
                }
            }
        }
        toString.append(")");
        toString.append(")");
        return toString.toString();
    }
    public String nextAtom(int i, String s)
    {
        String atom = "";
        int j = i;
        while(j < s.length() && Character.isLetterOrDigit(s.charAt(j)))
        {
            j = j + 1;
        }
        atom = s.substring(i, j);
        return atom;
    }
    public void setUltimo(NodoLg ultimo)
    {
        this.ultimo = ultimo;
    }
    public void setPrimero(NodoLg primero)
    {
        this.primero = primero;
    }
    private int length = 0;
    public ArbolLista crear()
    {
        ArbolLista x = new ArbolLista();
        p = true;
        x.setRaiz(crear(this.getPrimero()));
        x.setLength(length);
        return x;
    }
    public void construir1(String s)
    {
        j = 1;
        primero = construir(s);
    }
    private NodoLg s = null;
    public NodoDoble crear(NodoLg x)
    {
        if(x == null)
        {
            return null;
        }
        NodoDoble n = new NodoDoble();
        if(x.isSw())
        {
            n.setValue(((NodoLg)x.getDato()).getDato());
            n.setLi(crear(((NodoLg)x.getDato()).getLiga()));
            n.setLd(crear(x.getLiga()));
        }
        else
        {
            n.setValue(x.getDato());
            if(x == this.getPrimero())
            {
                n.setLi(crear(x.getLiga()));
            }
            else
            {
                n.setLd(crear(x.getLiga()));
            }
        }
        length++;
        return n;
    }
    public ArbolLg convertir(ArbolLista x)
    {
        ArbolLg tree = new ArbolLg();p = true;
        tree.setPrimero(convertir(x.getRaiz()));
        return tree;
    }
    public NodoLg convertir(NodoDoble x)
    {
        if( x == null)
        {
            return null;
        }
        NodoLg n = null;
        if(x.getLi() != null)
        {
            if(!p)
            {
            n = new NodoLg();
            n.setSw(true);
            NodoLg m = new NodoLg(x.getValue());
            m.setLiga(convertir(x.getLi()));
            n.setLiga(convertir(x.getLd()));
            n.setDato(m);
            }
            else
            {
            n = new NodoLg(x.getValue());p=false;
            n.setLiga(convertir(x.getLi()));
            }
        }
        else
        {
            n = new NodoLg(x.getValue());
            n.setLiga(convertir(x.getLd()));
        }
        return n;
    }
    public NodoLg reversa(NodoLg x)
    {
        if(x == null)
        {
            return null;
        }
        Stack pila = new Stack();
        NodoLg z = x;
        while(z != null)
        {
            pila.push(z);
            z = z.getLiga();
        }
        NodoLg w = (NodoLg)pila.pop();
        NodoLg pri = w;
        NodoLg r = w;
        while(!pila.isEmpty())
        {
            z = (NodoLg)pila.pop();
            w.setLiga(z);
            if(w.isSw())
            {
                w.setDato(reversa((NodoLg)w.getDato()));
            }
            w = z;
        }
        w.setLiga(null);
        return pri;
    }
    public NodoLg construir(String s)
    {
        if(j < s.length())
        {
            char aActual = s.charAt(j-1);
            char sActual = s.charAt(j + 1);
            NodoLg x = new NodoLg();
            if(Character.isLetter(s.charAt(j)))
            {System.out.println("awa"+s.charAt(j));
                if(primero == null)
                {System.out.println(s.charAt(j)+"AWAWAWA");
                    primero = x;
                    primero.setDato(s.charAt(j));
                    j = j + 2;
                    primero.setLiga(construir(s));
                    return primero;
                }
                
                if((aActual == ',') && (sActual == '('))
                {
                    x.setSw(true);
                    NodoLg y = new NodoLg(s.charAt(j));
                    j = j + 2;
                    y.setLiga(construir(s));
                    x.setDato(y);
                    j= j +2;
                    x.setLiga(construir(s));
                    return x;
                }
                else
                {
                    if(s.charAt(j+1) == ')')
                    {
                        x.setDato(s.charAt(j));
                        j++;
                        return x;
                    }
                    else
                    {
                    System.out.println("("+s.charAt(j)+")");
                    x.setDato(s.charAt(j));
                    j = j + 1;
                    
                    System.out.println(s.charAt(j));
                    }
                }
            }
            if(j < s.length() && s.charAt(j) == ')')
            {
                j++;
            }
            if(j < s.length() && s.charAt(j) == ',')
            {
                j = j + 1;
                x.setLiga(construir(s));
            }
            else
            {
                j++;
                x.setLiga(construir(s));
            }
            if(x.getDato() == null)
            {
                return null;
            }
            return x;
        }
        return null;
    }
    public String toString(NodoLg x)
    {
        if(x == null)
        {
            return "";
        }
        String toString = "";
        if(x == primero)
        {
            toString= toString.concat("(");
            toString = toString.concat(x.getDato().toString());
            toString = toString.concat("(");
            toString = toString.concat(toString(x.getLiga()));
            toString = toString.concat(")");
            toString = toString.concat(")");
            return toString;
        }
        if(x.isSw())
        {
            toString = toString.concat((((NodoLg)x.getDato()).getDato()).toString());
            toString = toString.concat("(");
            toString = toString.concat(toString(((NodoLg)x.getDato()).getLiga()));
            toString = toString.concat(")");
        }
        else
        {
            toString = toString.concat(x.getDato().toString());
        }
        if(x.getLiga() != null)
        {
            toString = toString.concat(",");
            toString = toString.concat(toString(x.getLiga()));
        }
        return toString;
    }
    public void construirArbol(String s)
    {   
        Stack pila  = new Stack();
        char actual, sActual, aActual;
        NodoLg x = new NodoLg(),z = null;
        primero = x;
        ultimo = x;
        int i = 1;
        while(i < s.length() - 1)
        {
            actual = s.charAt(i);
            if(Character.isLetter(actual))
            {
                String atom = this.nextAtom(i, s);
                sActual = s.charAt(i + atom.length());
                aActual = s.charAt(i - 1);
                ultimo.setDato(atom);
                if(aActual == ',' & sActual == '(')
                {
                    x = new NodoLg();
                    x.setSw(true);
                    z.setLiga(x);
                    x.setDato(ultimo);
                    pila.push(x);
                }
                if((sActual == ',' & aActual == '(') | (sActual == ')' & aActual == '('))
                {
                    if(!pila.isEmpty())
                    {
                        ultimo = (NodoLg)pila.pop();
                        ultimo.setDato(atom);
                        z.setLiga(ultimo);
                    }
                    ultimo.setSw(false);
                }
                i = i + atom.length() - 1;
            }
            if(actual == ',')
            {
                x = new NodoLg();
                z = ultimo;
                ultimo.setLiga(x);
                ultimo = x;
            }
            if(actual == '(')
            {
                x = new NodoLg();
                ultimo.setLiga(x);
                z = ultimo;
                ultimo = x;
                pila.push(ultimo);
                x = new NodoLg();
                ultimo.setSw(true);
                ultimo.setDato(x);
                ultimo = x;
            }
            if(actual == ')')
            {
                if(!pila.isEmpty())
                {
                    ultimo = (NodoLg)pila.pop();
                }
            }
            i = i + 1;
        }
        arbol = this.convertir();
    }
}
