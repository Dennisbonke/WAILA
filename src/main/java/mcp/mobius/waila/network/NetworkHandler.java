package mcp.mobius.waila.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import mcp.mobius.waila.Waila;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class NetworkHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        Waila.log.info(String.format("Player %s connected. Sending ping", new Object[] { event.player }));
        WailaPacketHandler.INSTANCE.sendTo(new Message0x00ServerPing(), (EntityPlayerMP)event.player);
    }

}
