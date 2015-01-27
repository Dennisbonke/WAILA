package mcp.mobius.waila.api.impl;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.utils.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class ConfigHandler implements IWailaConfigHandler {

    private static ConfigHandler _instance = null;

    private ConfigHandler()
    {
        _instance = this;
    }

    public static ConfigHandler instance()
    {
        return _instance == null ? new ConfigHandler() : _instance;
    }

    private LinkedHashMap<String, ConfigModule> modules = new LinkedHashMap();
    private ArrayList<String> serverconfigs = new ArrayList();
    public HashMap<String, Boolean> forcedConfigs = new HashMap();
    public Configuration config = null;

    public void addModule(String modName, HashMap<String, String> options)
    {
        addModule(modName, new ConfigModule(modName, options));
    }

    public void addModule(String modName, ConfigModule options)
    {
        this.modules.put(modName, options);
    }

    public Set<String> getModuleNames()
    {
        return this.modules.keySet();
    }

    public HashMap<String, String> getConfigKeys(String modName)
    {
        if (this.modules.containsKey(modName)) {
            return ((ConfigModule)this.modules.get(modName)).options;
        }
        return null;
    }

    private void saveModuleKey(String modName, String key)
    {
        saveModuleKey(modName, key, Constants.CFG_DEFAULT_VALUE);
    }

    private void saveModuleKey(String modName, String key, boolean defvalue)
    {
        this.config.get(Constants.CATEGORY_MODULES, key, defvalue);
        this.config.get(Constants.CATEGORY_SERVER, key, Constants.SERVER_FREE);
        this.config.save();
    }

    public void addConfig(String modName, String key, String name)
    {
        addConfig(modName, key, name, Constants.CFG_DEFAULT_VALUE);
    }

    public void addConfig(String modName, String key, String name, boolean defvalue)
    {
        saveModuleKey(modName, key, defvalue);
        if (!this.modules.containsKey(modName)) {
            this.modules.put(modName, new ConfigModule(modName));
        }
        ((ConfigModule)this.modules.get(modName)).addOption(key, name);
    }

    public void addConfigServer(String modName, String key, String name)
    {
        addConfigServer(modName, key, name, Constants.CFG_DEFAULT_VALUE);
    }

    public void addConfigServer(String modName, String key, String name, boolean defvalue)
    {
        saveModuleKey(modName, key, defvalue);
        if (!this.modules.containsKey(modName)) {
            this.modules.put(modName, new ConfigModule(modName));
        }
        ((ConfigModule)this.modules.get(modName)).addOption(key, name);
        this.serverconfigs.add(key);
    }

    public boolean getConfig(String key)
    {
        return getConfig(key, Constants.CFG_DEFAULT_VALUE);
    }

    public boolean getConfig(String key, boolean defvalue)
    {
        if ((this.serverconfigs.contains(key)) && (!Waila.instance.serverPresent)) {
            return false;
        }
        if (this.forcedConfigs.containsKey(key)) {
            return ((Boolean)this.forcedConfigs.get(key)).booleanValue();
        }
        Property prop = this.config.get(Constants.CATEGORY_MODULES, key, defvalue);
        return prop.getBoolean(defvalue);
    }

    public boolean isServerRequired(String key)
    {
        return this.serverconfigs.contains(key);
    }

    public boolean getConfig(String category, String key, boolean default_)
    {
        Property prop = this.config.get(category, key, default_);
        return prop.getBoolean(default_);
    }

    public void setConfig(String category, String key, boolean state)
    {
        this.config.getCategory(category).put(key, new Property(key, String.valueOf(state), Property.Type.BOOLEAN));
        this.config.save();
    }

    public int getConfig(String category, String key, int default_)
    {
        Property prop = this.config.get(category, key, default_);
        return prop.getInt();
    }

    public void setConfig(String category, String key, int state)
    {
        this.config.getCategory(category).put(key, new Property(key, String.valueOf(state), Property.Type.INTEGER));
        this.config.save();
    }

    public void loadDefaultConfig(FMLPreInitializationEvent event)
    {
        this.config = new Configuration(event.getSuggestedConfigurationFile());

        this.config.get("general", Constants.CFG_WAILA_SHOW, true);
        this.config.get("general", Constants.CFG_WAILA_MODE, true);
        this.config.get("general", Constants.CFG_WAILA_LIQUID, false);
        this.config.get("general", Constants.CFG_WAILA_METADATA, false);
        this.config.get("general", Constants.CFG_WAILA_KEYBIND, true);
        this.config.get("general", Constants.CFG_WAILA_NEWFILTERS, true);

        mcp.mobius.waila.overlay.OverlayConfig.posX = this.config.get("general", Constants.CFG_WAILA_POSX, 5000).getInt();
        mcp.mobius.waila.overlay.OverlayConfig.posY = this.config.get("general", Constants.CFG_WAILA_POSY, 100).getInt();

        mcp.mobius.waila.overlay.OverlayConfig.alpha = this.config.get("general", Constants.CFG_WAILA_ALPHA, 80).getInt();
        mcp.mobius.waila.overlay.OverlayConfig.bgcolor = this.config.get("general", Constants.CFG_WAILA_BGCOLOR, 1048592).getInt();
        mcp.mobius.waila.overlay.OverlayConfig.gradient1 = this.config.get("general", Constants.CFG_WAILA_GRADIENT1, 5243135).getInt();
        mcp.mobius.waila.overlay.OverlayConfig.gradient2 = this.config.get("general", Constants.CFG_WAILA_GRADIENT2, 2621567).getInt();
        mcp.mobius.waila.overlay.OverlayConfig.fontcolor = this.config.get("general", Constants.CFG_WAILA_FONTCOLOR, 10526880).getInt();
        mcp.mobius.waila.overlay.OverlayConfig.scale = this.config.get("general", Constants.CFG_WAILA_SCALE, 100).getInt() / 100.0F;

        mcp.mobius.waila.handlers.HUDHandlerEntities.nhearts = this.config.get("general", Constants.CFG_WAILA_NHEARTS, 20).getInt();
        mcp.mobius.waila.handlers.HUDHandlerEntities.maxhpfortext = this.config.get("general", Constants.CFG_WAILA_MAXHP, 40).getInt();

        this.config.getCategory(Constants.CATEGORY_MODULES).setComment("Those are the config keys defined in modules.\nServer side, it is used to enforce keys client side using the next section.");
        this.config.getCategory(Constants.CATEGORY_SERVER).setComment("Any key set to true here will ensure that the client is using the configuration set in the 'module' section above.\nThis is useful for enforcing false to 'cheating' keys like silverfish.");

        this.config.save();
    }

}
