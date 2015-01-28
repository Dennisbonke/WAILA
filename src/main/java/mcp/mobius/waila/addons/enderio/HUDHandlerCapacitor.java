package mcp.mobius.waila.addons.enderio;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class HUDHandlerCapacitor implements IWailaDataProvider {

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
        if (accessor.getPlayer().func_70093_af()) {
            try
            {
                String maxIOStr = LangUtil.translateG("hud.msg.maxio", new Object[0]);
                String inputStr = LangUtil.translateG("hud.msg.input", new Object[0]);
                String outputStr = LangUtil.translateG("hud.msg.output", new Object[0]);
                if (config.getConfig("enderio.storage"))
                {
                    Integer maxEnergyStored = (Integer)EnderIOModule.TCB_getMaxEnergyStored.invoke(accessor.getTileEntity(), new Object[0]);
                    Double energyStored = (Double)EnderIOModule.TCB_getEnergyStored.invoke(accessor.getTileEntity(), new Object[0]);
                    currenttip.add(String.format("%s%.1f%s / %s%d%s RF", new Object[] { SpecialChars.WHITE, Double.valueOf(energyStored.doubleValue() * 10.0D), SpecialChars.RESET, SpecialChars.WHITE, Integer.valueOf(maxEnergyStored.intValue() * 10), SpecialChars.RESET }));
                }
                if (config.getConfig("enderio.inout"))
                {
                    Integer maxIO = (Integer)EnderIOModule.TCB_getMaxIO.invoke(accessor.getTileEntity(), new Object[0]);
                    Integer maxInput = (Integer)EnderIOModule.TCB_getMaxInput.invoke(accessor.getTileEntity(), new Object[0]);
                    Integer maxOutput = (Integer)EnderIOModule.TCB_getMaxOutput.invoke(accessor.getTileEntity(), new Object[0]);
                    currenttip.add(String.format("%s : %s%d%sRF/t ", new Object[] { maxIOStr, SpecialChars.TAB + SpecialChars.ALIGNRIGHT + SpecialChars.WHITE, Integer.valueOf(maxIO.intValue() * 10), SpecialChars.TAB + SpecialChars.ALIGNRIGHT }));
                    currenttip.add(String.format("%s : %s%d%sRF/t ", new Object[] { inputStr, SpecialChars.TAB + SpecialChars.ALIGNRIGHT + SpecialChars.WHITE, Integer.valueOf(maxInput.intValue() * 10), SpecialChars.TAB + SpecialChars.ALIGNRIGHT }));
                    currenttip.add(String.format("%s : %s%d%sRF/t ", new Object[] { outputStr, SpecialChars.TAB + SpecialChars.ALIGNRIGHT + SpecialChars.WHITE, Integer.valueOf(maxOutput.intValue() * 10), SpecialChars.TAB + SpecialChars.ALIGNRIGHT }));
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
