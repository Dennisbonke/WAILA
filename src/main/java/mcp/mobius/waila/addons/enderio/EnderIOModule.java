package mcp.mobius.waila.addons.enderio;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Method;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class EnderIOModule {

    public static Class TileCapacitorBank = null;
    public static Method TCB_getMaxInput = null;
    public static Method TCB_getMaxOutput = null;
    public static Method TCB_getEnergyStored = null;
    public static Method TCB_getMaxEnergyStored = null;
    public static Method TCB_getMaxIO = null;
    public static Class TileTesseract = null;

    public static void register()
    {
        try
        {
            Class ModClass = Class.forName("crazypants.enderio.EnderIO");
            Waila.log.log(Level.INFO, "EnderIO mod found.");
        }
        catch (ClassNotFoundException e)
        {
            Waila.log.log(Level.INFO, "[EnderIO] EnderIO mod not found.");
            return;
        }
        try
        {
            TileCapacitorBank = Class.forName("crazypants.enderio.machine.power.TileCapacitorBank");
            TCB_getEnergyStored = TileCapacitorBank.getMethod("getEnergyStored", new Class[0]);
            TCB_getMaxInput = TileCapacitorBank.getMethod("getMaxInput", new Class[0]);
            TCB_getMaxOutput = TileCapacitorBank.getMethod("getMaxOutput", new Class[0]);
            TCB_getMaxEnergyStored = TileCapacitorBank.getMethod("getMaxEnergyStored", new Class[0]);
            TCB_getMaxIO = TileCapacitorBank.getMethod("getMaxIO", new Class[0]);

            TileTesseract = Class.forName("crazypants.enderio.machine.hypercube.TileHyperCube");
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
        catch (Exception e)
        {
            Waila.log.log(Level.WARN, "[EnderStorage] Unhandled exception." + e);
            return;
        }
        ModuleRegistrar.instance().addConfig("EnderIO", "enderio.inout");
        ModuleRegistrar.instance().addConfig("EnderIO", "enderio.storage");
        ModuleRegistrar.instance().addConfig("EnderIO", "enderio.owner");
        ModuleRegistrar.instance().addConfig("EnderIO", "enderio.channel");
        ModuleRegistrar.instance().registerBodyProvider(new HUDHandlerCapacitor(), TileCapacitorBank);
        ModuleRegistrar.instance().registerBodyProvider(new HUDHandlerTesseract(), TileTesseract);
        ModuleRegistrar.instance().registerNBTProvider(new HUDHandlerCapacitor(), TileCapacitorBank);
        ModuleRegistrar.instance().registerNBTProvider(new HUDHandlerTesseract(), TileTesseract);
    }

}
