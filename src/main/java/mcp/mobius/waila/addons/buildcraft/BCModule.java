package mcp.mobius.waila.addons.buildcraft;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Method;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class BCModule {

    public static Class TileTank = null;
    public static Method TileTank_getTankInfo = null;

    public static void register()
    {
        try
        {
            TileTank = Class.forName("buildcraft.factory.TileTank");
            TileTank_getTankInfo = TileTank.getMethod("getTankInfo", new Class[] { ForgeDirection.class });

            ModuleRegistrar.instance().addConfig("Buildcraft", "bc.tankamount");
            ModuleRegistrar.instance().addConfig("Buildcraft", "bc.tanktype");
            ModuleRegistrar.instance().registerHeadProvider(new HUDHandlerBCTanks(), TileTank);
            ModuleRegistrar.instance().registerBodyProvider(new HUDHandlerBCTanks(), TileTank);
        }
        catch (ClassNotFoundException e)
        {
            Waila.log.log(Level.WARN, "[BC] Class not found. " + e);
            return;
        }
        catch (NoSuchMethodException e)
        {
            Waila.log.log(Level.WARN, "[BC] Method not found." + e);
            return;
        }
    }

}
