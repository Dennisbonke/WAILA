package mcp.mobius.waila.api.impl;

import au.com.bytecode.opencsv.CSVReader;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.*;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class ModuleRegistrar implements IWailaRegistrar {

    private static ModuleRegistrar instance = null;
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> headBlockProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> bodyBlockProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> tailBlockProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> stackBlockProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaDataProvider>> NBTDataProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaBlockDecorator>> blockClassDecorators = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> headEntityProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> bodyEntityProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> tailEntityProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> overrideEntityProviders = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaEntityProvider>> NBTEntityProviders = new LinkedHashMap();
    public LinkedHashMap<String, ArrayList<IWailaFMPProvider>> headFMPProviders = new LinkedHashMap();
    public LinkedHashMap<String, ArrayList<IWailaFMPProvider>> bodyFMPProviders = new LinkedHashMap();
    public LinkedHashMap<String, ArrayList<IWailaFMPProvider>> tailFMPProviders = new LinkedHashMap();
    public LinkedHashMap<String, ArrayList<IWailaFMPDecorator>> FMPClassDecorators = new LinkedHashMap();
    public LinkedHashMap<Class, HashSet<String>> syncedNBTKeys = new LinkedHashMap();
    public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> wikiDescriptions = new LinkedHashMap();
    public LinkedHashMap<Class, ArrayList<IWailaSummaryProvider>> summaryProviders = new LinkedHashMap();
    public LinkedHashMap<String, String> IMCRequests = new LinkedHashMap();
    public LinkedHashMap<String, IWailaTooltipRenderer> tooltipRenderers = new LinkedHashMap();

    private ModuleRegistrar()
    {
        instance = this;
    }

    public static ModuleRegistrar instance()
    {
        if (instance == null) {
            instance = new ModuleRegistrar();
        }
        return instance;
    }

    public void addIMCRequest(String method, String modname)
    {
        this.IMCRequests.put(method, modname);
    }

    public void addConfig(String modname, String key, String configname)
    {
        addConfig(modname, key, configname, Constants.CFG_DEFAULT_VALUE);
    }

    public void addConfigRemote(String modname, String key, String configname)
    {
        addConfigRemote(modname, key, configname, Constants.CFG_DEFAULT_VALUE);
    }

    public void addConfig(String modname, String key)
    {
        addConfig(modname, key, Constants.CFG_DEFAULT_VALUE);
    }

    public void addConfigRemote(String modname, String key)
    {
        addConfigRemote(modname, key, Constants.CFG_DEFAULT_VALUE);
    }

    public void addConfig(String modname, String key, String configname, boolean defvalue)
    {
        ConfigHandler.instance().addConfig(modname, key, LangUtil.translateG(configname, new Object[0]), defvalue);
    }

    public void addConfigRemote(String modname, String key, String configname, boolean defvalue)
    {
        ConfigHandler.instance().addConfigServer(modname, key, LangUtil.translateG(configname, new Object[0]), defvalue);
    }

    public void addConfig(String modname, String key, boolean defvalue)
    {
        ConfigHandler.instance().addConfig(modname, key, LangUtil.translateG("option." + key, new Object[0]), defvalue);
    }

    public void addConfigRemote(String modname, String key, boolean defvalue)
    {
        ConfigHandler.instance().addConfigServer(modname, key, LangUtil.translateG("option." + key, new Object[0]), defvalue);
    }

    public void registerHeadProvider(IWailaDataProvider dataProvider, Class block)
    {
        registerProvider(dataProvider, block, this.headBlockProviders);
    }

    public void registerBodyProvider(IWailaDataProvider dataProvider, Class block)
    {
        registerProvider(dataProvider, block, this.bodyBlockProviders);
    }

    public void registerTailProvider(IWailaDataProvider dataProvider, Class block)
    {
        registerProvider(dataProvider, block, this.tailBlockProviders);
    }

    public void registerStackProvider(IWailaDataProvider dataProvider, Class block)
    {
        registerProvider(dataProvider, block, this.stackBlockProviders);
    }

    public void registerNBTProvider(IWailaDataProvider dataProvider, Class entity)
    {
        registerProvider(dataProvider, entity, this.NBTDataProviders);
    }

    public void registerHeadProvider(IWailaEntityProvider dataProvider, Class entity)
    {
        registerProvider(dataProvider, entity, this.headEntityProviders);
    }

    public void registerBodyProvider(IWailaEntityProvider dataProvider, Class entity)
    {
        registerProvider(dataProvider, entity, this.bodyEntityProviders);
    }

    public void registerTailProvider(IWailaEntityProvider dataProvider, Class entity)
    {
        registerProvider(dataProvider, entity, this.tailEntityProviders);
    }

    public void registerNBTProvider(IWailaEntityProvider dataProvider, Class entity)
    {
        registerProvider(dataProvider, entity, this.NBTEntityProviders);
    }

    public void registerHeadProvider(IWailaFMPProvider dataProvider, String name)
    {
        registerProvider(dataProvider, name, this.headFMPProviders);
    }

    public void registerBodyProvider(IWailaFMPProvider dataProvider, String name)
    {
        registerProvider(dataProvider, name, this.bodyFMPProviders);
    }

    public void registerTailProvider(IWailaFMPProvider dataProvider, String name)
    {
        registerProvider(dataProvider, name, this.tailFMPProviders);
    }

    public void registerOverrideEntityProvider(IWailaEntityProvider dataProvider, Class entity)
    {
        registerProvider(dataProvider, entity, this.overrideEntityProviders);
    }

    public void registerDecorator(IWailaBlockDecorator decorator, Class block)
    {
        registerProvider(decorator, block, this.blockClassDecorators);
    }

    public void registerDecorator(IWailaFMPDecorator decorator, String name)
    {
        registerProvider(decorator, name, this.FMPClassDecorators);
    }

    private <T, V> void registerProvider(T dataProvider, V clazz, LinkedHashMap<V, ArrayList<T>> target)
    {
        if ((clazz == null) || (dataProvider == null)) {
            throw new RuntimeException(String.format("Trying to register a null provider or null block ! Please check the stacktrace to know what was the original registration method. [Provider : %s, Target : %s]", new Object[] { dataProvider.getClass().getName(), clazz }));
        }
        if (!target.containsKey(clazz)) {
            target.put(clazz, new ArrayList());
        }
        ArrayList<T> providers = (ArrayList)target.get(clazz);
        if (providers.contains(dataProvider)) {
            return;
        }
        ((ArrayList)target.get(clazz)).add(dataProvider);
    }

    @Deprecated
    public void registerSyncedNBTKey(String key, Class target)
    {
        if (!this.syncedNBTKeys.containsKey(target)) {
            this.syncedNBTKeys.put(target, new HashSet());
        }
        ((HashSet)this.syncedNBTKeys.get(target)).add(key);
    }

    public void registerTooltipRenderer(String name, IWailaTooltipRenderer renderer)
    {
        if (!this.tooltipRenderers.containsKey(name)) {
            this.tooltipRenderers.put(name, renderer);
        } else {
            Waila.log.warn(String.format("A renderer named %s already exists (Class : %s). Skipping new renderer.", new Object[] { name, renderer.getClass().getName() }));
        }
    }

    public ArrayList<IWailaDataProvider> getHeadProviders(Object block)
    {
        return getProviders(block, this.headBlockProviders);
    }

    public ArrayList<IWailaDataProvider> getBodyProviders(Object block)
    {
        return getProviders(block, this.bodyBlockProviders);
    }

    public ArrayList<IWailaDataProvider> getTailProviders(Object block)
    {
        return getProviders(block, this.tailBlockProviders);
    }

    public ArrayList<IWailaDataProvider> getStackProviders(Object block)
    {
        return getProviders(block, this.stackBlockProviders);
    }

    public ArrayList<IWailaDataProvider> getNBTProviders(Object block)
    {
        return getProviders(block, this.NBTDataProviders);
    }

    public ArrayList<IWailaEntityProvider> getHeadEntityProviders(Object entity)
    {
        return getProviders(entity, this.headEntityProviders);
    }

    public ArrayList<IWailaEntityProvider> getBodyEntityProviders(Object entity)
    {
        return getProviders(entity, this.bodyEntityProviders);
    }

    public ArrayList<IWailaEntityProvider> getTailEntityProviders(Object entity)
    {
        return getProviders(entity, this.tailEntityProviders);
    }

    public ArrayList<IWailaEntityProvider> getOverrideEntityProviders(Object entity)
    {
        return getProviders(entity, this.overrideEntityProviders);
    }

    public ArrayList<IWailaEntityProvider> getNBTEntityProviders(Object entity)
    {
        return getProviders(entity, this.NBTEntityProviders);
    }

    public ArrayList<IWailaFMPProvider> getHeadFMPProviders(String name)
    {
        return getProviders(name, this.headFMPProviders);
    }

    public ArrayList<IWailaFMPProvider> getBodyFMPProviders(String name)
    {
        return getProviders(name, this.bodyFMPProviders);
    }

    public ArrayList<IWailaFMPProvider> getTailFMPProviders(String name)
    {
        return getProviders(name, this.tailFMPProviders);
    }

    public ArrayList<IWailaSummaryProvider> getSummaryProvider(Object item)
    {
        return getProviders(item, this.summaryProviders);
    }

    public ArrayList<IWailaBlockDecorator> getBlockDecorators(Object block)
    {
        return getProviders(block, this.blockClassDecorators);
    }

    public ArrayList<IWailaFMPDecorator> getFMPDecorators(String name)
    {
        return getProviders(name, this.FMPClassDecorators);
    }

    public IWailaTooltipRenderer getTooltipRenderer(String name)
    {
        return (IWailaTooltipRenderer)this.tooltipRenderers.get(name);
    }

    private <T> ArrayList<T> getProviders(Object obj, LinkedHashMap<Class, ArrayList<T>> target)
    {
        ArrayList<T> returnList = new ArrayList();
        for (Class clazz : target.keySet()) {
            if (clazz.isInstance(obj)) {
                returnList.addAll((Collection)target.get(clazz));
            }
        }
        return returnList;
    }

    private <T> ArrayList<T> getProviders(String name, LinkedHashMap<String, ArrayList<T>> target)
    {
        return (ArrayList)target.get(name);
    }

    @Deprecated
    public HashSet<String> getSyncedNBTKeys(Object target)
    {
        HashSet<String> returnList = new HashSet();
        for (Class clazz : this.syncedNBTKeys.keySet()) {
            if (clazz.isInstance(target)) {
                returnList.addAll((Collection)this.syncedNBTKeys.get(clazz));
            }
        }
        return returnList;
    }

    public boolean hasStackProviders(Object block)
    {
        return hasProviders(block, this.stackBlockProviders);
    }

    public boolean hasHeadProviders(Object block)
    {
        return hasProviders(block, this.headBlockProviders);
    }

    public boolean hasBodyProviders(Object block)
    {
        return hasProviders(block, this.bodyBlockProviders);
    }

    public boolean hasTailProviders(Object block)
    {
        return hasProviders(block, this.tailBlockProviders);
    }

    public boolean hasNBTProviders(Object block)
    {
        return hasProviders(block, this.NBTDataProviders);
    }

    public boolean hasHeadEntityProviders(Object entity)
    {
        return hasProviders(entity, this.headEntityProviders);
    }

    public boolean hasBodyEntityProviders(Object entity)
    {
        return hasProviders(entity, this.bodyEntityProviders);
    }

    public boolean hasTailEntityProviders(Object entity)
    {
        return hasProviders(entity, this.tailEntityProviders);
    }

    public boolean hasOverrideEntityProviders(Object entity)
    {
        return hasProviders(entity, this.overrideEntityProviders);
    }

    public boolean hasNBTEntityProviders(Object entity)
    {
        return hasProviders(entity, this.NBTEntityProviders);
    }

    public boolean hasHeadFMPProviders(String name)
    {
        return hasProviders(name, this.headFMPProviders);
    }

    public boolean hasBodyFMPProviders(String name)
    {
        return hasProviders(name, this.bodyFMPProviders);
    }

    public boolean hasTailFMPProviders(String name)
    {
        return hasProviders(name, this.tailFMPProviders);
    }

    public boolean hasBlockDecorator(Object block)
    {
        return hasProviders(block, this.blockClassDecorators);
    }

    public boolean hasFMPDecorator(String name)
    {
        return hasProviders(name, this.FMPClassDecorators);
    }

    private <T> boolean hasProviders(Object obj, LinkedHashMap<Class, ArrayList<T>> target)
    {
        for (Class clazz : target.keySet()) {
            if (clazz.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    private <T> boolean hasProviders(String name, LinkedHashMap<String, ArrayList<T>> target)
    {
        return target.containsKey(name);
    }

    public boolean hasSummaryProvider(Class item)
    {
        return this.summaryProviders.containsKey(item);
    }

    @Deprecated
    public boolean hasSyncedNBTKeys(Object target)
    {
        for (Class clazz : this.syncedNBTKeys.keySet()) {
            if (clazz.isInstance(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDocTextModID(String modid)
    {
        return this.wikiDescriptions.containsKey(modid);
    }

    public boolean hasDocTextItem(String modid, String item)
    {
        if (hasDocTextModID(modid)) {
            return ((LinkedHashMap)this.wikiDescriptions.get(modid)).containsKey(item);
        }
        return false;
    }

    public boolean hasDocTextMeta(String modid, String item, String meta)
    {
        if (hasDocTextItem(modid, item)) {
            return ((LinkedHashMap)((LinkedHashMap)this.wikiDescriptions.get(modid)).get(item)).containsKey(meta);
        }
        return false;
    }

    public LinkedHashMap<String, String> getDocText(String modid, String name)
    {
        return (LinkedHashMap)((LinkedHashMap)this.wikiDescriptions.get(modid)).get(name);
    }

    public String getDocText(String modid, String name, String meta)
    {
        return (String)((LinkedHashMap)((LinkedHashMap)this.wikiDescriptions.get(modid)).get(name)).get(meta);
    }

    public boolean hasDocTextSpecificMeta(String modid, String name, String meta)
    {
        for (String s : getDocText(modid, name).keySet()) {
            if (s.equals(meta)) {
                return true;
            }
        }
        return false;
    }

    public String getDoxTextWildcardMatch(String modid, String name)
    {
        Set<String> keys = ((LinkedHashMap)this.wikiDescriptions.get(modid)).keySet();
        for (String s : keys)
        {
            String regexed = s;
            regexed = regexed.replace(".", "\\.");
            regexed = regexed.replace("*", ".*");
            if (name.matches(s)) {
                return s;
            }
        }
        return null;
    }

    private List<String[]> readFileAsString(String filePath)
            throws IOException
    {
        InputStream in = getClass().getResourceAsStream(filePath);
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        CSVReader reader = new CSVReader(input);

        List<String[]> myEntries = reader.readAll();
        reader.close();

        return myEntries;
    }

}
