package mcp.mobius.waila.addons.enderstorage;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Dennisbonke on 28-1-2015.
 */
public class EnderStorageModule {

    public static Class TileFrequencyOwner = null;
    public static Field TileFrequencyOwner_Freq = null;
    public static Class EnderStorageManager = null;
    public static Method GetColourFromFreq = null;
    public static Class TileEnderTank = null;

    public static void register()
    {
        try
        {
            Class EnderStorage = Class.forName("codechicken.enderstorage.EnderStorage");
            Waila.log.log(Level.INFO, "EnderStorage mod found.");
        }
        catch (ClassNotFoundException e)
        {
            Waila.log.log(Level.INFO, "[EnderStorage] EnderStorage mod not found.");
            return;
        }
        try
        {
            TileFrequencyOwner = Class.forName("codechicken.enderstorage.common.TileFrequencyOwner");
            TileFrequencyOwner_Freq = TileFrequencyOwner.getField("freq");

            EnderStorageManager = Class.forName("codechicken.enderstorage.api.EnderStorageManager");
            GetColourFromFreq = EnderStorageManager.getDeclaredMethod("getColourFromFreq", new Class[] { Integer.TYPE, Integer.TYPE });

            TileEnderTank = Class.forName("codechicken.enderstorage.storage.liquid.TileEnderTank");
        }
        catch (ClassNotFoundException e)
        {
            Waila.log.log(Level.WARN, "[EnderStorage] Class not found. " + e);
            return;
        }
        catch (NoSuchMethodException e)
        {
            Waila.log.log(Level.WARN, "[EnderStorage] Method not found." + e);
            return;
        }
        catch (NoSuchFieldException e)
        {
            Waila.log.log(Level.WARN, "[EnderStorage] Field not found." + e);
            return;
        }
        catch (Exception e)
        {
            Waila.log.log(Level.WARN, "[EnderStorage] Unhandled exception." + e);
            return;
        }
        ModuleRegistrar.instance().addConfig("EnderStorage", "enderstorage.colors");
        ModuleRegistrar.instance().registerBodyProvider(new HUDHandlerStorage(), TileFrequencyOwner);
        ModuleRegistrar.instance().registerNBTProvider(new HUDHandlerStorage(), TileFrequencyOwner);
    }

}
