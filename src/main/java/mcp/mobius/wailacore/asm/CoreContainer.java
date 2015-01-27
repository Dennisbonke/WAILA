package mcp.mobius.wailacore.asm;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class CoreContainer extends DummyModContainer {

    public CoreContainer()
    {
        super(new ModMetadata());

        ModMetadata md = getMetadata();
        md.modId = "WailaCore";
        md.name = "WailaCore";
        md.version = "1.0.0";
        md.credits = "ProfMobius";
        md.authorList = Arrays.asList(new String[]{"ProfMobius"});
        md.description = "Coremod associated with Waila to force backward compatibility with the API";
        md.url = "profmobius.blogspot.com";
    }

    public boolean registerBus(EventBus bus, LoadController controller)
    {
        return true;
    }

}
