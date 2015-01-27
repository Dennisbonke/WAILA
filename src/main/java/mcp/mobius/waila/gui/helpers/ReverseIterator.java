package mcp.mobius.waila.gui.helpers;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class ReverseIterator<T> implements Iterable<T> {

    private ListIterator<T> listIterator;

    public ReverseIterator(Collection<T> wrappedList)
    {
        List list = Lists.newArrayList(wrappedList);
        this.listIterator = list.listIterator(wrappedList.size());
    }

    public Iterator<T> iterator()
    {
        new Iterator()
        {
            public boolean hasNext()
            {
                return ReverseIterator.this.listIterator.hasPrevious();
            }

            public T next()
            {
                return ReverseIterator.this.listIterator.previous();
            }

            public void remove()
            {
                ReverseIterator.this.listIterator.remove();
            }
        };
        return null;
    }

}
