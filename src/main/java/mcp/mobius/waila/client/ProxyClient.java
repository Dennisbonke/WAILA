package mcp.mobius.waila.client;

import cpw.mods.fml.common.Loader;
import mcp.mobius.waila.server.ProxyServer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class ProxyClient extends ProxyServer {

    TrueTypeFont minecraftiaFont;

    public void registerHandlers()
    {
        LangUtil.loadLangDir("waila");

        this.minecraftiaFont = FontLoader.createFont(new ResourceLocation("waila", "fonts/Minecraftia.ttf"), 14.0F, true);
        if (Loader.isModLoaded("NotEnoughItems")) {
            try
            {
                Class.forName("mcp.mobius.waila.handlers.nei.NEIHandler").getDeclaredMethod("register", new Class[0]).invoke(null, new Object[0]);
            }
            catch (Exception e) {}
        } else {
            MinecraftForge.EVENT_BUS.register(new VanillaTooltipHandler());
        }
        ModuleRegistrar.instance().registerHeadProvider(new HUDHandlerBlocks(), Block.class);
        ModuleRegistrar.instance().registerTailProvider(new HUDHandlerBlocks(), Block.class);

        ModuleRegistrar.instance().registerHeadProvider(new HUDHandlerEntities(), Entity.class);
        ModuleRegistrar.instance().registerBodyProvider(new HUDHandlerEntities(), Entity.class);
        ModuleRegistrar.instance().registerTailProvider(new HUDHandlerEntities(), Entity.class);



        ModuleRegistrar.instance().addConfig("General", "general.showhp");
        ModuleRegistrar.instance().addConfig("General", "general.showcrop");
    }

    public Object getFont()
    {
        return this.minecraftiaFont;
    }

}
