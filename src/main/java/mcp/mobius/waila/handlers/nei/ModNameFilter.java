package mcp.mobius.waila.handlers.nei;

import codechicken.nei.SearchField;
import codechicken.nei.api.ItemFilter;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.item.ItemStack;

import java.util.regex.Pattern;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class ModNameFilter implements SearchField.ISearchProvider {

    public ItemFilter getFilter(String searchText)
    {
        Pattern pattern = SearchField.getPattern(searchText);
        return pattern == null ? null : new Filter(pattern);
    }

    public boolean isPrimary()
    {
        return false;
    }

    public static class Filter
            implements ItemFilter
    {
        Pattern pattern;

        public Filter(Pattern pattern)
        {
            this.pattern = pattern;
        }

        public boolean matches(ItemStack itemstack)
        {
            return this.pattern.matcher(ModIdentification.nameFromStack(itemstack).toLowerCase()).find();
        }
    }

}
