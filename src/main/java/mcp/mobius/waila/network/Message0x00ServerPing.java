package mcp.mobius.waila.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraftforge.common.config.ConfigCategory;

import java.util.HashMap;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class Message0x00ServerPing extends SimpleChannelInboundHandler<Message0x00ServerPing> implements IWailaMessage {

    HashMap<String, Boolean> forcedKeys = new HashMap();

    public Message0x00ServerPing()
    {
        ConfigCategory serverForcingCfg = ConfigHandler.instance().config.getCategory(Constants.CATEGORY_SERVER);
        for (String key : serverForcingCfg.keySet()) {
            if (serverForcingCfg.get(key).getBoolean(false)) {
                this.forcedKeys.put(key, Boolean.valueOf(ConfigHandler.instance().getConfig(key)));
            }
        }
    }

    public void encodeInto(ChannelHandlerContext ctx, IWailaMessage msg, ByteBuf target)
            throws Exception
    {
        target.writeShort(this.forcedKeys.keySet().size());
        for (String key : this.forcedKeys.keySet())
        {
            WailaPacketHandler.INSTANCE.writeString(target, key);
            target.writeBoolean(((Boolean)this.forcedKeys.get(key)).booleanValue());
        }
    }

    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IWailaMessage rawmsg)
    {
        try
        {
            Message0x00ServerPing msg = (Message0x00ServerPing)rawmsg;
            int nkeys = dat.readShort();
            for (int i = 0; i < nkeys; i++) {
                this.forcedKeys.put(WailaPacketHandler.INSTANCE.readString(dat), Boolean.valueOf(dat.readBoolean()));
            }
        }
        catch (Exception e)
        {
            WailaExceptionHandler.handleErr(e, getClass().toString(), null);
        }
    }

    protected void channelRead0(ChannelHandlerContext ctx, Message0x00ServerPing msg)
            throws Exception
    {
        Waila.log.info("Received server authentication msg. Remote sync will be activated");
        Waila.instance.serverPresent = true;
        for (String key : msg.forcedKeys.keySet()) {
            Waila.log.info(String.format("Received forced key config %s : %s", new Object[] { key, msg.forcedKeys.get(key) }));
        }
        ConfigHandler.instance().forcedConfigs = msg.forcedKeys;
    }

}
