package mcp.mobius.waila.handlers;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IWailaBlockDecorator;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaFMPDecorator;
import mcp.mobius.waila.api.impl.DataAccessorFMP;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Level;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class DecoratorFMP implements IWailaBlockDecorator {

    public void decorateBlock(ItemStack itemStack, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        NBTTagList list = accessor.getNBTData().func_150295_c("parts", 10);
        for (int i = 0; i < list.func_74745_c(); i++)
        {
            NBTTagCompound subtag = list.func_150305_b(i);
            String id = subtag.func_74779_i("id");
            if (ModuleRegistrar.instance().hasFMPDecorator(id))
            {
                DataAccessorFMP.instance.set(accessor.getWorld(), accessor.getPlayer(), accessor.getPosition(), subtag, id, accessor.getRenderingPosition(), accessor.getPartialFrame());
                for (IWailaFMPDecorator provider : ModuleRegistrar.instance().getFMPDecorators(id)) {
                    provider.decorateBlock(itemStack, DataAccessorFMP.instance, config);
                }
            }
        }
    }

    public static void register()
    {
        Class BlockMultipart = null;
        try
        {
            BlockMultipart = Class.forName("codechicken.multipart.BlockMultipart");
        }
        catch (ClassNotFoundException e)
        {
            Waila.log.log(Level.WARN, "[FMP] Class not found. " + e);
            return;
        }
        catch (Exception e)
        {
            Waila.log.log(Level.WARN, "[FMP] Unhandled exception." + e);
            return;
        }
        ModuleRegistrar.instance().registerDecorator(new DecoratorFMP(), BlockMultipart);
    }

}
