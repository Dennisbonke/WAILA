package mcp.mobius.waila.api;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaRegistrar {

    public abstract void addConfig(String paramString1, String paramString2, String paramString3);

    public abstract void addConfig(String paramString1, String paramString2, String paramString3, boolean paramBoolean);

    public abstract void addConfigRemote(String paramString1, String paramString2, String paramString3);

    public abstract void addConfigRemote(String paramString1, String paramString2, String paramString3, boolean paramBoolean);

    public abstract void addConfig(String paramString1, String paramString2);

    public abstract void addConfig(String paramString1, String paramString2, boolean paramBoolean);

    public abstract void addConfigRemote(String paramString1, String paramString2);

    public abstract void addConfigRemote(String paramString1, String paramString2, boolean paramBoolean);

    public abstract void registerStackProvider(IWailaDataProvider paramIWailaDataProvider, Class paramClass);

    public abstract void registerHeadProvider(IWailaDataProvider paramIWailaDataProvider, Class paramClass);

    public abstract void registerBodyProvider(IWailaDataProvider paramIWailaDataProvider, Class paramClass);

    public abstract void registerTailProvider(IWailaDataProvider paramIWailaDataProvider, Class paramClass);

    public abstract void registerNBTProvider(IWailaDataProvider paramIWailaDataProvider, Class paramClass);

    public abstract void registerHeadProvider(IWailaEntityProvider paramIWailaEntityProvider, Class paramClass);

    public abstract void registerBodyProvider(IWailaEntityProvider paramIWailaEntityProvider, Class paramClass);

    public abstract void registerTailProvider(IWailaEntityProvider paramIWailaEntityProvider, Class paramClass);

    public abstract void registerOverrideEntityProvider(IWailaEntityProvider paramIWailaEntityProvider, Class paramClass);

    public abstract void registerNBTProvider(IWailaEntityProvider paramIWailaEntityProvider, Class paramClass);

    public abstract void registerHeadProvider(IWailaFMPProvider paramIWailaFMPProvider, String paramString);

    public abstract void registerBodyProvider(IWailaFMPProvider paramIWailaFMPProvider, String paramString);

    public abstract void registerTailProvider(IWailaFMPProvider paramIWailaFMPProvider, String paramString);

    public abstract void registerDecorator(IWailaBlockDecorator paramIWailaBlockDecorator, Class paramClass);

    public abstract void registerDecorator(IWailaFMPDecorator paramIWailaFMPDecorator, String paramString);

    @Deprecated
    public abstract void registerSyncedNBTKey(String paramString, Class paramClass);

    public abstract void registerTooltipRenderer(String paramString, IWailaTooltipRenderer paramIWailaTooltipRenderer);

}
