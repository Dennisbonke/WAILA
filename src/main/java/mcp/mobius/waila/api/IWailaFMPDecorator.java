package mcp.mobius.waila.api;

import net.minecraft.item.ItemStack;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaFMPDecorator {

    public abstract void decorateBlock(ItemStack paramItemStack, IWailaFMPAccessor paramIWailaFMPAccessor, IWailaConfigHandler paramIWailaConfigHandler);

}
