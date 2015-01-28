package mcp.mobius.waila.addons.enderio;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class HUDHandlerTesseract implements IWailaDataProvider {

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
        if (accessor.getPlayer().func_70093_af())
        {
            String channel = "%s : %s%s%s %s";
            if (config.getConfig("enderio.channel"))
            {
                String frequser;
                String freq;
                if (accessor.getNBTData().func_74764_b("channelName"))
                {
                    freq = accessor.getNBTData().func_74779_i("channelName");
                    if (accessor.getNBTData().func_74764_b("channelUser")) {
                        frequser = "[ " + SpecialChars.WHITE + accessor.getNBTData().func_74779_i("channelUser") + SpecialChars.RESET + " ]";
                    } else {
                        frequser = "[ " + SpecialChars.WHITE + LangUtil.translateG("hud.msg.public", new Object[0]) + SpecialChars.RESET + " ]";
                    }
                }
                else
                {
                    freq = LangUtil.translateG("hud.msg.none", new Object[0]);
                    frequser = "";
                }
                currenttip.add(String.format(channel, new Object[] { LangUtil.translateG("hud.msg.frequency", new Object[0]), SpecialChars.TAB + SpecialChars.WHITE, freq, SpecialChars.RESET, frequser }));
            }
            if ((config.getConfig("enderio.owner")) &&
                    (accessor.getNBTData().func_74764_b("owner"))) {
                currenttip.add(String.format("%s : %s%s", new Object[] { LangUtil.translateG("hud.msg.owner", new Object[0]), SpecialChars.TAB + SpecialChars.WHITE, accessor.getNBTData().func_74779_i("owner") }));
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
