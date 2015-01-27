package mcp.mobius.waila.api;

import net.minecraft.item.ItemStack;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaBlockDecorator {

    public abstract void decorateBlock(ItemStack paramItemStack, IWailaDataAccessor paramIWailaDataAccessor, IWailaConfigHandler paramIWailaConfigHandler);

}
