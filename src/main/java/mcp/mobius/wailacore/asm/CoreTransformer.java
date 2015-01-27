package mcp.mobius.wailacore.asm;

import net.minecraft.launchwrapper.IClassTransformer;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class CoreTransformer implements IClassTransformer {

    public byte[] transform(String name, String srgname, byte[] bytes)
    {
        return bytes;
    }

}
