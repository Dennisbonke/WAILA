package mcp.mobius.waila.api.impl;

import mcp.mobius.waila.api.ITaggedList;

import java.util.*;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class TipList<E, T> extends ArrayList<E> implements ITaggedList<E, T> {

    Map<E, Set<T>> tags = new HashMap();

    public boolean add(E e, T tag)
    {
        if (!this.tags.containsKey(e)) {
            this.tags.put(e, new HashSet());
        }
        ((Set)this.tags.get(e)).add(tag);
        return super.add(e);
    }

    public boolean add(E e, Collection<? extends T> taglst)
    {
        if (!this.tags.containsKey(e)) {
            this.tags.put(e, new HashSet());
        }
        ((Set)this.tags.get(e)).addAll(taglst);

        return super.add(e);
    }

    public Set<T> getTags(E e)
    {
        Set<T> ret = (Set)this.tags.get(e);
        if ((ret == null) && (contains(e)))
        {
            this.tags.put(e, new HashSet());
            ret = (Set)this.tags.get(e);
        }
        return ret;
    }

    public Set<T> getTags(int index)
    {
        return getTags(get(index));
    }

    public void addTag(E e, T tag)
    {
        if ((contains(e)) && (!this.tags.containsKey(e))) {
            this.tags.put(e, new HashSet());
        }
        ((Set)this.tags.get(e)).add(tag);
    }

    public void addTag(int index, T tag)
    {
        addTag(get(index), tag);
    }

    public void removeTag(E e, T tag)
    {
        if ((contains(e)) && (!this.tags.containsKey(e))) {
            this.tags.put(e, new HashSet());
        }
        ((Set)this.tags.get(e)).remove(tag);
    }

    public void removeTag(int index, T tag)
    {
        removeTag(get(index), tag);
    }

    public Set<E> getEntries(T tag)
    {
        Set<E> ret = new HashSet();
        for (Map.Entry<E, Set<T>> s : this.tags.entrySet()) {
            if (((Set)s.getValue()).contains(tag)) {
                ret.add(s.getKey());
            }
        }
        return ret;
    }

    public void removeEntries(T tag)
    {
        for (E e : getEntries(tag)) {
            remove(e);
        }
    }

    public String getTagsAsString(E e)
    {
        String ret = "";
        for (T s : (Set)this.tags.get(e)) {
            ret = ret + s.toString() + ",";
        }
        if (ret.length() > 0) {
            ret = ret.substring(0, ret.length() - 1);
        }
        return ret;
    }

    public void clear()
    {
        this.tags.clear();
        super.clear();
    }

    public E set(int index, E element)
    {
        this.tags.remove(get(index));
        return super.set(index, element);
    }

    public E remove(int index)
    {
        this.tags.remove(get(index));
        return super.remove(index);
    }

    public boolean remove(Object o)
    {
        this.tags.remove(o);
        return super.remove(o);
    }

    public boolean removeAll(Collection<?> c)
    {
        for (Object o : c) {
            this.tags.remove(o);
        }
        return super.removeAll(c);
    }

    protected void removeRange(int fromIndex, int toIndex)
    {
        for (int i = fromIndex; i < toIndex; i++) {
            this.tags.remove(get(i));
        }
        super.removeRange(fromIndex, toIndex);
    }

    public boolean retainAll(Collection<?> c)
    {
        for (E e : this.tags.keySet()) {
            if (!c.contains(e)) {
                this.tags.remove(e);
            }
        }
        return super.retainAll(c);
    }

}
