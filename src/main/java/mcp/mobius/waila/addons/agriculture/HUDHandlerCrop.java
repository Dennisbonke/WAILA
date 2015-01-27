package mcp.mobius.waila.addons.agriculture;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
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
public class HUDHandlerCrop implements IWailaDataProvider {

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
        if (!config.getConfig("general.showcrop")) {
            return currenttip;
        }
        int growthStage = accessor.getMetadata();

        float growthValue = growthStage / 6.0F * 100.0F;
        if (growthValue < 100.0D) {
            currenttip.add(String.format("%s : %.0f %%", new Object[] { LangUtil.translateG("hud.msg.growth", new Object[0]), Float.valueOf(growthValue) }));
        } else {
            currenttip.add(String.format("%s : %s", new Object[] { LangUtil.translateG("hud.msg.growth", new Object[0]), LangUtil.translateG("hud.msg.mature", new Object[0]) }));
        }
        return currenttip;
    }

    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
    {
        return tag;
    }

}
