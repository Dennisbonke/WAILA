package mcp.mobius.waila.api;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaFMPProvider {

    public abstract List<String> getWailaHead(ItemStack paramItemStack, List<String> paramList, IWailaFMPAccessor paramIWailaFMPAccessor, IWailaConfigHandler paramIWailaConfigHandler);

    public abstract List<String> getWailaBody(ItemStack paramItemStack, List<String> paramList, IWailaFMPAccessor paramIWailaFMPAccessor, IWailaConfigHandler paramIWailaConfigHandler);

    public abstract List<String> getWailaTail(ItemStack paramItemStack, List<String> paramList, IWailaFMPAccessor paramIWailaFMPAccessor, IWailaConfigHandler paramIWailaConfigHandler);

}
