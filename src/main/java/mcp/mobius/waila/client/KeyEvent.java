package mcp.mobius.waila.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class KeyEvent {

    public static KeyBinding key_cfg;
    public static KeyBinding key_show;
    public static KeyBinding key_liquid;
    public static KeyBinding key_recipe;
    public static KeyBinding key_usage;

    @SubscribeEvent
    public void onKeyEvent(InputEvent.KeyInputEvent event)
    {
        int key = Keyboard.getEventKey();
        Minecraft mc = Minecraft.func_71410_x();
        if (Keyboard.getEventKeyState()) {
            if (key == key_cfg.func_151463_i())
            {
                if (mc.field_71462_r == null) {
                    mc.func_147108_a(new ScreenConfig(mc.field_71462_r));
                }
            }
            else if ((key == key_show.func_151463_i()) && (ConfigHandler.instance().getConfig("general", Constants.CFG_WAILA_MODE, false)))
            {
                boolean status = ConfigHandler.instance().getConfig("general", Constants.CFG_WAILA_SHOW, true);
                ConfigHandler.instance().setConfig("general", Constants.CFG_WAILA_SHOW, !status);
            }
            else if ((key == key_show.func_151463_i()) && (!ConfigHandler.instance().getConfig("general", Constants.CFG_WAILA_MODE, false)))
            {
                ConfigHandler.instance().setConfig("general", Constants.CFG_WAILA_SHOW, true);
            }
            else if (key == key_liquid.func_151463_i())
            {
                boolean status = ConfigHandler.instance().getConfig("general", Constants.CFG_WAILA_LIQUID, true);
                ConfigHandler.instance().setConfig("general", Constants.CFG_WAILA_LIQUID, !status);
            }
            else if (key == key_recipe.func_151463_i())
            {
                if (Loader.isModLoaded("NotEnoughItems")) {
                    try
                    {
                        Class.forName("mcp.mobius.waila.handlers.nei.NEIHandler").getDeclaredMethod("openRecipeGUI", new Class[] { Boolean.TYPE }).invoke(null, new Object[] { Boolean.valueOf(true) });
                    }
                    catch (Exception e) {}
                }
            }
            else if ((key == key_usage.func_151463_i()) &&
                    (Loader.isModLoaded("NotEnoughItems")))
            {
                try
                {
                    Class.forName("mcp.mobius.waila.handlers.nei.NEIHandler").getDeclaredMethod("openRecipeGUI", new Class[] { Boolean.TYPE }).invoke(null, new Object[] { Boolean.valueOf(false) });
                }
                catch (Exception e) {}
            }
        }
    }

    public KeyEvent()
    {
        key_cfg = new KeyBinding(Constants.BIND_WAILA_CFG, 82, "Waila");
        key_show = new KeyBinding(Constants.BIND_WAILA_SHOW, 79, "Waila");
        key_liquid = new KeyBinding(Constants.BIND_WAILA_LIQUID, 80, "Waila");
        key_recipe = new KeyBinding(Constants.BIND_WAILA_RECIPE, 81, "Waila");
        key_usage = new KeyBinding(Constants.BIND_WAILA_USAGE, 75, "Waila");

        ClientRegistry.registerKeyBinding(key_cfg);
        ClientRegistry.registerKeyBinding(key_show);
        ClientRegistry.registerKeyBinding(key_liquid);
        ClientRegistry.registerKeyBinding(key_recipe);
        ClientRegistry.registerKeyBinding(key_usage);
    }

}
