package mcp.mobius.waila.api;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public abstract interface IWailaConfigHandler {

    public abstract Set<String> getModuleNames();

    public abstract HashMap<String, String> getConfigKeys(String paramString);

    public abstract boolean getConfig(String paramString, boolean paramBoolean);

    public abstract boolean getConfig(String paramString);

}
