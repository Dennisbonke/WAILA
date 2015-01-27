package mcp.mobius.waila.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.utils.AccessHelper;
import mcp.mobius.waila.utils.NBTUtil;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class Message0x01TERequest extends SimpleChannelInboundHandler<Message0x01TERequest> implements IWailaMessage {

    private static Field classToNameMap = null;
    public int dim;
    public int posX;
    public int posY;
    public int posZ;

    static
    {
        try
        {
            classToNameMap = TileEntity.class.getDeclaredField("classToNameMap");
            classToNameMap.setAccessible(true);
        }
        catch (Exception e)
        {
            try
            {
                classToNameMap = TileEntity.class.getDeclaredField("field_145853_j");
                classToNameMap.setAccessible(true);
            }
            catch (Exception f)
            {
                throw new RuntimeException(f);
            }
        }
    }

    public HashSet<String> keys = new HashSet();

    public Message0x01TERequest(TileEntity ent, HashSet<String> keys)
    {
        this.dim = ent.func_145831_w().field_73011_w.field_76574_g;
        this.posX = ent.field_145851_c;
        this.posY = ent.field_145848_d;
        this.posZ = ent.field_145849_e;
        this.keys = keys;
    }

    public void encodeInto(ChannelHandlerContext ctx, IWailaMessage msg, ByteBuf target)
            throws Exception
    {
        target.writeInt(this.dim);
        target.writeInt(this.posX);
        target.writeInt(this.posY);
        target.writeInt(this.posZ);
        target.writeInt(this.keys.size());
        for (String key : this.keys) {
            WailaPacketHandler.INSTANCE.writeString(target, key);
        }
    }

    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IWailaMessage rawmsg)
    {
        try
        {
            Message0x01TERequest msg = (Message0x01TERequest)rawmsg;
            msg.dim = dat.readInt();
            msg.posX = dat.readInt();
            msg.posY = dat.readInt();
            msg.posZ = dat.readInt();

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

    protected void channelRead0(ChannelHandlerContext ctx, Message0x01TERequest msg)
            throws Exception
    {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        World world = DimensionManager.getWorld(msg.dim);
        TileEntity entity = world.func_147438_o(msg.posX, msg.posY, msg.posZ);
        Block block = world.func_147439_a(msg.posX, msg.posY, msg.posZ);
        if (entity != null) {
            try
            {
                NBTTagCompound tag = new NBTTagCompound();
                boolean hasNBTBlock = ModuleRegistrar.instance().hasNBTProviders(block);
                boolean hasNBTEnt = ModuleRegistrar.instance().hasNBTProviders(entity);
                EntityPlayerMP player;
                if ((hasNBTBlock) || (hasNBTEnt))
                {
                    tag.func_74768_a("x", msg.posX);
                    tag.func_74768_a("y", msg.posY);
                    tag.func_74768_a("z", msg.posZ);
                    tag.func_74778_a("id", (String)((HashMap)classToNameMap.get(null)).get(entity.getClass()));

                    player = ((NetHandlerPlayServer)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get()).field_147369_b;
                    for (IWailaDataProvider provider : ModuleRegistrar.instance().getNBTProviders(block)) {
                        try
                        {
                            tag = provider.getNBTData(player, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        }
                        catch (AbstractMethodError ame)
                        {
                            tag = AccessHelper.getNBTData(provider, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        }
                        catch (NoSuchMethodError nsm)
                        {
                            tag = AccessHelper.getNBTData(provider, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        }
                    }
                    for (IWailaDataProvider provider : ModuleRegistrar.instance().getNBTProviders(entity)) {
                        try
                        {
                            tag = provider.getNBTData(player, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        }
                        catch (AbstractMethodError ame)
                        {
                            tag = AccessHelper.getNBTData(provider, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        }
                        catch (NoSuchMethodError nsm)
                        {
                            tag = AccessHelper.getNBTData(provider, entity, tag, world, msg.posX, msg.posY, msg.posZ);
                        }
                    }
                }
                else
                {
                    entity.func_145841_b(tag);
                    tag = NBTUtil.createTag(tag, msg.keys);
                }
                tag.func_74768_a("WailaX", msg.posX);
                tag.func_74768_a("WailaY", msg.posY);
                tag.func_74768_a("WailaZ", msg.posZ);
                tag.func_74778_a("WailaID", (String)((HashMap)classToNameMap.get(null)).get(entity.getClass()));

                ctx.writeAndFlush(new Message0x02TENBTData(tag)).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            }
            catch (Throwable e)
            {
                WailaExceptionHandler.handleErr(e, entity.getClass().toString(), null);
            }
        }
    }

    public Message0x01TERequest() {}

}
