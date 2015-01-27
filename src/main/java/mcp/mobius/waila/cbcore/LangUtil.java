package mcp.mobius.waila.cbcore;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.Language;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class LangUtil {

    public static LangUtil instance = new LangUtil(null);
    public String prefix;

    public LangUtil(String prefix)
    {
        this.prefix = prefix;
    }

    public static String translateG(String s, Object... format)
    {
        return instance.translate(s, format);
    }

    public String translate(String s, Object... format)
    {
        if ((this.prefix != null) && (!s.startsWith(this.prefix + "."))) {
            s = this.prefix + "." + s;
        }
        String ret = LanguageRegistry.instance().getStringLocalization(s);
        if (ret.length() == 0) {
            ret = LanguageRegistry.instance().getStringLocalization(s, "en_US");
        }
        if (ret.length() == 0) {
            ret = StatCollector.func_74838_a(s);
        }
        if (ret.length() == 0) {
            return s;
        }
        if (format.length > 0) {
            ret = String.format(ret, format);
        }
        return ret;
    }

    public void addLangFile(InputStream resource, String lang)
            throws IOException
    {
        LanguageRegistry reg = LanguageRegistry.instance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
        for (;;)
        {
            String read = reader.readLine();
            if (read == null) {
                break;
            }
            int equalIndex = read.indexOf('=');
            if (equalIndex != -1)
            {
                String key = read.substring(0, equalIndex);
                String value = read.substring(equalIndex + 1);
                if (this.prefix != null) {
                    key = this.prefix + "." + key;
                }
                reg.addStringLocalization(key, lang, value);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static LangUtil loadLangDir(String domain)
    {
        return new LangUtil(domain).addLangDir(new ResourceLocation(domain, "lang"));
    }

    @SideOnly(Side.CLIENT)
    public LangUtil addLangDir(ResourceLocation dir)
    {
        IResourceManager resManager = Minecraft.func_71410_x().func_110442_L();
        for (Language lang : Minecraft.func_71410_x().func_135016_M().func_135040_d())
        {
            String langID = lang.func_135034_a();
            IResource langRes;
            try
            {
                langRes = resManager.func_110536_a(new ResourceLocation(dir.func_110624_b(), dir.func_110623_a() + '/' + langID + ".lang"));
            }
            catch (Exception e) {}
            continue;
            try
            {
                addLangFile(langRes.func_110527_b(), langID);
            }
            catch (IOException e)
            {
                System.err.println("Failed to load lang resource. domain=" + this.prefix + ", resource=" + langRes);
                e.printStackTrace();
            }
        }
        return this;
    }

}
