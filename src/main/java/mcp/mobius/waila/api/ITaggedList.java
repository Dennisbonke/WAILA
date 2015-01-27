package mcp.mobius.waila.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface ITaggedList<E, T> extends List<E> {

    public abstract boolean add(E paramE, T paramT);

    public abstract boolean add(E paramE, Collection<? extends T> paramCollection);

    public abstract Set<T> getTags(E paramE);

    public abstract Set<T> getTags(int paramInt);

    public abstract void addTag(E paramE, T paramT);

    public abstract void addTag(int paramInt, T paramT);

    public abstract void removeTag(E paramE, T paramT);

    public abstract void removeTag(int paramInt, T paramT);

    public abstract Set<E> getEntries(T paramT);

    public abstract void removeEntries(T paramT);

    public abstract String getTagsAsString(E paramE);

}
