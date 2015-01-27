package mcp.mobius.wailacore.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Map;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
@IFMLLoadingPlugin.TransformerExclusions({"mcp.mobius.wailacore.asm"})
public class CoreDescription implements IFMLLoadingPlugin {

    public static final Logger log = LogManager.getLogger("WailaCore");
    public static File location;

    public String[] getASMTransformerClass()
    {
        return new String[] { "mcp.mobius.wailacore.asm.CoreTransformer" };
    }

    public String getModContainerClass()
    {
        return "mcp.mobius.wailacore.asm.CoreContainer";
    }

    public String getSetupClass()
    {
        return null;
    }

    public void injectData(Map<String, Object> data)
    {
        if (data.containsKey("coremodLocation")) {
            location = (File)data.get("coremodLocation");
        }
    }

    public String getAccessTransformerClass()
    {
        return "mcp.mobius.wailacore.asm.CoreAccessTransformer";
    }

}
