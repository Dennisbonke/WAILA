package mcp.mobius.waila.gui.truetyper;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class FontLoader {

    public static TrueTypeFont loadSystemFont(String name, float defSize, boolean antialias)
    {
        return loadSystemFont(name, defSize, antialias, 0);
    }

    public static TrueTypeFont loadSystemFont(String name, float defSize, boolean antialias, int type)
    {
        TrueTypeFont out = null;
        try
        {
            Font font = new Font(name, type, (int)defSize);
            font = font.deriveFont(defSize);
            out = new TrueTypeFont(font, antialias);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return out;
    }

    public static TrueTypeFont createFont(ResourceLocation res, float defSize, boolean antialias)
    {
        return createFont(res, defSize, antialias, 0);
    }

    public static TrueTypeFont createFont(ResourceLocation res, float defSize, boolean antialias, int type)
    {
        TrueTypeFont out = null;
        try
        {
            Font font = Font.createFont(type, Minecraft.func_71410_x().func_110442_L().func_110536_a(res).func_110527_b());
            font = font.deriveFont(defSize);
            out = new TrueTypeFont(font, antialias);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return out;
    }

}
