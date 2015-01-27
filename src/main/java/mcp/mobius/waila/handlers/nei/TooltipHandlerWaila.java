package mcp.mobius.waila.handlers.nei;

import codechicken.nei.guihook.IContainerTooltipHandler;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class TooltipHandlerWaila implements IContainerTooltipHandler {

    public List<String> handleItemDisplayName(GuiContainer arg0, ItemStack itemstack, List<String> currenttip)
    {
        return currenttip;
    }

    public List<String> handleItemTooltip(GuiContainer arg0, ItemStack itemstack, int arg2, int arg3, List<String> currenttip)
    {
        String canonicalName = ModIdentification.nameFromStack(itemstack);
        if ((canonicalName != null) && (!canonicalName.equals(""))) {
            currenttip.add("§9§o" + canonicalName);
        }
        return currenttip;
    }

    public List<String> handleTooltip(GuiContainer arg0, int arg1, int arg2, List<String> currenttip)
    {
        return currenttip;
    }

}
