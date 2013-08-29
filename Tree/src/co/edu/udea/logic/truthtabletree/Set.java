/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.udea.logic.truthtabletree;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
public class Set implements Joint, List
{
    private NodoD head = null;
    private int length = 0;
    public Set()
    {
        head = new NodoD(null);
        head.setLd(head);
        head.setLi(head);
    }
    public boolean isEmpty()
    {
        return head.getLd() == head;
    }
    public NodoD getFirst()
    {
        return head.getLd();
    }
    public void setHead(NodoD head)
    {
        this.head = head;
    }
    public NodoD getHead()
    {
        return head;
    }
    public Set intersection(Joint b)
    {
        Set nuevo = new Set();
        if(b instanceof Set)
        {
            NodoD x = this.getFirst();
            while(x != this.getHead())
            {
                if(b.contains(x.getValue()))
                {
                    nuevo.add(x.getValue());
                }
                x = x.getLd();
            }
        }
        return nuevo;
    }
    public Set union(Joint b)
    {
        Set nuevo = new Set();
        if(b instanceof Set)
        {
            Set cb = (Set)b;
            NodoD x = this.getFirst();
            while(x != this.getHead())
            {
                nuevo.add(x.getValue());
                x = x.getLd();
            }
            x = cb.getFirst();
            while(x != cb.getHead())
            {
                nuevo.add(x.getValue());
                x = x.getLd();
            }
        }
        return nuevo;
    }
    public Set complement(Joint b)
    {
        Set nuevo = new Set();
        if(b instanceof Set)
        {
            Set cb = (Set)b;
            if(this.isSubset(b))
            {
                NodoD x = this.getFirst();
                while(x != this.getHead())
                {
                    if(!cb.contains(x.getValue()))
                    {
                        nuevo.add(x.getValue());
                    }
                    x = x.getLd();
                }
            }
        }
        return nuevo;
    }
    public boolean isSubset(Joint b)
    {
        if(b instanceof Set)
        {
            Set cb = (Set)b;
            NodoD x = cb.getFirst();
            while(x != cb.getHead())
            {
                if(!this.contains(x.getValue()))
                {
                    return false;
                }
                x = x.getLd();
            }
            return true;
        }
        return false;
    }
    @Override
    public String toString()
    {
        StringBuilder toString = new StringBuilder("");
        toString.append("{");
        Iterator i = this.iterator();
        while(i.hasNext())
        {
            Object obj = i.next().toString();
            toString.append(obj);
            if(i.hasNext())
            {
                toString.append(",");
            }
        }
        toString.append("}");
        return toString.toString();
    }

    public boolean addAll(int index, Collection c)
    {
        if(index < 0 || index > length - 1)
        {
            return false;
        }
        boolean changed = false;
        NodoD x = this.getFirst();
        int j = 0;
        while(x != this.getHead() && j <= index)
        {
            x = x.getLd();
            j = j + 1;
        }
        Iterator i = c.iterator();
        while(i.hasNext())
        {
            Object obj = i.next();
            if(!this.contains(obj))
            {
                NodoD n = new NodoD(obj);
                x.getLi().setLd(n);
                n.setLi(x.getLi());
                n.setLd(x);
                x.setLi(n);
                length++;
                changed = true;
            }
        }
        return changed;
    }

    public Object get(int index)
    {
        if(index < 0 || index > length - 1)
        {
            return null;
        }
        Iterator i = this.iterator();
        int j = 0;
        Object get = null;
        while(i.hasNext() && j <= index)
        {
            get = i.next();
            j = j + 1;
        }
        return get;
    }

    public Object set(int index, Object element)
    {
        if(index < 0 || index > length - 1)
        {
            return null;
        }
        NodoD x = this.getFirst();
        int j = 0;
        while(x != this.getHead() && j <= index)
        {
            if(j == index)
            {
                Object obj = x.getValue();
                x.setValue(element);
                return obj;
            }
            j = j + 1;
            x = x.getLd();
        }
        return null;
    }

    public void add(int index, Object element)
    {
        if(index < 0 || index > length - 1)
        {
            return;
        }
        NodoD x = this.getFirst();
        int j = 0;
        while(x != this.getHead() && j <= index)
        {
            if(j == index)
            {
                if(!this.contains(element))
                {
                    NodoD n = new NodoD(element);
                    x.getLi().setLd(n);
                    n.setLi(x.getLi());
                    n.setLd(x);
                    x.setLi(n);
                    length++;
                }
                return;
            }
            j = j + 1;
            x = x.getLd();
        }
    }

    public Object remove(int index)
    {
        if(index < 0 || index > length - 1)
        {
            return null;
        }
        NodoD x = this.getFirst();
        int j = 0;
        while(x != this.getHead() && j <= index)
        {
            if(j == index)
            {
                Object obj = x.getValue();
                x.getLi().setLd(x.getLd());
                x.getLd().setLi(x.getLi());
                length--;
                return obj;
            }
            x = x.getLd();
        }
        return null;
    }

    public int indexOf(Object o)
    {
        Iterator i = this.iterator();
        int j = 0;
        while(i.hasNext())
        {
            Object obj = i.next();
            if(obj.equals(o))
            {
                return j;
            }
            j = j + 1;
        }
        return -1;
    }

    public int lastIndexOf(Object o)
    {
        return this.indexOf(o);
    }

    public ListIterator listIterator()
    {
        return (ListIterator)this.iterator();
    }

    public ListIterator listIterator(int index)
    {
        return new IteradorConjunto(index);
    }

    public List subList(int fromIndex, int toIndex)
    {
        Iterator i = this.listIterator(fromIndex);
        List l = new Set();
        int j = fromIndex;
        while(i.hasNext() && j < toIndex)
        {
            Object obj = i.next();
            l.add(obj);
            j = j + 1;
        }
        return l;
    }
    public class NodoD<V>
    {
        private NodoD li, ld;
        private V value = null;
        public NodoD(NodoD n)
        {
            this();
            if(n != head)
            {
                this.value = (V)n.value;
                if(n.ld != head)
                {
                    
                }
            }
        }

        public NodoD()
        {
        }
        public NodoD getLd() {
                return ld;
            }
        public NodoD getLi() {
                return li;
            }
        public V getValue() {
                return value;
            }
        public void setLd(NodoD ld) {
                this.ld = ld;
            }
        public void setLi(NodoD li) {
                this.li = li;
            }
        public void setValue(V value) {
                this.value = value;
            }
        public NodoD(Object value)
        {
                this.value = (V)value;
            }
        }
    private Set local = this;
    public class IteradorConjunto implements ListIterator
    {
        private NodoD current = head;
        private int currentIndex = -1;
        public IteradorConjunto()
        {}
        public IteradorConjunto(int index)
        {
            current = head;
            currentIndex = 0;
            if(index < 0 || index > length - 1)
            {
                return;
            }
            while(currentIndex < index)
            {
                current = current.getLd();
                currentIndex = currentIndex + 1;
            }
        }
        public boolean hasNext()
        {
            return current.getLd() != head;
        }
        public Object next()
        {
            Object next = null;
            if(this.hasNext())
            {
                currentIndex = currentIndex + 1;
                current = current.getLd();
                next = current.getValue();
            }
            return next;
        }
        public void remove()
        {
            local.remove(current.getValue());
        }
        public boolean hasPrevious() {
            return current.getLi() != head;
        }

        public Object previous()
        {
            Object previous = null;
            if(this.hasPrevious())
            {
                if(currentIndex == 0 | currentIndex == -1)
                {
                    currentIndex = length - 1;
                }
                else
                {
                    currentIndex = currentIndex - 1;
                }
                current = current.getLi();
                previous = current.getValue();
            }
            return previous;
        }

        public int nextIndex() {
            return (currentIndex + 1)%length;
        }

        public int previousIndex() {
            int index = -1;
            if(currentIndex == 0 | currentIndex == -1)
            {
                index = length - 1;
            }
            else
            {
                index = currentIndex - 1;
            }
            return index;
        }
        public void set(Object e)
        {
            if(current != null && current != head)
            {
                current.setValue(e);
            }
        }
        public void add(Object e) 
        {
            local.add(e);
        }
    }
    public int size()
    {
        return length;
    }
    public boolean contains(Object o)
    {
        Iterator it = this.iterator();
        while(it.hasNext())
        {
            Object obj = it.next();
            if(obj != null && obj.equals(o))
            {
                return true;
            }
        }
        return false;
    }
    public Iterator iterator()
    {
        return new IteradorConjunto();
    }
    public Object[] toArray()
    {
        Object toArray[] = new Object[length];
        ListIterator i = (ListIterator)this.iterator();
        while(i.hasNext())
        {
            toArray[i.nextIndex()] = i.next();
        }
        return toArray;
    }
    public Object[] toArray(Object[] a)
    {
        if(a.length == this.length)
        {
            ListIterator i = (ListIterator)this.iterator();
            while(i.hasNext())
            {
                a[i.nextIndex()] = i.next();
            }
            return a;
        }
        return this.toArray();
    }
    public boolean add(Object e)
    {
        if(this.contains(e))
        {
           return false;
        }
        NodoD x = new NodoD(e);
        head.getLi().setLd(x);
        x.setLi(head.getLi());
        head.setLi(x);
        x.setLd(head);
        length++;
        return true;
    }
    public boolean remove(Object o)
    {
        NodoD x = this.getFirst();
        while(x != this.getHead())
        {
            if(x.getValue() != null && x.getValue().equals(o))
            {
                x.getLi().setLd(x.getLd());
                x.getLd().setLi(x.getLi());
                length--;
                return true;
            }
            x = x.getLd();
        }
        return false;
    }
    public boolean containsAll(Collection c)
    {
        Iterator i = c.iterator();
        while(i.hasNext())
        {
            Object obj = i.next();
            if(!this.contains(obj))
            {
                return false;
            }
        }
        return true;
    }
    public boolean addAll(Collection c)
    {
        Iterator i = c.iterator();
        boolean changed = false;
        while(i.hasNext())
        {
            Object obj = i.next();
            if(this.add(obj))
            {
                changed = true;
            }
        }
        return changed;
    }
    public boolean retainAll(Collection c)
    {
        Iterator i = this.iterator();
        boolean changed = false;
        while(i.hasNext())
        {
            Object obj = i.next();
            if(!c.contains(obj))
            {
                this.remove(obj);
                changed = true;
            }
        }
        return changed;
    }

    public boolean removeAll(Collection c)
    {
        Iterator i = c.iterator();
        boolean changed = false;
        while(i.hasNext())
        {
            Object obj = i.next();
            if(this.remove(obj))
            {
                changed = true;
            }
        }
        return changed;
    }

    public void clear() 
    {
        this.head.setLd(head);
        this.head.setLi(head);
        this.length = 0;
    }

}
