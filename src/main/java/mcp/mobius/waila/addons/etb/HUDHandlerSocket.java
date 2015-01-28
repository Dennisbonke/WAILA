package mcp.mobius.waila.addons.etb;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Dennisbonke on 28-1-2015.
 */
public class HUDHandlerSocket implements IWailaDataProvider {

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
        if (!config.getConfig("etb.displaydata")) {
            return currenttip;
        }
        try
        {
            int[] sides = (int[])ETBModule.Socket_sides.get(accessor.getTileEntity());
            Object[] configs = (Object[])ETBModule.Socket_configs.get(accessor.getTileEntity());
            Item module = (Item)ETBModule.module.get(null);
            for (int s = 0; s < 6; s++) {
                if (sides[s] != 0)
                {
                    int tank = ((Integer)ETBModule.SC_tank.get(configs[s])).intValue();
                    int inventory = ((Integer)ETBModule.SC_inventory.get(configs[s])).intValue();
                    boolean[] rsControl = (boolean[])ETBModule.SC_rsControl.get(configs[s]);
                    boolean[] rsLatch = (boolean[])ETBModule.SC_rsLatch.get(configs[s]);


                    ItemStack stack = new ItemStack(module, 1, sides[s]);
                    String tipstr = String.format("%-5s : %s ", new Object[] { ForgeDirection.getOrientation(s), stack.func_82833_r() });

                    String configstr = "[ ";
                    if (tank != -1) {
                        configstr = configstr + "§9" + String.valueOf(tank) + " ";
                    }
                    if (inventory != -1) {
                        configstr = configstr + "§a" + String.valueOf(inventory) + " ";
                    }
                    if ((rsControl[0] != 0) || (rsControl[1] != 0) || (rsControl[2] != 0))
                    {
                        configstr = configstr + "§c";
                        configstr = configstr + (rsControl[0] != 0 ? "1" : "0");
                        configstr = configstr + (rsControl[1] != 0 ? "1" : "0");
                        configstr = configstr + (rsControl[2] != 0 ? "1" : "0");
                        configstr = configstr + " ";
                    }
                    if ((rsLatch[0] != 0) || (rsLatch[1] != 0) || (rsLatch[2] != 0))
                    {
                        configstr = configstr + "§5";
                        configstr = configstr + (rsLatch[0] != 0 ? "1" : "0");
                        configstr = configstr + (rsLatch[1] != 0 ? "1" : "0");
                        configstr = configstr + (rsLatch[2] != 0 ? "1" : "0");
                        configstr = configstr + " ";
                    }
                    configstr = configstr + "§r]";
                    if (!configstr.equals("[ §r]")) {
                        tipstr = tipstr + " " + configstr;
                    }
                    currenttip.add(tipstr);
                }
            }
        }
        catch (Exception e)
        {
            currenttip = WailaExceptionHandler.handleErr(e, accessor.getTileEntity().getClass().getName(), currenttip);
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
