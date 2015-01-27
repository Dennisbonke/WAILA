package mcp.mobius.waila.server;

import cpw.mods.fml.common.Loader;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IWailaRegistrar;

import java.lang.reflect.Method;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class ProxyServer {

    public void registerHandlers() {}

    public void registerMods()
    {
        HUDHandlerVanilla.register();


        BCModule.register();


        IC2Module.register();


        ThaumcraftModule.register();


        EnderStorageModule.register();


        GravestoneModule.register();


        TwilightForestModule.register();


        ThermalExpansionModule.register();


        ETBModule.register();





        ProjectRedModule.register();


        ExtraUtilitiesModule.register();


        OpenBlocksModule.register();


        RailcraftModule.register();


        StevesCartsModule.register();





        CarpentersModule.register();


        HarvestcraftModule.register();


        MagicalCropsModule.register();


        StatuesModule.register();


        AgricultureModule.register();
        if (Loader.isModLoaded("ForgeMultipart"))
        {
            HUDHandlerFMP.register();
            DecoratorFMP.register();
        }
    }

    public void registerIMCs()
    {
        for (String s : ModuleRegistrar.instance().IMCRequests.keySet()) {
            callbackRegistration(s, (String)ModuleRegistrar.instance().IMCRequests.get(s));
        }
    }

    public void callbackRegistration(String method, String modname)
    {
        String[] splitName = method.split("\\.");
        String methodName = splitName[(splitName.length - 1)];
        String className = method.substring(0, method.length() - methodName.length() - 1);

        Waila.log.info(String.format("Trying to reflect %s %s", new Object[] { className, methodName }));
        try
        {
            Class reflectClass = Class.forName(className);
            Method reflectMethod = reflectClass.getDeclaredMethod(methodName, new Class[] { IWailaRegistrar.class });
            reflectMethod.invoke(null, new Object[] { ModuleRegistrar.instance() });

            Waila.log.info(String.format("Success in registering %s", new Object[] { modname }));
        }
        catch (ClassNotFoundException e)
        {
            Waila.log.warn(String.format("Could not find class %s", new Object[] { className }));
        }
        catch (NoSuchMethodException e)
        {
            Waila.log.warn(String.format("Could not find method %s", new Object[] { methodName }));
        }
        catch (Exception e)
        {
            Waila.log.warn(String.format("Exception while trying to access the method : %s", new Object[] { e.toString() }));
        }
    }

    public Object getFont()
    {
        return null;
    }

}
