package mcp.mobius.waila.handlers;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class HUDHandlerBlocks implements IWailaDataProvider {

    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        String name = null;
        try
        {
            String s = DisplayUtil.itemDisplayNameShort(itemStack);
            if ((s != null) && (!s.endsWith("Unnamed"))) {
                name = s;
            }
            if (name != null) {
                currenttip.add(name);
            }
        }
        catch (Exception e) {}
        if (itemStack.func_77973_b() == Items.field_151137_ax)
        {
            int md = accessor.getMetadata();
            String s = "" + md;
            if (s.length() < 2) {
                s = " " + s;
            }
            currenttip.set(currenttip.size() - 1, name + " " + s);
        }
        if (currenttip.size() == 0) {
            currenttip.add("< Unnamed >");
        } else if (ConfigHandler.instance().getConfig("general", Constants.CFG_WAILA_METADATA, true)) {
            currenttip.add(String.format(SpecialChars.ITALIC + "[%d:%d] | %s", new Object[] { Integer.valueOf(accessor.getBlockID()), Integer.valueOf(accessor.getMetadata()), accessor.getBlockQualifiedName() }));
        }
        return currenttip;
    }

    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        currenttip.add(SpecialChars.RENDER + "{Plip}" + SpecialChars.RENDER + "{Plop,thisisatest,222,333}");

        String modName = ModIdentification.nameFromStack(itemStack);
        if ((modName != null) && (!modName.equals(""))) {
            currenttip.add(SpecialChars.BLUE + SpecialChars.ITALIC + modName);
        }
        return currenttip;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
    {
        return tag;
    }

}
