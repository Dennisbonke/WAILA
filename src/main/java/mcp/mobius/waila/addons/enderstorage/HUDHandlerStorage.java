package mcp.mobius.waila.addons.enderstorage;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Dennisbonke on 28-1-2015.
 */
public class HUDHandlerStorage implements IWailaDataProvider {

    private static String[] colors = {
            LangUtil.translateG("hud.msg.white", new Object[0]),
            LangUtil.translateG("hud.msg.orange", new Object[0]),
            LangUtil.translateG("hud.msg.magenta", new Object[0]),
            LangUtil.translateG("hud.msg.lblue", new Object[0]),
            LangUtil.translateG("hud.msg.yellow", new Object[0]),
            LangUtil.translateG("hud.msg.lime", new Object[0]),
            LangUtil.translateG("hud.msg.pink", new Object[0]),
            LangUtil.translateG("hud.msg.gray", new Object[0]),
            LangUtil.translateG("hud.msg.lgray", new Object[0]),
            LangUtil.translateG("hud.msg.cyan", new Object[0]),
            LangUtil.translateG("hud.msg.purple", new Object[0]),
            LangUtil.translateG("hud.msg.blue", new Object[0]),
            LangUtil.translateG("hud.msg.brown", new Object[0]),
            LangUtil.translateG("hud.msg.green", new Object[0]),
            LangUtil.translateG("hud.msg.red", new Object[0]),
            LangUtil.translateG("hud.msg.black", new Object[0]) };

    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        if (config.getConfig("enderstorage.colors")) {
            try
            {
                int freq = EnderStorageModule.TileFrequencyOwner_Freq.getInt(accessor.getTileEntity());
                int freqLeft = ((Integer)EnderStorageModule.GetColourFromFreq.invoke(null, new Object[] { Integer.valueOf(freq), Integer.valueOf(0) })).intValue();
                int freqCenter = ((Integer)EnderStorageModule.GetColourFromFreq.invoke(null, new Object[] { Integer.valueOf(freq), Integer.valueOf(1) })).intValue();
                int freqRight = ((Integer)EnderStorageModule.GetColourFromFreq.invoke(null, new Object[] { Integer.valueOf(freq), Integer.valueOf(2) })).intValue();
                if (!EnderStorageModule.TileEnderTank.isInstance(accessor.getTileEntity())) {
                    currenttip.add(String.format("%s/%s/%s", new Object[] { colors[freqLeft], colors[freqCenter], colors[freqRight] }));
                } else {
                    currenttip.add(String.format("%s/%s/%s", new Object[] { colors[freqRight], colors[freqCenter], colors[freqLeft] }));
                }
            }
            catch (Exception e)
            {
                currenttip = WailaExceptionHandler.handleErr(e, accessor.getTileEntity().getClass().getName(), currenttip);
            }
        }
        return currenttip;
    }

    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
    {
        if (te != null) {
            te.func_145841_b(tag);
        }
        return tag;
    }

}
