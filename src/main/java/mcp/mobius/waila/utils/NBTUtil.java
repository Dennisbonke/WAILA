package mcp.mobius.waila.utils;

import net.minecraft.nbt.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Dennisbonke on 27-1-2015.
 */
public class NBTUtil {

    public static NBTBase getTag(String key, NBTTagCompound tag)
    {
        String[] path = key.split("\\.");

        NBTTagCompound deepTag = tag;
        for (String i : path) {
            if (deepTag.func_74764_b(i))
            {
                if ((deepTag.func_74781_a(i) instanceof NBTTagCompound)) {
                    deepTag = deepTag.func_74775_l(i);
                } else {
                    return deepTag.func_74781_a(i);
                }
            }
            else {
                return null;
            }
        }
        return deepTag;
    }

    public static NBTTagCompound setTag(String key, NBTTagCompound targetTag, NBTBase addedTag)
    {
        String[] path = key.split("\\.");

        NBTTagCompound deepTag = targetTag;
        for (int i = 0; i < path.length - 1; i++)
        {
            if (!deepTag.func_74764_b(path[i])) {
                deepTag.func_74782_a(path[i], new NBTTagCompound());
            }
            deepTag = deepTag.func_74775_l(path[i]);
        }
        deepTag.func_74782_a(path[(path.length - 1)], addedTag);

        return targetTag;
    }

    public static NBTTagCompound createTag(NBTTagCompound inTag, HashSet<String> keys)
    {
        if (keys.contains("*")) {
            return inTag;
        }
        NBTTagCompound outTag = new NBTTagCompound();
        for (String key : keys)
        {
            NBTBase tagToAdd = getTag(key, inTag);
            if (tagToAdd != null) {
                outTag = setTag(key, outTag, tagToAdd);
            }
        }
        return outTag;
    }

    public static void writeNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutputStream par1DataOutputStream)
            throws IOException
    {
        if (par0NBTTagCompound == null)
        {
            par1DataOutputStream.writeShort(-1);
        }
        else
        {
            byte[] abyte = CompressedStreamTools.func_74798_a(par0NBTTagCompound);
            if (abyte.length > 32000)
            {
                par1DataOutputStream.writeShort(-1);
            }
            else
            {
                par1DataOutputStream.writeShort((short)abyte.length);
                par1DataOutputStream.write(abyte);
            }
        }
    }

    public static NBTTagCompound readNBTTagCompound(DataInputStream par0DataInputStream)
            throws IOException
    {
        short short1 = par0DataInputStream.readShort();
        if (short1 < 0) {
            return null;
        }
        byte[] abyte = new byte[short1];
        par0DataInputStream.readFully(abyte);
        return CompressedStreamTools.func_152457_a(abyte, NBTSizeTracker.field_152451_a);
    }

    public static int getNBTInteger(NBTTagCompound tag, String keyname)
    {
        NBTBase subtag = tag.func_74781_a(keyname);
        if ((subtag instanceof NBTTagInt)) {
            return tag.func_74762_e(keyname);
        }
        if ((subtag instanceof NBTTagShort)) {
            return tag.func_74765_d(keyname);
        }
        if ((subtag instanceof NBTTagByte)) {
            return tag.func_74771_c(keyname);
        }
        return 0;
    }

}
