package mcp.mobius.waila.handlers.nei;

import codechicken.nei.SearchField;
import codechicken.nei.api.ItemFilter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.regex.Pattern;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class OreDictFilter implements SearchField.ISearchProvider {

    public ItemFilter getFilter(String searchText)
    {
        if ((!searchText.startsWith("#")) || (searchText.length() < 2)) {
            return null;
        }
        Pattern pattern = SearchField.getPattern(searchText.substring(1));
        return pattern == null ? null : new Filter(pattern);
    }

    public boolean isPrimary()
    {
        return true;
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
            int[] ids = OreDictionary.getOreIDs(itemstack);
            boolean found = false;
            for (int id : ids) {
                if (this.pattern.matcher(OreDictionary.getOreName(id).toLowerCase()).find()) {
                    found = true;
                }
            }
            return found;
        }
    }

}
