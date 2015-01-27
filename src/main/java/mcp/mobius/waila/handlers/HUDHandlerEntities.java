package mcp.mobius.waila.handlers;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.EntityRegistry;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class HUDHandlerEntities implements IWailaEntityProvider {

    public static int nhearts = 20;
    public static float maxhpfortext = 40.0F;

    public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        try
        {
            currenttip.add(SpecialChars.WHITE + entity.func_70005_c_());
        }
        catch (Exception e)
        {
            currenttip.add(SpecialChars.WHITE + "Unknown");
        }
        return currenttip;
    }

    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        if (!config.getConfig("general.showhp")) {
            return currenttip;
        }
        if ((entity instanceof EntityLivingBase))
        {
            String hptip = "";

            nhearts = nhearts <= 0 ? 20 : nhearts;

            float health = ((EntityLivingBase)entity).func_110143_aJ() / 2.0F;
            float maxhp = ((EntityLivingBase)entity).func_110138_aP() / 2.0F;
            if (((EntityLivingBase)entity).func_110143_aJ() > maxhpfortext)
            {
                currenttip.add(String.format("HP : " + SpecialChars.WHITE + "%.0f" + SpecialChars.GRAY + " / " + SpecialChars.WHITE + "%.0f", new Object[] { Float.valueOf(((EntityLivingBase)entity).func_110143_aJ()), Float.valueOf(((EntityLivingBase)entity).func_110138_aP()) }));
            }
            else
            {
                for (int i = 0; i < MathHelper.func_76141_d(health); i++)
                {
                    hptip = hptip + SpecialChars.HEART;
                    if (hptip.length() % (nhearts * SpecialChars.HEART.length()) == 0)
                    {
                        currenttip.add(hptip);
                        hptip = "";
                    }
                }
                if (((EntityLivingBase)entity).func_110143_aJ() > MathHelper.func_76141_d(health) * 2.0F)
                {
                    hptip = hptip + SpecialChars.HHEART;
                    if (hptip.length() % (nhearts * SpecialChars.HEART.length()) == 0)
                    {
                        currenttip.add(hptip);
                        hptip = "";
                    }
                }
                for (int i = 0; i < MathHelper.func_76141_d(maxhp - health); i++)
                {
                    hptip = hptip + SpecialChars.EHEART;
                    if (hptip.length() % (nhearts * SpecialChars.HEART.length()) == 0)
                    {
                        currenttip.add(hptip);
                        hptip = "";
                    }
                }
                if (!hptip.equals("")) {
                    currenttip.add(hptip);
                }
            }
        }
        return currenttip;
    }

    public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        try
        {
            currenttip.add(SpecialChars.BLUE + SpecialChars.ITALIC + getEntityMod(entity));
        }
        catch (Exception e)
        {
            currenttip.add(SpecialChars.BLUE + SpecialChars.ITALIC + "Unknown");
        }
        return currenttip;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity te, NBTTagCompound tag, World world)
    {
        return tag;
    }

    private static String getEntityMod(Entity entity)
    {
        String modName = "";
        try
        {
            EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(entity.getClass(), true);
            ModContainer modC = er.getContainer();
            modName = modC.getName();
        }
        catch (NullPointerException e)
        {
            modName = "Minecraft";
        }
        return modName;
    }

}
