package mcp.mobius.waila.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.utils.AccessHelper;
import mcp.mobius.waila.utils.NBTUtil;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.HashSet;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class Message0x03EntRequest extends SimpleChannelInboundHandler<Message0x03EntRequest> implements IWailaMessage {

    public int dim;
    public int id;
    public HashSet<String> keys = new HashSet();

    public Message0x03EntRequest() {}

    public Message0x03EntRequest(Entity ent, HashSet<String> keys)
    {
        this.dim = ent.field_70170_p.field_73011_w.field_76574_g;
        this.id = ent.func_145782_y();
        this.keys = keys;
    }

    public void encodeInto(ChannelHandlerContext ctx, IWailaMessage msg, ByteBuf target)
            throws Exception
    {
        target.writeInt(this.dim);
        target.writeInt(this.id);
        target.writeInt(this.keys.size());
        for (String key : this.keys) {
            WailaPacketHandler.INSTANCE.writeString(target, key);
        }
    }

    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IWailaMessage rawmsg)
    {
        try
        {
            Message0x03EntRequest msg = (Message0x03EntRequest)rawmsg;
            msg.dim = dat.readInt();
            msg.id = dat.readInt();


            int nkeys = dat.readInt();
            for (int i = 0; i < nkeys; i++) {
                this.keys.add(WailaPacketHandler.INSTANCE.readString(dat));
            }
        }
        catch (Exception e)
        {
            WailaExceptionHandler.handleErr(e, getClass().toString(), null);
        }
    }

    protected void channelRead0(ChannelHandlerContext ctx, Message0x03EntRequest msg)
            throws Exception
    {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        World world = DimensionManager.getWorld(msg.dim);
        Entity entity = world.func_73045_a(msg.id);
        if (entity != null) {
            try
            {
                NBTTagCompound tag = new NBTTagCompound();

                EntityPlayerMP player = ((NetHandlerPlayServer)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get()).field_147369_b;
                if (ModuleRegistrar.instance().hasNBTEntityProviders(entity))
                {
                    for (IWailaEntityProvider provider : ModuleRegistrar.instance().getNBTEntityProviders(entity)) {
                        try
                        {
                            tag = provider.getNBTData(player, entity, tag, world);
                        }
                        catch (AbstractMethodError ame)
                        {
                            tag = AccessHelper.getNBTData(provider, entity, tag);
                        }
                    }
                }
                else
                {
                    entity.func_70109_d(tag);
                    tag = NBTUtil.createTag(tag, msg.keys);
                }
                tag.func_74768_a("WailaEntityID", entity.func_145782_y());

                ctx.writeAndFlush(new Message0x04EntNBTData(tag)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            }
            catch (Throwable e)
            {
                WailaExceptionHandler.handleErr(e, entity.getClass().toString(), null);
            }
        }
    }

}
