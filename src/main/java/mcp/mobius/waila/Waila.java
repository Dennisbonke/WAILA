package mcp.mobius.waila;

import com.google.common.collect.UnmodifiableIterator;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.relauncher.Side;
import mcp.mobius.waila.client.KeyEvent;
import mcp.mobius.waila.server.ProxyServer;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
@Mod(modid="Waila", name="Waila", version="1.5.8", dependencies="after:NotEnoughItems@[1.0.4.0,)", acceptableRemoteVersions="*")
public class Waila {

    @Mod.Instance("Waila")
    public static Waila instance;
    @SidedProxy(clientSide="mcp.mobius.waila.client.ProxyClient", serverSide="mcp.mobius.waila.server.ProxyServer")
    public static ProxyServer proxy;
    public static Logger log = LogManager.getLogger("Waila");
    public boolean serverPresent = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.instance().loadDefaultConfig(event);
        OverlayConfig.updateColors();
        MinecraftForge.EVENT_BUS.register(new DecoratorRenderer());
        WailaPacketHandler.INSTANCE.ordinal();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        try
        {
            Field eBus = FMLModContainer.class.getDeclaredField("eventBus");
            eBus.setAccessible(true);
            com.google.common.eventbus.EventBus FMLbus = (com.google.common.eventbus.EventBus)eBus.get(FMLCommonHandler.instance().findContainerFor(this));
            FMLbus.register(this);
        }
        catch (Throwable t) {}
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            MinecraftForge.EVENT_BUS.register(new DecoratorRenderer());
            FMLCommonHandler.instance().bus().register(new KeyEvent());
            FMLCommonHandler.instance().bus().register(WailaTickHandler.instance());
        }
        FMLCommonHandler.instance().bus().register(new NetworkHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.registerHandlers();
        ModIdentification.init();
    }

    @Subscribe
    public void loadComplete(FMLLoadCompleteEvent event)
    {
        proxy.registerMods();
        proxy.registerIMCs();
    }

    @Mod.EventHandler
    public void processIMC(FMLInterModComms.IMCEvent event)
    {
        for (UnmodifiableIterator localUnmodifiableIterator = event.getMessages().iterator(); localUnmodifiableIterator.hasNext();)
        {
            FMLInterModComms.IMCMessage imcMessage = (FMLInterModComms.IMCMessage)localUnmodifiableIterator.next();
            if (imcMessage.isStringMessage()) {
                if (imcMessage.key.equalsIgnoreCase("addconfig"))
                {
                    String[] params = imcMessage.getStringValue().split("\\$\\$");
                    if (params.length != 3)
                    {
                        log.warn(String.format("Error while parsing config option from [ %s ] for %s", new Object[] { imcMessage.getSender(), imcMessage.getStringValue() }));
                    }
                    else
                    {
                        log.info(String.format("Receiving config request from [ %s ] for %s", new Object[] { imcMessage.getSender(), imcMessage.getStringValue() }));
                        ConfigHandler.instance().addConfig(params[0], params[1], params[2]);
                    }
                }
                else if (imcMessage.key.equalsIgnoreCase("register"))
                {
                    log.info(String.format("Receiving registration request from [ %s ] for method %s", new Object[] { imcMessage.getSender(), imcMessage.getStringValue() }));
                    ModuleRegistrar.instance().addIMCRequest(imcMessage.getStringValue(), imcMessage.getSender());
                }
            }
        }
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandDumpHandlers());
    }

}
