package mcp.mobius.waila.api;

import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaSummaryProvider {

    public abstract LinkedHashMap<String, String> getSummary(ItemStack paramItemStack, LinkedHashMap<String, String> paramLinkedHashMap, IWailaConfigHandler paramIWailaConfigHandler);

}
