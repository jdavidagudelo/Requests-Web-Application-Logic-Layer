/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udea.logic.generictree;
import java.util.*;
public class ArbolAVL1
{
    private NodoAVL raiz = null;
    public ArbolAVL1()
    {
    }
    public void setRaiz(NodoAVL raiz)
    {
        this.raiz = raiz;
    }
    public NodoAVL getRaiz()
    {
        return raiz;
    }
    public void rebalancearArbol(NodoAVL q)
    {
        NodoAVL pq = null;
        if(q.getFb().equals(0))
        {
            pq = padre(q);
            if(pq == null)
            {
                return;
            }
            if(pq.getFb().equals(0))
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
                if(q == pq.getLd())
                {
                    pq.setFb(pq.getFb() + 1);
                }
                else
                {
                    pq.setFb(pq.getFb() - 1);
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
    public int getFB(NodoAVL x)
    {
        return altura(x.getLi()) - altura(x.getLd());
    }
    public void eliminarNodo(NodoAVL p, NodoAVL q)
    {
        if(p.getLd() == null & p.getLi() == null)
        {
            if(q == null)
            {
                raiz = null;
                return;
            }
            if(p == q.getLd())
            {
                q.setFb(q.getFb() + 1);
                q.setLd(null);
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
                    q.setLi(null);
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
                if(q == null)
                {
                    raiz = p.getLi();
                    return;
                }
                if(p == q.getLd())
                {
                    q.setFb(q.getFb() + 1);
                    q.setLd(p.getLi());
                    p.getLi().setLp(q);
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
                        q.setLi(p.getLi());
                        p.getLi().setLp(q);
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
                    if(q == null)
                    {
                        raiz = p.getLd();
                        return;
                    }
                    if(p == q.getLd())
                    {
                        q.setFb(q.getFb() + 1);
                        q.setLd(p.getLd());
                        p.getLd().setLp(q);
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
                            q.setLi(p.getLd());
                            p.getLd().setLp(q);
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
                    menor(p);
                }
            }
        }
    }
    public void borrar(Object d)
    {
        if(raiz == null)
        {
            return;
        }
        NodoAVL p = raiz;
        NodoAVL q = null;
        while(p!= q && p != null && !p.getDato().equals(d))
        {
            q = p;
            if(d instanceof Integer & p.getDato() instanceof Integer)
            {
                int w = (Integer)d;
                int z = (Integer)p.getDato();
                if(w < z)
                {
                    p = p.getLi();
                }
                else
                {
                    p = p.getLd();
                }
            }
        }
        if(p != null)
        {
            eliminarNodo(p, q);
        }
    }
    public void menor(NodoAVL x)
    {
        NodoAVL p = x.getLd();
        while(p.getLi() != null)
        {
            p = p.getLi();
        }
        NodoAVL pp = padre(p);
        x.setDato(p.getDato());
        if(p.getLd() != null)
        {
            p.setDato(p.getLd().getDato());
            this.eliminarNodo(p.getLd(), p);
        }
        else
        {
            this.eliminarNodo(p, pp);
        }
    }
    public void rotarMenosAltura(NodoAVL z)
    {
        NodoAVL x = z;
        while(x != null)
        {
            NodoAVL px = padre(x);
            NodoAVL aux = null;
            if(x.getFb().equals(2))
            {
                if(x.getLi().getFb().equals(1))
                {
                    aux = this.unaRotDer(x, x.getLi());
                    aux.setLp(px);
                    if(px != null)
                    {
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
                        raiz = aux;
                    }
                }
                else
                {
                    if(x.getLi().getFb().equals(-1))
                    {
                        aux = this.dobleRotDer(x, x.getLi());
                        aux.setLp(px);
                        if(px != null)
                        {
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
                            raiz = aux;
                        }
                    }
                    else
                    {
                        if(x.getLi().getFb().equals(0))
                        {
                            aux = this.unaRotDerEs(x, x.getLi());
                            aux.setLp(px);
                            if(px != null)
                            {
                                if(x == px.getLd())
                                {
                                    px.setLd(aux);
                                }
                                else
                                {
                                    px.setLi(aux);
                                }
                            }
                            else
                            {
                                raiz = aux;
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
                        aux = this.unaRotIzq(x, x.getLd());
                        aux.setLp(px);
                        if(px != null)
                        {
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
                            raiz = aux;
                        }
                    }
                    else
                    {
                        if(x.getLd().getFb().equals(1))
                        {
                            aux = this.dobleRotIzq(x, x.getLd());
                            aux.setLp(px);
                            if(px != null)
                            {
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
                                raiz = aux;
                            }
                        }
                        else
                        {
                            if(x.getLd().getFb().equals(0))
                            {
                                aux = this.unaRotIzqEs(x, x.getLd());
                                aux.setLp(px);
                                if(px != null)
                                {
                                    if(x == px.getLd())
                                    {
                                        px.setLd(aux);
                                    }
                                    else
                                    {
                                        px.setLi(aux);
                                    }
                                }
                                else
                                {
                                    raiz = aux;
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
    public ArbolAVL1 convertir(ArbolLg x)
    {
        ArbolAVL1 z = new ArbolAVL1();
        NodoAVL q = null, r = null;
        Stack pila = new Stack(), pila1 = new Stack();
        NodoLg p = x.getPrimero(), aux = null;
        q = new NodoAVL(p.getDato());
        z.setRaiz(q);
        p = p.getLiga();
        if(p != null)
        {
            if(p.isSw())
            {
                r = new NodoAVL(((NodoLg)p.getDato()).getDato());
                q.setLi(r);
                q = r;
            }
            else
            {
                r = new NodoAVL(p.getDato());
                q.setLi(r);
                q = r;
            }
        }
        while(p != null)
        {
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
                                r = new NodoAVL(((NodoLg)aux.getDato()).getDato());
                                pila1.push(r);
                                q.setLd(r);
                            }
                            else
                            {
                                r = new NodoAVL(aux.getDato());
                                pila1.push(r);
                                q.setLd(r);
                            }
                        }
                        r = new NodoAVL(((NodoLg)p.getDato()).getDato());
                        q.setLi(r);
                        q = r;
                    }
                    else
                    {
                        if(aux != null)
                        {
                            if(aux.isSw())
                            {
                                r = new NodoAVL(((NodoLg)aux.getDato()).getDato());
                                pila1.push(r);
                                q.setLd(r);
                            }
                            else
                            {
                                r = new NodoAVL(aux.getDato());
                                pila1.push(r);
                                q.setLd(r);
                            }
                        }
                        r = new NodoAVL(p.getDato());
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
                        r = new NodoAVL(((NodoLg)p.getDato()).getDato());
                        q.setLd(r);
                        q = r;
                    }
                    else
                    {
                        r = new NodoAVL(p.getDato());
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
                    q = (NodoAVL)pila1.pop();
                }
            }
        }
        return z;
    }
    public NodoAVL unaRotDerEs(NodoAVL p, NodoAVL q)
    {
        p.setLi(q.getLd());
        if(q.getLd() != null)
        {
            q.getLd().setLp(p);
        }
        p.setLp(q);
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    public NodoAVL unaRotIzqEs(NodoAVL p, NodoAVL q)
    {
        p.setLd(q.getLi());
        if(q.getLi() != null)
        {
            q.getLi().setLp(p);
        }
        p.setLp(q);
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
    public NodoAVL padre(NodoAVL x)
    {
        return x.getLp();
    }
    public void insertar(Object d)
    {
        NodoAVL x = new NodoAVL(d);
        if(raiz == null)
        {
            raiz = x;
            return;
        }
        Integer z, w;
        z = (Integer)d;
        NodoAVL p, q, pivote, pp;
        p = raiz;
        q = null;
        pivote = raiz;
        pp = null;
        while(p != null)
        {
            if(p.getDato().equals(d))
            {
                return;
            }
            if(!p.getFb().equals(0))
            {
                pivote = p;
                pp = q;
            }
            q = p;
            {
                w = (Integer)p.getDato();
                if(z < w)
                {
                    p = p.getLi();
                }
                else
                {
                    p = p.getLd();
                }
            }
        }
        w = (Integer)q.getDato();
        if(z < w)
        {
            q.setLi(x);
            x.setLp(q);
        }
        else
        {
            q.setLd(x);
            x.setLp(p);
        }
        Integer aux = pivote.getFb();
        w = (Integer)pivote.getDato();
        if(z < w)
        {
            pivote.setFb(aux + 1);
            q = pivote.getLi();
        }
        else
        {
            pivote.setFb(aux - 1);
            q = pivote.getLd();
        }
        p = q;
        while(p != x)
        {
            w = (Integer)p.getDato();
            if(z < w)
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
        if(Math.abs((Integer)pivote.getFb()) < 2)
        {
            return;
        }
        if(pivote.getFb().equals(2))
        {
            if(q.getFb().equals(1))
            {
                this.unaRotDer(pivote, q);
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
                this.unaRotIzq(pivote, q);
            }
            else
            {
                q = this.dobleRotIzq(pivote, q);
            }
        }
        if(pivote == raiz)
        {
            raiz = q;
            q.setLp(null);
            return;
        }
        if(pp.getLi() == pivote)
        {
            q.setLp(pp);
            pp.setLi(q);
        }
        else
        {
            q.setLp(pp);
            pp.setLd(q);
        }
    }
    public void posorden(NodoAVL x)
    {
        if(x != null)
        {
            posorden(x.getLi());
            posorden(x.getLd());
            System.out.print(x.getDato()+",");
        }
    }
    public void inorden(NodoAVL x)
    {
        if(x != null)
        {
            inorden(x.getLi());
            System.out.print(x.getDato()+",");
            inorden(x.getLd());
        }
    }
    public NodoAVL unaRotDer(NodoAVL p, NodoAVL q)
    {
        p.setLi(q.getLd());
        if(q.getLd() != null)
        {
            q.getLd().setLp(p);
        }
        p.setLp(q);
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoAVL unaRotIzq(NodoAVL p, NodoAVL q)
    {
        p.setLd(q.getLi());
        if(q.getLi() != null)
        {
            q.getLi().setLp(p);
        }
        p.setLp(q);
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoAVL dobleRotDer(NodoAVL p, NodoAVL q)
    {
        NodoAVL r = q.getLd();
        q.setLd(r.getLi());
        if(r.getLi() != null)
        {
            r.getLi().setLp(q);
        }
        q.setLp(r);
        r.setLi(q);
        p.setLi(r.getLd());
        if(r.getLd() != null)
        {
            r.getLd().setLp(p);
        }
        p.setLp(r);
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
    public NodoAVL dobleRotIzq(NodoAVL p, NodoAVL q)
    {
        NodoAVL r = q.getLi();
        q.setLi(r.getLd());
        if(r.getLd() != null)
        {
            r.getLd().setLp(q);
        }
        q.setLp(r);
        r.setLd(q);
        p.setLd(r.getLi());
        if(r.getLi() != null)
        {
            r.getLi().setLp(p);
        }
        p.setLp(r);
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
    public int altura(NodoAVL x)
    {
        NodoAVL y = x;
        if(y == null)
        {
            return 0;
        }
        int h = 1;
        int hMax = 1;
        h = 1 + altura(y.getLd());
        if(h > hMax)
        {
            hMax = h;
        }
        h = 1 + altura(y.getLi());
        if(h > hMax)
        {
            hMax = h;
        }
        return hMax;
    }
}
