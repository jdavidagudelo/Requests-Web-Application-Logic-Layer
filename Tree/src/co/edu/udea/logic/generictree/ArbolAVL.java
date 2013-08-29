/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.generictree;

import java.util.Stack;
public class ArbolAVL
{
    private NodoAVL raiz = null;
    public ArbolAVL()
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
            {System.out.println("Como");
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
                if(p == q.getLd())
                {
                    q.setFb(q.getFb() + 1);
                    q.setLd(p.getLi());
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
                        q.setLd(p.getLd());
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
    public void borrarAVL(Object d)
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
    public ArbolAVL convertir(ArbolLg x)
    {
        ArbolAVL z = new ArbolAVL();
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
        {System.out.println(x.getDato()+""+x.getFb());
            NodoAVL px = padre(x);
            NodoAVL aux = null;
            if(x.getFb().equals(2))
            {
                if(x.getLi().getFb().equals(1))
                {
                    aux = this.unaRotDer(x, x.getLi());
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
                        aux = unaRotIzq(x, x.getLd());
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
    public NodoAVL padre(NodoAVL x)
    {
        NodoAVL p = raiz;
        int w, z;
        while(p != null && p.getLd() != x && p.getLi() != x)
        {
            if(x.getDato() instanceof Integer & p.getDato() instanceof Integer)
            {
                w = (Integer)x.getDato();
                z = (Integer)p.getDato();
                if(w > z)
                {
                    p = p.getLd();
                }
                else
                {
                    p = p.getLi();
                }
            }
        }
        return p;
    }
    public void insertarAVL(Object d)
    {
        NodoAVL x = new NodoAVL(d);
        if(raiz == null)
        {
            raiz = x;
            return;
        }
        Integer z = 0, w = 0;
        if(d instanceof Integer)
        {
            z = (Integer)d;
        }
        NodoAVL p, q, pivote, pp;
        p = raiz;
        q = null;
        pivote = raiz;
        pp = null;
        while(p != null)
        {
            if(p.getDato() instanceof Integer & d instanceof Integer)
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
        if(q.getDato() instanceof Integer & d instanceof Integer)
        {
            w = (Integer)q.getDato();
            if(z < w)
            {
                q.setLi(x);
            }
            else
            {
                q.setLd(x);
            }
        }
        Integer aux = pivote.getFb();
        if(pivote.getDato() instanceof Integer)
        {
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
        }
        p = q;
        while(p != x & p.getDato() instanceof Integer)
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
        if(pivote == raiz)
        {
            raiz = q;
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
    public void posorden(NodoAVL x)
    {
        if(x != null)
        {
            posorden(x.getLi());
            posorden(x.getLd());
            System.out.print(x.getDato()+",");
        }
    }
    public void preorden(NodoAVL x)
    {
        if(x != null)
        {
            System.out.print(x.getDato()+",");
            preorden(x.getLi());
            preorden(x.getLd());
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
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoAVL unaRotIzq(NodoAVL p, NodoAVL q)
    {
        p.setLd(q.getLi());
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);
        return q;
    }
    public NodoAVL dobleRotDer(NodoAVL p, NodoAVL q)
    {
        NodoAVL r = q.getLd();
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
    public NodoAVL dobleRotIzq(NodoAVL p, NodoAVL q)
    {
        NodoAVL r = q.getLi();
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
    public NodoAVL unaRotDerEs(NodoAVL p, NodoAVL q)
    {
        p.setLi(q.getLd());
        q.setLd(p);
        p.setFb(1);
        q.setFb(-1);
        return q;
    }
    public NodoAVL unaRotIzqEs(NodoAVL p, NodoAVL q)
    {
        p.setLd(q.getLi());
        q.setLi(p);
        p.setFb(-1);
        q.setFb(1);
        return q;
    }
}
