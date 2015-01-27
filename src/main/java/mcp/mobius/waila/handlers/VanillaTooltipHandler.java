package mcp.mobius.waila.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class VanillaTooltipHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tooltipEvent(ItemTooltipEvent event)
    {
        String canonicalName = ModIdentification.nameFromStack(event.itemStack);
        if ((canonicalName != null) && (!canonicalName.equals(""))) {
            event.toolTip.add("§9§o" + canonicalName);
        }
    }

}
